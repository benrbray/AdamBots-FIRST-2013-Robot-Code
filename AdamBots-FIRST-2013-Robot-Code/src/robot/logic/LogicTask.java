/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic;

import robot.RobotObject;

/**
 *
 * @author Ben
 */
public abstract class LogicTask extends RobotObject {
    
    //// CONSTANTS -------------------------------------------------------------
    
    /** Success Status */
    public static final int SUCCESS = 0;
    /** Failure Status */
    public static final int FAILURE = 1;
    
    //// PROTECTED VARAIBLES ---------------------------------------------------
    
    protected boolean _initialized = false;
    protected boolean _done = false;
    
    //// CONSTRUCTOR -----------------------------------------------------------
    
    public LogicTask(){
	
    }
    
    //// PUBLIC METHODS --------------------------------------------------------
    
    /**
     * Called when the Task is started.
     */
    public final void initializeTask(){
		_initialized = true;
		initialize();
    }
    
    /**
     * Called periodically while the Task is being executed.
     */
    public final void updateTask(){
		update();
    }
    
    /**
     * Called when the Task is stopped or completed.  If the Task has been
     * initialized, returns the status reported by the subclass.  Otherwise,
     * returns the status reported by the subclass only if that status is not
     * the success status.  This ensures that the Task always fails when it has
     * not been initialized.
     * @return A status message.  (0 = success)
     */
    public final int finishTask(){
		int status = finish();
		return (!_initialized && status==SUCCESS)?FAILURE:status;
    }
    
    //// ABSTRACT TASK METHODS -------------------------------------------------
    
    /**
     * Initialization method to be implemented by subclasses of LogicTask.
     */
    protected abstract void initialize();
    
    /**
     * Update method to be implemented by subclasses of LogicTask.
     */
    protected abstract void update();
    
    /**
     * Finish method to be implemented by subclasses of LogicTask.
     */
    protected abstract int finish();
    
    //// IMPLEMENTED METHODS ---------------------------------------------------
    
    /**
     * Indicates whether or not the task has been completed.
     * @return Has the task been completed?
     */
    public final boolean isDone(){
		return _done;
    }
}
