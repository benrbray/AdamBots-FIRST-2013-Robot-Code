/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.targeting;

import robot.RobotObject;
import robot.behavior.RobotShoot;
import robot.camera.RobotCamera;

/**
 *
 * @author Nathan
 */
public class TargetShooterSpeedLogic extends RobotObject {
	//// PRINT FILTERING -------------------------------------------------------
	
	/** Hide RobotObject field to allow for proper print filtering. */
	public static boolean verboseOutput = true;
	
	//// PRIVATE VARIABLES -----------------------------------------------------

	private static boolean _isTargeting = false;
	private static boolean _manualVoltage = false;
	private static double _restSpeed = 0;
	private static double _speedMultiplier = 1;

	//// INITIALIZATION --------------------------------------------------------
	
	/**
	 * Initializes anything the class might need.
	 */
	public static void init() {
	}
	
	//// UPDATE ----------------------------------------------------------------
	
	public static void update() {
		if ( !_isTargeting ) {
			RobotShoot.setSpeed(_restSpeed * _speedMultiplier);
		} else if (!_manualVoltage) {
			RobotShoot.setSpeed(calculateSpeed() * _speedMultiplier);
		}
	}

	//// SETTER METHODS --------------------------------------------------------
	
	/**
	 * Sets the speed for the shooter to be at when not actively targeting (default 0).
	 * @param speed The speed (in RPM) to set the shooter to.
	 */
	public static void setRestSpeedRPM( double speed ) {
		_restSpeed = speed;
	}
	
	public static void setSpeedMultiplier( double multiplier )
	{
		_speedMultiplier = multiplier;
	}

	/**
	 * Activates or deactivates targeting.
	 * @param x Whether to target.
	 */
	public static void setIsTargeting( boolean x ) {
		_isTargeting = x;
	}
	
	/**
	 * Releases RobotShoot's control of the shooter motor.
	 * @param b 
	 */
	public static void enableManualVoltage(boolean b) {
		_manualVoltage = b;
		
		if (_manualVoltage) {
			RobotShoot.stopPID();
		} else {
			RobotShoot.startPID();
		}
	}
	
	//// CALCULATIONS ----------------------------------------------------------

	/**
	 * Calculates the RPM speed to set shooter to hit the target from RobotCamera.getDistance().
	 * @return Calculated RPM.
	 */
	public static double calculateSpeed() {
		return 0.5 + 0.01 * RobotCamera.getDistanceInches();
	}
}
