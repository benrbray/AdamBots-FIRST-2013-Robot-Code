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
    
    public static FancyCounter encoderDriveLeft1;
    public static FancyCounter encoderDriveRight1;
    public static FancyCounter encoderDriveLeft2;
    public static FancyCounter encoderDriveRight2;
    
    public static FancyCounter encoderWinch1;
    public static FancyCounter encoderWinch2;
    
    public static DigitalInput limitWinch1;
    public static DigitalInput limitWinch2;
    
    public static DigitalInput limitArm1;
    public static DigitalInput limitArm2;
    
    public static FancyCounter encoderElevator1;
    public static FancyCounter encoderElevator2;
    
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
    
    public static FancyCounter encoderShooterSpeed;
    public static FancyCounter encoderShooterAngle1;
    public static FancyCounter encoderShooterAngle2;
    
    public static AnalogChannel opticalDiscTop;
    public static AnalogChannel opticalDiscBottom;
    
    /**
     Instantiates all sensors handled by class.
     **/
    public static void initialize()
    {
        gyroChassis = new Gyro(1); //?
        opticalHook1 = new AnalogChannel(2);
        opticalHook2 = new AnalogChannel(3);
        opticalHook3 = new AnalogChannel(4);
        opticalHook4 = new AnalogChannel(5);
        opticalDiscTop = new AnalogChannel(6);
        opticalDiscBottom = new AnalogChannel(7);
        
        //ENCODER_DRIVE_LEFT1 = new Encoder(1);
        encoderDriveLeft1 = new FancyCounter(1);
        encoderDriveLeft2 = new FancyCounter(2);
        encoderDriveRight1 = new FancyCounter(3);
        encoderDriveRight2 = new FancyCounter(4);
        
        encoderWinch1 = new FancyCounter(5);
        encoderWinch2 = new FancyCounter(6);
        
        limitWinch1 = new DigitalInput(7);
        limitWinch2 = new DigitalInput(8);
        
        limitArm1 = new DigitalInput(9);
        limitArm2 = new DigitalInput(10);
        
        encoderElevator1 = new FancyCounter(11);
        encoderElevator2 = new FancyCounter(12);
        
        limitElevator1 = new DigitalInput(13);
        limitElevator2 = new DigitalInput(14);
        
        config1 = new DigitalInput(2,1);
        config2 = new DigitalInput(2,2);
        config3= new DigitalInput(2,3);
        
        
        encoderShooterSpeed = new FancyCounter(2,4);
        encoderShooterAngle1 = new FancyCounter(2,5);
        encoderShooterAngle2 = new FancyCounter(2,6);
        
    }
    
    
}
