package robot;

/**
 * Contains port constants, static references to all sensors and actuators, and 
 * information about the robot's state.  Sensors shouldn't be referenced
 * directly; instead, update the Robot's state once every ~50ms (the natural
 * "iteration delay" of the robot).  Actuators should only be referenced
 * directly if there is no "Fancy" wrapper class for them; if there is,
 * initialize the "Fancy" actuator using the direct reference.
 * @author Ben
 */
public class RobotState {
    //// PORT CONSTANTS --------------------------------------------------------
    
    public static final int TURRET_MOTOR	= 5;
    public static final int BRIDGE_TIPPER	= 10;
    public static final int BALL_ELEVATOR	= 4;
    public static final int CAMERA_LED		= 4;
    public static final int TOP_GEAR		= 7;
    public static final int BOTTOM_GEAR		= 6;
    public static final int TOGGLEBOX_1		= 4;
    public static final int TOGGLEBOX_2		= 5;
    public static final int TOGGLEBOX_3		= 6;
    
    public static class Shooter {
        public static final int BOTTOM          = 2;
        public static final int COUNTER_TOP     = 7;
        public static final int COUNTER_BOTTOM  = 6;
        public static final int FEED            = 3;
    }
    
    public static class Turret {
	public static final int LIMIT_L = 13;
	public static final int TURRET_LIMIT_R  = 11;
    }
    
    public static class Drive {
	public static final int LEFT_MOTORS     = 6;
	public static final int RIGHT_MOTORS    = 7;
	public static final int SHIFTER_SERVO   = 8;
    }
	
    public static class BridgeTipper {
	public static final int LIMIT_DOWN = 10; 
	public static final int LIMIT_UP = 9;
    }
    
    //// ACTUATOR REFERENCES ---------------------------------------------------
    
    public static class Actuators {
	
    }
    
    //// SENSOR REFERENCES -----------------------------------------------------
    
    public static class Sensors {
	
    }
    
    //// CONTROL REFERENCES ----------------------------------------------------
    
    public static class Controls {
	
    }
    
    //// UPDATE ----------------------------------------------------------------
    
    /**
     * Reads in the states of frequently-used sensors and stores them in the
     * current RobotState.
     */
    public static void updateRobotState(){
	
    }
    
    public static int elapsedTimeSinceUpdate(){
	return 0;
    }
}
