/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.behavior;

import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author Tyler
 */
public class Feeding {
    
    //// VARIABLES -------------------------------------------------------------
    SpeedController _feeder;
    
    //// CONSTRUCTORS ----------------------------------------------------------
    
    /**
     * Default Constuctor
     */
    public Feeding() {
	_feeder = null;
    }
    
    /**
     * Constructor, sets the motor
     * @param feeder 
     */
    
    public Feeding(SpeedController feeder) {
	_feeder = feeder;
    }
    
    //// METHODS ---------------------------------------------------------------
    
    /**
     * sets the voltage
     * @param speed 
     */
    public void setVoltage(double speed) {
	_feeder.set(speed);
    }
    
    /**
     * returns the voltage that the motor is at
     * @return
     */
    public double getVoltage() {
	return _feeder.get();
    }
    
    /**
     * returns the motor you are using
     * @return 
     */
    public SpeedController getMotor() {
	return _feeder;
    }
}
