/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.sensors;

import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Ben
 */
public class RobotSensors {
    //AxisCamera handled in RobotCamera[?]
    //In Limit1,2 pairs, 1 is top. 2 is bottom. 
    
    public static Encoder encoderDriveLeft;
    public static Encoder encoderDriveRight;
    
    public static Encoder encoderWinch;
    
    public static DigitalInput limitWinch1;
    public static DigitalInput limitWinch2;
    
    public static DigitalInput limitArm1;
    public static DigitalInput limitArm2;
    
    public static Encoder encoderElevator;
    
    public static DigitalInput limitElevator1;
    public static DigitalInput limitElevator2;
    
    
    public static AnalogChannel opticalHook1;
    public static AnalogChannel opticalHook2;
    public static AnalogChannel opticalHook3;
    public static AnalogChannel opticalHook4;
    
    public static Gyro gyroChassis;
    
    public static Accelerometer accelerometerChassis; // ?? ?? Is this the right class?
    
    public static DigitalInput config1;
    public static DigitalInput config2;
    public static DigitalInput config3;
    
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
        gyroChassis = new Gyro(1); //?
        opticalHook1 = new AnalogChannel(2);
        opticalHook2 = new AnalogChannel(3);
        opticalHook3 = new AnalogChannel(4);
        opticalHook4 = new AnalogChannel(5);
        opticalDiscTop = new AnalogChannel(6);
        opticalDiscBottom = new AnalogChannel(7);
        
        //ENCODER_DRIVE_LEFT1 = new Encoder(1);
        encoderDriveLeft = new Encoder(1,2);
        encoderDriveRight = new Encoder(3,4);
        
        encoderWinch = new Encoder(5,6);
        
        limitWinch1 = new DigitalInput(7);
        limitWinch2 = new DigitalInput(8);
        
        limitArm1 = new DigitalInput(9);
        limitArm2 = new DigitalInput(10);
        
        encoderElevator = new Encoder(11,12);
        
        limitElevator1 = new DigitalInput(13);
        limitElevator2 = new DigitalInput(14);
        
        config1 = new DigitalInput(2,1);
        config2 = new DigitalInput(2,2);
        config3= new DigitalInput(2,3);
        
        
        counterShooterSpeed = new FancyCounter(2,4);
        encoderShooterAngle = new Encoder(2,5,2,6);
        
    }
}
