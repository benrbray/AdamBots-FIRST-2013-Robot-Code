/**
 * This class handles the updateJoystick Methods as well as the update magic box methods from last year.
 * @author Steven
 * @team-members Steven, Haden
 */

package robot.logic.teleop;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotMain;
import robot.actuators.RobotActuators;
import robot.behavior.*;
import robot.control.*;
import robot.logic.LogicPhase;
import robot.logic.TargetLogic;
import robot.sensors.*;

public class TeleopLogic extends LogicPhase {
    
    //// CONSTANTS -------------------------------------------------------------
    
    //// VARIABLES -------------------------------------------------------------
    
    private FancyJoystick _primaryJoy;
    private FancyJoystick _secondaryJoy;
    
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
    private boolean _winchEnabled;
    private boolean _winchEnabledToggleReleased;
    
    // Secondary drive
    private double _shooterAngleChangerDrive;
    private double _elevatorDrive;
    private int _numShots;
    private boolean _numShotsReleased;
    
    /**
     * Creates an instance of TeleopLogic.
     */
    public TeleopLogic() {
	super();
    }
    
    /**
     * Initializes variables, objects, etc to their starting states.
     */
    public void initPhase() {
	//// INITIALIZE JOYSTICKS ----------------------------------------------
	
	_primaryJoy = RobotMain.primaryJoystick;
	_secondaryJoy = RobotMain.secondaryJoystick;
	
	MagicBox.init();
	
	//// INITIALIZE JOYSTICK AND MAGIC BOX VALUE ARRAYS --------------------
	//Add 1 to the arrays because the inputs start at one rather than 0.
	_primaryButtons = new boolean[FancyJoystick.XBOX_BUTTONS + 1];
	_secondaryButtons = new boolean[FancyJoystick.XBOX_BUTTONS + 1];
	_magicBoxButtons = new boolean[MagicBox.NUM_BUTTONS + 1];
	
	_primaryAxis = new double[FancyJoystick.XBOX_BUTTONS + 1];
	_secondaryAxis = new double[FancyJoystick.XBOX_AXES + 1];
	
	//// INITIALIZE TELEOP VARIABLES ---------------------------------------
	_leftDrive = 0;
	_rightDrive = 0;
	
	_shooterAngleChangerDrive = 0;
	_elevatorDrive = 0;
	_numShots = 0;
	_numShotsReleased = true;
	
	_highGear = false;
	_winchEnabled = false;
	_winchEnabledToggleReleased = false;
    }
    
    /**
     * Update method. To be called periodically by MainControl.
     */
    public void updatePhase() {
	
	//// UPDATE JOYSTICK AND MAGIC BOX VALUES ------------------------------
	updateJoystickValues();
	updateMagicBox();
	
	//// PRIMARY DRIVER ----------------------------------------------------
	_leftDrive = _primaryAxis[FancyJoystick.AXIS_TRIGGERS] - _primaryAxis[FancyJoystick.AXIS_LEFT_X];
	_rightDrive = -(_primaryAxis[FancyJoystick.AXIS_TRIGGERS] + _primaryAxis[FancyJoystick.AXIS_LEFT_X]);
	
	// If the left and right drive variables are both equal to 0 and the auto target button is held then let's auto target
	if (_leftDrive == 0 && _rightDrive == 0 && _primaryButtons[FancyJoystick.BUTTON_RB]) {
	    TargetLogic.startAutomaticDriving();
	    SmartDashboard.putString("chassisTargeting", "Enabled");
	} else {
	    TargetLogic.stopAutomaticDriving();
	    
	    // Keeps the drive variables in their -1 to 1 range
	    _leftDrive = Math.max(-1, Math.min(1, _leftDrive));
	    _rightDrive = Math.max(-1, Math.min(1, _rightDrive));
	    
	    RobotDrive.drive(_leftDrive, _rightDrive);
	    SmartDashboard.putString("chassisTargeting", "Disabled");
	}
	
	// Handle shifting
	if (_primaryButtons[FancyJoystick.BUTTON_LB]) {
            if (_primaryButtons[FancyJoystick.BUTTON_A]) {
		_highGear=false;
		RobotDrive.switchGear(RobotDrive.SHIFTER_LOW);
            } else if (_primaryButtons[FancyJoystick.BUTTON_Y]) {
		_highGear=true;
		RobotDrive.switchGear(RobotDrive.SHIFTER_HIGH);
            }
        }
	
	SmartDashboard.putBoolean("highGear", _highGear);
	
	// Infeed roller for pickup mechanism
	if (_primaryButtons[FancyJoystick.BUTTON_X]) {
	    RobotPickup.intakeRoller(Relay.Value.kForward);
	    SmartDashboard.putString("intakeRoller", "forward");
	} else if (_primaryButtons[FancyJoystick.BUTTON_B]) {
	    RobotPickup.intakeRoller(Relay.Value.kReverse);
	    SmartDashboard.putString("intakeRoller", "reverse");
	} else {
	    RobotPickup.intakeRoller(Relay.Value.kOff);
	    SmartDashboard.putString("intakeRoller", "off");
	}
	
	// Winch Safety
	if (_primaryButtons[FancyJoystick.BUTTON_START] && _primaryButtons[FancyJoystick.BUTTON_BACK] && _winchEnabledToggleReleased) {
	    _winchEnabled = !_winchEnabled;
	    _winchEnabledToggleReleased = false;
	} else if (!_primaryButtons[FancyJoystick.BUTTON_START] && !_primaryButtons[FancyJoystick.BUTTON_BACK]) {
	    _winchEnabledToggleReleased = true;
	}
	
	SmartDashboard.putBoolean("winchSafetyEnabled", _winchEnabled);
	
	// Winch operation
	if (_winchEnabled) {
	    //TODO: Drive winch based on primary axis right y
	} else {
	    //TODO: Set winch drive to 0
	}
	
	//// SECONDARY DRIVER --------------------------------------------------
	_shooterAngleChangerDrive = _secondaryAxis[FancyJoystick.AXIS_LEFT_Y];
	
	// If the secondary driver requests auto targeting...Else keep speed at a constant.
	if (_secondaryButtons[FancyJoystick.BUTTON_RB]) {
	    
	    TargetLogic.beginTargeting();
	    
	    // Shooter angle control.
	    // Start by driving based on joystick if the joystick has input.
	    if (_shooterAngleChangerDrive != 0) {
		RobotActuators.shooterAngleMotor.set(_shooterAngleChangerDrive);
		SmartDashboard.putString("shooterAngleChanger", "manual" + _shooterAngleChangerDrive);
	    } else if (_magicBoxButtons[MagicBox.AUTO_ANGLE_ENABLED]) {
		TargetLogic.setShooterConstantAngle(0);
		SmartDashboard.putString("shooterAngleChanger", "automatic");
	    } else if (_magicBoxButtons[MagicBox.SHOOT_FROM_PYRAMID]){
		TargetLogic.setShooterConstantAngle(MagicBox.PYRAMID_SHOT_ANGLE);
		SmartDashboard.putString("shooterAngleChanger", "pyramid");
	    } else if (_magicBoxButtons[MagicBox.SHOOT_FROM_FULL_COURT]) {
		TargetLogic.setShooterConstantAngle(MagicBox.FULL_COURT_SHOT_ANGLE);
		SmartDashboard.putString("shooterAngleChanger", "full court");
	    }
	    
	    // Shooter speed control
	    // Start with automatic speed if it is enabled.
	    if (_magicBoxButtons[MagicBox.AUTO_SHOOTER_SPEED_ENABLED]) {
		TargetLogic.setShooterConstantSpeed(0);
		SmartDashboard.putString("shooterSpeed", "automatic");
	    } else if (_magicBoxButtons[MagicBox.SHOOT_FROM_PYRAMID]) {
		TargetLogic.setShooterConstantSpeed(MagicBox.PYRAMID_SHOT_SPEED);
		SmartDashboard.putString("shooterSpeed", "pyramid");
	    } else if (_magicBoxButtons[MagicBox.SHOOT_FROM_FULL_COURT]) {
		TargetLogic.setShooterConstantSpeed(MagicBox.FULL_COURT_SHOT_SPEED);
		SmartDashboard.putString("shooterSpeed", "full court");
	    } else {
		TargetLogic.setShooterConstantSpeed(MagicBox.getShooterManualSpeed());
		SmartDashboard.putString("shooterSpeed", "manual " + MagicBox.getShooterManualSpeed());
	    }
	    
	    SmartDashboard.putString("secondaryAutoTarget", "true");
	} else {
	    //TODO: NATHAN constant shooter speed
	    TargetLogic.endTargeting();
	    RobotActuators.shooterWheelMotor.set(MagicBox.getShooterManualSpeed());
	    RobotActuators.shooterAngleMotor.set(_shooterAngleChangerDrive);
	    SmartDashboard.putString("shooterSpeed", "manual " + MagicBox.getShooterManualSpeed());
	    SmartDashboard.putString("secondaryAutoTarget", "false");
	}
	
	// Drive elevator
	_elevatorDrive = _secondaryAxis[FancyJoystick.AXIS_TRIGGERS];
	
	RobotActuators.discElevator.set(_elevatorDrive);
	
	// Disk fire control
	//TODO: Check shooter pneumatic control
	if (_secondaryButtons[FancyJoystick.BUTTON_A]) {
	    RobotActuators.shooterFeederSolenoid.set(Relay.Value.kOn);
	    //RobotActuators.shooterFeederSolenoid.set(true);
	    
	    if (_numShotsReleased) {
		_numShots++;
		_numShotsReleased = false;
	    }
	} else {
	    RobotActuators.shooterFeederSolenoid.set(Relay.Value.kOff);
	    //RobotActuators.shooterFeederSolenoid.set(false);
	    
	    _numShotsReleased = true;
	}
	
	SmartDashboard.putNumber("numShots", _numShots);
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
     * Gathers magic box values, and updates shooter multiplier and angle offset.
     */
    private void updateMagicBox() {
	MagicBox.update();
	
	TargetLogic.setShooterSpeedMultiplier(MagicBox.getShooterMultiplier());
	TargetLogic.setShooterAngleOffset(MagicBox.getAngleOffset());
	
	for (int i = 0; i < MagicBox.NUM_BUTTONS; i++) {
	    _magicBoxButtons[i] = MagicBox.getDigitalIn(i);
	}
    }
    
    public void finishPhase() {
	
    }
}
