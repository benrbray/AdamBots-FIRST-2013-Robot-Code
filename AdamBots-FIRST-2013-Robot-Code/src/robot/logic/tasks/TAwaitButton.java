/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

import edu.wpi.first.wpilibj.DriverStation;
import robot.RobotMain;
import robot.control.FancyJoystick;
import robot.logic.LogicTask;
import robot.logic.teleop.TeleopLogic;

/**
 * Waits for one of the drivers to press a button.
 * @author Ben
 */
public class TAwaitButton extends LogicTask {
    //// PRIVATE VARIABLES -----------------------------------------------------
    
    private FancyJoystick _joystick;
    private int _button;
    private boolean _latch;

    //// CONSTRUCTOR -----------------------------------------------------------
    
    /**
     * A task that waits until the specified button is pressed.
     * @param joystickPort Which joystick?
     * @param button The button channel to monitor for input.
     * @see robot.control.FancyJoystick
     */
    public TAwaitButton(int joystickPort, int button){
		this(joystickPort, button, false);
    }
    
    /**
     * A task that waits until the specified button is pressed.
     * @param joystickPort Which joystick?
     * @param button The button channel to monitor for input.
     * @param latch If set to TRUE, _done will not be updated once it is set to TRUE.
     */
    public TAwaitButton(int joystickPort, int button, boolean latch){
		// Make Sure We're in Teleop
		if(!RobotMain.getInstance().isOperatorControl()){
		   throw new IllegalStateException("TAwaitButton cannot be used outside of the Teleop."); 
		}

		_joystick = joystickPort == FancyJoystick.SECONDARY_DRIVER ? 
						RobotMain.secondaryJoystick : 
						RobotMain.primaryJoystick;
		_button = button;
		_latch = latch;
    }
    
    //// INITIALIZATION --------------------------------------------------------
    
    public void initialize() {
	
    }

    //// UPDATE ----------------------------------------------------------------
    
    public void update() {
		if(!_done || (_done && !_latch)){
			_done = _joystick.getRawButton(_button);
		}
    }

    //// FINISH ----------------------------------------------------------------
    
    public int finish() {
		return _done ? SUCCESS : FAILURE;
    }
    
}
