/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.camera;

import robot.RobotMain;
import robot.control.MagicBox;



/**
 * A Java thread which periodically calls RobotCamera.work() to avoid blocking computation.
 * @author Nathan
 */
public class CameraThread implements Runnable
{
	public void run()
	{
		while (true)
		{
			if (!RobotMain.getInstance().isAutonomous() && MagicBox.getDigitalIn(7)) {
				RobotCamera.work();
			}
			try {
				Thread.sleep(20);
			} catch (Exception e) {
				System.err.print(e);
			}
		}
	}
}
