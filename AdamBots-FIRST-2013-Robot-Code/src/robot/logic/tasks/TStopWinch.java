/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

import robot.behavior.RobotClimb;
import robot.logic.LogicTask;

/**
 * Stops the winch at its current position.
 * @author Ben
 */
public class TStopWinch extends LogicTask {

    /**
     * This task stops the winch at its current position.
     */
    public TStopWinch(){
	
    }
    
    //// INITIALIZATION --------------------------------------------------------
    
    public void initialize() {
	RobotClimb.stopWinch();
    }

    //// UPDATE ----------------------------------------------------------------
    
    public void update() {
	_done = _initialized;
    }

    //// FINISH ----------------------------------------------------------------
    
    public int finish() {
	return _done ? SUCCESS : FAILURE;
    }
    
}
