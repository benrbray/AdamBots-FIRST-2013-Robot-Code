/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic;

/**
 * Contains methods that should be implemented by each "logic phase".  Each of the three logic phases extends this class (AutonLogic, ClimbLogic, TeleopLogic).
 * @author Ben
 * @see auton.AutonLogic
 * @see climb.ClimbLogic
 * @see TeleopLogic
 */
public interface ILogicPhase {
    
    /**
     * Called when this Logic Phase is granted control of the robot.
     */
    public void init();
    
    /**
     * Called periodically while this Logic Phase possesses control.
     */
    public void update();
    
    /**
     * Called immediately before control is taken from this Logic Phase.
     */
    public void finish();
}
