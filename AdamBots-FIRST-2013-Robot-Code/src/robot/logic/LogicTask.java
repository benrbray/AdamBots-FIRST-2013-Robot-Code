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
    
    public LogicTask(){
	
    }
    
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
}
