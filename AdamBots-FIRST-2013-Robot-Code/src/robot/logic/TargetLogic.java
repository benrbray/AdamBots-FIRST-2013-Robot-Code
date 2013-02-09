/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic;

import com.sun.squawk.util.MathUtils;
import robot.behavior.RobotDrive;
import robot.behavior.RobotShoot;
import robot.camera.RobotCamera;
import robot.sensors.RobotSensors;

/**
 * TargetLogic calls RobotShoot and RobotDrive and asks for data from
 * RobotCamera to direct targeting; update() must be called periodically. This
 * class doesn't directly interface with any motors.
 *
 * @author Nathan
 */
public abstract class TargetLogic {

	public static final double TARGET_HEIGHT_INCHES = 60.0;
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
	/**
	 * The recorded value of left chassis encoder as of last image capture.
	 */
	private static double _myLastEncoderLeft = 0;
	/**
	 * The recorded value of right chassis encoder as of last image capture.
	 */
	private static double _myLastEncoderRight = 0;
	/**
	 * The ratio of ticks to degrees in degrees/tick.
	 */
	private static final double ENCODER_TO_DEGREES = 1;
	/**
	 * The radius of the circle containing the 4 wheels of the robot.
	 */
	public static double robotRadiusInches = 5;
	private static double _shooterSpeedMultiplier = 1;
	private static double _shooterAngleOffset = 0;
	private static double _shooterConstantSpeed = 0;
	private static double _shooterConstantAngle = 0;
	private static boolean _stopDriving = false;
	private static double _targetTurnSpeed = 0;
	private static double _currentTargetAngle = 0;
	private static double _currentShooterSpeed = 0;

	/**
	 * Sets the multiplier for the shooter when controlling.
	 * @param m The new multiplier value to be used in shooter speed determination.
	 */
	public static void setShooterSpeedMultiplier( double m ) {
		_shooterSpeedMultiplier = m;
	}

	/**
	 * The vertical angle offset used in targeting.
	 * @param m 
	 */
	public static void setShooterAngleOffset( double m ) {
		_shooterAngleOffset = m;
	}

	/**
	 * Sets the constant shooter speed which overrides the calculated speed. Set to 0 to return control to automated systems.
	 * @param m 
	 */
	public static void setShooterConstantSpeed( double m ) {
		_shooterConstantSpeed = m;
	}

	/**
	 * Sets a constant angle for the shooter [see setShooterConstantSpeed(double)].
	 * @param m 
	 */
	public static void setShooterConstantAngle( double m ) {
		_shooterConstantAngle = m;
	}

	/**
	 * Starts automatic control of the chassis for targeting.
	 */
	public static void startAutomaticDriving() {
		_stopDriving = false;
	}

	/**
	 * Stops automatic control of the chassis for targeting.
	 */
	public static void stopAutomaticDriving() {
		_stopDriving = true;
	}

	/**
	 * Initiates targeting.
	 */
	public static void beginTargeting() {
		if ( _isTargeting ) {
			return;
		}//Exit early if already begun.
		_isTargeting = true;
		RobotCamera.imageUnfresh();//To wait for new image.
	}

	/**
	 * Ends targeting.
	 */
	public static void endTargeting() {
		_doTurn = false;
	}

	/**
	 * Gets the current calculated ideal chassis turn speed for targeting. (in PWM)
	 * @return PWM ideal chassis turn speed.
	 */
	public static double getAutomatedDriveTurnSpeed() {
		return _targetTurnSpeed;
	}

	/**
	 * 
	 * @return The automatically calculated targeting angle.
	 */
	public static double getAutomatedTargetAngle() {
		return _currentTargetAngle;
	}

	/**
	 * 
	 * @return The automatically calculated shooter speed.
	 */
	public static double getShooterAutomatedSpeed() {
		return _currentShooterSpeed;
	}

	/**
	 * Call this function constantly.
	 */
	public static void update() {
		System.out.println("DIS: " + RobotCamera.getDistanceInches());
		if ( _isTargeting ) {
			if ( RobotCamera.imageIsFresh() ) {
				_doTurn = true;
				//Can act on new data.
				RobotCamera.imageUnfresh();
				_lastTargetAngleDegrees = RobotCamera.getDirectionDegrees();
				_myLastEncoderLeft = RobotSensors.encoderDriveLeft.getDistance();
				_myLastEncoderRight = RobotSensors.encoderDriveRight.getDistance();
				/* Like this:
				 * The wheels plot a circle as they spin together.
				 * Convert arc length to interior angle.
				 * (Use average of left/right to improve accuracy)
				 */
			}
			if ( _doTurn ) {
				double leftEncoder = RobotSensors.encoderDriveLeft.getDistance();
				double leftAngle = ENCODER_TO_DEGREES * (leftEncoder - _myLastEncoderLeft);
				double rightEncoder = RobotSensors.encoderDriveRight.getDistance();
				double rightAngle = -ENCODER_TO_DEGREES * (rightEncoder - _myLastEncoderRight);//neg might not need?
				double angled = (leftAngle + rightAngle) / 2;
				//Compare angled to _lastTargetAngleDegrees
				double s = angled - _lastTargetAngleDegrees;
				if ( Math.abs(s) > 3 ) {
					if ( !_stopDriving ) {
						_targetTurnSpeed = s / 10;
						RobotDrive.turn(_targetTurnSpeed); // Possibly backwards.
					}
				}
				else {
					_currentTargetAngle = MathUtils.atan(TARGET_HEIGHT_INCHES / RobotCamera.getDistanceInches());
					if ( _shooterConstantAngle == 0 ) {

						RobotShoot.setAngleDegrees((_currentTargetAngle + _shooterAngleOffset) * Math.PI / 180.0);
					}
					else {
						RobotShoot.setAngleDegrees(_shooterConstantAngle + _shooterAngleOffset);
					}
					_currentShooterSpeed = 0.6;
					if ( _shooterConstantSpeed == 0 ) {

						RobotShoot.setSpeed(_currentShooterSpeed * _shooterSpeedMultiplier);
					}
					else {
						RobotShoot.setSpeed(_shooterConstantSpeed * _shooterSpeedMultiplier);
					}
				}
			}
		}
	}
}
