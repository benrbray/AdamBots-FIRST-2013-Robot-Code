/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.sensors;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author Adambots 245
 */
public class FancyCounter extends Counter implements PIDSource {

	// New constructor with radius option
	// getDiameter();
	// setDiameter();
	// getDistance();
	
	double diameter;
    double lastRpm;
	double distance;
    int errorVal;
    private int _ticksPerPeriod;

    public FancyCounter(int channel) {
        super(channel);
        lastRpm = 0;
        errorVal = 0;
		diameter = 0;
	_ticksPerPeriod = 1;
    }

    public FancyCounter(int slot, int channel) {
        super(slot, channel);
        lastRpm = 0;
        errorVal = 0;
		diameter = 0;
	_ticksPerPeriod = 1;
    }
    
    public FancyCounter(int slot, int channel, int ticksPerPeriod) {
        super(slot, channel);
        lastRpm = 0;
        errorVal = 0;
		diameter = 0;
	_ticksPerPeriod = ticksPerPeriod;
    }
	
    public FancyCounter(int slot, int channel, int ticksPerPeriod, double _diameter) {
        super(slot, channel);
        lastRpm = 0;
        errorVal = 0;
		diameter = _diameter;
	_ticksPerPeriod = ticksPerPeriod;
    }
	
    public FancyCounter(int slot, int channel, double _diameter) {
        super(slot, channel);
        lastRpm = 0;
        errorVal = 0;
		diameter = _diameter;
	_ticksPerPeriod = 1;

    }

    public int getError() {

        return errorVal;
    }

    public void resetError() {
        errorVal = 0;
    }

	public double getDiameter() {
		return diameter;
	}
	
	public void setDiameter(double _diameter) {
		diameter = _diameter;
	}
	
//	public double getDistance() {
//		return get() * diameter * Math.PI / _ticksPerPeriod;
//	}
	
    public double pidGet() {
        double time = getPeriod();
        double rpm = (60 / time) / _ticksPerPeriod;

        // if (rom > 5000)  // was that before, but i thought i might need something different that wasn't 5000
        if (rpm > 6000) {
            errorVal++;
            rpm = lastRpm;
        } else if (rpm < 300) {
			errorVal++;
            rpm = lastRpm;
		} else {
			lastRpm = rpm;
		}

        return rpm;
    }
    
    public void setTicksPerPeriod(int t) {
	_ticksPerPeriod = t;
    }
}
