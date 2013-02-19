/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.targeting;

import robot.RobotObject;

/**
 *
 * @author Nathan
 */
public abstract class TargetShooterAngleLogic extends RobotObject {
	private static double _restAngle = 0;
	private static boolean _isTargeting = false;
	private static double _degreeOffset = 0;

	/**
	 * Initializes any class data needed.
	 */
	public static void init() {
	}
	
	public static void setRestAngle(double angle)
	{
		_restAngle = angle;
	}
	
	public static void setAngleOffsetDegrees(double offset)
	{
		_degreeOffset = offset;
	}

	/**
	 * Tells the class whether to perform automated targeting.
	 * @param doTarget 
	 */
	public static void setIsTargeting( boolean doTarget ) {
		_isTargeting = doTarget;
	}
	
	public static boolean isTargeting() {
		return _isTargeting;
	}

	/**
	 * An update function to be called constantly.
	 */
	public static void update() {
	}
}
