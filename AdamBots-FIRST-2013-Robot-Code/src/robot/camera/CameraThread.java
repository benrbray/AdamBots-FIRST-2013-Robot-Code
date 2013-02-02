/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.camera;



/**
 *
 * @author Nathan
 */
public class CameraThread implements Runnable
{
	public void run()
	{
		while (true)
		{
			CameraProcessor.work();
		}
	}
}
