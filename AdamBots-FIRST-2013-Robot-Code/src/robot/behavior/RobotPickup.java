/**
 * This class contains methods to control the pickup mechanisms on the robot.
 * @author Steven
 */

package robot.behavior;

import edu.wpi.first.wpilibj.Relay;
import robot.actuators.RobotActuators;

public abstract class RobotPickup {
    
    private static double _winchSpeed;
    private static Relay.Value _relayValue;
    
    /**
     * Called to initialize RobotPickup.
     */
    public static void init() {
	_winchSpeed = 0;
	_relayValue = Relay.Value.kOff;
    }
    
    /**
     * Periodically call me.
     */
    public static void update() {
	//TODO: Add logic to stop the pickup device from killing itself.
	RobotActuators.discWinch.set(_winchSpeed);
	RobotActuators.discIntake.set(_relayValue);
    }
    
    /**
     * Method to control the disc pickup winch.
     * @param speed The speed to set the winch motor to.
     */
    public static void setWinch(double speed) {
	_winchSpeed = speed;
    }
    
    /**
     * Controls the disk intake roller.
     * @param value The direction to run the intake roller. kForward is in kBackward is out.
     */
    public static void intakeRoller(Relay.Value value) {
	_relayValue = value;
    }
}
