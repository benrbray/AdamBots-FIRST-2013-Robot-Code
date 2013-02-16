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
 *
 * @author Blue
 */
public class TTargetStart extends LogicTask {
    public int finish()
    {
        return _done ? SUCCESS : FAILURE;
    }
    
    public void update()
    {
        _done = true;
    }
    
    public void initialize()
    {
        TargetShooterSpeedLogic.setIsTargeting(true);
		TargetShooterAngleLogic.setIsTargeting(true);
		TargetSpinLogic.setIsTargeting(true);
    }
}
