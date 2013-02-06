/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.behavior;

import edu.wpi.first.wpilibj.Relay;
import robot.actuators.RobotActuators;
import robot.sensors.RobotSensors;

/**
 *
 * @author Curtis
 */
public abstract class RobotClimb {
    //// CONSTANTS -------------------------------------------------------------
    
    /** The tolerance of the winch targeting. */
    public static final double TOLERANCE = 0.5;
    
    //// PRIVATE VARIABLES -----------------------------------------------------
    
    // Winch Positioning
    private static double _winchTarget = 0;
    
    //// UPDATE ----------------------------------------------------------------
    
    /**
     * Manages limit, encoder, and winch using the requested last target from setWinchTarget.
     * Must be called 'periodically'.
     */
    public static void update()
    {
        
        if (RobotSensors.limitWinchA.get()) //TODO: Determine 1 vs 2 for limits, winch speed pairing.
        {
            _winchTarget = Math.min(_winchTarget,getWinchPosition()-TOLERANCE/2);
        }
        if (RobotSensors.limitWinchB.get())
        {
            _winchTarget = Math.max(_winchTarget,getWinchPosition()+TOLERANCE/2);
        }
        if (_winchTarget > getWinchPosition() + TOLERANCE)
        {
        RobotActuators.climbWinch.set(0.1); //TODO: Verify sign
        }
        if (_winchTarget < getWinchPosition() - TOLERANCE)
        {
            RobotActuators.climbWinch.set(-0.1);
        }
    }
    
    
    //// WINCH -----------------------------------------------------------------
    
    /**
     * Set the target position (inches?) of the winch (e.g., 0 for reel in, 15 for reel out)
     * @param speed The PWM speed to set it at.
     */
    public static void setWinchTarget(double target)
    {
        _winchTarget = target;
    }
    
    /**
     * Determine the winch position based on encoder values.
     * @return The distance returned by the encoder's getDistance() method.
     * This distance is scaled by the encoder's "distancePerPulse" value.
     */
    private static double getWinchPosition()
    {
        return RobotSensors.encoderWinch.getDistance();
    }
    
    /**
     * Has the winch reached its target position?
     * @see setWinchTarget
     * @return Winch status.
     */
    public static boolean isWinchInPosition(){
	return false; // TODO:  Logic Here
    }
    
    /**
     * Stops the motion of the winch (by setting the target to the current position and stopping the motor)
     */
    public static void stopWinch()
    {
        _winchTarget = getWinchPosition();
        RobotActuators.climbWinch.set(0);
    }
    
    //// SOLENOID CONTROL ------------------------------------------------------
    
    public static void startSolenoid()
    {
        RobotActuators.climbWinchRelease.set(Relay.Value.kOn);
    }
    public static void stopSolenoid()
    {
        RobotActuators.climbWinchRelease.set(Relay.Value.kOn);
    }
}
