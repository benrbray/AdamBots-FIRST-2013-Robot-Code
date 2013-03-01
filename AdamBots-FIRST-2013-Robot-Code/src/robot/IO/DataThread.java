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

    public static final int LOG_DATA = 0;
    public static final int LOAD_CALIBRATIONS = 1;
    
    private int _process;
    
    public DataThread(int process){
        _process = process;
    }
    
    public void run() {
        switch (_process){
            case LOG_DATA:
                DataIO.logData();
                break;
            case LOAD_CALIBRATIONS:
                DataIO.loadCalibrations();
                break;
        }
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
    }
    
}
