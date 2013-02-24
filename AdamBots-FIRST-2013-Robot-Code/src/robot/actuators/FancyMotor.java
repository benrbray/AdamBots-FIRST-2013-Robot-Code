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
import robot.RobotObject;

/**
 * Wrapper class for motors.  Controls motor access and makes limit switches
 * easier to implement.
 * @author Ben
 */
public class FancyMotor extends RobotObject implements SpeedController {
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
			fm.enforceLimits();
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
	
    //// PRIVATE VARIABLES -----------------------------------------------------
    
    // Motor Reference
    private SpeedController _motor;
    
    // Limit Switches
    private DigitalInput _positiveLimit = null;
    private DigitalInput _negativeLimit = null;
    
    //// CONSTRUCTOR -----------------------------------------------------------
    
    /**
     * Wrap the FancyMotor class around a pre-existing SpeedController instance.
     * @param motor A SpeedController instance.
     */
    public FancyMotor(SpeedController motor){
        this(motor, null, null);
    }
    
    public FancyMotor(SpeedController motor, DigitalInput upperLimit, DigitalInput lowerLimit){
		System.out.println("FancyMotor created.");
		
        // Initialize Variables
        _motor = motor;
        _positiveLimit = upperLimit;
        _negativeLimit = lowerLimit;
		
		// Push to Static List of FancyMotors
		_fancyMotors.addElement(this);
    }
    
    //// LIMIT SWITCHES --------------------------------------------------------
	
	/**
	 * Check to see if there is at least one limit switch available.  Prints a
	 * warning if there are no limit switches currently associated with this 
	 * instance.
	 * @return FALSE if there are no limit switches currently associated with
	 * this instance.
	 */
	private boolean checkLimitAvailability(){
		// Print Warning if There Aren't Any Limit Switches Attached
		if(_positiveLimit == null && _negativeLimit == null){
			System.err.println("Warning:  A FancyMotor has no limit switch references!");
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Stops this FancyMotor if a limit has been reached.
	 * @return TRUE if a limit was reached.
	 */
	private boolean enforceLimits(){
		checkLimitAvailability();
		
		// Get Limit Switch Values
		boolean limitPositive = (_positiveLimit == null) ? false : _positiveLimit.get();
        boolean limitNegative = (_negativeLimit == null) ? false : _negativeLimit.get();
		
		// If the limits have been reached, stop the motor
        if (limitPositive || limitNegative) {
			this.set(0.0);
			return true;
        } else {
			return false;
		}
	}
	
	/**
	 * Determines whether or not the desired motor value would turn the motor
	 * past one of the limit switches associated with this FancyMotor.  If not,
	 * it sets the motor to the desired value.  Otherwise, the motor is set to
	 * zero.
	 * @param motorValue The desired motor value. (-1.0-1.0)
	 * @return TRUE if a limit was reached.
	 */
	private boolean enforceLimitsAndSet(double motorValue){
		return enforceLimitsAndSet(motorValue, (byte)0);
	}
	
	/**
	 * Determines whether or not the desired motor value would turn the motor
	 * past one of the limit switches associated with this FancyMotor.  If not,
	 * it sets the motor to the desired value.  Otherwise, the motor is set to
	 * zero.
	 * @param motorValue The desired motor value. (-1.0-1.0)
	 * @param syncGroup Motor sync group.
	 * @return TRUE if a limit was reached.
	 */
	private boolean enforceLimitsAndSet(double motorValue, byte syncGroup){
		checkLimitAvailability();
		
		// Get Limit Switch Values
		boolean limitPositive = (_positiveLimit == null) ? false : _positiveLimit.get();
        boolean limitNegative = (_negativeLimit == null) ? false : _negativeLimit.get();
		
		// If the limits have been reached, stop the motor
        if ((limitPositive && motorValue > 0) || (limitNegative && motorValue < 0)) {
			_motor.set(0.0);
			return true;
        } else {
			_motor.set(motorValue, (byte)syncGroup);
			return false;
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
		enforceLimitsAndSet(speed, syncGroup);
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
	public void setPositiveLimit(DigitalInput upperLimit){
		_positiveLimit = upperLimit;
	}
	
	/**
	 * Set the limit switch used to stop the motor from moving in the NEGATIVE
	 * direction when the switch is pressed.
	 * @param lowerLimit A reference to a limit switch (DigitalInput) instance.
	 * @see edu.wpi.first.wpilibj.DigitalInput
	 * @see robot.sensors.RobotSensors
	 */
	public void setNegativeLimit(DigitalInput lowerLimit){
		_negativeLimit = lowerLimit;
	}


}
