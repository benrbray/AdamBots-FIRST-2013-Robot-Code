/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.behavior;

import robot.RobotMain;
import robot.actuators.RobotActuators;
import robot.camera.RobotCamera;
import robot.control.MagicBox;
import robot.logic.FancyPIDController;
import robot.logic.targeting.TargetShooterAngleLogic;
import robot.sensors.RobotSensors;
import utils.MathUtils;

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
	public static final double SHOOTER_ANGLE_TOLERANCE = 0.15;
	/**
	 * The angle that the shooter is currently moving towards.
	 */
	private static double _targetAngleDegrees;
	/**
	 * The PID interface used to control the shooter.
	 */
	private static FancyPIDController _shooterPID;

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
	public static double getShooterAngleDegrees()
	{
		double x = (RobotSensors.stringPot.getAverageVoltage()-4.88)/-0.8156; // Length of string in inches
		double y = 12.0 + 3/8.0; // Length of triangle side #1
		double z = 12.0 + 1/2.0; // Length of triangle side #2
		return com.sun.squawk.util.MathUtils.acos(Math.min(1,Math.max(0,(x*x-y*y-z*z)/(-2.0*y*z))))*180.0/Math.PI + 19;//TODO: Use potentiometer calculations
	}
	
	public static double getIdealShooterAngle()
	{
		double x = RobotCamera.getTargetLocationUnits();
		double a = .0002195378;
		double b = -0.1315442966;
		double c = 37.98049416;
		return a*x*x + b*x + c;
	}
	
	/**
	 * Sets the target angle of elevation to go for while TargetShooterAngleLogic.isTargeting() is true.
	 * @param angle The target angle of elevation.
	 */
	public static void setTargetAngleDegrees(double angle)
	{
		_targetAngleDegrees = angle;
	}
	
	/**
	 * Called periodically to control the shooterAngle motor.
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
	
	public static void stopPID() {
		if (_shooterPID.isEnable()) {
			_shooterPID.disable();
		}
	}
	
	public static void startPID() {
		if (!_shooterPID.isEnable()) {
			_shooterPID.enable();
		}
	}
	
	public static void updatePIDConstants() {
		_shooterPID.setPID(SHOOTER_KP, SHOOTER_KI, SHOOTER_KD);
	}
	
	public static void setTargetAngle(double ang)
	{
		_targetAngleDegrees = ang;
	}
	
	private static boolean _moveToTarget = false;
	
	public static void startMovingToTarget()
	{
		_moveToTarget = true;
	}
	public static void stopMovingToTarget()
	{
		_moveToTarget = false;
	}
	
	

	/**
	 * To be called constantly. Adjusts the angle of the shooter if TargetShooterAngleLogic.isTargeting().
	 * Tries to move shooter to match the value set by setShooterAngleDegrees(double).
	 */
	public static void update() {
		if (RobotMain.getInstance().isAutonomous())
		{
			if ( Math.abs(getShooterAngleDegrees() - _targetAngleDegrees) < SHOOTER_ANGLE_TOLERANCE ) {
				RobotActuators.shooterAngleMotor.set(0);
			}
			else {
				RobotActuators.shooterAngleMotor.set(-MathUtils.sign((_targetAngleDegrees - getShooterAngleDegrees()) / 40.0));
			}
		}
		else
		{
			if (MagicBox.getDigitalIn(7))
			{
				_targetAngleDegrees = getIdealShooterAngle();
				if ( Math.abs(getShooterAngleDegrees() - _targetAngleDegrees) < SHOOTER_ANGLE_TOLERANCE ) {
					RobotActuators.shooterAngleMotor.set(0);
				}
				else {
					RobotActuators.shooterAngleMotor.set(-MathUtils.sign((_targetAngleDegrees - getShooterAngleDegrees()) / 40.0));
				}
			}
		}
	}
}
