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
import java.util.Vector;

/**
 * Wrapper class for motors.  Controls motor access and makes limit switches
 * easier to implement.
 * @author Ben
 */
public class FancyMotor implements SpeedController {
    //// CONSTANTS -------------------------------------------------------------
    
	//// STATIC MOTOR CONTROL --------------------------------------------------
	
	public static Vector _fancyMotors;
	
	public static void init(){
		_fancyMotors = new Vector();
	}
	
	/**
	 * Iterates through the list of FancyMotor instances contained within this
	 * class, and calls checkLimits() on each, to ensure that nothing breaks.
	 */
	public static void update(){
		for(int i = 0; i < _fancyMotors.size(); i++){
			FancyMotor fm = (FancyMotor) _fancyMotors.elementAt(i);
			fm.checkLimits();
		}
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
	
    //// PUBLIC VARIABLES ------------------------------------------------------
    
    //// PRIVATE VARIABLES -----------------------------------------------------
    
    // Motor Reference
    private SpeedController _motor;
    
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
    }
    
    public FancyMotor(SpeedController motor, DigitalInput upperLimit, DigitalInput lowerLimit){
        // Initialize Variables
        _motor = motor;
        _upperLimit = upperLimit;
        _lowerLimit = lowerLimit;
		
		// Push to Static List of FancyMotors
		_fancyMotors.addElement(this);
    }
    
    //// UPDATE ----------------------------------------------------------------
    
	/**
	 * Checks the limit switches associated with this FancyMotor and stops the 
	 * motor if they're pressed (and if the motor is trying to go past them!).
	 */
	private void checkLimits(){
		// Get Limit Switch Values
		boolean limitUpper = (_upperLimit == null) ? false : _upperLimit.get();
        boolean limitLower = (_lowerLimit == null) ? false : _lowerLimit.get();

		// Print Warning if There Aren't Any Limit Switches Attached
		if(_upperLimit == null && _lowerLimit == null){
			System.err.println("Warning:  A FancyMotor has no limit switch references!");
		}
		
		// If the limits have been reached, stop the motor
        if ((limitUpper && _motor.get() > 0) || (limitLower && _motor.get() < 0)) {
            _motor.set(0, (byte)0);
        }
	}
	
    //// MOTOR ACCESS ----------------------------------------------------------
    
	/**
	 * Sets the value of the motor, assuming that no limits have been reached.
	 * @param speed The speed at which to run the motor.
	 * @see edu.wpi.first.wpilibj.SpeedController#set(double) 
	 */
    public void set(double speed){
        set(speed, (byte)0);
    }
	
	/**
	 * Sets the value of the motor, assuming that no limits have been reached.
	 * @param speed The speed at which to run the motor.
	 * @param syncGroup The sync group that the motor is part.
	 * @see edu.wpi.first.wpilibj.SpeedController#set(double, byte) 
	 */
	public void set(double speed, byte syncGroup){
		_motor.set(speed, syncGroup);
		checkLimits();
	}
	
	public void disable(){
		_motor.disable();
	}
	
	public double get(){
		return _motor.get();
	}
	
	public void pidWrite(double output) {
		_motor.pidWrite(output);
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
