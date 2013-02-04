/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

import robot.logic.LogicTask;

/**
 *
 * @author Ben
 */
public final class TAwaitStatus extends LogicTask {
    //// CONSTANTS -------------------------------------------------------------
    
    public static final int WINCH_IN_POSITION = 0;
    public static final int SHOOTER_IN_POSITION = 1;
    
    //// PRIVATE VARIABLES -----------------------------------------------------
    
    private int _status;
    
    //// CONSTRUCTOR -----------------------------------------------------------
    
    public TAwaitStatus(int status){
	_status = status;
    }

    //// INITIALIZATION --------------------------------------------------------
    
    public void initializeTask() {
	
    }

    //// UPDATE ----------------------------------------------------------------
    
    public void updateTask() {
	switch(_status){
	    
	}
    }

    //// FINISH ----------------------------------------------------------------
    
    public int finishTask() {
	return 0;
    }
    
    //// COMPLETION STATUS -----------------------------------------------------
    
    public boolean isDone(){
	return false;
    }
    
}
