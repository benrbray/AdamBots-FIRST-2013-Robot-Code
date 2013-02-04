/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.camera;



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
			RobotCamera.work();
		}
	}
}
