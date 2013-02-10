/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

import robot.control.FancyJoystick;
import robot.logic.LogicTask;
import robot.logic.teleop.TeleopLogic;

/**
 *
 * @author Ben
 */
public class TAwaitButton extends LogicTask {
    //// PRIVATE VARIABLES -----------------------------------------------------
    
    FancyJoystick _joystick;
    private int _button;
    private boolean _latch;

    //// CONSTRUCTOR -----------------------------------------------------------
    
    public TAwaitButton(int joystickPort, int button){
	this(joystickPort, button, false);
    }
    
    /**
     * 
     * @param joystickPort
     * @param button
     * @param latch If set to TRUE, done will not be updated after it is initially
     * set to TRUE.
     */
    public TAwaitButton(int joystickPort, int button, boolean latch){
	_joystick = null;
	_button = button;
	_latch = latch;
	initializeTask();
    }
    
    //// INITIALIZATION --------------------------------------------------------
    
    public void initializeTask() {
	
    }

    //// UPDATE ----------------------------------------------------------------
    
    public void updateTask() {
	if(!_done || (_done && !_latch)){
	    _done = _joystick.getRawButton(_button);
	}
    }

    //// FINISH ----------------------------------------------------------------
    
    public int finishTask() {
	return _done ? SUCCESS : FAILURE;
    }
    
}
