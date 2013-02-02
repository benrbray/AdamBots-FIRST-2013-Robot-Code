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
    public static Victor driveLeft;
    /** Right drive Victor. */
    public static Victor driveRight;
    
    /** Left transmission Servo.*/
    public static Servo transmissionLeft;
    /** Right transmission Servo. */
    public static Servo transmissionRight;
    
    //// CLIMBING --------------------------------------------------------------
    
    /** Climb pivot motor. */
    public static Victor climbPivot;
    /** Climb left winch motor. */
    public static Victor climbWinchLeft;
    /** Climb right winch motor. */
    public static Victor climbWinchRight;
    
    //// DISC ACQUISITION ------------------------------------------------------
    
    /** Disc elevator motor. */
    public static Victor discElevator;
    /** Spike for disc lift system. */
    public static Relay discLift;
    
    //// SHOOTER ---------------------------------------------------------------
    
    /** Shooter wheel motor. */
    public static Victor shooterWheel;
    /** Controls the angle of attack of the shooter. */
    public static Victor shooterAngleMotor;
    
    
}
