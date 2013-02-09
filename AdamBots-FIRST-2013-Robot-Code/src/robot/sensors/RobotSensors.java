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
    //// SENSOR CONSTANTS ------------------------------------------------------
    
    //// SENSOR INSTANCES ------------------------------------------------------
    
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
    
    public static AnalogChannel opticalHook1;
    public static AnalogChannel opticalHook2;
    public static AnalogChannel opticalHook3;
    public static AnalogChannel opticalHook4;
    
    public static Gyro gyroChassis;
    
    public static Accelerometer accelerometerChassis; // TODO: Is this the right class?
    
    /** Robot Configuration Switch #1 */
    public static AnalogChannel config1;
    /** Robot Configuration Switch #2 */
    public static AnalogChannel config2;
    /** Robot Configuration Switch #3 */
    public static AnalogChannel config3;
    
    public static FancyCounter counterShooterSpeed;
    public static Encoder encoderShooterAngle;
    
    public static AnalogChannel opticalDiscTop;
    public static AnalogChannel opticalDiscBottom;
    
    //// INITIALIZATION --------------------------------------------------------
    
    /**
     Instantiates all sensors handled by class.
     **/
    public static void init()
    {
        gyroChassis	    = new Gyro(1);
        opticalHook1	    = new AnalogChannel(2);
        opticalHook2	    = new AnalogChannel(3);
        opticalHook3	    = new AnalogChannel(4);
        opticalHook4	    = new AnalogChannel(5);
        opticalDiscTop	    = new AnalogChannel(6);
        opticalDiscBottom   = new AnalogChannel(7);
	
        encoderDriveLeft    = new Encoder(1,2);
        encoderDriveRight   = new Encoder(3,4);
        
        encoderWinch	    = new Encoder(5,6);
        
        limitWinchA	    = new DigitalInput(7);
        limitWinchB	    = new DigitalInput(8);
        
        limitArmA	    = new DigitalInput(9);
        limitArmB	    = new DigitalInput(10);
        
        encoderElevator	    = new Encoder(11,12);
        
        limitElevatorA	    = new DigitalInput(13);
        limitElevatorB	    = new DigitalInput(14);
        
        config1		    = new AnalogChannel(2,1);
        config2		    = new AnalogChannel(2,2);
        config3		    = new AnalogChannel(2,3);
        
        counterShooterSpeed = new FancyCounter(2,4);
        encoderShooterAngle = new Encoder(2,5,2,6);
		
		counterShooterSpeed.start();
		counterShooterSpeed.setMaxPeriod(10000);
		counterShooterSpeed.setUpSourceEdge(true,false);
    }
}
