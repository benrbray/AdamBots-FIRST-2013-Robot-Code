/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic;

import com.sun.squawk.util.MathUtils;
import robot.behavior.RobotDrive;
import robot.behavior.RobotShoot;
import robot.camera.CameraProcessor;
import robot.sensors.RobotSensors;

/**
 * TargetLogic calls RobotShoot and RobotDrive and asks for data from RobotCamera to direct targeting; update() must be called periodically.
 * This class doesn't directly interface with any motors.
 * @author Nathan
 */
public abstract class TargetLogic {
	
	/**
	 * Whether it's currently targeting as opposed to doing nothing.
	 */
	private static boolean _isTargeting = false;
	/**
	 * Whether to turn; if it knows where to turn to.
	 */
	private static boolean _doTurn = false;
	/**
	 * The last angle that it needs to turn; compare to current angle.
	 */
	private static double _lastTargetAngleDegrees = 0;
	
	private static double _myFreshEncoderLeft = 0;
	private static double _myFreshEncoderRight = 0;
	
	private static final double ENCODER_TO_DEGREES = 1;
	
	/**
	 * The radius of the circle containing the 4 wheels of the robot.
	 */
	public static double robotRadiusInches = 5;
	
	public static boolean isTargeting()
	{
		return _isTargeting;
	}
	
	/**
	 * Initiates targeting; harmless to call if already targeting.
	 */
    public static void beginTargeting()
	{
		if (_isTargeting) {return;}//Exit early if already begun.
		RobotDrive.driveStraight(0);//Stop!
		_isTargeting = true;
		_doTurn = false;
		CameraProcessor.imageUnfresh();//To wait for new image.
	}
	
	
	/**
	 * Ends targeting.
	 */
	public static void endTargeting()
	{
		_doTurn = false;
		_isTargeting = false;
	}
	
	/**
	 * Call this function constantly.
	 */
	public static void update()
	{
		if (_isTargeting)
		{
			if (CameraProcessor.imageIsFresh())
			{
				_doTurn = true;
				//Can act on new data.
				CameraProcessor.imageUnfresh();
				_lastTargetAngleDegrees = CameraProcessor.getDirectionDegrees();
				_myFreshEncoderLeft = RobotSensors.encoderDriveLeft.getDistance();
				_myFreshEncoderRight = RobotSensors.encoderDriveRight.getDistance();
				/* Like this:
				 * The wheels plot a circle as they spin together.
				 * Convert arc length to interior angle.
				 * (Use average of left/right to improve accuracy)
				 */
			}
			if (_doTurn)
			{
				double leftEncoder = RobotSensors.encoderDriveLeft.getDistance();
				double leftAngle = ENCODER_TO_DEGREES * (leftEncoder - _myFreshEncoderLeft);
				double rightEncoder = RobotSensors.encoderDriveRight.getDistance();
				double rightAngle = -ENCODER_TO_DEGREES * (rightEncoder - _myFreshEncoderRight);//neg might not need?
				double angled = (leftAngle+rightAngle)/2;
				//Compare angled to _lastTargetAngleDegrees
				double s = _lastTargetAngleDegrees - angled;
				if (Math.abs(s) > 3)
				{
					RobotDrive.drive(s / 10,-s / 10); // Possibly backwards.
				}
				else
				{
					RobotDrive.driveStraight(0);
					//Try to shoot.
					RobotShoot.setAngleDegrees(180/Math.PI*MathUtils.atan(114/CameraProcessor.getDistanceInches()));
					RobotShoot.setSpeed(0.5);
				}
			}
		}
	}
}
