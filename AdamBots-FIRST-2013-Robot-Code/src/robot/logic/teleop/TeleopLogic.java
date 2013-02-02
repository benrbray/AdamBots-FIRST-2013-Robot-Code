/**
 * This class handles the updateJoystick Methods as well as the update magic box methods from last year.
 * @author Steven
 * @team-members Steven, Haden
 */

package robot.logic.teleop;

import robot.RobotMain;
import robot.behavior.*;
import robot.control.*;
import robot.logic.LogicPhase;
import robot.sensors.*;

public class TeleopLogic extends LogicPhase {
    
    //// CONSTANTS -------------------------------------------------------------
    
    //// VARIABLES -------------------------------------------------------------
    private RobotDrive _robotDrive;
    private RobotPickup _robotPickup;
    private RobotShoot _robotShoot;
    private RobotClimb _robotClimb;
    private RobotSensors _robotSensors;
    
    private FancyJoystick _primaryJoy;
    private FancyJoystick _secondaryJoy;
    private MagicBox _magicBox;
    
    private boolean _primaryButtons[];
    private boolean _secondaryButtons[];
    private boolean _magicBoxButtons[];
    
    private double _primaryAxis[];
    private double _secondaryAxis[];
    
    //// DRIVE VARIABLES -------------------------------------------------------
    // Chassis drive
    private double _leftDrive;
    private double _rightDrive;
    private boolean _highGear;
    
    // Secondary drive
    private double _shooterAngleChangerDrive;
    private double _elevatorDrive;
    
    /**
     * Creates an instance of TeleopLogic.
     */
    public TeleopLogic() {
	
    }
    
    /**
     * Initializes variables, objects, etc to their starting states.
     */
    public void init() {
	
	//// INITIALIZE ROBOT CONTROL AND SENSOR CLASS REFERENCES --------------
	_robotDrive = RobotMain.robotDrive;
	_robotPickup = RobotMain.robotPickup;
	_robotShoot = RobotMain.robotShoot;
	_robotClimb = RobotMain.robotClimb;
	_robotSensors = RobotMain.robotSensors;
	
	//// INITIALIZE JOYSTICK AND MAGIC BOX VALUE ARRAYS --------------------
	_primaryButtons = new boolean[FancyJoystick.XBOX_BUTTONS];
	_secondaryButtons = new boolean[FancyJoystick.XBOX_BUTTONS];
	_magicBoxButtons = new boolean[MagicBox.NUM_BUTTONS];
	
	_primaryAxis = new double[FancyJoystick.XBOX_BUTTONS];
	_secondaryAxis = new double[FancyJoystick.XBOX_AXES];
	
    }
    
    /**
     * Update method. To be called periodically by MainControl.
     */
    public void update() {
	
	//// UPDATE JOYSTICK AND MAGIC BOX VALUES ------------------------------
	updateJoystickValues();
	updateMagicBox();
	
	//// PRIMARY DRIVER ----------------------------------------------------
	_leftDrive = _primaryAxis[FancyJoystick.AXIS_TRIGGERS] - _primaryAxis[FancyJoystick.AXIS_LEFT_X];
	_rightDrive = -(_primaryAxis[FancyJoystick.AXIS_TRIGGERS] + _primaryAxis[FancyJoystick.AXIS_LEFT_X]);
	
	// If the left and right drive variables are both equal to 0 and the auto target button is held then let's auto target
	if (_leftDrive == 0 && _rightDrive == 0 && _primaryButtons[FancyJoystick.BUTTON_RB]) {
	    //TODO: Implement auto targeting, values will be assigned to right and left drive here.
	}
	
	// Keeps the drive variables in their -1 to 1 range
	_leftDrive = Math.max(-1, Math.min(1, _leftDrive));
	_rightDrive = Math.max(-1, Math.min(1, _rightDrive));
	
	_robotDrive.drive(_leftDrive, _rightDrive);
	
	// Handle shifting
	if (_primaryButtons[FancyJoystick.BUTTON_LB]) {
            if (_primaryButtons[FancyJoystick.BUTTON_A]) {
		_highGear=false;
            } else if (_primaryButtons[FancyJoystick.BUTTON_Y]) {
		_highGear=true;
            }
        }
	
	// Infeed roller for pickup mechanism
	if (_primaryButtons[FancyJoystick.BUTTON_X]) {
	    //TODO: Run pickup roller
	} else if (_primaryButtons[FancyJoystick.BUTTON_B]) {
	    //TODO: Reverse pickup roller
	} else {
	    //TODO: Pickup roller off
	}
	
	//// SECONDARY DRIVER --------------------------------------------------
	_shooterAngleChangerDrive = _secondaryAxis[FancyJoystick.AXIS_LEFT_Y];
	
	if (_secondaryButtons[FancyJoystick.BUTTON_RB]) {
	    if (_shooterAngleChangerDrive == 0 && _magicBoxButtons[MagicBox.AUTO_ANGLE_ENABLED]) {
		//TODO: Add automatic shooter angle adjustment
	    }
	    
	    if (_magicBoxButtons[MagicBox.AUTO_SHOOTER_SPEED_ENABLED]) {
		//TODO: Add automatic shooter speed calls
	    }
	}
	
	//TODO: Run shooter angle changer
	
	// Drive elevator
	_elevatorDrive = _secondaryAxis[FancyJoystick.AXIS_TRIGGERS];
	//TODO: Drive elevator
	
	// Disk fire control
	if (_secondaryButtons[FancyJoystick.BUTTON_A]) {
	    //TODO: Extend firing solenoid
	} else {
	    //TODO: Retract firing solenoid
	}
    }
    
    /**
     * Gathers joystick button and axis values and stores them in their respective arrays.
     */
    private void updateJoystickValues() {
	for (int i = 1; i < FancyJoystick.XBOX_BUTTONS; i++) {
	    _primaryButtons[i] = _primaryJoy.getRawButton(i);
	    _secondaryButtons[i] = _secondaryJoy.getRawButton(i);
	}
	
	for (int i = 0; i < FancyJoystick.XBOX_AXES; i++) {
	    _primaryAxis[i] = _primaryJoy.getDeadAxis(i);
	    _secondaryAxis[i] = _secondaryJoy.getDeadAxis(i);
	}
    }
    
    /**
     * Gathers magic box values.
     */
    private void updateMagicBox() {
	for (int i = 0; i < MagicBox.NUM_BUTTONS; i++) {
	    _magicBoxButtons[i] = _magicBox.getDigitalIn(i);
	}
    }
    
    public void finish(){
	
    }
}
