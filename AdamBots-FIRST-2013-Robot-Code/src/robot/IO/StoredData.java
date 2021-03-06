/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.IO;
import edu.wpi.first.wpilibj.DriverStation;
import java.util.Calendar;
import robot.camera.RobotCamera;
import robot.sensors.RobotSensors;


/**
 * Provides a way to store and format data before it is logged.
 * This class should never be directly accessed.
 * @author Jonathan
 */
public class StoredData {

    //Groupings of data to be stored
    private Category[] _general;
    private Category[] _shooter;
    private Category[] _climbing;
    private Category[] _drive;
    private Category[] _shots;
    private String[] _shotLog;
    private boolean _gameTime;
    
    /**
     * New Line Character recognized by Windows.
     */
    public static final String NL = "\r\n";
    

    /**
     * Initializes all of the Category arrays.
     * @param gameTime Whether the time variable is time from match start or current time
     */
    public StoredData(boolean gameTime) {
        _general = new Category[0];
        _climbing = new Category[0];
        _shooter = new Category[0];
        _drive = new Category[0];
        _shots = new Category[0];
        
	_shotLog = new String[0];
        _gameTime = gameTime;
    }

    
    
    /**
     * Stores data in the General Category array.
     *
     * @param s The string of data to be stored.
     */
    protected void storeGeneralData(String s) {
        _general = addData(_general, s);
    }

    /**
     * Stores data in the Shooter Category array.
     *
     * @param s The string of data to be stored.
     */
    protected void storeShooterData(String s) {
        _shooter = addData(_shooter, s);
    }

    /**
     * Stores data in the Climbing Category array.
     *
     * @param s The string of data to be stored.
     */
    protected void storeClimbingData(String s) {
        _climbing = addData(_climbing, s);
    }

    /**
     * Stores data in the Drive Category array.
     *
     * @param s The string of data to be stored.
     */
    protected void storeDriveData(String s) {
        _drive = addData(_drive, s);
    }

     /**
     * Stores the distance the camera returns, the angle from the sensor, 
     * and the RPM from the sensor.
     */
    public void storeShot(){
	String log = "" + RobotCamera.getDistanceInches() + "\t" + 
		RobotSensors.counterShooterAngle.getDistance() + "\t" +
		RobotSensors.counterShooterSpeed.pidGet();
	
        _shots = addData(_shots, log);
        
    }
    
    /**
     * Adds data into a index in the category array.
     *
     * @param c The array that you want to add a row of data to.
     * @param s The string of data you want to store.
     * @return An array with the data added.
     */
    private Category[] addData(Category[] c, String s) {
        Category[] a = c;
        String title;
        String info;
        if (s.indexOf(":") != -1) {
            title = s.substring(0, s.indexOf(":"));
            info = s.substring(s.indexOf(":") + 1);
        } else {
            title = "Info";
            info = s;
        }

        while (info.charAt(0) == ' ') {
            info = info.substring(1);
        }

        int l = a.length;
        if (l > 0) {
            int index = categoryLocation(a, title);
            if (index != -1) {
                a[index].add(info);
            } else {
                a = addCategoryIndex(a);
                a[a.length - 1] = new Category(title);
                a[a.length - 1].add(info);
            }
        } else {
            a = addCategoryIndex(a);
            a[a.length - 1] = new Category(title);
            a[a.length - 1].add(info);
        }
        return a;
    }
	
    /**
     * Returns the index of a category in the array.
     *
     * @param c The array of categories to look through.
     * @param s The title of the data to be added.
     * @return The index of the category if it exists, -1 if not.
     */
    private int categoryLocation(Category[] c, String s) {
        int location = -1;

        int l = c.length;

        for (int i = 0; i < l; i++) {
            if (c[i].getName().equals(s)) {
                return i;
            }
        }

        return location;
    }

    /**
     * Used to store groupings of information within a section.
     */
    private class Category {

        private String _name;
        private String _contents;

        /**
         * Creates a new Category.
         *
         * @param name The name of the Category.
         */
        public Category(String name) {
            _name = name;
            _contents = new String();
        }

        /**
         * Adds contents to be stored in the Category. Appends the current time
         * to the beginning of the data being stored.
         *
         * @param add Contents to be added to the Category.
         */
        private void add(String add) {
            Calendar date = Calendar.getInstance();

            _contents += NL + getTime() + "\t" + add;
        }

        /**
         * Gets the name of the category.
         *
         * @return The name of the category.
         */
        public String getName() {
            return _name;
        }

        /**
         * Override to return the contents of the category.
         *
         * @return Returns the formatted copy of the contents.
         */
        public String toString() {
            return NL + _name + " " + wrap("*", 25-_name.length()) + _contents;
        }
    }

    /**
     * Adds a new index to a Category array
     *
     * @param in The Category array to be modified.
     * @return A Category array with a new empty index.
     */
    private Category[] addCategoryIndex(Category[] in) {
        Category[] temp = new Category[in.length + 1];
        System.arraycopy(in, 0, temp, 0, in.length);
        return temp;
    }

	private String[] addStringIndex(String[] in) {
        String[] temp = new String[in.length + 1];
        System.arraycopy(in, 0, temp, 0, in.length);
        return temp;
    }
	
    /**
     * Formats and returns the StoredData class as a string.
     *
     * @return The data class as a string.
     */
    public String toString() {
        
		boolean write = false;
		int s = _shots.length+_general.length+_shooter.length+_climbing.length+_drive.length;
        if (s!=0){
			write = true;
		}
		
        String all = new String();
		if(write){
			all += addCategoryArray(_shots, "Shots") + addCategoryArray(_general, "General") + addCategoryArray(_shooter, "Shooter") 
                + addCategoryArray(_climbing, "Climbing") + addCategoryArray(_drive, "Drive");
		}else{
			all = "";
		}
        return all;
    }

    /**
     * Combines all the contents of a Category array, split with new lines.
     *
     * @param c The Category array to be combined.
     * @return The full String of the Category array.
     */
    private String addCategoryArray(Category[] c, String title) {
        String a = new String();
        int l = c.length;
        if (l != 0) {
            a = NL + NL + NL + title + " " + wrap("-", 25 - title.length()) + NL;
            for (int i = 0; i < l; i++) {
                a += c[i]+NL;
            }
            
        }

        return a;
    }
	
    /**
     * Combines all the contents of a String array, split with new lines.
     *
     * @param c The String array to be combined.
     * @return The full String of the String array.
     */
    private String addStringArray(String[] s){
	String a = new String();
	int l = s.length;
	if (l != 0){
		a = NL + NL + NL + "ShotLog" + " " + wrap("-", 18) + NL;
	}
	
	for (int i = 0; i < l; i ++){
		a+=s[i]+NL;
	}
		
	return a;
    }
	
    
    /**
     * Adds a string a certain number of times
     * @param r String to be repeated
     * @param t Number of times to repeat it
     * @return The full string
     */
    private static String wrap(String r, int t){
        String f = new String();
        
        for (int i = 0; i < t; i ++){
            f+=r;
        }
        return f;
    }
    
    /**
     * Returns the current Time (H:M:S)
     */
    public static String getCurrentTime(){
        Calendar date = Calendar.getInstance();
        
        String time = (((date.get(Calendar.HOUR_OF_DAY) + 2) % 24) + ":"
                    + ((date.get(Calendar.MINUTE) + 20) % 60) + ":" 
                    + date.get(Calendar.SECOND));
            
            return time;
    }
    
    /**
     * Gets the time from match start.
     * @return Time from start of match.
     */
    public static double getMatchTime(){
        DriverStation game = DriverStation.getInstance();
        return game.getMatchTime();
    }
    
    /**
     * Returns a time dependent on whether current time or match time is
     * being stored
     * @return Match time or current time.
     */
    public String getTime(){
       return ((_gameTime)?(String.valueOf(getMatchTime())):(getCurrentTime()));
    }
    
    /**
     * Returns the current day (M/D/Y)
     * @return 
     */
    public static String getCurrentDay(){
        Calendar date = Calendar.getInstance();
        
        String currentDate = (date.get(Calendar.MONTH)) + "/" 
                + ((date.get(Calendar.DAY_OF_MONTH))) + "/" + 
                (date.get(Calendar.YEAR));
        return currentDate;
    }
    
}