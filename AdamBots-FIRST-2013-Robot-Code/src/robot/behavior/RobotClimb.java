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
    
    private static double _winchSpeed = 0;
    /**
     * Set the target speed of the winch (e.g., 0.5 for reel out, -0.5 for reel in)
     * @param speed The PWM speed to set it at.
     */
    public static void setWinchSpeed(double speed)
    {
        _winchSpeed = speed;
    }
    public static void update()
    {
        if (RobotSensors.limitWinch1.get()) //TODO: Determine 1 vs 2 for limits, winch speed pairing.
        {
            _winchSpeed = Math.max(0,_winchSpeed);
        }
        if (RobotSensors.limitWinch2.get()) //TODO: Determine 1 vs 2 for limits, winch speed pairing.
        {
            _winchSpeed = Math.min(0,_winchSpeed);
        }
        RobotActuators.climbWinch.set(_winchSpeed);
    }
    
    public static void startSolenoid()
    {
        RobotActuators.climbWinchRelease.set(Relay.Value.kOn);
    }
    public static void stopSolenoid()
    {
        RobotActuators.climbWinchRelease.set(Relay.Value.kOn);
    }
}
