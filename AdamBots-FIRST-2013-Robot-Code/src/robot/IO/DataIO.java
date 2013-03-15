package robot.IO;

import com.sun.squawk.io.BufferedReader;
import com.sun.squawk.microedition.io.FileConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import javax.microedition.io.Connector;


/**
 * Handles reading and writing of text files on the cRIO.
 * Also used for storing data to be logged.
 * 
 * Use DataIO.store() or .storeShooterData/Climbing/Drive to save information
 *      in a specific category.
 * When storing data, prefix the string with "Title: " to give your sub-category
 *      a title. Otherwise, it will default to "Info".
 * 
 * Use DataIO.writeLogFile() to write all saved information into a text file.
 * Manually write information with .writeLogFile(String data).
 * 
 * Run DataIO.loadCalibrations() to load the calibration data from the text file
 * 
 * Store data in the log file using the following format:
 * CALIBRATION_NAME_X=2200 % This is a comment
 * 
 * Use DataIO.getCalibration(int lineNumber) to get data from the index.
 * 
 * TL;DR 
 * LOGGING: Store with .store(), write file with .writeLogFile().
 * LOADING: Load calibrations with .loadCalibrations(), get calibration with
 *       .getCalibrations(int lineNumber).
 * 
 * @author Jonathan
 */
public class DataIO {
    
    /*
     * TODO: Create folder on CRIO for logs to be stored
     * Check if writing works in disabledInit();
     * Confirm overwriting the calibration file will work
     * 
     */
    
    
    
    /**
     * The name of the calibration file
     */
    public static String CALIBRATION_FILE = "calib";
    //Calibration Index Values -------------------------------------------------
    
    //Add a comment to describe the purpose of the value
    public static final int SOMEONE = 0;
    public static final int ELSE = 1;
    public static final int NEEDS = 2;
    public static final int TO = 3;
    public static final int PUT = 4;
    public static final int NAMES = 5;
    public static final int HERE = 6;
    //--------------------------------------------------------------------------
    
    //Data Storage -------------------------------------------------------------
    private static StoredData _storage = new StoredData(true);
    private static String[] fullCalib;
    private static double[] calib;
    //--------------------------------------------------------------------------
    
    
    //Storing Data -------------------------------------------------------------
    
    /**
     * Stores data for the shooter.
     * Prefix data with "Title: " to save it in a specified category.
     * @param s The String of data to be stored.
     */
    public static void storeShooter(String s){
        _storage.storeShooterData(s);
    }
    
    /**
     * Stores data for climbing.
     * Prefix data with "Title: " to save it in a specified category.
     * @param s The String of data to be stored.
     */
    public static void storeClimbing(String s){
        _storage.storeClimbingData(s);
    }
    
    /**
     * Stores data for drive.
     * Prefix data with "Title: " to save it in a specified category.
     * @param s The String of data to be stored.
     */
    public static void storeDrive(String s){
        _storage.storeDriveData(s);
    }
    
    /**
     * Stores general data.
     * Prefix data with "Title: " to save it in a specified category.
     * @param s The String of data to be stored.
     */
    public static void store(String s){
        _storage.storeGeneralData(s);
    }
    
    /**
    * Logs the information from the last shot taken
    */
    public static void storeShot(){
            _storage.storeShot();
    }
	
    /**
     * Returns the StoredData class as a string
     * @return a string representation of the data class.
     */
    private String getData(){
        return _storage.toString();
    }
    //--------------------------------------------------------------------------
    
    
    //File Reading -------------------------------------------------------------
    
    /**
     * Returns a string containing all of the lines in the specified text file.
     * 
     * @param filename The name of the file on the cRIO.
     * @return A string containing the text file, separated by line.
     */
    public static String getFileContents(String filename){
        
        //Sets the filename
        if (filename.indexOf(".txt")!=-1){
            filename = filename.substring(0,filename.indexOf(".txt"));
        }
        String url = "file:///" + filename + ".txt";
       
        String contents = "";
        
        /* 
         * Opens a FileConnection at the given URL. Creates a reader from the
         * FileConnection, gets lines and puts them into one file seperated by
         * the return escape character.
         */
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
     * Splits the given string by line into an array of strings.
     * @param full The full string to be split.
     * @return An array filled with the given string split by line.
     */
    private static String[] splitLines(String full){
        int last = 0;
        int next;
        int i = 0;
        String[] split = new String[1];
        
        while ((next = full.indexOf("\n", last)) != -1){
            split[i] = full.substring(last, next);
            i++;
            last = next + 1;
            String[] temp = new String[split.length+1];
            System.arraycopy(split, 0, temp, 0, split.length);
            split = temp;
            
        }
        
        if (split[split.length-1]==null){
            String[] temp = new String[split.length-1];
            System.arraycopy(split, 0, temp, 0, split.length - 1);
            split = temp;
        }
        
        
        
        return split;
    }
    //--------------------------------------------------------------------------
    
    
    
    // Writing Files -----------------------------------------------------------
    
    /**
     * Writes the given content to a file with the given file name.
     * @param filename Name of file to be saved.
     * @param contents Contents so be stored in text file.
     */
    public static void writeToFile(String filename, String contents){
        
        try{
            /*
             * BufferedWriter wouldn't work, so a DataOutputStream is used 
             * instead. A new stream is created from a FileConnection created
             * from the given filename. The stream writes the given string, 
             * flushes and closes.
             */
            DataOutputStream file;
            FileConnection fc;
            fc = (FileConnection) Connector.open("file:///" + filename +".txt", 
                    Connector.WRITE);
            fc.create();
            file = fc.openDataOutputStream();
            file.writeUTF(contents);
            file.flush();
            file.close();
            fc.close();
            
        }catch(IOException e){
        }
    }
    
    /**
     * Creates a log file from given data.
     * Log file format: "Log_HH!MM_DD_MM.txt"
     * @param data A string of data to be logged.
     */
    public static void writeLogFile(String data){
			if (!data.equals("")){
            Calendar date = Calendar.getInstance();
        
            String currentDate = StoredData.getCurrentDay() + " " + StoredData.getCurrentTime();
        
            data = ("Logged on " + currentDate + "\n" + data);
            String fileTime = StoredData.getCurrentTime().replace(':', '!') + " " + 
                date.get(Calendar.DAY_OF_MONTH) + "_" + 
                (date.get(Calendar.MONTH)+1);
            
            String filename = "logFiles/Log_"+ fileTime;
            writeToFile(filename, data);
			}
    }
    
    /**
     * Logs all stored and formatted data.
     * Log file format: "Log_HH!MM_DD_MM.txt"
     */
    public static void writeLogFile(){
        writeLogFile(_storage.toString());
    }
    //--------------------------------------------------------------------------
    
    
    // Calibrations ------------------------------------------------------------
    
    /**
    * Loads and parses the calibration file to store it in an array of doubles.
    * Needs to be run before the calibration data can be accessed.
    */
    public static void loadCalibrations(){
        try{
            fullCalib = splitLines(getFileContents(CALIBRATION_FILE));
            calib = new double[fullCalib.length];
        
            for (int i = 0; i < fullCalib.length; i++){
                calib[i] = parseCalib(fullCalib[i]);
            }
        
        }catch(Exception e){}
        
    }
    
//    /**
//     * Updates the array of calibration data with new values
//     * @param lineNumber The location of information in the calibration array.
//     * @param value The new value.
//     */
//    public static void changeCalibValue(int lineNumber, int value){
//        calib[lineNumber] = value; 
//    }
    
    /**
     * Gets the calibration value at a given index
     * @param lineNumber The line to get the value from
     * @return The calibration value
     */
    public static double getCalibrations(int lineNumber){
        return calib[lineNumber];
    }
    
//    /**
//     * Rewrites the calibration file with updated information
//     */
//    public static void overwriteCalibrations(){
//        String full = "";
//        boolean write = false;
//        if (calib.length == fullCalib.length){
//        for (int i = 0; i < fullCalib.length; i++){
//            if (calib[i] != parseCalib(fullCalib[i])){
//                String c = fullCalib[i];
//                write = true;
//                String prefix;
//                String suffix = "";
//                prefix = c.substring(0, c.indexOf('=')+1);
//                if (c.indexOf('%') != -1){
//                   suffix = c.substring('%');
//                }
//                fullCalib[i] = prefix + calib[i] + suffix;
//            }
//            
//            full = fullCalib[i] + StoredData.NL;
//            
//        }
//        
//        if (write){
//            writeToFile(CALIBRATION_FILE, full);
//        }
//        }
//    }

    /**
     * Parses a line of calibration to get a value
     * @param c The line to parse
     * @return The calibration value
     */
    private static double parseCalib(String c){
        
            if (c.indexOf('%') != -1){
                c = c.substring(c.indexOf('='), c.indexOf('%'));
            }else{
                c = c.substring(c.indexOf('='));
            }
            
            String value = "";
            
            for (int j = 0; j < c.length(); j++){
                if(Character.isDigit(c.charAt(j)) || c.charAt(j)=='.'){
                    value+=c.charAt(j);
                }
            }
            
            return Double.parseDouble(value);
            
    }
    
}