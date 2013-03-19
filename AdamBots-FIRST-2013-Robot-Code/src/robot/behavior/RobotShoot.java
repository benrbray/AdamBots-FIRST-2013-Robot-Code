/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.behavior;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotMain;
import robot.actuators.RobotActuators;
import robot.camera.RobotCamera;
import robot.control.MagicBox;
import robot.logic.FancyPIDController;
import robot.sensors.RobotSensors;

/**
 * RobotShoot sets the speed and angle of the shooter; update() must be called
 * periodically.
 *
 * @author Nathan
 */
public abstract class RobotShoot extends RobotBehavior {

	public static double SHOOTER_KI = 0.0001;									//// WAS 0.001 as well as 0.0015 changed for different shooter
	public static double SHOOTER_KP = 0.001;									//// WAS 0.002 as well as 0.002 changed for different shooter
	public static double SHOOTER_KD = 0.0005;									//// WAS 0.000
	public static double SHOOTER_PID_TOLERANCE = 0.15;
	public static double SHOOTER_MAX_INPUT = 10000;
	public static double SHOOTER_MIN_INPUT = 0;
	public static double SHOOTER_MAX_OUTPUT = 1.0;
	public static double SHOOTER_MIN_OUTPUT = 0.15;
	/**
	 * The degrees of tolerance permitted in setting the target angle.
	 */
	public static final double SHOOTER_ANGLE_TOLERANCE = 0.25;
	/**
	 * The angle that the shooter is currently moving towards.
	 */
	private static double _targetAngleDegrees;
	/**
	 * The PID interface used to control the shooter.
	 */
	private static FancyPIDController _shooterPID;
	/**
	 * Turns on and off the ability to set target angle.
	 */
	private static boolean _moveToTarget = false;

	/**
	 * init() creates the static private _shooterPid() for controlling the shooter wheel.
	 */
	public static void init() {
		_shooterPID = new FancyPIDController(SHOOTER_KP, SHOOTER_KI, SHOOTER_KD,
				RobotSensors.counterShooterSpeed, RobotActuators.shooterWheelMotor);
		_shooterPID.setInputRange(SHOOTER_MIN_INPUT, SHOOTER_MAX_INPUT);
		_shooterPID.setOutputRange(SHOOTER_MIN_OUTPUT, SHOOTER_MAX_OUTPUT);
		_shooterPID.setPercentTolerance(SHOOTER_PID_TOLERANCE);
		_shooterPID.enable();
	}

	/**
	 * Calculates and returns the shooter angle from the string potentiometer.
	 * @return angle of elevation (above horizontal).
	 */
	public static double getShooterAngleDegrees() {
		double x = (RobotSensors.stringPot.getAverageVoltage() - 4.88) / -0.8156; // Length of string in inches
		double y = 12.0 + 3 / 8.0; // Length of triangle side #1
		double z = 12.0 + 1 / 2.0; // Length of triangle side #2
		return com.sun.squawk.util.MathUtils.acos(Math.min(1, Math.max(0, (x * x - y * y - z * z) / (-2.0 * y * z)))) * 180.0 / Math.PI + 19;//TODO: Use potentiometer calculations
	}

	/**
	 * Calculates the ideal angle, in degrees as calculated from the RobotCamera's distance value.
	 * @return Degree automatically calculated angle for best shooting.
	 */
	public static double getIdealShooterAngle() {
		double x = RobotCamera.getTargetLocationUnits();
		double a = .0002195378;//Constants determined from quadratic best fit.
		double b = -0.1315442966;
		double c = 37.98049416;
		return a * x * x + b * x + c;
	}

	/**
	 * Sets the target angle of elevation to go for after  is true.
	 * @param angle The target angle of elevation.
	 */
	public static void setTargetAngleDegrees( double angle ) {
		_targetAngleDegrees = angle;
	}

	/**
	 * Tells whether the shooter's angle is close to the target angle.
	 */
	public static boolean isShooterInPosition() {
		return Math.abs(getShooterAngleDegrees() - _targetAngleDegrees) < SHOOTER_ANGLE_TOLERANCE;
	}

	/**
	 * Returns whether or not the PID on the shooter motor reports it's at speed.
	 * @return Whether or not the PID on the shooter motor reports it's at speed.
	 */
	public static boolean isShooterUpToSpeed() {
		return _shooterPID.isAtSpeed();
	}

	/**
	 * Sets the speed of the shooter wheel motor with the PID.
	 * @param speed_rpm The speed of the shooter in rpm.
	 */
	public static void setSpeed( double speed_rpm ) {
		_shooterPID.setRPM(speed_rpm);
	}

	/**
	 * Disables pid.
	 */
	public static void stopPID() {
		if ( _shooterPID.isEnable() ) {
			_shooterPID.disable();
		}
	}

	/**
	 * Starts pid.
	 */
	public static void startPID() {
		if ( !_shooterPID.isEnable() ) {
			_shooterPID.enable();
		}
	}

	/**
	 * Provides new pid constants for the pid.
	 */
	public static void updatePIDConstants() {
		_shooterPID.setPID(SHOOTER_KP, SHOOTER_KI, SHOOTER_KD);
	}

	/**
	 * Enables control over the target angle.
	 */
	public static void startMovingToTarget() {
		_moveToTarget = true;
	}

	/**
	 * Disables control over the target angle.
	 */
	public static void stopMovingToTarget() {
		_moveToTarget = false;
	}

	/**
	 * Adjusts shooter angle if .startMovingToTarget() has been called or Magic Box Siwtch #7 is on.
	 */
	public static void update() {
		if (!RobotMain.getInstance().isAutonomous())
		{
			SmartDashboard.putString("Shooter Angle Offset", (getShooterAngleDegrees() - _targetAngleDegrees)+"/"+SHOOTER_ANGLE_TOLERANCE );
			SmartDashboard.putBoolean("Shooter In Position",isShooterInPosition());
		}
		SmartDashboard.putBoolean("DO TARGET", false);
		if ( _moveToTarget || (!RobotMain.getInstance().isAutonomous() && MagicBox.getDigitalIn(7)) ) {
			SmartDashboard.putBoolean("DO TARGET", true);
			if ( !RobotMain.getInstance().isAutonomous() && MagicBox.getDigitalIn(7) ) {
				_targetAngleDegrees = getIdealShooterAngle();
			}
			double target = Math.max(19, _targetAngleDegrees);
			if ( isShooterInPosition() ) {
				RobotActuators.shooterAngleMotor.set(0);
			}
			else {
				RobotActuators.shooterAngleMotor.set(-utils.MathUtils.sign(target - getShooterAngleDegrees()) / 40.0);
			}
		}
	}
}
