/**
 * This class handles the updateJoystick Methods as well as the update magic box methods from last year.
 * @author Steven
 * @team-members Steven, Haden
 */

package robot.logic.teleop;

import robot.RobotMain;
import robot.behavior.*;
import robot.control.*;
import robot.sensors.*;

public class TeleopLogic {
    
    //// CONSTANTS -------------------------------------------------------------
    
    //// VARIABLES -------------------------------------------------------------
    private RobotDrive _robotDrive;
    private RobotPickup _robotPickup;
    private RobotShoot _robotShoot;
    private RobotClimb _robotClimb;
    private RobotCamera _robotCamera;
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
    
    /**
     * Creates an instance of TeleopLogic.
     */
    public TeleopLogic() {
	
    }
    
    /**
     * Initializes variables, objects, etc to their starting states.
     */
    public void teleopInit() {
	
	//// INITIALIZE ROBOT CONTROL AND SENSOR CLASS REFERENCES --------------
	_robotDrive = RobotMain.robotDrive;
	_robotPickup = RobotMain.robotPickup;
	_robotShoot = RobotMain.robotShoot;
	_robotClimb = RobotMain.robotClimb;
	_robotCamera = RobotMain.robotCamera;
	//_robotSensors = RobotMain.robotSensors;
	
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
    public void tick() {
	
	//// UPDATE JOYSTICK AND MAGIC BOX VALUES ------------------------------
	updateJoystickValues();
	updateMagicBox();
	
	//// DRIVE CHASSIS -----------------------------------------------------
	_leftDrive = _primaryAxis[FancyJoystick.AXIS_TRIGGERS] - _primaryAxis[FancyJoystick.AXIS_LEFT_X];
	_rightDrive = -(_primaryAxis[FancyJoystick.AXIS_TRIGGERS] - _primaryAxis[FancyJoystick.AXIS_LEFT_X]);
	//restricts the motion of the drive variables between 1 & -1
	_leftDrive = Math.max(-1, Math.min(1, _leftDrive));
        _rightDrive = Math.max(-1, Math.min(1, _rightDrive));
	_robotDrive.drive(left, right);
	//Handle shifting
	if (_primaryButtons[FancyJoystick.BUTTON_LB]) {
            if (_primaryButtons[FancyJoystick.BUTTON_A]) {
		_highGear=false;
            } else if (_primaryButtons[FancyJoystick.BUTTON_Y]) {
		_highGear=true;
            }
        }
	
	/* REFERENCE MAIN DRIVER CODE
	// Chassis drive motor math
        _triggers = _main.getRawAxis(FancyJoystick.AXIS_TRIGGERS);
        _leftX = _main.getDeadAxis(FancyJoystick.AXIS_LEFT_X);
        _bridgeTipperSpeed = _main.getDeadAxis(FancyJoystick.AXIS_RIGHT_Y) * -1;
        
        _leftDrive = -_leftX + _triggers;
        _rightDrive = -(_leftX + _triggers);
        
        // Restrict
        double limit = 1;
        _leftDrive = Math.max(-limit, Math.min(limit, _leftDrive));
        _rightDrive = Math.max(-limit, Math.min(limit, _rightDrive));
        
        _leftDriveMotors.set(_leftDrive);
        _rightDriveMotors.set(_rightDrive);
        
        // Shifter logic
        if (_main.getRawButton(FancyJoystick.BUTTON_LB)) {
            //Handle shifting
            if (_main.getRawButton(FancyJoystick.BUTTON_A)) {
                selectGear(SHIFTER_LOW);
                _dbBooleanPack[DB_CURRENT_GEAR] = false;
            } else if (_main.getRawButton(FancyJoystick.BUTTON_Y)) {
                selectGear(SHIFTER_HIGH);
                _dbBooleanPack[DB_CURRENT_GEAR] = true;
            }
        }
	*/
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
}
