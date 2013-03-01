/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot;

/**
 *
 * @author Ben
 */
public abstract class RobotObject {
    //// PUBLIC VARIABLES ------------------------------------------------------
    
    /** If set to FALSE, print statements from this class will be ignored. */
    public static boolean verboseOutput = false;
    
    //// OUTPUT FILTERING ------------------------------------------------------
    
    /**
     * Prints a String to the output window if verboseOutput is set to TRUE.
     * @param s The message to print.
     * @see #verboseOutput
     */
    public static final void print(String s){
		if(verboseOutput) { System.out.print(s); }
    }
    /**
     * Prints a String, followed by a newline character, to the output window
     * if verboseOutput is set to TRUE.
     * @param s The message to print.
     * @see #verboseOutput
     */
    public static final void println(String s){
		if(verboseOutput) { System.out.println(s); }
    }
}
