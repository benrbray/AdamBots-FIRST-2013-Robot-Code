/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package robot;


import edu.wpi.first.wpilibj.IterativeRobot;
import robot.behavior.RobotClimb;
import robot.behavior.RobotDrive;
import robot.behavior.RobotPickup;
import robot.behavior.RobotShoot;
import robot.sensors.RobotCamera;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotMain extends IterativeRobot {
    //// STATIC INSTANCE VARIABLES ---------------------------------------------
    
    /*
     * RobotMain includes these static instances because they will be commonly
     * referenced elsewhere, and we don't want to pass them as arguments all
     * over the place.
     * 
     * (Ben 1/23/13)
     */
    
    /** Statically accessible instance of RobotDrive. */
    public static RobotDrive robotDrive;
    /** Statically accessible instance of RobotPickup. */
    public static RobotPickup robotPickup;
    /** Statically accessible instance of RobotShoot. */
    public static RobotShoot robotShoot;
    /** Statically accessible instance of RobotCamera. */
    public static RobotCamera robotCamera;
    /** Statically accessible instance of RobotClimb. */
    public static RobotClimb robotClimb;
    
    //// ITERATIVE ROBOT METHODS -----------------------------------------------
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
	
    }
    
    //// AUTONOMOUS ------------------------------------------------------------
    
    public void autonomousInit() {
	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }
    
    //// TELEOP ----------------------------------------------------------------

    /**
     * Initialization code for teleop mode should go here
     */
    public void teleopInit() {
	
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
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
    
}
