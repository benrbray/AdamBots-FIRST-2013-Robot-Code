package robot.actuators;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
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
	shooterAngleMotor   = new Talon(4);
	discElevator	    = new Talon(5);
	climbWinch	    = new Talon(6);
	transmissionLeft    = new Servo(7);
	transmissionRight   = new Servo(8);
	
	discIntake	    = new Relay(1);
	shooterFeederSolenoid = new Solenoid(2);
	hopperSolenoid = new Relay(3);
	climbWinchSolenoid   = new Relay(4);
	
	discIntake.setDirection(Relay.Direction.kBoth);
        shooterFeederSolenoid.set(false);
	hopperSolenoid.setDirection(Relay.Direction.kBoth);
	climbWinchSolenoid.setDirection(Relay.Direction.kForward);
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
    /** Winch ratchet release solenoid. */
    public static Relay climbWinchSolenoid;
    
    //// DISC ACQUISITION ------------------------------------------------------
    
    /** Disc pickup elevator winch. */
    public static Talon discElevator;
    /** Spike to control the intake roller. */
    public static Relay discIntake;
    /** Controls the hopper solenoid. */
    public static Relay hopperSolenoid;
    
    //// SHOOTER ---------------------------------------------------------------
    
    /** Shooter wheel motor. */
    public static Victor shooterWheelMotor;
    /** Controls the angle of attack of the shooter. */
    public static Talon shooterAngleMotor;
    /** Shooter feeder solenoid. */
    public static Solenoid shooterFeederSolenoid;
}
