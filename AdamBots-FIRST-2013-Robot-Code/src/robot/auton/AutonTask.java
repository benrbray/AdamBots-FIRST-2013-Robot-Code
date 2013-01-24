/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.auton;

/**
 *
 * @author Ben
 */
public abstract class AutonTask {
    //// CONSTANTS -------------------------------------------------------------
    
    public static final int SUCCESS = 0;
    public static final int FAILURE = 1;
    
    //// AUTONLOGIC ------------------------------------------------------------
    
    private AutonType _autonType;
    
    //// CONSTRUCTOR -----------------------------------------------------------
    
    public AutonTask(AutonType autonType){
	this._autonType = autonType;
    }
    
    //// METHODS ---------------------------------------------------------------
    
    public final void done(int status){
	_autonType.next(status);
    }
}
