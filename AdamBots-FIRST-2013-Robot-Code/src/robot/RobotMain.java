/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package robot;


import edu.wpi.first.wpilibj.IterativeRobot;
import robot.actuators.RobotActuators;
import robot.behavior.RobotClimb;
import robot.behavior.RobotDrive;
import robot.behavior.RobotPickup;
import robot.behavior.RobotShoot;
import robot.logic.LogicPhase;
import robot.logic.PIDLogic;
import robot.logic.auton.AutonLogic;
import robot.logic.climb.ClimbLogic;
import robot.logic.teleop.TeleopLogic;
import robot.sensors.RobotSensors;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation.
 * 
 * RobotMain includes static instances of each behavior class, because they will
 * be commonly referenced elsewhere, and we don't want to pass them as arguments
 * all over the place.
 * 
 * @author Ben Bray
 * @author Steven Ploog
 */
public class RobotMain extends IterativeRobot {
    //// STATIC INSTANCE VARIABLES ---------------------------------------------
    
    /** Statically accessible instance of RobotDrive. */
    public static RobotDrive robotDrive;
    /** Statically accessible instance of RobotPickup. */
    public static RobotPickup robotPickup;
    /** Statically accessible instance of RobotShoot. */
    public static RobotShoot robotShoot;
    /** Statically accessible instance of RobotClimb. */
    public static RobotClimb robotClimb;
    /** Statically accessible instance of RobotSensors. */
    public static RobotSensors robotSensors;
    
    //// ROBOT LOGIC PHASES ----------------------------------------------------
    
    private LogicPhase _currentLogicPhase = null;
    private AutonLogic _autonLogic;
    private TeleopLogic _teleopLogic;
    private ClimbLogic _climbLogic;
    
    //// ITERATIVE ROBOT METHODS -----------------------------------------------
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
	// Initialize Classes with Static References
	RobotActuators.init();
	RobotSensors.init();
    }
    
    //// AUTONOMOUS ------------------------------------------------------------
    
    /**
     * Initialization code for the autonomous period.
     */
    public void autonomousInit() {
	_autonLogic = new AutonLogic();
	segueToLogicPhase(_autonLogic);
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
	_teleopLogic = new TeleopLogic();
	_climbLogic = new ClimbLogic();
	segueToLogicPhase(_teleopLogic);
	
	if(_autonLogic != null){ _autonLogic = null; }
    }
    
    /**
     * This function is called periodically during operator control.
     */
    public void teleopPeriodic() {
	// Update the Current Logic Phase (should be _teleopLogic or _climbLogic)
	update();
    }
    
    //// UPDATE ----------------------------------------------------------------
    
    public void update(){
	// Update the current LogicPhase
	_currentLogicPhase.updatePhase();
	
	// Update Subsystems
	robotShoot.update();
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
	
    }

    /**
     *  Periodic code for disabled mode should go here.
     */
    public void disabledPeriodic() {
	
    }
    
    //// LOGICPHASE METHODS ----------------------------------------------------
    
    /**
     * Revokes power from the logic phase currently in control and grants
     * control to the phase specified.  Before the segue, this method invokes
     * finish() in the original phase, and after the segue, this method invokes 
     * init() in the new phase.
     * @param phase An integer indicating the phase to switch to.
     * @return Boolean value indicating the success or failure of the segue.
     * @see LogicPhase#AUTONOMOUS
     * @see LogicPhase#TELEOP
     * @see LogicPhase#CLIMB
     */
    public boolean segueToLogicPhase(int phase){
	LogicPhase segueTo;
	switch(phase){
	    case LogicPhase.AUTONOMOUS:
		segueTo = new AutonLogic();
		break;
	    case LogicPhase.TELEOP:
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
     * control to the phase specified.  Before the segue, this method invokes
     * finish() in the original phase, and after the segue, this method invokes 
     * init() in the new phase.
     * @param phase The phase to transition to.
     * @return Boolean value indicating the success or failure of the segue.
     * @see LogicPhase
     * @see LogicPhase#finish() 
     * @see LogicPhase#init();
     */
    public boolean segueToLogicPhase(LogicPhase phase){
	if(_currentLogicPhase != null){
	    _currentLogicPhase.finishPhase();
	}
	
	_currentLogicPhase = phase;
	_currentLogicPhase.initPhase();
	
	return true; // TODO:  Update segueToLogicPhase() return value as needed.
    }
}
