/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.actuators;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Victor;

/**
 * Contains static instances of robot actuators.
 * @author Ben
 */
public class RobotActuators {
    
    //// INITIALIZATION --------------------------------------------------------
    
    /**
     * Initializes static actuator instances.
     */
    public void init(){
	driveLeft = new Victor(1);
	driveRight = new Victor(2);
	
	transmissionLeft = new Servo(9);
	transmissionRight = new Servo(10);
	
	shooterWheelMotor = new Victor(3);
	shooterAngleMotor = new Victor(4);
	
	discWinch = new Relay(1);
	
	climbWinch = new Victor(7);
	
    }
    
    //// DRIVE -----------------------------------------------------------------
    
    /** Left drive Victor. */
    public static Victor driveLeft;
    /** Right drive Victor. */
    public static Victor driveRight;
    
    /** Left transmission Servo.*/
    public static Servo transmissionLeft;
    /** Right transmission Servo. */
    public static Servo transmissionRight;
    
    //// CLIMBING --------------------------------------------------------------
    
    /** Climbing Winch. */
    public static Victor climbWinch;
    
    //// DISC ACQUISITION ------------------------------------------------------
    
    /** Spike to control winch for the pickup system. */
    public static Relay discWinch;
    
    //// SHOOTER ---------------------------------------------------------------
    
    /** Shooter wheel motor. */
    public static Victor shooterWheelMotor;
    /** Controls the angle of attack of the shooter. */
    public static Victor shooterAngleMotor;
}
