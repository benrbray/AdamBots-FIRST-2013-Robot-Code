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
    
    /** Primary Driver Controller Port Number. */
    public static final int PRIMARY_DRIVER = 1;
    /** Secondary Driver Controller Port Number. */
    public static final int SECONDARY_DRIVER = 2;
    
    /** Axis values below this threshold will be ignored. */
    public static final double DEFAULT_DEAD_ZONE = 0.3;
    
    /** XBOX 360 South Face Button */
    public static final int BUTTON_A        = 1;
    /** XBOX 360 East Face Button */
    public static final int BUTTON_B        = 2;
    /** XBOX 360 West Face Button */
    public static final int BUTTON_X        = 3;
    /** XBOX 360 North Face Button */
    public static final int BUTTON_Y        = 4;
    /** XBOX 360 Left Bumper (Top) */
    public static final int BUTTON_LB       = 5;
    /** XBOX 360 Right Bumper (Top) */
    public static final int BUTTON_RB       = 6;
    /** XBOX 360 Back Button */
    public static final int BUTTON_BACK     = 7;
    /** XBOX 360 Start Button */
    public static final int BUTTON_START    = 8;
    /** XBOX 360 Left Joystick Button (Press Down) */
    public static final int BUTTON_LEFTJOY  = 9;
    /** XBOX 360 Right Joystick Button (Press Down) */
    public static final int BUTTON_RIGHTJOY = 10;
    
    /** XBOX 360 Left Horizontal Axis (Left=-1, Right=1) */ 
    public static final int AXIS_LEFT_X     = 1;
    /** XBOX 360 Left Vertical Axis (Up=-1, Down=1) */
    public static final int AXIS_LEFT_Y     = 2;
    /** XBOX 360 Trigger Axis (?) */
    public static final int AXIS_TRIGGERS   = 3;
    /** XBOX 360 Right Horizontal Axis (Left=-1, Right=1) */
    public static final int AXIS_RIGHT_X    = 4;
    /** XBOX 360 Right Vertical Axis (Up=-1, Down=1) */
    public static final int AXIS_RIGHT_Y    = 5;
    
    /** The number of axes on a standard XBOX 360 controller. */
    public static final int XBOX_AXES       = 6;
    /** The number of buttons on a standard XBOX 360 controller. */
    public static final int XBOX_BUTTONS    = 10;
    
    //// BUTTONS ---------------------------------------------------------------
    
    //// AXES ------------------------------------------------------------------
    
    /** Internal dead zone value. */
    private double _deadZone = 0;
    /** The "live zone" is always equal to (1-_deadZone). */
    private double _liveZone = 1;
    
    //// CONSTRUCTOR -----------------------------------------------------------
    
    /**
     * Creates a new FancyJoystick instance configured for use with an XBOX 360
     * controller.
     * @param port The joystick port number.
     */
    public FancyJoystick(int port){
        super(port, XBOX_BUTTONS, XBOX_AXES);
        setDeadZone(DEFAULT_DEAD_ZONE);
    }
    
    /**
     * Creates a new FancyJoystick instance configured for use with an XBOX 360
     * controller.
     * @param port The joystick port number.
     * @param deadZone Specifies a dead zone threshold, between zero and one.
     * Axis values below this threshold will be ignored, and zero will be
     * returned.
     */
    public FancyJoystick(int port, double deadZone){
        super(port, XBOX_BUTTONS, XBOX_AXES);
        setDeadZone(deadZone);
    }
    
    /**
     * Creates a new FancyJoystick instance.  You must specify the number of
     * channels and buttons for this joystick.
     * @param port The joystick port number.
     * @param numChannels The maximum number of channels of input provided by
     * this joystick.
     * @param numAxes The maximum number of axes given by this joystick. 
     */
    public FancyJoystick(int port, int numChannels, int numAxes){
        super(port, numChannels, numAxes);
        setDeadZone(DEFAULT_DEAD_ZONE);
    }
    
    /**
     * Creates a new FancyJoystick instance.  You must specify the number of
     * channels and buttons for this joystick.
     * @param port The joystick port number.
     * @param numChannels The maximum number of channels of input provided by
     * this joystick.
     * @param numAxes The maximum number of axes given by this joystick. 
     * @param deadZone Specifies a dead zone threshold, between zero and one.
     * Axis values below this threshold will be ignored, and zero will be
     * returned.
     */
    public FancyJoystick(int port, int numChannels, int numAxes, double deadZone){
        super(port, numChannels, numAxes);
        setDeadZone(deadZone);
    }
    
    //// INPUT METHODS ---------------------------------------------------------
    
    /**
     * Returns the value of the specified axis, after being subjected to dead
     * zone constraints.  Only values above the dead zone are reported.
     * @param axis The axis number.
     * @return The specified axis value, after dead zones.
     * @see FancyJoystick#setDeadZone(double) 
     */
    public double getDeadAxis(int axis){
        return getDeadAxis(axis, _deadZone);
    }
    
    /**
     * Returns the value of the specified axis, after being subjected to dead
     * zone constraints.  Only values above the dead zone are reported.
     * @param axis The axis number.
     * @param deadZone The dead zone value to use.  (does not change the
     * internal dead zone value of this instance.)
     * @return The specified axis value, after dead zones.
     * @see FancyJoystick#setDeadZone(double) 
     */
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
    
    /**
     * Set the default dead zone threshold for this instance.
     * @param value A value in the range [0-1]
     */
    public void setDeadZone(double value){
        _deadZone = Math.min(1.0, Math.max(0.0, value));
        _liveZone = 1 - _deadZone;
    }
    
    /**
     * 
     * @return The dead zone threshold for this FancyJoystick instance.
     * @see FancyJoystick#setDeadZone(double) 
     */
    public double getDeadZone(){
	return _deadZone;
    }
}
