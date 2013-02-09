/**
 * This class contains methods to control the pickup mechanisms on the robot.
 * @author Steven
 */

package robot.behavior;

import edu.wpi.first.wpilibj.Relay;
import robot.actuators.RobotActuators;
import robot.sensors.RobotSensors;

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
	
	RobotActuators.discIntake.set(_relayValue);
	
	//TODO: Confirm logic for winch safety.
	if (!RobotSensors.limitElevatorA.get() && !RobotSensors.limitElevatorB.get()) { // No switch pressed
	    RobotActuators.discElevator.set(_winchSpeed);
	} else if (RobotSensors.limitElevatorA.get() && _winchSpeed < 0) { // Elevator at top, speed set to lower
	    RobotActuators.discElevator.set(_winchSpeed);
	} else if (RobotSensors.limitElevatorB.get() && _winchSpeed > 0) { // Elevator at bottom, speed set to raise
	    RobotActuators.discElevator.set(_winchSpeed);
	} else {
	    RobotActuators.discElevator.set(0);
	}
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
