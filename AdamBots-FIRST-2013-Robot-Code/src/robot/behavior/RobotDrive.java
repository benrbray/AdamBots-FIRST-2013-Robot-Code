/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.behavior;

import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author Ben
 */
public class RobotDrive {
    
    // there will be a class TBD that will store all the robot
    // sensors, motors
    // take out the next few lines when static class is made
    public final int PORT_LEFT_VICTOR = 1;
    public final int PORT_RIGHT_VICTOR = 1;
    
    private Victor _leftVictor = new Victor( PORT_LEFT_VICTOR );
    private Victor _rightVictor = new Victor( PORT_RIGHT_VICTOR );
    
    
    public RobotDrive( )
    {
	
	
    }
    
    /**
     * 
     * @param leftSpeed to set the left speed
     * @param rightSpeed to set the right speed
     * sets the speed of the wheels to the parameters given
     */
    public void drive( double leftSpeed, double rightSpeed )
    {
	
    }
    
    /**
     * 
     * @param speed for the speed of both wheels
     * calls drive and sends it the parameters of speed for both
     */
    public void driveStraight( double speed )
    {

    }
    
    /**
     * 
     * @param speed to turn in place
     * turns in place at the speed given
     */
    public void turn( double speed )
    {
	
    }
    
    /**
     * stops all the motors
     */
    public void stop()
    {
	
    }
    
}
