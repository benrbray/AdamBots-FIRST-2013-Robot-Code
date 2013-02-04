/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.behavior;

import robot.actuators.RobotActuators;
import robot.sensors.RobotSensors;

/**
 * RobotShoot sets the speed and angle of the shooter; update() must be called
 * periodically.
 *
 * @author Nathan
 */
public abstract class RobotShoot {

    /**
     * The angle that the shooter is currently moving towards.
     */
    private static double _targetAngleDegrees;

    public static void update() {
	double d = RobotSensors.encoderShooterAngle.getDistance();
	/**
	 * TODO: Rewrite 'd' to be a proper angle. *
	 */
	if (Math.abs(d - _targetAngleDegrees) < 3) {
	    RobotActuators.shooterAngleMotor.set(0);
	} else {
	    RobotActuators.shooterAngleMotor.set(Math.max(-1, Math.min(1, (_targetAngleDegrees - d) / 360.0)));//Probably too slow.
	}
    }

    /**
     * Assigns a target angle to the shooter. Sets private _targetAngle.
     *
     * @param angle The desired angled for the shooter from horizontal.
     */
    public static void setAngleDegrees(double angle) {
	//TODO: Fix pulse count...
	_targetAngleDegrees = angle;

    }

    /**
     * Sets the speed of the shooter wheel.
     *
     * @param speed The speed of the shooter in units/second.
     */
    public static void setSpeed(double speed) {
	RobotActuators.shooterWheelMotor.set(speed);
    }
}
