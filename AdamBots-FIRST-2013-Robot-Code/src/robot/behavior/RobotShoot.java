/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.behavior;

import robot.actuators.RobotActuators;
import robot.logic.PIDLogic;
import robot.sensors.RobotSensors;
import utils.MathUtils;

/**
 * RobotShoot sets the speed and angle of the shooter; update() must be called
 * periodically.
 *
 * @author Nathan
 */
public abstract class RobotShoot {

	/**
	 * The degrees of tolerance permitted in setting the target angle.
	 */
	public static final double SHOOTER_ANGLE_TOLERANCE = 1;
	/**
	 * The angle that the shooter is currently moving towards.
	 */
	private static double _targetAngleDegrees;
	/**
	 * The PID interface used to control the shooter.
	 */
	private static PIDLogic _shooterPid;

	public static double getTargetAngleDegrees() {
		return _targetAngleDegrees;
	}

	private static double convertFromEncoderToAngle( double enc ) {
		return enc;//TODO: actually write the method.
	}

	/**
	 * Called periodically to control the shooterAngle motor.
	 */
	public static boolean isShooterInPosition() {
		double d = convertFromEncoderToAngle(RobotSensors.encoderShooterAngle.getDistance());
		return Math.abs(d - _targetAngleDegrees) < SHOOTER_ANGLE_TOLERANCE;
	}

	/**
	 * init() creates the static private _shooterPid() for controlling the shooter wheel.
	 */
	public static void init() {
		_shooterPid = new PIDLogic(RobotActuators.shooterWheelMotor, RobotSensors.counterShooterSpeed, 0, 0, 0);
	}

	/**
	 * Assigns a target angle to the shooter. Sets private _targetAngle.
	 * @param angle The desired angled for the shooter from horizontal.
	 */
	public static void setAngleDegrees( double angle ) {
		//TODO: Fix pulse count...
		_targetAngleDegrees = angle;
	}

	/**
	 * Sets the speed of the shooter wheel motor with the PID.
	 * @param speed_rpm The speed of the shooter in rpm.
	 */
	public static void setSpeed( double speed_rpm ) {
		_shooterPid.setRPM(speed_rpm);
	}

	public static void changeTargetAngleDegrees( double delta ) {
		_targetAngleDegrees += delta;
	}
	
		public static void update() {
		double d = convertFromEncoderToAngle(RobotSensors.encoderShooterAngle.getDistance());
		/**
		 * TODO: Rewrite 'd' to be a proper angle. *
		 * TODO: Check encoder at limits.
		 */
		if ( Math.abs(d - _targetAngleDegrees) < SHOOTER_ANGLE_TOLERANCE ) {
			RobotActuators.shooterAngleMotor.set(0);
		}
		else {
			RobotActuators.shooterAngleMotor.set(MathUtils.sign((_targetAngleDegrees - d) / 10.0));
		}
	}
}
