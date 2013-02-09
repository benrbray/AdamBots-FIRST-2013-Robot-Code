/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.sensors;

import edu.wpi.first.wpilibj.*;

/**
 * <p>Contains static instances of every sensor on the robot.  Classes that require
 * input from sensors should access them through one of the instances here; some
 * sensors have been wrapped with a "Fancy" class, simplifying their use
 * elsewhere.</p>
 * <p><h3>Additional Notes</h3>
 *   <ul>
 *     <li>The Axis Camera is handled within RobotCamera.</li>
 *     <li>Pairs of Limit Switches are labeled with either A or B, corresponding
 *	   to "ABOVE" and "BELOW," respectively.</li>
 *   </ul>
 * </p>
 * <p>Note that the Axis Camera is handled within RobotCamera.</p>
 * @author Ben
 */
public class RobotSensors {
    //AxisCamera handled in RobotCamera[?]
    //In Limit1,2 pairs, 1 is top. 2 is bottom.
    
    public static Encoder encoderDriveLeft;
    public static Encoder encoderDriveRight;
    
    public static Encoder encoderWinch;
    
    public static DigitalInput limitWinchA;
    public static DigitalInput limitWinchB;
    
    public static DigitalInput limitArmA;
    public static DigitalInput limitArmB;
    
    public static Encoder encoderElevator;
    
    public static DigitalInput limitElevatorA;
    public static DigitalInput limitElevatorB;
    
    
    public static DigitalInput limitHookLeftArm;
    public static DigitalInput limitHookRightArm;
    public static DigitalInput limitHookLeftBase;
    public static DigitalInput limitHookRightBase;
    
    public static Gyro gyroChassis;
    
    public static Accelerometer accelerometerChassis; // ?? ?? Is this the right class?
    
    public static AnalogChannel config1;
    public static AnalogChannel config2;
    public static AnalogChannel config3;
    
    public static FancyCounter counterShooterSpeed;
    public static Encoder encoderShooterAngle;
    
    public static DigitalInput limitDiscTop;
    public static DigitalInput limitDiscBottom;
    
    public static DigitalInput limitShooterA;
    public static DigitalInput limitShooterB;
    
    /**
     * Instantiates all sensors handled by class.
     */
    public static void init()
    {
	
	//// ANALOG CARD -------------------------------------------------------
	
        gyroChassis = new Gyro(1); //?
        config1 = new AnalogChannel(2);
        config2 = new AnalogChannel(3);
        config3 = new AnalogChannel(4);
	
	//// DIGITAL CARD 1 ----------------------------------------------------
	
        encoderDriveLeft = new Encoder(1,2);
        encoderDriveRight = new Encoder(3,4);
        
        encoderWinch = new Encoder(5,6);
        
        limitWinchA = new DigitalInput(7);
        limitWinchB = new DigitalInput(8);
        
        limitArmA = new DigitalInput(9);
        limitArmB = new DigitalInput(10);
        
        encoderElevator = new Encoder(11,12);
        
        limitElevatorA = new DigitalInput(13);
        limitElevatorB = new DigitalInput(14);
        
	//// DIGITAL CARD 2 ----------------------------------------------------
        counterShooterSpeed = new FancyCounter(2,1);
        encoderShooterAngle = new Encoder(2,2,2,3);
        
        limitHookLeftArm = new DigitalInput(2,4);
        limitHookRightArm = new DigitalInput(2,5);
        limitHookLeftBase = new DigitalInput(2,6);
        limitHookRightBase = new DigitalInput(2,7);
        
        limitDiscTop = new DigitalInput(2,8);
        limitDiscBottom = new DigitalInput(2,9);
        
        limitShooterA = new DigitalInput(2,10);
        limitShooterB = new DigitalInput(2,11);
    }   
}
