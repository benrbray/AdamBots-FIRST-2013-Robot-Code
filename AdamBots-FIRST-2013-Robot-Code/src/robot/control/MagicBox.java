package robot.control;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * This class will make it easier to access the digital and analog inputs
 * of the Cypress board. They are accessed through 
 * DriverStation.getDigital/AnalogInput
 * 
 * This class should be used as a helper for a particular robot's magic box.
 * 
 * @author Steven
 */
public class MagicBox {
    
    //// MAGIC BOX CONSTANTS ---------------------------------------------------
    public static final int NUM_BUTTONS = 0;
    
    public static final int AUTO_SHOOTER_SPEED_ENABLED	= 5;
    public static final int SHOOTER_MULTIPLIER_UP	= 3;
    public static final int SHOOTER_MULTIPLIER_DOWN	= 4;
    
    public static final int AUTO_ANGLE_ENABLED	= 6;
    public static final int ANGLE_OFFSET_UP	= 1;
    public static final int ANGLE_OFFSET_DOWN	= 2;
    
    public static final double SHOOTER_MULTIPLIER_INCREMENT	= .05;
    public static final double SHOOTER_MANUAL_SPEED_INCREMENT	= .05;
    public static final double ANGLE_OFFSET_INCREMENT		= 2.5;
    
    public static final double PYRAMID_SHOT_SPEED = 0;
    public static final double PYRAMID_SHOT_ANGLE = 50;
    
    public static final double FULL_COURT_SPEED = .75;
    public static final double FULL_COURT_ANGLE = 30;
    
    //// MAGIC BOX VARIABLES ---------------------------------------------------
    private static double _shooterMultiplier;
    private static double _shooterManualSpeed;
    private static double _angleOffset;
    
    private static boolean _shooterMultiplierButtonReleased;
    private static boolean _angleOffsetButtonReleased;
    
    private static DriverStation _ds;
    
    //// BODY OF CLASS ---------------------------------------------------------
    
    /**
     * Must be called to initialize the magic box.c
     */
    public static void init() {
	_ds = DriverStation.getInstance();

	_shooterMultiplier = 1.0;
	_shooterManualSpeed = .5;
	_angleOffset = 0;

	_shooterMultiplierButtonReleased = false;
	_angleOffsetButtonReleased = false;
    }
    
    /**
     * Update method to be called periodically.
     */
    public static void update() {
	//// SHOOTER MULTIPLIER LOGIC ------------------------------------------
	if (!_ds.getDigitalIn(SHOOTER_MULTIPLIER_UP) && _shooterMultiplierButtonReleased) {
	    _shooterMultiplier += SHOOTER_MULTIPLIER_INCREMENT;
	    _shooterManualSpeed += SHOOTER_MANUAL_SPEED_INCREMENT;
	    _shooterMultiplierButtonReleased = false;

	} else if (!_ds.getDigitalIn(SHOOTER_MULTIPLIER_DOWN) && _shooterMultiplierButtonReleased) {
	    _shooterMultiplier -= SHOOTER_MULTIPLIER_INCREMENT;
	    _shooterManualSpeed -= SHOOTER_MANUAL_SPEED_INCREMENT;
	    _shooterMultiplierButtonReleased = false;

	} else if (_ds.getDigitalIn(SHOOTER_MULTIPLIER_UP) && _ds.getDigitalIn(SHOOTER_MULTIPLIER_DOWN)) {
	    _shooterMultiplierButtonReleased = true;
	}

	//// ANGLE OFFSET LOGIC ------------------------------------------------
	if (!_ds.getDigitalIn(ANGLE_OFFSET_UP) && _angleOffsetButtonReleased) {
	    _angleOffset += ANGLE_OFFSET_INCREMENT;
	    _angleOffsetButtonReleased = false;

	} else if (!_ds.getDigitalIn(ANGLE_OFFSET_DOWN) && _angleOffsetButtonReleased) {
	    _angleOffset -= ANGLE_OFFSET_INCREMENT;
	    _angleOffsetButtonReleased = false;

	} else if (_ds.getDigitalIn(ANGLE_OFFSET_UP) && _ds.getDigitalIn(ANGLE_OFFSET_DOWN)) {
	    _angleOffsetButtonReleased = true;
	}
    }
    
    /**
     * @return The current value of the shooter multiplier.
     */
    public static double getShooterMultiplier() {
	return _shooterMultiplier;
    }
    
    /**
     * @param shooterMultiplier The value to set shooter multiplier to.
     */
    public static void setShooterMultiplier(double shooterMultiplier) {
	_shooterMultiplier = shooterMultiplier;
    }
    
    /**
     * @return The current value of the manual shooter speed.
     */
    public static double getShooterManualSpeed() {
	return _shooterManualSpeed;
    }
    
    /**
     * @param speed The speed to set.
     */
    public static void setShooterManualSpeed(double speed) {
	_shooterManualSpeed = speed;
    }
    
    /**
     * @return The current angle offset.
     */
    public static double getAngleOffset() {
	return _angleOffset;
    }
    
    /**
     * @param angleOffset The value to set the angle offset to.
     */
    public static void setAngleOffset(double angleOffset) {
	_angleOffset = angleOffset;
    }
    
    /**
     * This method is a more convenient way to access the digital inputs
     * on the Cypress board when a MagicBox has been created.
     * @param channel The Cypress board channel to read.
     * @return Digital input from channel "channel."
     */
    public static boolean getDigitalIn(int channel) {
	return _ds.getDigitalIn(channel);
    }
    
    /**
     * This method is a more convenient way to access the analog inputs
     * on the Cypress board when a MagicBox has been created.
     * @param channel The Cypress board channel to read.
     * @return The analog channel "channel" on the Cypress board.
     */
    public static double getAnalogIn(int channel) {
	return _ds.getAnalogIn(channel);
    }
    
    /**
     * This method returns an inverted digital input from a Cypress board.
     * @param channel The Cypress board channel to read.
     * @return Inverted digital input from Cypress channel "channel."
     */
    public static boolean getInvertedDigitalIn(int channel) {
	return !_ds.getDigitalIn(channel);
    }
}