package robot.IO;

import java.io.IOException;
import java.io.InputStreamReader;
import javax.microedition.io.Connector;
import java.util.Date;

import com.sun.squawk.io.BufferedReader;
import com.sun.squawk.microedition.io.FileConnection;
import java.io.DataOutputStream;
import java.util.Calendar;


/**
 * Handles reading and writing of text files on the cRIO.
 * Also used for storing data to be logged.
 * @author Jonathan
 */
public class DataIO {
    
    private static Data _storage = new Data();
    
    
    //Storing Data -------------------------------------------------------------
    
    /**
     * Stores data for the shooter.
     * @param s The String of data to be stored.
     */
    public static void storeShooter(String s){
        _storage.storeShooter(s);
    }
    
    /**
     * Stores data for climbing.
     * @param s The String of data to be stored.
     */
    public static void storeClimbing(String s){
        _storage.storeClimbing(s);
    }
    
    /**
     * Stores data for drive.
     * @param s The String of data to be stored.
     */
    public static void storeDrive(String s){
        _storage.storeDrive(s);
    }
    
    /**
     * Stores general data.
     * @param s The String of data to be stored.
     */
    public static void store(String s){
        _storage.storeGeneral(s);
    }
    
    public static String getData(){
        return _storage.toString();
    }
    
    //Reading and writing files ------------------------------------------------
    
    /**
     * Returns a string containing all of the lines in the specified text file
     * 
     * @param filename The name of the file on the cRIO
     * @return         A string containing the text file, separated by line
     */
    public static String getFileContents(String filename){
        String url = "file:///" + filename + ".txt";
        String contents = new String();
        
        try{
            FileConnection c = (FileConnection) Connector.open(url);
            BufferedReader buf = new BufferedReader(new InputStreamReader(c
                    .openInputStream()));
            String line;
            while((line = buf.readLine()) != null){
                contents+=line+ "\n";
            }
            buf.close();
        } catch (IOException e){}
        
        return contents;
    }
    
   /**
    * Returns an array filled with the calibration data.
    * @return An array of strings where each element is a line of 
    * calibration data
    */
    public static String[] getCalibrations(){
        String contents = getFileContents("/calib.txt");
        String[] calib = splitLines(contents);
        
        return calib;
    }
    
    /**
     * Splits the given string by line into an array of strings
     * @param full The full string to be split
     * @return     An array filled with the given string split by line
     */
    public static String[] splitLines(String full){
        int last = 0;
        int next;
        int i = 0;
        String[] split = new String[1];
        
        while ((next = full.indexOf("\n", last)) != -1){
            System.out.println(last + " " + next);
            split[i] = full.substring(last, next);
            i++;
            last = next + 1;
            String[] temp = new String[split.length+1];
            System.arraycopy(split, 0, temp, 0, split.length);
            split = temp;
            
        }
        
        return split;
    }
    
    /**
     * Creates a log file from given data.
     * @param data A string of data to be logged.
     */
    public static void logData(String data){
        
        Calendar date = Calendar.getInstance();
        
        String currentDate = Data.currentDay() + " " + Data.currentTime();
        
        data = ("Logged on " + currentDate + "\n" + data);
        String fileTime = Data.currentTime().replace(':', '!') + " " + 
                date.get(Calendar.DAY_OF_MONTH) + "_" + 
                (date.get(Calendar.MONTH)+1);
            
        String filename = "Log "+ fileTime;
        writeToFile(filename, data);
        
    }
    
    /**
     * Logs all stored and formatted data.
     */
    public static void logData(){
        logData(_storage.toString());
    }
    
    /**
     * Logs the data on a new thread.
     */
    public static void threadLog(){
        Thread logger = new Thread(new DataThread());
        logger.start();
        
    }
    
    /**
     * Writes the given content to a file with the given file name.
     * @param filename Name of file to be saved.
     * @param contents Contents so be stored in text file.
     */
    public static void writeToFile(String filename, String contents){
        
        try{
            
            DataOutputStream file;
            FileConnection fc;
            fc = (FileConnection) Connector.open("file:///" + filename +".txt", Connector.WRITE);
            fc.create();
            file = fc.openDataOutputStream();
            file.writeUTF(contents);
            file.flush();
            file.close();
            fc.close();
            
        }catch(IOException e){
        }
    }
    
}
