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
    
    /**
     * Waits an exact period of time, specified in milliseconds.  The delay is
     * based on the current system time, and not the time reported by the field.
     * @param milliseconds The number of milliseconds to wait.
     */
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
