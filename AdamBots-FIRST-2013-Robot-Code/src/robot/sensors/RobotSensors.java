/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.sensors;

import edu.wpi.first.wpilibj.*;

/**
 * <p>Contains static instances of every sensor on the robot. Classes that
 * require input from sensors should access them through one of the instances
 * here; some sensors have been wrapped with a "Fancy" class, simplifying their
 * use elsewhere.</p> <p><h3>Additional Notes</h3> <ul> <li>The Axis Camera is
 * handled within RobotCamera.</li> <li>Pairs of Limit Switches are labeled with
 * either A or B, corresponding to "ABOVE" and "BELOW," respectively.</li> </ul>
 * </p> <p>Note that the Axis Camera is handled within RobotCamera.</p>
 *
 * @author Ben
 */
public class RobotSensors {
    
    //// CONSTANTS -------------------------------------------------------------
    public static final int ANA1 = 1;
    public static final int DIO1 = 2;
    public static final int DIO2 = 3;
    public static final int SOL1 = 4;
    
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
    public static void init() {

        //// ANALOG CARD -------------------------------------------------------

        gyroChassis = new Gyro(ANA1, 1); //?
        config1 = new AnalogChannel(ANA1, 2);
        config2 = new AnalogChannel(ANA1, 3);
        config3 = new AnalogChannel(ANA1, 4);

        //// DIGITAL CARD 1 ----------------------------------------------------

        encoderDriveLeft = new Encoder(DIO1, 1, DIO1, 2);
        encoderDriveRight = new Encoder(DIO1, 3, DIO1, 4);

        encoderWinch = new Encoder(DIO1, 5, DIO1, 6);

        limitWinchA = new DigitalInput(DIO1, 7);
        limitWinchB = new DigitalInput(DIO1, 8);

        limitArmA = new DigitalInput(DIO1, 9);
        limitArmB = new DigitalInput(DIO1, 10);

        encoderElevator = new Encoder(DIO1, 11, DIO1, 12);

        limitElevatorA = new DigitalInput(DIO1, 13);
        limitElevatorB = new DigitalInput(DIO1, 14);

        //// DIGITAL CARD 2 ----------------------------------------------------
        counterShooterSpeed = new FancyCounter(DIO2, 1);
        counterShooterSpeed.start();
        counterShooterSpeed.setMaxPeriod(100000);
        counterShooterSpeed.setUpSourceEdge(true, false);

        encoderShooterAngle = new Encoder(DIO2, 2, DIO2, 3);

        limitHookLeftArm = new DigitalInput(DIO2, 4);
        limitHookRightArm = new DigitalInput(DIO2, 5);
        limitHookLeftBase = new DigitalInput(DIO2, 6);
        limitHookRightBase = new DigitalInput(DIO2, 7);

        limitDiscTop = new DigitalInput(DIO2, 8);
        limitDiscBottom = new DigitalInput(DIO2, 9);

        limitShooterA = new DigitalInput(DIO2, 10);
        limitShooterB = new DigitalInput(DIO2, 11);
    }
}
