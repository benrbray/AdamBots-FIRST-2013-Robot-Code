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
public abstract class RobotCamera {

	private static final double TARGET_WIDTH_INCHES = 62;
	private static final double TARGET_HEIGHT_INCHES = 20;
	private static final double TARGET_ELEVATION_INCHES_TO_BOTTOM = 100;
	private static final double VIEW_ANGLE_DEGREES_HORIZONTAL = 50;
	private static final double VIEW_ANGLE_PIXELS_HORIZONTAL = 320;
	private static final double VIEW_ANGLE_DEGREES_VERTICAL = 38;//CHECK
	private static final double VIEW_ANGLE_PIXELS_VERTICAL = 240;
	private static final double VIEW_HEIGHT_OVER_WIDTH = 0.75;
	private static final double CAMERA_ANGLE_CENTER_ELEVATION_DEGREES = 20;//CHECK
	private static final double CAMERA_HEIGHT_INCHES = 12;//CHECK
	/**
	 * The camera instance used in tracking.
	 */
	private static AxisCamera _camera;
	/**
	 * The current captured imaged.
	 */
	private static ColorImage _srcImage = null;
	/**
	 * The current identified green target.
	 */
	private static Target _greenTarget;
	/**
	 * Distance in FEET to target based on most recent exposure.
	 **/
	private static double _recentDistanceInches = 0;
	/**
	 * In DEGREES, direction (negative left?) toward target based on most recent exposure.
	 **/
	private static double _recentThetaDegrees = 0; //Radians
	/**
	 * The reference to the CameraThread object which calls work().
	 */
	private static Thread _cameraThread = null;
	/**
	 * Whether the current image is fresh; employed by TargetLogic.
	 */
	private static boolean _freshImage = false;

	/**
	 A class essentially equivalent to ParticleAnalysisReport sans some data.
	 **/
	/* Test different lighting
	 * Test 1 light v 2 lights (brightness)
	 * (interfering light?)
	 */
	static public class Target {

		Target( int nx, int ny, int nw, int nh ) {
			x = nx;
			y = ny;
			w = nw;
			h = nh;
			x2 = x + w;
			y2 = y + h;
		}
		/**
		 * The left edge's x position.
		 */
		public int x;
		/**
		 * The top edge's y position.
		 */
		public int y;
		/**
		 * The width of the bounding box.
		 */
		public int w;
		/**
		 * The height of the bounding box.
		 */
		public int h;
		/**
		 * The right edge's x position.
		 */
		public int x2;
		/**
		 * The bottom edge's y position.
		 */
		public int y2;
	}

	/**
	 * Periodic update function which ensures CameraThread is running.
	 */
	public static void update() {
		if ( _cameraThread == null ) {
			_cameraThread = new Thread(new CameraThread());
		}
		if ( !_cameraThread.isAlive() ) {
			_cameraThread.start();
		}
	}

	/**
	 * Returns the distance in inches to the target according to the most recent available exposure.
	 **/
	public static double getDistanceInches() {
		return _recentDistanceInches;
	}

	/**
	 Returns the direction (in radians) to the target according to the most recent available exposure.
	 Negative is left of center, positive is right of center. 0 is on target.
	 **/
	public static double getDirectionDegrees() {
		return _recentThetaDegrees;
	}

	/**
	 * Internal image-processing which isolates the green board.
	 */
	private static void greenBox() {
		MonoImage saturationHSVOriginal = null;
		MonoImage hueHSVOriginal = null;
		MonoImage valueOriginal = null;

		BinaryImage result = null;
		try {
			saturationHSVOriginal = _srcImage.getHSVSaturationPlane();
			valueOriginal = _srcImage.getValuePlane();
			hueHSVOriginal = _srcImage.getHSVHuePlane();

			_srcImage.replaceRedPlane(hueHSVOriginal);
			_srcImage.replaceGreenPlane(saturationHSVOriginal);
			_srcImage.replaceBluePlane(valueOriginal);

			result = _srcImage.thresholdRGB(110, 140, 200, 256, 225, 256);

			ParticleAnalysisReport[] greens = result.getOrderedParticleAnalysisReports();
			ParticleAnalysisReport board = null;
			for (int i = 0; i < greens.length; i++) {
				if ( (board == null || board.particleArea < greens[i].particleArea) && greens[i].particleArea < greens[i].boundingRectWidth * greens[i].boundingRectHeight * 0.55 ) {
					board = greens[i];
				}
			}
			double size = board.particleArea;
			ParticleAnalysisReport q = null;
			for (int i = 0; i < greens.length; i++) {
				if (greens[i].particleArea> size * 0.5)
				{
					System.out.println("Candidate: " + greens[i].boundingRectLeft + "," + greens[i].boundingRectTop);
				}
				if ( (greens[i].particleArea > size * 0.5 && greens[i].particleArea < greens[i].boundingRectWidth * greens[i].boundingRectHeight * 0.55) && (q == null || q.center_mass_y > greens[i].center_mass_y) ) {
					q = greens[i];
				}
			}
			board = q;
			_greenTarget = new Target(board.boundingRectLeft, board.boundingRectTop, board.boundingRectWidth, board.boundingRectHeight);
			System.out.println("Target location:" + _greenTarget.x + " ," + _greenTarget.y + " |w,h,a " + _greenTarget.w + "|" + _greenTarget.h + " ," + board.particleArea);
		}
		catch (NIVisionException e) {
		}
		finally {
			try {
				free(valueOriginal);
				free(saturationHSVOriginal);
				free(hueHSVOriginal);
				free(result);
			}
			catch (NIVisionException e) {
			}
		}
	}

	/**
	 Initializes AxisCamera instance and sets camera parameters. Should be called once, at robot initialization.
	 **/
	public static void init() {
		_camera = AxisCamera.getInstance("10.2.45.11"); // get an instance ofthe camera
		//_camera = AxisCamera.getInstance("192.168.0.90");
		_camera.writeMaxFPS(10);
		_camera.writeExposurePriority(AxisCamera.ExposurePriorityT.frameRate);
		_camera.writeResolution(AxisCamera.ResolutionT.k320x240);
		_camera.writeCompression(10);
	}

	/**
	 * Tells whether the current is fresh
	 * @return Freshness of image (true for "is fresh")
	 */
	public static boolean imageIsFresh() {
		return _freshImage;
	}

	/**
	 * Alerts RobotCamera that current image is no longer fresh; is called immediately after collecting image data.
	 */
	public static void imageUnfresh() {
		_freshImage = false;
	}


	
	public static void calculateAngle()
	{
		_recentThetaDegrees = (double) (_greenTarget.x + _greenTarget.w / 2 - VIEW_ANGLE_PIXELS_HORIZONTAL / 2.0) * (VIEW_ANGLE_DEGREES_HORIZONTAL) / (VIEW_ANGLE_PIXELS_HORIZONTAL);
	}
	
	public static void calculateDistance()
	{
		_recentDistanceInches = 14874.0 / ((_greenTarget.w + _greenTarget.h) / 2.0);
	}
	public static void calculateDistanceAlternate()
	{
		double m = (_greenTarget.w + _greenTarget.h / 2) / VIEW_ANGLE_PIXELS_VERTICAL * VIEW_ANGLE_DEGREES_VERTICAL;
		double a = CAMERA_ANGLE_CENTER_ELEVATION_DEGREES + VIEW_ANGLE_DEGREES_VERTICAL / 2;
		double h = TARGET_HEIGHT_INCHES / 2 + TARGET_ELEVATION_INCHES_TO_BOTTOM;
		_recentDistanceInches = h / Math.tan(Math.PI / 180 * (a-m));
	}
	
	/**
	 * Performs "work" on the image, excluding "greenbox." Called by CameraThread only.
	 1. Grabs source image and saves it.
	 2. Calls greenbox.
	 3. Free all objects
	 4. Calculates distance / angle.
	 **/
	public static void work() {
		_srcImage = null;
		try
		{
			_srcImage = _camera.getImage();
			_srcImage.write("/raw.png");
			greenBox();
			calculateDistance();
			calculateAngle();
			_freshImage = true;
		}
		catch (Exception e) {
		}
		finally {
			try {
				free(_srcImage);
			}
			catch (NIVisionException e) {
			}
		}
	}

	/**
	 * Free functions avoid freeing images which are `null`.
	 * @param x The ColorImage/BinaryImage/MonoImage to free.
	 * @throws NIVisionException 
	 */
	private static void free( ColorImage x ) throws NIVisionException {
		if ( x != null ) {
			x.free();
		}
	}

	/**
	 * Free functions avoid freeing images which are `null`.
	 * @param x The ColorImage/BinaryImage/MonoImage to free.
	 * @throws NIVisionException 
	 */
	private static void free( BinaryImage x ) throws NIVisionException {
		if ( x != null ) {
			x.free();
		}
	}

	/**
	 * Free functions avoid freeing images which are `null`.
	 * @param x The ColorImage/BinaryImage/MonoImage to free.
	 * @throws NIVisionException 
	 */
	private static void free( MonoImage x ) throws NIVisionException {
		if ( x != null ) {
			x.free();
		}
	}
}
