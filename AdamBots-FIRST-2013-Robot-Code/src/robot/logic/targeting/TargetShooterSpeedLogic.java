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

	private static boolean _isTargeting = false;
	private static double _restSpeed = 0;

	/**
	 * Initializes anything the class might need.
	 */
	public static void init() {
	}

	/**
	 * Sets the speed for the shooter to be at when not actively targeting (default 0).
	 * @param speed The speed (in RPM) to set the shooter to.
	 */
	public static void setRestSpeedRPM( double speed ) {
		_restSpeed = speed;
	}

	/**
	 * Activates or deactivates targeting.
	 * @param x Whether to target.
	 */
	public static void setIsTargeting( boolean x ) {
		_isTargeting = x;
	}

	/**
	 * Calculates the RPM speed to set shooter to hit the target from RobotCamera.getDistance().
	 * @return Calculated RPM.
	 */
	public static double calculateSpeed() {
		return 0.5 + 0.01 * RobotCamera.getDistanceInches();
	}

	public static void update() {
		if ( !_isTargeting ) {
			RobotShoot.setSpeed(_restSpeed);
			return;
		}
		RobotShoot.setSpeed(calculateSpeed());
	}
}
