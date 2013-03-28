/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

import robot.behavior.RobotDrive;
import robot.logic.LogicTask;

/**
 *
 * @author Steven
 */
public class TShiftGear extends LogicTask {
	//// CONSTANTS -------------------------------------------------------------
	
	public static final boolean HIGH_GEAR = true;
	public static final boolean LOW_GEAR = false;
	
	//// PRIVATE VARIABLES -----------------------------------------------------
	
	private double _servoValue;
	
	//// CONSTRUCTOR -----------------------------------------------------------
	
	public TShiftGear(boolean high){
		this(high?RobotDrive.SHIFTER_HIGH:RobotDrive.SHIFTER_LOW);
	}
	
	public TShiftGear(double servoValue){
		_servoValue = servoValue;
	}
	
	//// INITIALIZE ------------------------------------------------------------
	
	protected void initialize() {
		RobotDrive.switchGear(_servoValue);
	}

	//// UPDATE ----------------------------------------------------------------
	
	protected void update() {
		_done = true;
	}

	//// CONSTRUCTOR -----------------------------------------------------------
	
	protected int finish() {
		return _done ? SUCCESS : FAILURE;
	}
	
}
