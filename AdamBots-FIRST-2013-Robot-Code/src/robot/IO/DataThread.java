/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.IO;

/**
 * Creates a data log on a separate thread
 * @author Jonathan
 */
public class DataThread implements Runnable{

    public void run() {
        DataIO.logData();
    }
    
}
