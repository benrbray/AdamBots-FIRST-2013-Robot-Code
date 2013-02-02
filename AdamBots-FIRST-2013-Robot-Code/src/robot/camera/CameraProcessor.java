/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.camera;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.*;

/**
 * This class is called ONLY by TargetLogic; it processes camera data.
 * @author Nathan Fenner et al
 */
/**
 * Directions for camera IP:
 * On bridge, camera IP is: 10.2.45.11
 * On cRIO port 2, IP is: 192.168.0.90
 * In computer, IP is either BUT local IP must be in the same block i.e. (10.2.45.* / 192.168.0.*)
 * User/pass is frc/frc or root/admin
 * Must configure camera IP to be the correct IP
 * When initializing camera instance, give it the correct IP.
 * 
 * This class must be initialized with initialize() and asked to work using work().
 * After work() is finished, you may request distance or direction to the visible best target.
 **/
public class CameraProcessor
{
	private static final double TARGET_WIDTH_INCHES = 62;
	private static final double TARGET_HEIGHT_INCHES = 20;//CHECK!?!
	private static final double VIEW_ANGLE_DEGREES = 50;
	private static final double VIEW_ANGLE_PIXELS = 320;//horizontal
	private static final double VIEW_HEIGHT_OVER_WIDTH = 0.75;
	private static AxisCamera _camera;//The Camera instance used in tracking.
	private static ColorImage _srcImage = null;//The current captured image.
	private static Target _greenTarget;//The green rectangle target
	/** Distance in FEET to target based on most recent exposure **/
	private static double _recentDistanceInches = 0;
	/** In DEGREES, direction (negative left?) toward target based on most recent exposure **/
	private static double _recentThetaDegrees = 0; //Radians
	private static Thread _cameraThread = null;
	private static double _targetEdgeWidth = 0;
	private static boolean _freshImage = false;

	/**
	 A class essentially equivalent to ParticleAnalysisReport sans some data.
	 **/
	/* Test different lighting
	 * Test 1 light v 2 lights (brightness)
	 * (interfering light?)
	 */
	static public class Target
	{
		Target( int nx, int ny, int nw, int nh, double nhprime )
		{
			x = nx;
			y = ny;
			w = nw;
			h = nh;
			x2 = x + w;
			y2 = y + h;
			hprime = nhprime;
		}
		public double hprime;
		public int x;
		public int y;
		public int w;
		public int h;
		public int x2;
		public int y2;
	}

	public static void loop()
	{
		if ( _cameraThread == null )
		{
			_cameraThread = new Thread(new CameraThread());
		}
		if ( !_cameraThread.isAlive() )
		{
			System.out.println("KICK!");
			_cameraThread.start();
		}
	}

	/**
	 Returns the distance in inches to the target according to the most recent available exposure.
	 **/
	public static double getDistanceInches()
	{
		return _recentDistanceInches;
	}

	/**
	 Returns the direction (in radians) to the target according to the most recent available exposure.
	 Negative is left of center, positive is right of center. 0 is on target.
	 **/
	public static double getDirectionDegrees()
	{

		return _recentThetaDegrees;
	}

	private static void greenBox()
	{
		MonoImage saturationHSVOriginal = null;
		MonoImage hueHSVOriginal = null;
		MonoImage valueOriginal = null;

		BinaryImage result = null;
		try
		{
			saturationHSVOriginal = _srcImage.getHSVSaturationPlane();
			valueOriginal = _srcImage.getValuePlane();
			hueHSVOriginal = _srcImage.getHSVHuePlane();
			saturationHSVOriginal = _srcImage.getHSVSaturationPlane();

			_srcImage.replaceRedPlane(hueHSVOriginal);
			_srcImage.replaceGreenPlane(saturationHSVOriginal);
			_srcImage.replaceBluePlane(valueOriginal);
			_srcImage.write("/swapped.png");

			result = _srcImage.thresholdRGB(110, 140, 230, 256, 250, 256);
			result.write("/green.png");

			ParticleAnalysisReport[] greens = result.getOrderedParticleAnalysisReports();
			ParticleAnalysisReport board = null;
			for (int i = 0; i < greens.length; i++)
			{
				if ( (board == null || board.particleArea < greens[i].particleArea) && greens[i].particleArea < greens[i].boundingRectWidth * greens[i].boundingRectHeight * 0.55 )
				{
					board = greens[i];
				}
			}
			double size = board.particleArea;
			ParticleAnalysisReport q = null;
			for (int i = 0; i < greens.length; i++)
			{
				if ( (greens[i].particleArea > size * 0.5 && greens[i].particleArea < greens[i].boundingRectWidth * greens[i].boundingRectHeight * 0.55) && (q == null || q.center_mass_y > greens[i].center_mass_y) )
				{
					q = greens[i];
				}
			}
			board = q;
			/*
			 A : particleAre
			 W : width = bounding width
			 T : edge width = k * W; k = 0.118
			 H : end height < bounding height

			 2*T*H + 2*T*W - 4*T*T = A
			 2*T*H = A - 2*T*W + 4*T*T
			 H = (A - 2*T*W + 4*T*T) / (2*T)
			 */
			double t = 0.00427 * board.boundingRectWidth; // Constant derived from example! Redo every once and a while
			double h = (board.particleArea - 2 * t * board.boundingRectWidth + 4 * t * t) / (2 * t);

			_greenTarget = new Target(board.boundingRectLeft, board.boundingRectTop, board.boundingRectWidth, board.boundingRectHeight, h);
		}
		catch (NIVisionException e)
		{
		}
		finally
		{
			try
			{
				free(valueOriginal);
				free(saturationHSVOriginal);
				free(hueHSVOriginal);
				free(result);
			}
			catch (NIVisionException e)
			{
				//System.out.println("Failure in colorBox.");
			}
		}
		//result.write("/blueident.png");

		/* Blue:
		 * Blue is largest component.
		 * Blue is at least some value.
		 * Second closest is k*blue at most.
		 */
		//Blue regions identified.
		//Use these to figure out where the rest is...
		//Ideally, the back.
	}

	/**
	 Initialized camera object and sets camera parameters. Should be called once, at robot initialization.
	 **/
	public static void initialize()
	{
		//how it will be on the robot ; _camera = AxisCamera.getInstance("10.2.45.11");  // get an instance ofthe camera
		_camera = AxisCamera.getInstance("192.168.0.90");
		_camera.writeMaxFPS(10);
		_camera.writeExposurePriority(AxisCamera.ExposurePriorityT.frameRate);
		_camera.writeResolution(AxisCamera.ResolutionT.k320x240);
		_camera.writeCompression(10);
	}
	
	/**
	 * Tells whether the current is fresh
	 * @return Freshness of image (true for "is fresh")
	 */
	public static boolean imageIsFresh()
	{
		return _freshImage;
	}
	
	/**
	 * Says current image is no longer fresh; call after collecting image data.
	 */
	public static void imageUnfresh()
	{
		_freshImage = false;
	}

	
	/**
	 1. Grabs source image.
	 2. Initiates blueBox() or redBox()
	 3. Free all objects
	 4. Initiates whiteBox()
	 5. Free all objects
	 6. Initiate postImageAnalysisAnalysis(), establishing a _whiteTarget object.
	 7. Write to distance, theta using _whiteTarget.
	 **/
	public static void work()
	{
		_srcImage = null;
		try // Lots of exceptions can happen
		{
			_srcImage = _camera.getImage();
			_srcImage.write("/raw.png");
			greenBox();//Depending on team color...
			//Need to determine how switch works.
			_recentDistanceInches = _greenTarget.hprime / 24 * 23.8;
			//_recentDistanceInches = (TARGET_HEIGHT_INCHES / 2) / Math.tan(_greenTarget.hprime * Math.toRadians(VIEW_ANGLE_DEGREES*VIEW_HEIGHT_OVER_WIDTH) / (VIEW_ANGLE_PIXELS*VIEW_HEIGHT_OVER_WIDTH) / 2.0);
			//_recentDistanceInches *= _greenTarget.h / 24;
			_recentThetaDegrees = (double) (_greenTarget.x + _greenTarget.w / 2 - VIEW_ANGLE_PIXELS / 2.0) * (VIEW_ANGLE_DEGREES) / (VIEW_ANGLE_PIXELS);
			_freshImage = true;
		}
		catch (Exception e)
		{
			//System.out.println("Exception in 'work()': " + e.getMessage() + "/" + e.toString() + "/" + e.getClass());
		}
		finally
		{
			try
			{
				free(_srcImage);
			}
			catch (NIVisionException e)
			{
				//System.out.println("Failure in 'analyze()'.");
			}
		}
	}

	private static void free( ColorImage x ) throws NIVisionException
	{
		if ( x != null )
		{
			x.free();
		}
	}

	private static void free( BinaryImage x ) throws NIVisionException
	{
		if ( x != null )
		{
			x.free();
		}
	}

	private static void free( MonoImage x ) throws NIVisionException
	{
		if ( x != null )
		{
			x.free();
		}
	}
}
