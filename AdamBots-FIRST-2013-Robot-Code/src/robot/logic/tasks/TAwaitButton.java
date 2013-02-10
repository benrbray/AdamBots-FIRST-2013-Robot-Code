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

    //// CONSTRUCTOR -----------------------------------------------------------
    
    public TAwaitButton(int joystickPort, int button){
	_joystick = null;
	_button = button;
	initializeTask();
    }
    
    //// INITIALIZATION --------------------------------------------------------
    
    public void initializeTask() {
	
    }

    //// UPDATE ----------------------------------------------------------------
    
    public void updateTask() {
	
    }

    //// FINISH ----------------------------------------------------------------
    
    public int finishTask() {
	return SUCCESS;
    }
    
}
