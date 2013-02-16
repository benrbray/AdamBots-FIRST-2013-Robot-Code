/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

import robot.logic.LogicTask;
import robot.logic.targeting.TargetShooterAngleLogic;
import robot.logic.targeting.TargetShooterSpeedLogic;
import robot.logic.targeting.TargetSpinLogic;

/**
 * Stops targeting.
 * @author Blue
 */
public class TTargetStop extends LogicTask {
    //// INITIALIZE ------------------------------------------------------------
    
    public void initialize()
    {
        TargetShooterSpeedLogic.setIsTargeting(false);
	TargetShooterAngleLogic.setIsTargeting(false);
	TargetSpinLogic.setIsTargeting(false);
    }
    
    //// UPDATE ----------------------------------------------------------------
    
    public void update()
    {
        _done = true;
    }
    
    //// FINISH ----------------------------------------------------------------
    
    public int finish()
    {
        return _done ? SUCCESS : FAILURE;
    }
}
