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
    
    //// MAGIC BOX VARIABLES ---------------------------------------------------
    
    private DriverStation _ds;
    
    //// BODY OF CLASS ---------------------------------------------------------
    
    public MagicBox() {
	_ds = DriverStation.getInstance();
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
