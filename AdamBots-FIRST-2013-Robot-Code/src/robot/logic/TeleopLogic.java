/**
 * This class handles the updateJoystick Methods as well as the update magic box methods from last year.
 */

package robot.logic;

import robot.control.FancyJoystick;
import robot.control.MagicBox;

/**
 *
 * @author Steven
 */
public class TeleopLogic {
    
    //// CONSTANTS -------------------------------------------------------------
    
    //// VARIABLES -------------------------------------------------------------
    private FancyJoystick _primaryJoy;
    private FancyJoystick _secondaryJoy;
    private MagicBox _magicBox;
    
    private boolean _primaryButtons[];
    private boolean _secondaryButtons[];
    private boolean _magicBoxButtons[];
    
    private double _primaryAxis[];
    private double _secondaryAxis[];
    
    /**
     * Creates an instance of TeleopLogic.
     */
    public TeleopLogic() {
	
    }
    
    /**
     * Initializes variables, objects, etc to their starting states.
     */
    public void teleopInit() {
	//// INITIALIZE JOYSTICK AND MAGIC BOX VALUE ARRAYS --------------------
	_primaryButtons = new boolean[FancyJoystick.XBOX_BUTTONS];
	_secondaryButtons = new boolean[FancyJoystick.XBOX_BUTTONS];
	_magicBoxButtons = new boolean[MagicBox.NUM_BUTTONS];
	
	_primaryAxis = new double[FancyJoystick.XBOX_BUTTONS];
	_secondaryAxis = new double[FancyJoystick.XBOX_AXES];
	
    }
    
    /**
     * Update method. To be called periodically by MainControl.
     */
    public void tick() {
	
    }
    
    /**
     * Gathers joystick button and axis values and stores them in their respective arrays.
     */
    private void updateJoystickValues() {
	for (int i = 1; i < FancyJoystick.XBOX_BUTTONS; i++) {
	    _primaryButtons[i] = _primaryJoy.getRawButton(i);
	    _secondaryButtons[i] = _secondaryJoy.getRawButton(i);
	}
	
	for (int i = 0; i < FancyJoystick.XBOX_AXES; i++) {
	    _primaryAxis[i] = _primaryJoy.getDeadAxis(i);
	    _secondaryAxis[i] = _secondaryJoy.getDeadAxis(i);
	}
    }
    
    /**
     * Gathers magic box values.
     */
    private void updateMagicBox() {
	
    }
}
