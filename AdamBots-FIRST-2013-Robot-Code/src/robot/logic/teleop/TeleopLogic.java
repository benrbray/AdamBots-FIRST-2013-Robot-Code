/**
 * This class handles the updateJoystick Methods as well as the update magic box
 * methods from last year.
 *
 * @author Steven
 * @team-members Steven, Haden
 */

package robot.logic.teleop;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.IO.DataIO;
import robot.RobotMain;
import robot.actuators.RobotActuators;
import robot.behavior.*;
import robot.control.*;
import robot.logic.LogicPhase;
import robot.logic.targeting.TargetSpinLogic;
import robot.logic.targeting.TargetShooterSpeedLogic;
import robot.logic.targeting.TargetShooterAngleLogic;
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
	private double _setWinch;
    
    private boolean _highGear;
    private boolean _winchEnabled;
    private boolean _winchEnabledToggleReleased;
    
    // Secondary drive
    private double _shooterAngleChangerDrive;
    private double _elevatorDrive;
	private boolean _shooterEnabled;
	private boolean _shooterEnabledToggleReleased;

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
		print("TeleopLogic :: initPhase() ... ");

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
		_setWinch = 0;

		_shooterAngleChangerDrive = 0;
		_elevatorDrive = 0;
		_shooterEnabled = true;
		_shooterEnabledToggleReleased = false;

		_highGear = false;
		_winchEnabled = false;
		_winchEnabledToggleReleased = false;
		
		RobotDrive.shiftHigh();

		println("Completed.");
    }

    /**
     * Update method. To be called periodically by MainControl.
     */
    public void updatePhase() {
		
		//RobotActuators.cameraLED.set(true);
		
		//// UPDATE JOYSTICK AND MAGIC BOX VALUES ------------------------------

		updateJoystickValues();
		updateMagicBox();

		//// PRIMARY DRIVER ----------------------------------------------------

		_leftDrive = _primaryAxis[FancyJoystick.AXIS_TRIGGERS] - _primaryAxis[FancyJoystick.AXIS_LEFT_X];
		_rightDrive = _primaryAxis[FancyJoystick.AXIS_TRIGGERS] + _primaryAxis[FancyJoystick.AXIS_LEFT_X];

		// If the left and right drive variables are both equal to 0 and the auto target button is held then let's auto target
		if (_leftDrive == 0 && _rightDrive == 0 && _primaryButtons[FancyJoystick.BUTTON_RB]) {
			TargetSpinLogic.setIsTargeting(true);
		} else {
			TargetSpinLogic.setIsTargeting(false);

			// Keeps the drive variables in their -1 to 1 range
			_leftDrive = Math.max(-1, Math.min(1, _leftDrive));
			_rightDrive = Math.max(-1, Math.min(1, _rightDrive));

			RobotDrive.drive(_leftDrive, _rightDrive);
		}

		// Handle shifting
		if (_primaryButtons[FancyJoystick.BUTTON_LB]) {
			if (_primaryButtons[FancyJoystick.BUTTON_A]) {
				_highGear = false;
                                RobotDrive.shiftLow();
			} else if (_primaryButtons[FancyJoystick.BUTTON_Y]) {
				_highGear = true;
                                RobotDrive.shiftHigh();
			}
		}
		
		// Kickstand control
		if (_primaryButtons[FancyJoystick.BUTTON_B]) {
			RobotActuators.kickstandSolenoid.set(true);
		} else {
			RobotActuators.kickstandSolenoid.set(true);
		}

		//SmartDashboard.putBoolean("highGear", _highGear);

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
			//Positive pulls winch in
			//Negative lets winch out
			
			_setWinch = -_primaryAxis[FancyJoystick.AXIS_RIGHT_Y]; // * MagicBox.getClimbWinchMultiplier();
			//SmartDashboard.putNumber("climbWinchMultiplier", MagicBox.getClimbWinchMultiplier());
			
			if (_setWinch < 0) {
		//// DANGER DANGER DANGER DANGER DANGER SET IGNORE LIMIT IS DANGEROUS, ROBOT DESTRUCTION CAN RESULT
				RobotActuators.climbWinch.setIgnoreLimit(_setWinch);
				
				//RobotActuators.climbWinch2.set(RobotActuators.climbWinch.get());
				
				RobotActuators.climbWinchSolenoid.set(Relay.Value.kOff);
			} else if (_primaryButtons[FancyJoystick.BUTTON_BACK]) {
				RobotActuators.climbWinchSolenoid.set(Relay.Value.kForward);
				RobotActuators.climbWinch.set(_setWinch);
				
				//RobotActuators.climbWinch2.set(RobotActuators.climbWinch.get());
			} else {
				RobotActuators.climbWinchSolenoid.set(Relay.Value.kOff);
				RobotActuators.climbWinch.set(0);
				//RobotActuators.climbWinch2.set(0);
			}
		} else {
			//TODO: Set winch drive to 0
			RobotActuators.climbWinchSolenoid.set(Relay.Value.kOff);
			RobotActuators.climbWinch.set(0);
			//RobotActuators.climbWinch2.set(0);
		}

		SmartDashboard.putNumber("winchVoltage2", RobotActuators.climbWinch.get());
		SmartDashboard.putNumber("primaryJoyRightYAxis", _primaryAxis[FancyJoystick.AXIS_RIGHT_Y]);
		SmartDashboard.putNumber("winchEncoder", RobotSensors.encoderWinch.get());

		//// SECONDARY DRIVER --------------------------------------------------
		
		// Stop the shooter completely, intended for use while climbing
		if (_shooterEnabledToggleReleased && _secondaryButtons[FancyJoystick.BUTTON_BACK] && _secondaryButtons[FancyJoystick.BUTTON_START]) {
			_shooterEnabled = !_shooterEnabled;
			_shooterEnabledToggleReleased = false;
		} else if (!(_secondaryButtons[FancyJoystick.BUTTON_BACK] || _secondaryButtons[FancyJoystick.BUTTON_START])) {
			_shooterEnabledToggleReleased = true;
		}
		
		_shooterAngleChangerDrive = _secondaryAxis[FancyJoystick.AXIS_LEFT_Y];

		if (_shooterAngleChangerDrive == 0) {
			_shooterAngleChangerDrive = _secondaryAxis[FancyJoystick.AXIS_TRIGGERS] / 5;
		}
		
		// If the secondary driver requests auto targeting...Else keep speed at a constant.
/*		if (_secondaryButtons[FancyJoystick.BUTTON_RB]) {

		// Shooter angle control.
			// Start by driving based on joystick if the joystick has input.
			if (_shooterAngleChangerDrive != 0) {
				RobotActuators.shooterAngleMotor.set(_shooterAngleChangerDrive);
				SmartDashboard.putString("shooterAngleChanger", "manual" + _shooterAngleChangerDrive);
			} else if (_magicBoxButtons[MagicBox.AUTO_ANGLE_ENABLED]) {
				TargetShooterSpeedLogic.setIsTargeting(true);
				SmartDashboard.putString("shooterAngleChanger", "automatic");
			} else if (_magicBoxButtons[MagicBox.SHOOT_FROM_PYRAMID]) {
				TargetShooterAngleLogic.setRestAngle(MagicBox.PYRAMID_SHOT_ANGLE);
				TargetShooterAngleLogic.setIsTargeting(false);
			SmartDashboard.putString("shooterAngleChanger", "pyramid");
			} else if (_magicBoxButtons[MagicBox.SHOOT_FROM_FULL_COURT]) {
				TargetShooterAngleLogic.setRestAngle(MagicBox.FULL_COURT_SHOT_ANGLE);
				TargetShooterAngleLogic.setIsTargeting(false);
				SmartDashboard.putString("shooterAngleChanger", "full court");
			}

		// Shooter speed control
			// Start with automatic speed if it is enabled.
			if (_magicBoxButtons[MagicBox.AUTO_SHOOTER_SPEED_ENABLED]) {
				TargetShooterSpeedLogic.setIsTargeting(true);
				SmartDashboard.putString("shooterSpeed", "automatic");
			} else if (_magicBoxButtons[MagicBox.SHOOT_FROM_PYRAMID]) {
				TargetShooterSpeedLogic.setRestSpeedRPM(MagicBox.PYRAMID_SHOT_SPEED);
				TargetShooterSpeedLogic.setIsTargeting(false);
				SmartDashboard.putString("shooterSpeed", "pyramid");
			} else if (_magicBoxButtons[MagicBox.SHOOT_FROM_FULL_COURT]) {
				TargetShooterSpeedLogic.setRestSpeedRPM(MagicBox.FULL_COURT_SHOT_SPEED);
				TargetShooterSpeedLogic.setIsTargeting(false);
				SmartDashboard.putString("shooterSpeed", "full court");
			} else {
				TargetShooterSpeedLogic.setRestSpeedRPM(MagicBox.SHOOTER_REST_SPEED * MagicBox.getShooterMultiplier());
				TargetShooterSpeedLogic.setIsTargeting(false);
				SmartDashboard.putString("shooterSpeed", "manual " + MagicBox.SHOOTER_REST_SPEED);
			}

			SmartDashboard.putString("secondaryAutoTarget", "true");
} else */
		if (_shooterEnabled) {
			TargetShooterSpeedLogic.enableManualVoltage(false);
			TargetShooterSpeedLogic.setIsTargeting(false);
			TargetShooterAngleLogic.setIsTargeting(false);
			TargetShooterSpeedLogic.setRestSpeedRPM(MagicBox.SHOOTER_REST_SPEED * MagicBox.getShooterMultiplier());
			
			//RobotActuators.shooterWheelMotor.set(MagicBox.getShooterManualSpeed());
			if (!MagicBox.getDigitalIn(7))
			{
				RobotSensors.counterShooterAngle.set(_shooterAngleChangerDrive);
			}
		} else {
			TargetShooterSpeedLogic.setIsTargeting(false);
			TargetShooterAngleLogic.setIsTargeting(false);
			TargetShooterSpeedLogic.setRestSpeedRPM(0.0);
			TargetShooterSpeedLogic.enableManualVoltage(true);
			if (!MagicBox.getDigitalIn(7))
			{
				RobotSensors.counterShooterAngle.set(_shooterAngleChangerDrive);
			}
		}
		
		// Sets shooter motor value to .7 to get it moving after a pid stall or to shoot manually.
		if (_secondaryButtons[FancyJoystick.BUTTON_X]) {
			TargetShooterSpeedLogic.enableManualVoltage(true);
			RobotActuators.shooterWheelMotor.set(.7 * MagicBox.getShooterMultiplier());
		} else {
			TargetShooterSpeedLogic.enableManualVoltage(false);
		}
		
		SmartDashboard.putNumber("shooterRPM", RobotSensors.counterShooterSpeed.pidGet());
		SmartDashboard.putNumber("shooterAngleEncoder", RobotSensors.counterShooterAngle.get());
		
		// Drive elevator
		//TODO: Re implement disc pickup
		//_elevatorDrive = _secondaryAxis[FancyJoystick.AXIS_TRIGGERS];
		//RobotActuators.discElevator.set(_elevatorDrive);

		// Disc fire control
		if (_secondaryButtons[FancyJoystick.BUTTON_A]) {
			//RobotActuators.shooterFeederSolenoid.set(Relay.Value.kOn);
			RobotActuators.shooterFeederSolenoid.set(true);
		} else {
			//RobotActuators.shooterFeederSolenoid.set(Relay.Value.kOff);
			RobotActuators.shooterFeederSolenoid.set(false);
		}
		
		// Disc intake control
		if (_secondaryButtons[FancyJoystick.BUTTON_B]) {
			RobotActuators.discIntakeRoller.set(1.0);
		} else {
			RobotActuators.discIntakeRoller.set(0.0);
		}
		
		// TODO: Remove this to disable pid updates from smartdashboard
		if (_secondaryButtons[FancyJoystick.BUTTON_RIGHTJOY]) {
			RobotShoot.updatePIDConstants();
		}
    }

    /**
     * Gathers joystick button and axis values and stores them in their
     * respective arrays.
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
     * Gathers magic box values, and updates shooter multiplier and angle
     * offset.
     */
    private void updateMagicBox() {
		MagicBox.update();

		for (int i = 0; i < MagicBox.NUM_BUTTONS; i++) {
			_magicBoxButtons[i] = MagicBox.getDigitalIn(i);
		}
    }

    public void finishPhase() {
		TargetShooterAngleLogic.setIsTargeting(false);
		TargetShooterSpeedLogic.setIsTargeting(false);
		TargetSpinLogic.setIsTargeting(false);
    }
}
