/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic;

/**
 * Contains methods that should be implemented by each "logic phase".  
 * Each of the three logic phases extends this class (AutonLogic, ClimbLogic, TeleopLogic).
 * @author Ben
 * @see auton.AutonLogic
 * @see climb.ClimbLogic
 * @see TeleopLogic
 */
public abstract class LogicPhase {
    
    /** Autonomous Phase Constant. */
    public static final int AUTONOMOUS = 0;
    /** Teleop Phase Constant. */
    public static final int TELEOP = 1;
    /** Climb Phase Constant. */
    public static final int CLIMB = 2;
    
    /**
     * Contains methods that should be implemented by each "logic phase".
     * Each of the three logic phases extends this class (AutonLogic, ClimbLogic, TeleopLogic).
     */
    public LogicPhase(){
	
    }
    
    /**
     * Called when this Logic Phase is granted control of the robot.
     */
    public abstract void init();
    
    /**
     * Called periodically while this Logic Phase possesses control.
     */
    public abstract void update();
    
    /**
     * Called immediately before control is taken from this Logic Phase.
     */
    public abstract void finish();
    
}
