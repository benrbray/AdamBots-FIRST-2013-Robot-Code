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
    
    /**
     * The tolerance of the winch targeting.
     */
    public static final double TOLERANCE = 0.5;
    
    private static double _winchTarget = 0;
    /**
     * Set the target position (inches?) of the winch (e.g., 0 for reel in, 15 for reel out)
     * @param speed The PWM speed to set it at.
     */
    public static void setWinchTarget(double target)
    {
        _winchTarget = target;
    }
    private static double getWinchPosition()
    {
        return RobotSensors.encoderWinch.getDistance();
    }
    public static void update()
    {
        
        if (RobotSensors.limitWinch1.get()) //TODO: Determine 1 vs 2 for limits, winch speed pairing.
        {
            _winchTarget = Math.min(_winchTarget,getWinchPosition()-TOLERANCE/2);
        }
        if (RobotSensors.limitWinch2.get())
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
    
    public static void startSolenoid()
    {
        RobotActuators.climbWinchRelease.set(Relay.Value.kOn);
    }
    public static void stopSolenoid()
    {
        RobotActuators.climbWinchRelease.set(Relay.Value.kOn);
    }
}
