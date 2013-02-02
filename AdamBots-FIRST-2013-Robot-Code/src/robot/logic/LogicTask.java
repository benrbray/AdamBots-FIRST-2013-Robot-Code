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
    
    public abstract void initializeTask();
    
    public abstract void updateTask();
    
    public abstract void finishTask();
}
