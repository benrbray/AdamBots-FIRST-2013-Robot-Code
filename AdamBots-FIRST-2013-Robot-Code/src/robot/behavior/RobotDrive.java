/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.behavior;

import edu.wpi.first.wpilibj.*;
import robot.actuators.RobotActuators;


/**
 *
 * @author Ben
 */
public abstract class RobotDrive extends RobotBehavior {
    
    // there will be a class TBD that will store all the robot
    // sensors, motors
    // take out the next few lines when static class is made
    public static final int PORT_LEFT_VICTOR = 1;
    public static final int PORT_RIGHT_VICTOR = 2;
    /** Shifter value*/
    public static final double SHIFTER_LOW = 1.0;
    public static final double SHIFTER_HIGH = 0.0;
    public static final double SHIFTER_NEUTRAL = 0.65;
    
    //private robot.actuators.RobotActuators robotActuators;
    
    /**
     * Initialize everything
     */
    public static void init() {
    }
    
    /**
     * 
     * @param leftSpeed to set the left speed
     * @param rightSpeed to set the right speed
     * sets the speed of the wheels to the parameters given
     */
    public static void drive( double leftSpeed, double rightSpeed ) {
	RobotActuators.driveRight.set(rightSpeed);
	RobotActuators.driveLeft.set(leftSpeed);
    }
    
    /**
     * 
     * @param speed for the speed of both wheels
     * calls drive and sends it the parameters of speed for both
     */
    public static void driveStraight( double speed )
    {
	RobotActuators.driveRight.set(speed);
	RobotActuators.driveLeft.set(speed);
    }
    
    /**
     * 
     * @param speed to turn in place
     * turns in place at the speed given
     */
    public static void turn( double speed )
    {
	RobotActuators.driveRight.set(-speed);
	RobotActuators.driveLeft.set(speed);
    }
    
    /**
     * 
     * stops all the motors
     */
    public static void stop()
    {
	RobotActuators.driveRight.set(0);
	RobotActuators.driveLeft.set(0);
    }
    
    /**
     * switches gear when called
     * @param ServoPosition used to set transmissionLeft position
     */
    public static void switchGear( double ServoPosition){
//        RobotActuators.transmissionLeft.set(ServoPosition);
//        System.out.println(RobotActuators.transmissionLeft.get());

        RobotActuators.transmissionLeft.set(ServoPosition);
	RobotActuators.transmissionRight.set(ServoPosition);
    }
}