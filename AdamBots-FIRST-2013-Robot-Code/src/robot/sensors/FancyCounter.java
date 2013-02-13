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

    double lastRpm;
    int errorVal;
    private int _ticksPerPeriod;

    public FancyCounter(int channel) {
        super(channel);
        lastRpm = 0;
        errorVal = 0;
	_ticksPerPeriod = 1;
    }

    public FancyCounter(int slot, int channel) {
        super(slot, channel);
        lastRpm = 0;
        errorVal = 0;
	_ticksPerPeriod = 1;
    }
    
    public FancyCounter(int slot, int channel, int ticksPerPeriod) {
        super(slot, channel);
        lastRpm = 0;
        errorVal = 0;
	_ticksPerPeriod = ticksPerPeriod;
    }

    public int getError() {

        return errorVal;
    }

    public void resetError() {
        errorVal = 0;
    }

    public double pidGet() {
        // double time = getPeriod()
        double time = getPeriod() * _ticksPerPeriod;
        double rpm = 60 / time;

        // if (rom > 5000)  // was that before, but i thought i might need something different that wasn't 5000
        if (rpm > 10000) {
            errorVal++;
            rpm = lastRpm;
        }

        lastRpm = rpm;

        return rpm;
    }
    
    public void setTicksPerPeriod(int t) {
	_ticksPerPeriod = t;
    }
}
