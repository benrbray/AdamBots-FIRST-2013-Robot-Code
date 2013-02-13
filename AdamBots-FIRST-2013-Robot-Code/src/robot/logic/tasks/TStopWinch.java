/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

import robot.behavior.RobotClimb;
import robot.logic.LogicTask;

/**
 *
 * @author Ben
 */
public class TStopWinch extends LogicTask {

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
