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
	/** Encoder Tolerance (in inches) */
	public static double ENCODER_TOLERANCE = 0.5;
	/** Gyro Tolerance (in degrees) for Gyro-Assisted Turning. */
	public static double GYRO_TOLERANCE_DEGREES = 3.0;
	
	//// PRIVATE VARIABLES -----------------------------------------------------
	
	// Autonomous / Guided Drive Control
	
	/** Use guided driving? */
	private static boolean _guidedDriving = false;
	/** Use gyroscope input during guided driving? */
	private static boolean _gyroAssist = false;
	/** The current angular displacement of the robot since guided driving began. */
	private static double _robotAngle = 0.0;
	/** A left encoder target value, in inches, for guided driving. */
	private static double _leftEncoderTargetInches = 0.0;
	/** A right encoder target value, in inches, for guided driving. */
	private static double _rightEncoderTargetInches = 0.0;
	/** The desired acceleration, expressed as a PWM value.  Range 0.0-1.0. */
	private static double _driveAcceleration = 0.0;
	/** Guided driving will begin at (or below) this speed. */
	private static double _driveSpeedInitial = 0.2;
	/** Accumulated guided driving speed for the left motor. */
	private static double _driveLeftVelocity = 0.0;
	/** Accumulated guided driving speed for the right motor. */
	private static double _driveRightVelocity = 0.0;
	/** A maximum speed for guided driving.  Note:  Actual speed accumulates 
	 * gradually based on the desired acceleration.*/
	private static double _driveSpeedTarget = 1.0;

	//// INITIALIZATION --------------------------------------------------------
	public static void init() {
	}

	//// UPDATE ----------------------------------------------------------------
	
	public static void update() {
		// Guided Driving Logic
		if ( _guidedDriving ) {
			// Check Encoders
			double leftDistance = RobotSensors.encoderDriveLeft.getDistance();
			double rightDistance = RobotSensors.encoderDriveRight.getDistance();
			double leftDifference = _leftEncoderTargetInches - leftDistance; // Positive if wheels need to move in positive dir
			double rightDifference = _rightEncoderTargetInches - rightDistance; // Positive if wheels need to move in positive dir

			// Accelerate
			double driveLeftDir = MathUtils.sign(leftDifference);
			double driveRightDir = MathUtils.sign(rightDifference);
			double driveLeftVelTarget = driveLeftDir * _driveSpeedTarget;
			double driveRightVelTarget = driveRightDir * _driveSpeedTarget;

			if ( Math.abs(_driveLeftVelocity - driveLeftVelTarget) > _driveAcceleration ) {
				_driveLeftVelocity += driveLeftDir * _driveAcceleration;
			}
			else {
				_driveLeftVelocity = driveLeftVelTarget;
			}

			if ( Math.abs(_driveRightVelocity - driveRightVelTarget) > _driveAcceleration ) {
				_driveRightVelocity += driveRightDir * _driveAcceleration;
			}
			else {
				_driveRightVelocity = driveRightVelTarget;
			}

			// Motors
			setMotors(_driveLeftVelocity, _driveRightVelocity);

			// Have we Reached the Target Values?
			if ( Math.abs((leftDifference + rightDifference) / 2.0) < ENCODER_TOLERANCE ) {
				endGuidedDriving();
			}
		}
	}

	//// DRIVE TYPE ------------------------------------------------------------
	
	/**
	 * Prepares the robot for guided driving.  During guided driving, RobotDrive
	 * should be continuously updated until it reaches its target.
	 */
	private static void beginGuidedDriving() {

		// Prepare Encoders
		RobotSensors.encoderDriveLeft.reset();
		RobotSensors.encoderDriveRight.reset();
		RobotSensors.encoderDriveLeft.start();
		RobotSensors.encoderDriveRight.start();

		// Set Velocities if Not Already Guided Driving
		if ( !_guidedDriving ) {
			_driveLeftVelocity = MathUtils.sign(_leftEncoderTargetInches) * Math.min(_driveSpeedInitial, _driveSpeedTarget);
			_driveRightVelocity = MathUtils.sign(_rightEncoderTargetInches) * Math.min(_driveSpeedInitial, _driveSpeedTarget);
		}

		_guidedDriving = true;
	}

	private static void endGuidedDriving() {
		if(_guidedDriving){
			_guidedDriving = false;
			
			// Stop Encoders
			RobotSensors.encoderDriveLeft.reset();
			RobotSensors.encoderDriveRight.reset();
			RobotSensors.encoderDriveLeft.stop();
			RobotSensors.encoderDriveRight.stop();

			_driveLeftVelocity = 0.0;
			_driveRightVelocity = 0.0;
			_driveSpeedTarget = 0.0;
			_leftEncoderTargetInches = 0.0;
			_rightEncoderTargetInches = 0.0;

			stop();
		}
	}

	/**
	 * Prepares the robot for direct driving.  During direct driving, RobotDrive
	 * performs no logic, but instead sends commands from external subsystems
	 * directly to the drive motors.
	 */
	private static void beginDirectDriving() {
		_guidedDriving = false;
	}

	//// AUTONOMOUS / GUIDED DRIVE METHODS -------------------------------------
	
	/**
	 * Begins a guided driving period, during which RobotDrive drives the robot
	 * for the specified distance, then stops.
	 * @param feet The number of feet to move forward.
	 * @param speed The speed at which to move.
	 */
	public static void driveDistanceFeet( double feet, double speed ) {
		driveDistanceInches(12.0 * feet, speed, 1.0);
	}

	/**
	 * Begins a guided driving period, during which RobotDrive drives the robot
	 * for the specified distance, then stops.
	 * @param feet The number of feet to move forward.
	 * @param speed The speed at which to move.
	 * @param accRate Acceleration Rate.
	 */
	public static void driveDistanceFeet( double feet, double speed, double accRate ) {
		driveDistanceInches(12.0 * feet, speed, accRate);
	}

	/**
	 * Begins a guided driving period, during which RobotDrive drives the robot
	 * for the specified distance, without acceleration, then stops.
	 * @param inches The number of inches to move forward.
	 * @param speed The speed at which to move.
	 */
	public static void driveDistanceInches( double inches, double speed ) {
		driveDistanceInches(inches, speed, 1.0);
	}

	/**
	 * Begins a guided driving period, during which RobotDrive drives the robot
	 * for the specified distance, then stops.
	 * @param inches The number of inches to move forward.
	 * @param speed The speed at which to move (positive).
	 * @param accRate Acceleration rate (positive).
	 */
	public static void driveDistanceInches( double inches, double speed, double accRate ) {
		_leftEncoderTargetInches = inches;
		_rightEncoderTargetInches = inches;
		_driveSpeedTarget = Math.abs(speed);
		_driveAcceleration = Math.abs(accRate);
		beginGuidedDriving();
	}

	/**
	 * Begins a guided driving period, during which RobotDrive turns the robot
	 * a specified number of radians, then stops.
	 * @param radians The number of radians to turn.
	 * @param speed The speed at which to turn (positive).
	 */
	public static void turnRadians( double radians, double speed ) {
		turnRadians(radians, speed, true);
	}

	/**
	 * Begins a guided driving period, during which RobotDrive turns the robot
	 * a specified number of radians, without acceleration then stops.
	 * @param radians The number of radians to turn.
	 * @param speed The speed at which to turn (positive).
	 * @param gyroAssist Whether or not feedback from the Gyro should be used to
	 * assist robot turning.
	 */
	public static void turnRadians( double radians, double speed, boolean gyroAssist ) {
		turnDegrees(radians * MathUtils.PI_UNDER_180, speed, gyroAssist);
	}

	/**
	 * Begins a guided driving period, during which RobotDrive turns the robot
	 * a specified number of degrees, without acceleration, then stops.
	 * @param degrees The number of degrees to turn.
	 * @param speed The speed at which to turn (positive).
	 */
	public static void turnDegrees( double degrees, double speed ) {
		turnDegrees(degrees, speed, true);
	}

	/**
	 * Begins a guided driving period, during which RobotDrive turns the robot
	 * a specified number of degrees, without acceleration, then stops.
	 * @param degrees The number of degrees to turn.
	 * @param speed The speed at which to turn.  (positive)
	 * @param gyroAssist Whether or not feedback from the Gyro should be used to
	 * assist robot turning.
	 */
	public static void turnDegrees( double degrees, double speed, boolean gyroAssist ) {
		turnDegrees(degrees, speed, 1.0, gyroAssist);
	}

	/**
	 * Begins a guided driving period, during which RobotDrive turns the robot
	 * a specified number of degrees, then stops.
	 * @param degrees The number of degrees to turn. (CW)
	 * @param speed The speed at which to turn.  (positive)
	 * @param accRate Acceleration Rate (positive).
	 * @param gyroAssist Whether or not feedback from the Gyro should be used to
	 * assist robot turning.
	 */
	public static void turnDegrees( double degrees, double speed, double accRate, boolean gyroAssist ) {
		_rightEncoderTargetInches = -degrees / RobotSensors.DPI_ENCODER_DRIVE_RIGHT_DEGREES;
		_leftEncoderTargetInches = degrees / RobotSensors.DPI_ENCODER_DRIVE_LEFT_DEGREES;
		_driveSpeedTarget = Math.abs(speed);
		_driveAcceleration = Math.abs(accRate);
		RobotSensors.gyroChassis.reset();
		beginGuidedDriving();
	}

	//// DIRECT DRIVE METHODS --------------------------------------------------
	
	/**
	 * Sets the speed of both sets of drive motors to the same value.
	 * @param speed for the speed of both wheels (+ Forwards, - Backwards)
	 * calls drive and sends it the parameters of speed for both
	 */
	public static void driveStraight( double speed ) {
		drive(speed, speed);
	}

	/**
	 * Rotates in place at the given speed.
	 * @param speed to turn in place (+ CW, - CCW)
	 */
	public static void turn( double speed ) {
		drive(speed, -speed);
	}

	/**
	 * Sets the speed of each set of drive motors individually.
	 * @param leftSpeed to set the left speed (+ Forwards, - Backwards)
	 * @param rightSpeed to set the right speed (+ Forwards, - Backwards)
	 * sets the speed of the wheels to the parameters given
	 */
	public static void drive( double leftSpeed, double rightSpeed ) {
		beginDirectDriving();
		setMotors(leftSpeed, rightSpeed);
	}

	//// SET MOTORS ------------------------------------------------------------
	
	/**
	 * Sets the speed of each drive motor.  The right motor has been inverted to
	 * ensure logical directional control.  (+ Forward, - Backwards)
	 * @param leftSpeed The desired speed of the left drive motor.
	 * @param rightSpeed The desired speed of the right drive motor.
	 */
	private static void setMotors( double leftSpeed, double rightSpeed ) {
		RobotActuators.driveLeft.set(leftSpeed);
		RobotActuators.driveRight.set(rightSpeed); // Right Inverted
	}

	//// STOP ------------------------------------------------------------------
	
	/**
	 * Stops every drive motor.  Ends guided driving if guided driving is active.
	 */
	public static void stop() {
		if(_guidedDriving) endGuidedDriving();
		RobotActuators.driveRight.set(0);
		RobotActuators.driveLeft.set(0);
	}

	//// GEAR SHIFTING ---------------------------------------------------------
	
	/**
	 * Switches gear.
	 * @param ServoPosition used to set transmissionLeft position
	 */
	public static void switchGear( double ServoPosition ) {
		RobotActuators.transmissionLeft.set(ServoPosition);
		RobotActuators.transmissionRight.set(ServoPosition);
	}
}
