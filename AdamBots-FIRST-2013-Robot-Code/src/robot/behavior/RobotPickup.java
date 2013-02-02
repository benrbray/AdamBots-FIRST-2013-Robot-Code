/**
 * This class contains methods to control the pickup mechanisms on the robot.
 * @author Steven
 */

package robot.behavior;

import edu.wpi.first.wpilibj.Relay;
import robot.actuators.RobotActuators;

public class RobotPickup {
    
    public RobotPickup() {
	
    }
    
    /**
     * Method to control the disc pickup winch.
     * @param value The direction to move the winch. Use Relay.Value
     */
    public void setWinch(Relay.Value value) {
	if (value == Relay.Value.kReverse) {
	    
	}
    }
}
