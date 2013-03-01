/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.sensors;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 *
 * @author Steven
 */
public class FancyDigitalInput extends DigitalInput {
	//// PRIVATE VARIABLES -----------------------------------------------------
	
	private boolean _inverted = false;
	
	//// CONSTRUCTORS ----------------------------------------------------------
	
	/**
	 * DigitalInput wrapper class with support for inverting the input.
	 * @param channel Port Number.
	 */
	public FancyDigitalInput(int channel){
		this(1, channel, false);
	}
	
	/**
	 * DigitalInput wrapper class with support for inverting the input.
	 * @param slot Digital Card Number.
	 * @param channel Port Number.
	 */
	public FancyDigitalInput(int slot, int channel){
		this(slot, channel, false);
	}
	
	/**
	 * DigitalInput wrapper class with support for inverting the input.
	 * @param channel Port Number.
	 * @param invert Should the value of this DigitalInput be inverted?
	 */
	public FancyDigitalInput(int channel, boolean invert){
		this(1, channel, invert);
	}
	
	/**
	 * DigitalInput wrapper class with support for inverting the input.
	 * @param slot Digital Card Number.
	 * @param channel Port Number.
	 * @param invert Should the value of this DigitalInput be inverted?
	 */
	public FancyDigitalInput(int slot, int channel, boolean invert){
		super(slot, channel);
		_inverted = invert;
	}
	
	//// DIGITAL INPUT OVERRIDE METHODS ----------------------------------------
	
	/**
	 * Returns the value of this DigitalInput.
	 * @return Digital input value.
	 * @see DigitalInput
	 */
	public boolean get(){
		return _inverted ? !super.get() : super.get();
	}
}