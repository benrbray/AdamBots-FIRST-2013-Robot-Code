/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DriverStation;
import robot.logic.LogicTask;

/**
 *
 * @author Ben
 */
public class TDelay extends LogicTask {
    //// PRIVATE VARIABLES -----------------------------------------------------
    
    private long _startTime;
    private int _milliseconds;
    
    //// CONSTRUCTOR -----------------------------------------------------------
    
    public TDelay(int milliseconds){
	_milliseconds = milliseconds;
    }
    
    //// INITIALIZE ------------------------------------------------------------
    
    protected void initialize() {
	_startTime = System.currentTimeMillis();
    }

    //// UPDATE ----------------------------------------------------------------
    
    protected void update() {
	if(System.currentTimeMillis() - _startTime >= _milliseconds){
	    _done = true;
	}
    }

    //// FINISH ----------------------------------------------------------------
    
    protected int finish() {
	return _done ? SUCCESS : FAILURE;
    }
    
}
