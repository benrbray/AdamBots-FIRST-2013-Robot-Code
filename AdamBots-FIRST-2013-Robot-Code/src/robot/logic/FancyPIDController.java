package robot.logic;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author Tyler
 */
public class FancyPIDController extends PIDController {
	//// CONSTANTS -------------------------------------------------------------
	
	/** Number of iterations of good readings required for "up to speed" status.*/
    public final int PID_BUFFER = 5;
	
    //// PRIVATE VARIABLES -----------------------------------------------------
     
	/** Keep track of the number of good iterations. */
    private int _targetBuffer = 0;

    //// CONSTRUCTOR -----------------------------------------------------------
    
	public FancyPIDController( double Kp, double Ki, double Kd, PIDSource source, PIDOutput output ) {
        super( Kp, Ki, Kd, source, output );     
    }
	
	public FancyPIDController( double Kp, double Ki, double Kd, PIDSource source, PIDOutput output, int period ) {
        super( Kp, Ki, Kd, source, output, period );     
    }
    
    //// PID METHODS -----------------------------------------------------------
    
	/**
	 * Sets the target RPM for this PID controller.
	 * @param targetRPM Target RPM.
	 */
    public void setRPM(double targetRPM) {
		// Enables the PID if it isn't enabled
		if (!super.isEnable()) {
			super.enable();
		}

		// This is necessary because setPoint() resets variables.
		if (super.getSetpoint() != targetRPM) {
			super.setSetpoint(targetRPM);
		}
    }
    
    /**
	 * Checks to see whether or not the PID has reached its target speed.  Note:
	 * there is a default buffer of five good iterations required for the PID to
	 * be considered "up to speed."
	 * @return TRUE if up to speed, FALSE otherwise.
	 */
    public boolean isAtSpeed() {
		// checks if the PID is on target
		if (super.onTarget()) {
			if (_targetBuffer <= PID_BUFFER) {
			_targetBuffer++;
			}
		} else {
			_targetBuffer = 0;
		}

		// checks how long the _pid had been on target
		if (_targetBuffer > PID_BUFFER) {
			return true;
		} else {
			return false;
		}
    }
}