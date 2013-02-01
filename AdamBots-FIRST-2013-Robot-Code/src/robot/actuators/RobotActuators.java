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
    
    public Victor driveLeft;
    public Victor driveRight;
    
    public Servo transmissionLeft;
    public Servo transmissionRight;
    
    //// CLIMBING --------------------------------------------------------------
    
    public Victor climbPivot;
    public Victor climbWinchLeft;
    public Victor climbWinchRight;
    
    //// DISC ACQUISITION ------------------------------------------------------
    
    public Victor discElevator;
    public Relay discLift;
    
    //// SHOOTER ---------------------------------------------------------------
    
    public Victor shooterWheelLeft;
    public Victor shooterWheelRight;
    public Victor shooterAngleMotor;
    
    
}
