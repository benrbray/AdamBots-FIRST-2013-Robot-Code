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

    public FancyCounter(int channel) {
        super(channel);
        lastRpm = 0;
        errorVal = 0;
    }

    public FancyCounter(int slot, int channel) {
        super(slot, channel);
        lastRpm = 0;
        errorVal = 0;
    }

    public int getError() {

        return errorVal;
    }

    public void resetError() {
        errorVal = 0;
    }

    public double pidGet() {
        // double time = getPeriod()
        double time = getPeriod() * 2;       // might be wrong, not sure if the * 2 is needed
        double rpm = 60 / time;

        // if (rom > 5000)  // was that before, but i thought i might need something different that wasn't 5000
        if (rpm > 10000) {
            errorVal++;
            rpm = lastRpm;
        }

        lastRpm = rpm;

        return rpm;
    }
}
