/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.behavior;

import edu.wpi.first.wpilibj.Victor;
import robot.actuators.RobotActuators;

/**
 *
 * @author Ben
 */
public class RobotDrive {
    
    // there will be a class TBD that will store all the robot
    // sensors, motors
    // take out the next few lines when static class is made
    public static final int PORT_LEFT_VICTOR = 1;
    public static final int PORT_RIGHT_VICTOR = 2;
    
    private static Victor driveLeft;
    private static Victor driveRight;
    
    
  
    
    /**
     * Initialize everything
     */
    public static void robotDriveInit() {
	driveRight = RobotActuators.driveRight;
	driveLeft = RobotActuators.driveLeft;
    }
    
    /**
     * 
     * @param leftSpeed to set the left speed
     * @param rightSpeed to set the right speed
     * sets the speed of the wheels to the parameters given
     */
    public static void drive( double leftSpeed, double rightSpeed ) {
	driveRight.set(rightSpeed);
	driveLeft.set(leftSpeed);
    }
    
    /**
     * 
     * @param speed for the speed of both wheels
     * calls drive and sends it the parameters of speed for both
     */
    public static void driveStraight( double speed )
    {
	driveRight.set(speed);
	driveLeft.set(speed);
    }
    
    /**
     * 
     * @param speed to turn in place
     * turns in place at the speed given
     */
    public static void turn( double speed )
    {
	driveRight.set(-speed);
	driveLeft.set(speed);
    }
    
    /**
     * 
     * stops all the motors
     */
    public static void stop()
    {
	driveRight.set(0);
	driveLeft.set(0);
    }
    
}
