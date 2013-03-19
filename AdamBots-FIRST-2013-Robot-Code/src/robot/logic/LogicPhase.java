/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic;

import robot.RobotObject;

/**
 * Contains methods that should be implemented by each "logic phase".  
 * Each of the three logic phases extends this class (AutonLogic, ClimbLogic, TeleopLogic).
 * @author Ben
 * @see auton.AutonLogic
 * @see climb.ClimbLogic
 * @see TeleopLogic
 */
public abstract class LogicPhase extends RobotObject {
	//// PRINT FILTERING -------------------------------------------------------
	
	/** Hide RobotObject field to allow for proper print filtering. */
	public static boolean verboseOutput = true;
	
    //// CONSTANTS -------------------------------------------------------------
	
    /** Autonomous Phase Constant. */
    public static final int AUTONOMOUS = 0;
    /** Teleop Phase Constant. */
    public static final int TELEOP = 1;
    /** Climb Phase Constant. */
    public static final int CLIMB = 2;
	
	//// CONSTRUCTOR -----------------------------------------------------------
    
    /**
     * Contains methods that should be implemented by each "logic phase".
     * Each of the three logic phases extends this class (AutonLogic, ClimbLogic, TeleopLogic).
     */
    public LogicPhase(){
	
    }
    
	//// ABSTRACT FLOW METHODS -------------------------------------------------
	
    /**
     * Called when this Logic Phase is granted control of the robot.
     */
    public abstract void initPhase();
    
    /**
     * Called periodically while this Logic Phase possesses control.
     */
    public abstract void updatePhase();
    
    /**
     * Called immediately before control is taken from this Logic Phase.
     */
    public abstract void finishPhase();
}
