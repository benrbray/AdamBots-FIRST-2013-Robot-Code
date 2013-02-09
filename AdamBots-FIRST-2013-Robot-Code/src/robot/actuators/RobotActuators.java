package robot.actuators;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

/**
 * Contains static instances of robot actuators.
 * @author Ben
 */
public class RobotActuators {
    //// ENFORCE NONINSTANTIABILITY --------------------------------------------
    
    private RobotActuators() throws Exception {
	throw new java.lang.Exception("You can't instantiate RobotActuators, silly!");
    }
    
    //// INITIALIZATION --------------------------------------------------------
    
    /**
     * Initializes static actuator instances.
     */
    public static void init(){
	driveLeft	    = new Victor(1);
	driveRight	    = new Victor(2);
	shooterWheelMotor   = new Victor(3);
	shooterAngleMotor   = new Victor(4);
	discElevator	    = new Talon(5);
	climbWinch	    = new Talon(6);
	climbArm	    = new Victor(7);
	transmissionLeft    = new Servo(8);
	transmissionRight   = new Servo(9);
	
	discIntake	    = new Relay(1);
	shooterFeederSolenoid = new Relay(2);
	climbWinchRelease   = new Relay(4);
	
	discIntake.setDirection(Relay.Direction.kBoth);
	shooterFeederSolenoid.setDirection(Relay.Direction.kForward);
	climbWinchRelease.setDirection(Relay.Direction.kForward);
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
    
    /** Climbing Winch. */
    public static Talon climbWinch;
    /** Climb arm motor */
    public static Victor climbArm;
    /** Winch ratchet release solenoid. */
    public static Relay climbWinchRelease;
    
    //// DISC ACQUISITION ------------------------------------------------------
    
    /** Disc pickup elevator winch. */
    public static Talon discElevator;
    /** Spike to control the intake roller. */
    public static Relay discIntake;
    
    //// SHOOTER ---------------------------------------------------------------
    
    /** Shooter wheel motor. */
    public static Victor shooterWheelMotor;
    /** Controls the angle of attack of the shooter. */
    public static Victor shooterAngleMotor;
    /** Shooter feeder solenoid. */
    public static Relay shooterFeederSolenoid;
}
