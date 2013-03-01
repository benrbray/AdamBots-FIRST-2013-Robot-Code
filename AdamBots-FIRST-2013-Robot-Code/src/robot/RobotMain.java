/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.IO.DataIO;
import robot.actuators.FancyMotor;
import robot.actuators.RobotActuators;
import robot.behavior.RobotClimb;
import robot.behavior.RobotDrive;
import robot.behavior.RobotPickup;
import robot.behavior.RobotShoot;
import robot.camera.RobotCamera;
import robot.control.FancyJoystick;
import robot.logic.LogicPhase;
import robot.logic.targeting.TargetShooterAngleLogic;
import robot.logic.targeting.TargetShooterSpeedLogic;
import robot.logic.targeting.TargetSpinLogic;
import robot.logic.auton.AutonLogic;
import robot.logic.climb.ClimbLogic;
import robot.logic.teleop.TeleopLogic;
import robot.sensors.RobotSensors;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation.
 *
 * @author Ben Bray
 * @author Steven Ploog
 */
public final class RobotMain extends IterativeRobot {
    //// INSTANCE --------------------------------------------------------------

    private static RobotMain _instance;

    /** Gets the active instance of RobotMain. **/
    public static RobotMain getInstance() {
		return _instance;
    }
    
    //// OUTPUT CONSTANTS ------------------------------------------------------
    
    public static final boolean VERBOSE_AUTON		= false;
    public static final boolean VERBOSE_TELEOP		= false;
    public static final boolean VERBOSE_CLIMB			= true;
    public static final boolean VERBOSE_ROBOTCLIMB		= true;
    public static final boolean VERBOSE_ROBOTDRIVE	= false;
    public static final boolean VERBOSE_ROBOTPICKUP	= false;
    public static final boolean VERBOSE_ROBOTSHOOT	= false;
    public static final boolean VERBOSE_ROBOTCAMERA	= false;
    public static final boolean VERBOSE_TARGETLOGIC	= false;
	public static final boolean VERBOSE_FANCYMOTOR		= true;
    
    //// ROBOT LOGIC PHASES ----------------------------------------------------
    
    private LogicPhase _currentLogicPhase = null;
    private AutonLogic _autonLogic;
    private TeleopLogic _teleopLogic;
    private ClimbLogic _climbLogic;
    
    //// JOYSTICKS -------------------------------------------------------------
    
    public static FancyJoystick primaryJoystick;
    public static FancyJoystick secondaryJoystick;

    //// ITERATIVE ROBOT METHODS -----------------------------------------------
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		_instance = this;
		
		// Initialize Classes with Static References
		FancyMotor.init();
		RobotActuators.init();
		RobotSensors.init();
		RobotActuators.configure();
		RobotSensors.configure();

		// Initialize Static Behavior Classes
		RobotDrive.init();
		RobotShoot.init();
		RobotPickup.init();

		//Initialize Static Logic Classes
		TargetShooterAngleLogic.init();
		TargetShooterSpeedLogic.init();
		TargetSpinLogic.init();

		// Output Filtering
		// TODO:  Print Filtering Broken
		RobotClimb.verboseOutput = VERBOSE_ROBOTCLIMB;
		RobotDrive.verboseOutput = VERBOSE_ROBOTDRIVE;
		RobotPickup.verboseOutput = VERBOSE_ROBOTPICKUP;
		RobotShoot.verboseOutput = VERBOSE_ROBOTSHOOT;
		RobotCamera.verboseOutput = VERBOSE_ROBOTCAMERA;
		TargetShooterAngleLogic.verboseOutput = VERBOSE_TARGETLOGIC;
		TargetShooterSpeedLogic.verboseOutput = VERBOSE_TARGETLOGIC;
		TargetSpinLogic.verboseOutput = VERBOSE_TARGETLOGIC;
		FancyMotor.verboseOutput = VERBOSE_FANCYMOTOR;

		// Initialize Joysticks
		primaryJoystick = new FancyJoystick(FancyJoystick.PRIMARY_DRIVER, .15);
		secondaryJoystick = new FancyJoystick(FancyJoystick.SECONDARY_DRIVER);

		// Turn lights on
		//RobotActuators.yellowLEDStrip.set(true);
    }

    //// AUTONOMOUS ------------------------------------------------------------
    
    /**
     * Initialization code for the autonomous period.
     */
    public void autonomousInit() {
		RobotCamera.init();
		_autonLogic = new AutonLogic();
		_autonLogic.verboseOutput = VERBOSE_AUTON;
		segueToLogicPhase(_autonLogic);

		RobotActuators.cameraLED.set(true);
    }

    /**
     * This function is called periodically during autonomous.
     */
    public void autonomousPeriodic() {
		// Update the Current Logic Phase (should be _autonLogic)
		update();
    }

    //// TELEOP ----------------------------------------------------------------
    
    /**
     * Initialization code for the teleoperated period.
     */
    public void teleopInit() {
		System.out.println("Teleop Init");
		// Camera Init
		RobotCamera.init();
		
		RobotCamera.init();
		_teleopLogic = new TeleopLogic();
		_climbLogic = new ClimbLogic();
		_teleopLogic.verboseOutput = VERBOSE_TELEOP;
		_climbLogic.verboseOutput = VERBOSE_AUTON;
		segueToLogicPhase(_teleopLogic);

		RobotActuators.cameraLED.set(true);
		RobotActuators.greenLEDStrip.set(true);
		RobotActuators.redLEDStrip.set(true);
		RobotActuators.yellowLEDStrip.set(true);

		if (_autonLogic != null) {
			_autonLogic = null;
		}
		RobotSensors.encoderDriveLeft.start();
		RobotSensors.encoderDriveLeft.reset();
		RobotSensors.encoderDriveRight.start();
		RobotSensors.encoderDriveRight.reset();
		
		System.out.println("TeleopInit finished.");
    }

    /**
     * This function is called periodically during operator control.
     */
    public void teleopPeriodic() {
		// Update the Current Logic Phase (should be _teleopLogic or _climbLogic)
		update();
		//System.out.println("L:" + RobotSensors.encoderDriveLeft.getRaw());
		//System.out.println("R:" + RobotSensors.encoderDriveRight.getRaw());
    }

    //// UPDATE ----------------------------------------------------------------
    
    public void update() {
		// Update the current LogicPhase
		_currentLogicPhase.updatePhase();
		
		// Compressor
		if (RobotSensors.pressureSwitch.get()) {
			RobotActuators.compressor.set(Relay.Value.kOff);
		} else {
			RobotActuators.compressor.set(Relay.Value.kOn);
		}
		
		// Reset Shooter Lift Encoder if it's at the Bottom of its Range
		if(RobotSensors.limitShooterB.get()){
			RobotSensors.counterShooterAngle.reset();
		}
		
		SmartDashboard.putBoolean("shooterAngleLimitB", RobotSensors.limitShooterB.get());

		// Update Subsystems
		TargetShooterSpeedLogic.update();
		TargetShooterAngleLogic.update();
		TargetSpinLogic.update();
		RobotShoot.update();
		RobotCamera.update();
		RobotPickup.update();
		RobotClimb.update();
		FancyMotor.update();	// Checks Limit Switches for each FancyMotor
		
		// Print to Dashboard
		SmartDashboard.putBoolean("pressureSwitch", RobotSensors.pressureSwitch.get());
		SmartDashboard.putNumber("CameraDistance", RobotCamera.getDistanceInches());
		//SmartDashboard.putNumber("Speed Wheel", RobotSensors.counterShooterSpeed.get());
		SmartDashboard.putNumber("Encoder Angle", RobotSensors.counterShooterAngle.get());
		if (RobotCamera._greenTarget != null) {
			SmartDashboard.putString("Target Location","(" + RobotCamera._greenTarget.x + "," + RobotCamera._greenTarget.y + ")");
		}
		
    }

    //// TEST ------------------------------------------------------------------
    
    /**
     * Initialization code for test mode should go here
     */
    public void testInit() {
    
	}

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
	}

    //// DISABLED --------------------------------------------------------------
    
    /**
     * Initialization code for disabled mode should go here
     */
    public void disabledInit() {
		System.out.println("Disabled Init");
		RobotDrive.switchGear(RobotDrive.SHIFTER_NEUTRAL);

		RobotActuators.cameraLED.set(false);
		RobotActuators.greenLEDStrip.set(false);
		RobotActuators.redLEDStrip.set(false);
		
		DataIO.logData();
    }

    /**
     * Periodic code for disabled mode should go here.
     */
    public void disabledPeriodic() {
		RobotDrive.switchGear(RobotDrive.SHIFTER_NEUTRAL);
		//SmartDashboard.putNumber("gyroAngle", RobotSensors.gyroChassis.getAngle());
		//SmartDashboard.putNumber("accelerometerAccel", RobotSensors.accelerometerChassis.getAcceleration());
		//SmartDashboard.putBoolean("configSwitchA", RobotSensors.configA.get());
		//SmartDashboard.putBoolean("configSwitchB", RobotSensors.configB.get());
		//SmartDashboard.putBoolean("configSwitchC", RobotSensors.configC.get());
    }

    //// LOGICPHASE METHODS ----------------------------------------------------
    /**
     * Revokes power from the logic phase currently in control and grants
     * control to the phase specified. Before the segue, this method invokes
     * finish() in the original phase, and after the segue, this method invokes
     * init() in the new phase.
     *
     * @param phase An integer indicating the phase to switch to.
     * @return Boolean value indicating the success or failure of the segue.
     * @see LogicPhase#AUTONOMOUS
     * @see LogicPhase#TELEOP
     * @see LogicPhase#CLIMB
     */
    public boolean segueToLogicPhase(int phase) {
		System.out.println("SegueToLogicPhase() with int");
		LogicPhase segueTo;
		switch (phase) {
			case LogicPhase.AUTONOMOUS:
			System.out.println("Moving to auton phase");
			segueTo = new AutonLogic();
			break;
			case LogicPhase.TELEOP:
			System.out.println("Moving to teleop phase");
			segueTo = new TeleopLogic();
			break;
			case LogicPhase.CLIMB:
			segueTo = new ClimbLogic();
			break;
			default:
			throw new IllegalArgumentException();
		}

		return segueToLogicPhase(segueTo);
    }

    /**
     * Revokes power from the logic phase currently in control and grants
     * control to the phase specified. Before the segue, this method invokes
     * finish() in the original phase, and after the segue, this method invokes
     * init() in the new phase.
     *
     * @param phase The phase to transition to.
     * @return Boolean value indicating the success or failure of the segue.
     * @see LogicPhase
     * @see LogicPhase#finishPhase()
     * @see LogicPhase#initPhase()
     */
    public boolean segueToLogicPhase(LogicPhase phase) {
		if (_currentLogicPhase != null) {
			_currentLogicPhase.finishPhase();
		}

		_currentLogicPhase = phase;
		_currentLogicPhase.initPhase();
		
		System.out.println("SegueToLogicPhase() finished");

		return true; // TODO:  Update segueToLogicPhase() return value as needed.
    }
}
