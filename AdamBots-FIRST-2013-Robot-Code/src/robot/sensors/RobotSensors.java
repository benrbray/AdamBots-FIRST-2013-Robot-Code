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
    
    public static FancyCounter ENCODER_CLIMBER;
    
    public static FancyCounter ENCODER_DRIVE_LEFT1;
    public static FancyCounter ENCODER_DRIVE_RIGHT1;
    public static FancyCounter ENCODER_DRIVE_LEFT2;
    public static FancyCounter ENCODER_DRIVE_RIGHT2;
    
    public static FancyCounter ENCODER_WINCH1;
    public static FancyCounter ENCODER_WINCH2;
    
    public static DigitalInput LIMIT_WINCH1;
    public static DigitalInput LIMIT_WINCH2;
    
    public static DigitalInput LIMIT_ARM1;
    public static DigitalInput LIMIT_ARM2;
    
    public static FancyCounter ENCODER_ELEVATOR1;
    public static FancyCounter ENCODER_ELEVATOR2;
    
    public static DigitalInput LIMIT_ELEVATOR1;
    public static DigitalInput LIMIT_ELEVATOR2;
    
    
    public static AnalogChannel OPTICAL_HOOK1;
    public static AnalogChannel OPTICAL_HOOK2;
    public static AnalogChannel OPTICAL_HOOK3;
    public static AnalogChannel OPTICAL_HOOK4;
    
    public static Gyro GYRO_CHASSIS;
    
    public static Accelerometer ACCELEROMETER_CHASSIS; // ?? ?? Is this the right class?
    
    public static DigitalInput CONFIG1;
    public static DigitalInput CONFIG2;
    public static DigitalInput CONFIG3;
    
    public static FancyCounter ENCODER_SHOOTER_SPEED;
    public static FancyCounter ENCODER_SHOOTER_ANGLE1;
    public static FancyCounter ENCODER_SHOOTER_ANGLE2;
    
    public static AnalogChannel OPTICAL_DISC_TOP;
    public static AnalogChannel OPTICAL_DISC_BOTTOM;
    
    /**
     Instantiates all sensors handled by class.
     **/
    public static void initialize()
    {
        GYRO_CHASSIS = new Gyro(1); //?
        OPTICAL_HOOK1 = new AnalogChannel(2);
        OPTICAL_HOOK2 = new AnalogChannel(3);
        OPTICAL_HOOK3 = new AnalogChannel(4);
        OPTICAL_HOOK4 = new AnalogChannel(5);
        OPTICAL_DISC_TOP = new AnalogChannel(6);
        OPTICAL_DISC_BOTTOM = new AnalogChannel(7);
        
        //ENCODER_DRIVE_LEFT1 = new Encoder(1);
        ENCODER_DRIVE_LEFT1 = new FancyCounter(1);
        ENCODER_DRIVE_LEFT2 = new FancyCounter(2);
        ENCODER_DRIVE_RIGHT1 = new FancyCounter(3);
        ENCODER_DRIVE_RIGHT2 = new FancyCounter(4);
        
        ENCODER_WINCH1 = new FancyCounter(5);
        ENCODER_WINCH2 = new FancyCounter(6);
        
        LIMIT_WINCH1 = new DigitalInput(7);
        LIMIT_WINCH2 = new DigitalInput(8);
        
        LIMIT_ARM1 = new DigitalInput(9);
        LIMIT_ARM2 = new DigitalInput(10);
        
        ENCODER_ELEVATOR1 = new FancyCounter(11);
        ENCODER_ELEVATOR2 = new FancyCounter(12);
        
        LIMIT_ELEVATOR1 = new DigitalInput(13);
        LIMIT_ELEVATOR2 = new DigitalInput(14);
        
        CONFIG1 = new DigitalInput(2,1);
        CONFIG2 = new DigitalInput(2,2);
        CONFIG3 = new DigitalInput(2,3);
        
        
        ENCODER_SHOOTER_SPEED = new FancyCounter(2,4);
        ENCODER_SHOOTER_ANGLE1 = new FancyCounter(2,5);
        ENCODER_SHOOTER_ANGLE2 = new FancyCounter(2,6);
        
    }
    
    
}
