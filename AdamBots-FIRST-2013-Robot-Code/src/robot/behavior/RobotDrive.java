/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.behavior;

import edu.wpi.first.wpilibj.*;
import robot.actuators.RobotActuators;
import robot.sensors.RobotSensors;
import utils.MathUtils;


/**
 * Responsible for controlling the robot's drive motors.
 * @author Ben, Tyler
 */
public abstract class RobotDrive extends RobotBehavior {
    //// CONSTANTS -------------------------------------------------------------
	
    // Shifter Value Constants
	
	/** Low Gear */
    public static final double SHIFTER_LOW = 0.8;
	/** High Gear */
    public static final double SHIFTER_HIGH = 0.3;
	/** Neutral */
    public static final double SHIFTER_NEUTRAL = 0.65;
	
	// Tolerance Constants
	
	/** Gyro Tolerance (in degrees) for Gyro-Assisted Turning. */
	public static double GYRO_TOLERANCE_DEGREES = 3.0;
	
	//// PRIVATE VARIABLES -----------------------------------------------------
	
	// Autonomous / Guided Drive Control
	private static boolean _guidedDriving = false;
	private static boolean _gyroAssist = true;
	private static double _robotAngle = 0.0;
	private static double _leftEncoderTargetDistance = 0.0;
	private static double _rightEncoderTargetDistance = 0.0;
    
	//// INITIALIZATION --------------------------------------------------------
	
    public static void init() {
		
    }
	
	//// UPDATE ----------------------------------------------------------------
	
	public static void update(){
		// Guided Driving Logic
		if(_guidedDriving){
			
		}
	}
	
	//// DRIVE TYPE ------------------------------------------------------------
	
	/**
	 * Prepares the robot for guided driving.  During guided driving, RobotDrive
	 * should be continuously updated until it reaches its target.
	 */
	private static void beginGuidedDriving(){
		_guidedDriving = true;
		RobotSensors.encoderDriveLeft.reset();
		RobotSensors.encoderDriveRight.reset();
		RobotSensors.encoderDriveRight.start();
	}
	
	/**
	 * Prepares the robot for direct driving.  During direct driving, RobotDrive
	 * performs no logic, but instead sends commands from external subsystems
	 * directly to the drive motors.
	 */
	private static void beginDirectDriving(){
		_guidedDriving = false;
		RobotSensors.encoderDriveLeft.stop();
		RobotSensors.encoderDriveRight.stop();
	}
	
	//// AUTONOMOUS / GUIDED DRIVE METHODS -------------------------------------
	
	/**
	 * Begins a guided driving period, during which RobotDrive drives the robot
	 * for the specified distance, then stops.
	 * @param feet The number of feet to move forward.
	 * @param speed The speed at which to move.
	 */
	public static void driveDistanceFeet(double feet, double speed){
		driveDistanceInches(12.0 * feet, speed);
	}
	
	/**
	 * Begins a guided driving period, during which RobotDrive drives the robot
	 * for the specified distance, then stops.
	 * @param inches The number of inches to move forward.
	 * @param speed The speed at which to move.
	 */
	public static void driveDistanceInches(double inches, double speed){
		beginGuidedDriving();
		_leftEncoderTargetDistance = inches;
		_rightEncoderTargetDistance = inches;
	}
	
	/**
	 * Begins a guided driving period, during which RobotDrive turns the robot
	 * a specified number of radians, then stops.
	 * @param radians The number of radians to turn.
	 * @param speed The speed at which to turn.
	 */
	public static void turnRadians(double radians, double speed){
		turnRadians(radians, speed, true);
	}
	
	/**
	 * Begins a guided driving period, during which RobotDrive turns the robot
	 * a specified number of radians, then stops.
	 * @param radians The number of radians to turn.
	 * @param speed The speed at which to turn.
	 * @param gyroAssist Whether or not feedback from the Gyro should be used to
	 * assist robot turning.
	 */
	public static void turnRadians(double radians, double speed, boolean gyroAssist){
		turnDegrees(radians * MathUtils.PI_UNDER_180, speed, gyroAssist);
	}
	
	/**
	 * Begins a guided driving period, during which RobotDrive turns the robot
	 * a specified number of degrees, then stops.
	 * @param degrees The number of degrees to turn.
	 * @param speed The speed at which to turn.
	 */
	public static void turnDegrees(double degrees, double speed){
		turnDegrees(degrees, speed, true);
	}
	
	/**
	 * Begins a guided driving period, during which RobotDrive turns the robot
	 * a specified number of degrees, then stops.
	 * @param degrees The number of degrees to turn.
	 * @param speed The speed at which to turn.
	 * @param gyroAssist Whether or not feedback from the Gyro should be used to
	 * assist robot turning.
	 */
	public static void turnDegrees(double degrees, double speed, boolean gyroAssist){
		beginGuidedDriving();
		RobotSensors.gyroChassis.reset();
	}
	
	
	
	//// DIRECT DRIVE METHODS --------------------------------------------------
    
    /**
     * Sets the speed of each set of drive motors individually.
     * @param leftSpeed to set the left speed
     * @param rightSpeed to set the right speed
     * sets the speed of the wheels to the parameters given
     */
    public static void drive( double leftSpeed, double rightSpeed ) {
		RobotActuators.driveRight.set(-rightSpeed);
		RobotActuators.driveLeft.set(leftSpeed);
    }
    
    /**
     * Sets the speed of both sets of drive motors to the same value.
     * @param speed for the speed of both wheels
     * calls drive and sends it the parameters of speed for both
     */
    public static void driveStraight( double speed )
    {
		RobotActuators.driveRight.set(-speed);
		RobotActuators.driveLeft.set(speed);
    }
    
    /**
     * Sets the speed of each side of each set of drive motors to opposite values.
     * @param speed to turn in place
     * turns in place at the speed given
     */
    public static void turn( double speed )
    {
		RobotActuators.driveRight.set(speed);
		RobotActuators.driveLeft.set(speed);
    }
	
	//// STOP ------------------------------------------------------------------
    
    /**
     * stops all the motors
     */
    public static void stop()
    {
		RobotActuators.driveRight.set(0);
		RobotActuators.driveLeft.set(0);
    }
	
	//// GEAR SHIFTING ---------------------------------------------------------
    
    /**
     * switches gear when called
     * @param ServoPosition used to set transmissionLeft position
     */
    public static void switchGear( double ServoPosition){
		//RobotActuators.transmissionLeft.set(ServoPosition);
		//println(RobotActuators.transmissionLeft.get());

        RobotActuators.transmissionLeft.set(ServoPosition);
		RobotActuators.transmissionRight.set(ServoPosition);
    }
}