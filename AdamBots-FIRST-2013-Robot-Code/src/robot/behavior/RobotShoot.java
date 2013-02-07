/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.behavior;

import robot.actuators.RobotActuators;
import robot.logic.PIDLogic;
import robot.sensors.RobotSensors;

/**
 * RobotShoot sets the speed and angle of the shooter; update() must be called
 * periodically.
 *
 * @author Nathan
 */
public abstract class RobotShoot {
    //// PRIVATE VARIABLES -----------------------------------------------------
    
    /** The angle that the shooter is currently moving towards. */
    private static double _targetAngleDegrees;
    /** The PID interface used to control the shooter. */
    private static PIDLogic _shooterPid;

    //// INITIALIZATION --------------------------------------------------------
    
    /**
     * init() creates the static private _shooterPid() for controlling the
     * shooter wheel.
     */
    public static void init() {
	_shooterPid = new PIDLogic(RobotActuators.shooterWheelMotor, RobotSensors.counterShooterSpeed, 0, 0, 0);
    }

    //// UPDATE ----------------------------------------------------------------
    
    /**
     * Called periodically to control the shooterAngle motor.
     */
    public static void update() {
	double d = RobotSensors.encoderShooterAngle.getDistance();
	/**
	 * TODO: Rewrite 'd' to be a proper angle. * TODO: Check encoder at
	 * limits
	 */
	if (Math.abs(d - _targetAngleDegrees) < 3) {
	    RobotActuators.shooterAngleMotor.set(0);
	} else {
	    RobotActuators.shooterAngleMotor.set(Math.max(-1, Math.min(1, (_targetAngleDegrees - d) / 360.0)));//Probably too slow.
	}
    }

    //// ANGLE OF ATTACK -------------------------------------------------------
    
    /**
     * Assigns a target angle to the shooter. Sets private _targetAngle.
     * @param angle The desired angled for the shooter from horizontal.
     */
    public static void setAngleDegrees(double angle) {
	//TODO: Fix pulse count...
	_targetAngleDegrees = angle;
    }

    /**
     * Has the shooter reached its target angle?
     * @return Shooter angle status.
     */
    public static boolean isShooterInPosition(){
	return false; // TODO:  isShooterInPosition() logic
    }
    
    //// SHOOTER SPEED ---------------------------------------------------------
    
    /**
     * Sets the speed of the shooter wheel.
     * @param speed_rpm The speed of the shooter in rpm
     */
    public static void setSpeed(double speed_rpm) {
	_shooterPid.setRPM(speed_rpm);
    }
    
    /**
     * Are the shooter wheels up to speed?
     * @return Shooter wheel status.
     */
    public static boolean isShooterUpToSpeed(){
	return false; // TODO:  isShooterInPosition() logic
    }
}
