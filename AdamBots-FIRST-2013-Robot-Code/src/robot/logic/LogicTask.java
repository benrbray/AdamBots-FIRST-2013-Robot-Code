/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic;

/**
 *
 * @author Ben
 */
public abstract class LogicTask {
    
    //// CONSTANTS -------------------------------------------------------------
    
    /** Success Status */
    public static final int SUCCESS = 0;
    /** Failure Status */
    public static final int FAILURE = 0;
    
    //// PROTECTED VARAIBLES ---------------------------------------------------
    
    protected boolean _done = false;
    
    //// CONSTRUCTOR -----------------------------------------------------------
    
    public LogicTask(){
	
    }
    
    //// ABSTRACT METHODS ------------------------------------------------------
    
    /**
     * Called when the Task is started.
     */
    public abstract void initializeTask();
    
    /**
     * Called periodically while the Task is being executed.
     */
    public abstract void updateTask();
    
    /**
     * Called when the Task is stopped or completed.
     * @return A status message.  (0 = success)
     */
    public abstract int finishTask();
    
    //// IMPLEMENTED METHODS ---------------------------------------------------
    
    /**
     * Indicates whether or not the task has been completed.
     * @return Has the task been completed?
     */
    public final boolean isDone(){
	return _done;
    }
}
