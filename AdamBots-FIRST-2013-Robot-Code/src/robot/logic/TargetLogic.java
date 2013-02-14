/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic;

import com.sun.squawk.util.MathUtils;
import robot.behavior.RobotShoot;
import robot.camera.RobotCamera;
import robot.logic.tasks.TTurnDegrees;

/**
 * TargetLogic calls RobotShoot and RobotDrive and asks for data from
 * RobotCamera to direct targeting; update() must be called periodically. This
 * class doesn't directly interface with any motors.
 *
 * @author Nathan
 */
public abstract class TargetLogic {

	private static TTurnDegrees turnLogic = null;
	public static final double TARGET_HEIGHT_INCHES = 60.0;
	/**
	 * Whether it's currently targeting as opposed to doing nothing.
	 */
	private static boolean _isTargeting = false;
	/**
	 * Whether to turn; if it knows where to turn to.
	 */
	private static boolean _doTurn = false;
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
		turnLogic.stop();
		turnLogic = null;
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
	private static double switchDouble( double a, double b ) {
		return a == 0 ? b : a;
	}

	public static void update() {
		System.out.println("DIS: " + RobotCamera.getDistanceInches());
		if ( _isTargeting ) {
			if ( RobotCamera.imageIsFresh() ) {
				_doTurn = true;
				//Can act on new data.
				RobotCamera.imageUnfresh();
				double direction = RobotCamera.getDirectionDegrees();
				if ( turnLogic != null ) {
					turnLogic.stop();
					turnLogic = null;
				}
				turnLogic = new TTurnDegrees(direction, 0.1, 2);
				turnLogic.initialize();
			}
			if ( _doTurn ) {
				if ( !_stopDriving && turnLogic != null ) {
					turnLogic.update();
					if ( turnLogic.finish() == LogicTask.SUCCESS ) {
						turnLogic.stop();
						turnLogic = null;
					}
				}
			}
			else {
				_currentTargetAngle = MathUtils.atan(TARGET_HEIGHT_INCHES / RobotCamera.getDistanceInches()) * Math.PI / 180.0;
				RobotShoot.setAngleDegrees(switchDouble(_shooterConstantAngle, _currentTargetAngle) + _shooterAngleOffset);
				_currentShooterSpeed = 0.6;
				RobotShoot.setSpeed(switchDouble(_shooterConstantSpeed, _currentShooterSpeed) * _shooterSpeedMultiplier);
			}
		}
	}
}
