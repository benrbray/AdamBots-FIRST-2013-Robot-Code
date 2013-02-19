/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.actuators;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

/**
 * Wrapper class for motors.  Controls motor access and makes limit switches
 * easier to implement.
 * @author Ben
 */
public class FancyMotor {
    //// CONSTANTS -------------------------------------------------------------
    
    //// PUBLIC VARIABLES ------------------------------------------------------
    
    //// PRIVATE VARIABLES -----------------------------------------------------
    
    // Motor Reference
    private SpeedController _motor;
    private boolean _jaguar;
    
    // Limit Switches
    private DigitalInput _upperLimit = null;
    private DigitalInput _lowerLimit = null;
    
    //// CONSTRUCTOR -----------------------------------------------------------
    
    /**
     * Wrap the FancyMotor class around a pre-existing SpeedController instance.
     * @param motor A SpeedController instance.
     */
    public FancyMotor(SpeedController motor){
        // Initialize Variables
        _motor = motor;
        _jaguar = (motor instanceof Jaguar);
    }
    
    public FancyMotor(SpeedController motor, DigitalInput upperLimit, DigitalInput lowerLimit){
        // Initialize Variables
        _motor = motor;
        _jaguar = (motor instanceof Jaguar);
        _upperLimit = upperLimit;
        _lowerLimit = lowerLimit;
    }
    
    //// STATIC CONSTRUCTOR METHODS --------------------------------------------
    
    /**
     * Create a new FancyMotor for a Jaguar.  Assumes the default digital module.
     * @param channel The PWM channel on the digital module that the Jaguar is attached to.
     * @return A new FancyMotor object.
     */
    public static FancyMotor createFancyJaguar(int channel){
        return new FancyMotor(new Jaguar(channel));
    }
    
    /**
     * Create a new FancyMotor for a Jaguar.  
     * @param slot The slot in the chassis that the digital module is plugged into.
     * @param channel The PWM channel on the digital module that the Jaguar is attached to.
     * @return A new FancyMotor object.
     */
    public static FancyMotor createFancyJaguar(int slot, int channel){
        return new FancyMotor(new Jaguar(slot, channel));
    }
    
    /**
     * Create a new FancyMotor for a Victor.  Assumes the default digital module.
     * @param channel The PWM channel on the digital module that the Victor is attached to.
     * @return A new FancyMotor object.
     */
    public static FancyMotor createFancyVictor(int channel){
        return new FancyMotor(new Victor(channel));
    }
    
    /**
     * Create a new FancyMotor for a Victor.  
     * @param slot The slot in the chassis that the digital module is plugged into.
     * @param channel The PWM channel on the digital module that the Victor is attached to.
     * @return A new FancyMotor object.
     */
    public static FancyMotor createFancyVictor(int slot, int channel){
        return new FancyMotor(new Victor(slot, channel));
    }
	
	/**
     * Create a new FancyMotor for a Jaguar.  
     * @param slot The slot in the chassis that the digital module is plugged into.
     * @param channel The PWM channel on the digital module that the Jaguar is attached to.
     * @return A new FancyMotor object.
     */
	public static FancyMotor createFancyTalon(int slot, int channel){
		return new FancyMotor(new Talon(slot, channel));
	}
	
	/**
     * Create a new FancyMotor for a Jaguar.  
     * @param slot The slot in the chassis that the digital module is plugged into.
     * @param channel The PWM channel on the digital module that the Jaguar is attached to.
     * @return A new FancyMotor object.
     */
	public static FancyMotor createFancyTalon(int channel) {
		return new FancyMotor(new Talon(channel));
	}
    
    //// INITIALIZATION --------------------------------------------------------
    
    //// MOTOR ACCESS ----------------------------------------------------------
    
    public void setMotor(double motorValue){
        boolean limitUpper = _upperLimit.get();
        boolean limitLower = _lowerLimit.get();

        if ((limitUpper && motorValue < 0) || (limitLower && motorValue > 0)) {
            _motor.set(motorValue);
        } else {
            _motor.set(0);
        }
    }
    
    //// GETTER / SETTER METHODS -----------------------------------------------
	
	/**
	 * Set the limit switch used to stop the motor from moving in the POSITIVE
	 * direction when the switch is pressed.
	 * @param upperLimit A reference to a limit switch (DigitalInput) instance.
	 * @see edu.wpi.first.wpilibj.DigitalInput
	 * @see robot.sensors.RobotSensors
	 */
	public void setUpperLimit(DigitalInput upperLimit){
		_upperLimit = upperLimit;
	}
	
	/**
	 * Set the limit switch used to stop the motor from moving in the NEGATIVE
	 * direction when the switch is pressed.
	 * @param lowerLimit A reference to a limit switch (DigitalInput) instance.
	 * @see edu.wpi.first.wpilibj.DigitalInput
	 * @see robot.sensors.RobotSensors
	 */
	public void setLowerLimit(DigitalInput lowerLimit){
		_lowerLimit = lowerLimit;
	}
}
