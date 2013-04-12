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
	
        private boolean whichGear;
	
	//// CONSTRUCTOR -----------------------------------------------------------
	
	public TShiftGear(boolean high){
            whichGear = high;
	}
	
	
	//// INITIALIZE ------------------------------------------------------------
	
	protected void initialize() {
            if (whichGear) {
                RobotDrive.shiftHigh();
            } else {
                RobotDrive.shiftLow();
            }
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
