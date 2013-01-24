/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot;

/**
 *
 * @author Ben
 */
public interface IRobotPhase {
    
    /**
     * Called when control is given to this IRobotPhase.
     */
    public void initialize();
    
    /**
     * Called just before control is taken from this IRobotPhase and given to another.
     * Here, you should stop all motors and 
     */
    public void finish();
    
}
