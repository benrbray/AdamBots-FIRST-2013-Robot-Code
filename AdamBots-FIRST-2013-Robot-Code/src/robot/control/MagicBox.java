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
public class MagicBox extends DriverStation {
    
    //// MAGIC BOX CONSTANTS ---------------------------------------------------
    public static final int NUM_BUTTONS = 0;
    
    public static final int AUTO_SHOOTER_SPEED_ENABLED = 0;
    public static final int SHOOTER_MULTIPLIER_UP = 0;
    public static final int SHOOTER_MULTIPLIER_DOWN = 0;
    
    public static final int AUTO_ANGLE_ENABLED = 0;
    public static final int ANGLE_OFFSET_UP = 0;
    public static final int ANGLE_OFFSET_DOWN = 0;
    
    public static final double SHOOTER_MULTIPLIER_INCREMENT = .05;
    public static final double ANGLE_OFFSET_INCREMENT = 2.5;
    
    //// MAGIC BOX VARIABLES ---------------------------------------------------
    private double _shooterMultiplier;
    private double _angleOffset;
    
    private boolean _shooterMultiplierButtonReleased;
    private boolean _angleOffsetButtonReleased;
    
    private DriverStation _ds;
    
    //// BODY OF CLASS ---------------------------------------------------------
    
    public MagicBox() {
	_ds = DriverStation.getInstance();
	
	_shooterMultiplier = 1.0;
	_angleOffset = 0;
	
	_shooterMultiplierButtonReleased = false;
	_angleOffsetButtonReleased = false;
    }
    
    /**
     * Update method to be called periodically.
     */
    public void tick() {
	if (!getDigitalIn(SHOOTER_MULTIPLIER_UP) && _shooterMultiplierButtonReleased) {
	    _shooterMultiplier += SHOOTER_MULTIPLIER_INCREMENT;
	    _shooterMultiplierButtonReleased = false;
	    
	} else if (!getDigitalIn(SHOOTER_MULTIPLIER_DOWN) && _shooterMultiplierButtonReleased) {
	    _shooterMultiplierButtonReleased = false;
	    
	} else if (getDigitalIn(SHOOTER_MULTIPLIER_UP) && getDigitalIn(SHOOTER_MULTIPLIER_DOWN)) {
	    _shooterMultiplierButtonReleased = true;
	}
	
	if (!getDigitalIn(ANGLE_OFFSET_UP) && _angleOffsetButtonReleased) {
	    _angleOffset += ANGLE_OFFSET_INCREMENT;
	    _angleOffsetButtonReleased = false;
	    
	} else if (!getDigitalIn(ANGLE_OFFSET_DOWN) && _angleOffsetButtonReleased) {
	    _angleOffsetButtonReleased = false;
	
	} else if (getDigitalIn(ANGLE_OFFSET_UP) && getDigitalIn(ANGLE_OFFSET_DOWN)) {
	    _angleOffsetButtonReleased = true;
	}
    }
    
    /**
     * @return The current value of the shooter multiplier.
     */
    public double getShooterMultiplier() {
	return _shooterMultiplier;
    }
    
    /**
     * This method returns an inverted digital input from a Cypress board.
     * @param channel The Cypress board channel to check.
     * @return Inverted digital input from Cypress channel "channel."
     */
    public boolean getInvertedDigitalIn(int channel) {
	return !_ds.getDigitalIn(channel);
    }
}
