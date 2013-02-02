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
	
    }
    
    //// DRIVE -----------------------------------------------------------------
    
    /** Left drive Victor. */
    public Victor driveLeft;
    /** Right drive Victor. */
    public Victor driveRight;
    
    /** Left transmission Servo.*/
    public Servo transmissionLeft;
    /** Right transmission Servo. */
    public Servo transmissionRight;
    
    //// CLIMBING --------------------------------------------------------------
    
    /** Climb pivot motor. */
    public Victor climbPivot;
    /** Climb left winch motor. */
    public Victor climbWinchLeft;
    /** Climb right winch motor. */
    public Victor climbWinchRight;
    
    //// DISC ACQUISITION ------------------------------------------------------
    
    /** Spike to control winch for the pickup system. */
    public Relay discWinch;
    
    //// SHOOTER ---------------------------------------------------------------
    
    /** Left shooter wheel motor. */
    public Victor shooterWheelLeft;
    /** Right shooter wheel motor. */
    public Victor shooterWheelRight;
    /** Controls the angle of attack of the shooter. */
    public Victor shooterAngleMotor;
    
    
}
