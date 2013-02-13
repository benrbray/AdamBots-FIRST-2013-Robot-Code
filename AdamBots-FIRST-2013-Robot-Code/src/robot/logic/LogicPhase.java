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
    
    public boolean verboseOutput = false;
    
    /**
     * Contains methods that should be implemented by each "logic phase".
     * Each of the three logic phases extends this class (AutonLogic, ClimbLogic, TeleopLogic).
     */
    public LogicPhase(){
	
    }
    
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
    
    /**
     * Prints a String to the output window if verboseOutput is set to TRUE.
     * @param s The message to print.
     * @see #verboseOutput
     */
    public final void print(String s){
	if(verboseOutput) System.out.print(s);
    }
    /**
     * Prints a String, followed by a newline character, to the output window
     * if verboseOutput is set to TRUE.
     * @param s The message to print.
     * @see #verboseOutput
     */
    public final void println(String s){
	if(verboseOutput) System.out.println(s + "\n");
    }
}
