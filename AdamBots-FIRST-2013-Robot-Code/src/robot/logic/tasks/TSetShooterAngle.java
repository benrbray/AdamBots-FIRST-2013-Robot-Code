/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

import robot.behavior.RobotShoot;
import robot.logic.LogicTask;

/**
 *
 * @author Steven
 */
public class TSetShooterAngle extends LogicTask {
	//// PRIVATE VARIABLES -----------------------------------------------------
	
	private double _angleDegrees;
	
	//// CONSTRUCTOR -----------------------------------------------------------
	
	public TSetShooterAngle(double angleDegrees){
		_angleDegrees = angleDegrees;
	}

	//// INITIALIZATION --------------------------------------------------------
	
	protected void initialize() {
		RobotShoot.startMovingToTarget();
		RobotShoot.setTargetAngleDegrees(_angleDegrees);
	}

	//// UPDATE ----------------------------------------------------------------
	
	protected void update() {
		_done = true;
	}

	//// FINISH ----------------------------------------------------------------
	
	protected int finish() {
		return _done ? SUCCESS : FAILURE;
	}
}
