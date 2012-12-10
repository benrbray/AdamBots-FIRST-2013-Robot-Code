package robot.control;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import edu.wpi.first.wpilibj.Joystick;
import utils.MathUtils;
import utils.MathUtils;

/**
 *
 * @author Ben
 */
public class FancyJoystick extends Joystick {
    //// CONSTANTS -------------------------------------------------------------
    
    public static final double DEFAULT_DEAD_ZONE = 0.3;
    
    public static final int BUTTON_A        = 1;
    public static final int BUTTON_B        = 2;
    public static final int BUTTON_X        = 3;
    public static final int BUTTON_Y        = 4;
    public static final int BUTTON_LB       = 5;
    public static final int BUTTON_RB       = 6;
    public static final int BUTTON_BACK     = 7;
    public static final int BUTTON_START    = 8;
    public static final int BUTTON_LEFTJOY  = 9;
    public static final int BUTTON_RIGHTJOY = 10;
    
    public static final int AXIS_LEFT_X     = 1;    // LEFT -1, RIGHT   1
    public static final int AXIS_LEFT_Y     = 2;    // UP   -1, DOWN    1
    public static final int AXIS_TRIGGERS   = 3;
    public static final int AXIS_RIGHT_X    = 4;    // LEFT -1, RIGHT   1
    public static final int AXIS_RIGHT_Y    = 5;    // UP   -1, DOWN    1
    
    public static final int XBOX_AXES       = 6;
    public static final int XBOX_BUTTONS    = 10;
    
    //// BUTTONS ---------------------------------------------------------------
    
    //// AXES ------------------------------------------------------------------
    
    private double _deadZone = 0;
    private double _liveZone = 1;
    
    //// CONSTRUCTOR -----------------------------------------------------------
    
    public FancyJoystick(int port){
        super(port, XBOX_BUTTONS, XBOX_AXES);
        setDeadZone(DEFAULT_DEAD_ZONE);
    }
    
    public FancyJoystick(int port, double deadZone){
        super(port, XBOX_BUTTONS, XBOX_AXES);
        setDeadZone(deadZone);
    }
    
    public FancyJoystick(int port, int numChannels, int numAxes){
        super(port, numChannels, numAxes);
        setDeadZone(DEFAULT_DEAD_ZONE);
    }
    
    public FancyJoystick(int port, int numChannels, int numAxes, double deadZone){
        super(port, numChannels, numAxes);
        setDeadZone(deadZone);
    }
    
    //// INPUT METHODS ---------------------------------------------------------
    
    public double getDeadAxis(int axis){
        return getDeadAxis(axis, _deadZone);
    }
    
    public double getDeadAxis(int axis, double deadZone){
        double liveZone = 1 - deadZone;
        double value = this.getRawAxis(axis);
        
        if(Math.abs(value) > deadZone){
            return (value - (deadZone * MathUtils.sign(value))) / liveZone;
        } else {
            return 0.0;
        }
    }
    
    //// GETTER / SETTER -------------------------------------------------------
    
    public void setDeadZone(double value){
        _deadZone = Math.min(1.0, Math.max(0.0, value));
        _liveZone = 1 - _deadZone;
    }
}
