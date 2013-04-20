package robot.actuators;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import robot.RobotObject;
import robot.sensors.RobotSensors;

/**
 * Contains static instances of robot actuators.
 * @author Ben
 */
public class RobotActuators extends RobotObject {
	//// PRINT FILTERING -------------------------------------------------------
	
	/** Hide RobotObject field to allow for proper print filtering. */
	public static boolean verboseOutput = true;
	
    //// ENFORCE NONINSTANTIABILITY --------------------------------------------
    
    private RobotActuators() throws Exception {
		throw new java.lang.Exception("You can't instantiate RobotActuators, silly!");
    }
	
	//// PORT CONSTANTS --------------------------------------------------------
	
	/**
	 * Port constants for the Competition Robot.
	 */
	public static final class CompetitionBot {
		/** Digital Out 1 Constants. */
		public static final class DigitalOut1 {
			public static final int LEFT_DRIVE_VICTOR = 1;
			public static final int RIGHT_DRIVE_VICTOR = 2;
			public static final int SHOOTER_VICTOR = 3;
			public static final int SHOOTER_ANGLE_TALON = 4;
			public static final int DISC_INTAKE_TALON = 5;
			public static final int CLIMB_WINCH_TALON = 6;
			public static final int LEFT_SHIFTER_SERVO = 7;
			public static final int RIGHT_SHIFTER_SERVO = 8;
			
			//TODO: REMOVE SECOND WINCH TALON
			//public static final int WINCH_TALON_2 = 9;
		}
		
		/** Digital Relay Constants. */
		public static final class DigitalRelay1 {
			public static final int UNUSED = 1;
			public static final int COMPRESSOR = 2;
			public static final int WINCH_SOLENOID = 3;
		}
		
		/** Solenoid port constants. */
		public static final class Solenoid {
			public static final int SHOOTER_PNEUMATIC_SOLENOID = 1;
			public static final int CAMERA_LED = 2;
			public static final int LED_GROUND_EFFECT = 3;
			public static final int LED_ARM_EFFECT = 4;
			public static final int LED_GREEN = 5;
		}
	}
	
	//// INSTANCES -------------------------------------------------------------
	
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
    public static FancyMotor climbWinch;
    /** Winch ratchet release solenoid. */
    public static Relay climbWinchSolenoid;
    
    //// DISC ACQUISITION ------------------------------------------------------

	/** Intake roller on hopper. */
	public static Talon discIntakeRoller;
    
    //// SHOOTER ---------------------------------------------------------------
    
    /** Shooter wheel motor. */
    public static Victor shooterWheelMotor;
    /** Controls the angle of attack of the shooter. (UP is Negative, DOWN is Positive)*/
    public static FancyMotor shooterAngleMotor;
    /** Shooter feeder solenoid. */
    public static Solenoid shooterFeederSolenoid;
	
	//// COMPRESSOR ------------------------------------------------------------
	
	/** Controls the compressor. */
	public static Relay compressor;
    
    //// PRETTY LIGHTS ---------------------------------------------------------
    
    /** Controls the led rings around the camera. */
    public static Solenoid cameraLED;
    /** Controls the green led strips on the robot. */
    public static Solenoid ledGroundEffect;
    /** Controls the red led strips on the robot */
    public static Solenoid ledArmEffect;
    /** Controls the yellow underglow lights. */
    public static Solenoid ledGreenEffect;
    
    //// INITIALIZATION --------------------------------------------------------
    
    /**
     * Initializes static actuator instances.
     */
    public static void init(){
		println("RobotActuators.init()");
		
		//// DIGITAL OUT 1 -----------------------------------------------------
		
		driveLeft			= new Victor(CompetitionBot.DigitalOut1.LEFT_DRIVE_VICTOR);
		driveRight			= new Victor(CompetitionBot.DigitalOut1.RIGHT_DRIVE_VICTOR);
		shooterWheelMotor   = new Victor(CompetitionBot.DigitalOut1.SHOOTER_VICTOR);
		shooterAngleMotor   = FancyMotor.createFancyTalon(CompetitionBot.DigitalOut1.SHOOTER_ANGLE_TALON);
		discIntakeRoller    = new Talon(CompetitionBot.DigitalOut1.DISC_INTAKE_TALON);
		climbWinch			= FancyMotor.createFancyTalon(CompetitionBot.DigitalOut1.CLIMB_WINCH_TALON);
		transmissionLeft    = new Servo(CompetitionBot.DigitalOut1.LEFT_SHIFTER_SERVO);
		transmissionRight   = new Servo(CompetitionBot.DigitalOut1.RIGHT_SHIFTER_SERVO);
		
		//// DIGITAL RELAY 1 ---------------------------------------------------
		
		compressor			= new Relay(CompetitionBot.DigitalRelay1.COMPRESSOR);
		climbWinchSolenoid	= new Relay(CompetitionBot.DigitalRelay1.WINCH_SOLENOID);

		//// SOLENOID ----------------------------------------------------------
		
		shooterFeederSolenoid = new Solenoid(CompetitionBot.Solenoid.SHOOTER_PNEUMATIC_SOLENOID);
		cameraLED = new Solenoid(CompetitionBot.Solenoid.CAMERA_LED);
		ledGroundEffect = new Solenoid(CompetitionBot.Solenoid.LED_GROUND_EFFECT);
		ledArmEffect = new Solenoid(CompetitionBot.Solenoid.LED_ARM_EFFECT);
		ledGreenEffect = new Solenoid(CompetitionBot.Solenoid.LED_GREEN);

		println("RobotActuators.init() finished");
    }
	
	//// CONFIG ----------------------------------------------------------------
	
	/** 
	 * Configures the Actuators contained within this class.  This method
	 * should be called after both RobotActuators.init() and RobotSensors.init()
	 * to ensure full compatibility.
	 */
	public static void configure(){
		// Finalize FancyMotors
		shooterAngleMotor.setPositiveLimit(RobotSensors.limitShooterB);
		climbWinch.setPositiveLimit(RobotSensors.limitWinchA);
		
		// Relays
		compressor.setDirection(Relay.Direction.kForward);
		climbWinchSolenoid.setDirection(Relay.Direction.kForward);

		// Solenoids
		shooterFeederSolenoid.set(false);
		
		// Turn off LEDs
		killAllLEDs();
	}
    
    //// LIGHT CONTROLING METHODS ----------------------------------------------
    
    /** Disables all LEDs on the robot. */
    public static void killAllLEDs() {
		cameraLED.set(false);
		ledGroundEffect.set(false);
		ledArmEffect.set(false);
		ledGreenEffect.set(false);
    }
    
}
