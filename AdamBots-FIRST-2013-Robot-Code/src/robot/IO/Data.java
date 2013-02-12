/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.IO;
import java.util.Calendar;

/**
 * Provides a way to store and format data before it is logged.
 *
 * @author Jonathan
 */
public class Data {

    //Groupings of data to be stored
    private Category[] _general;
    private Category[] _shooter;
    private Category[] _climbing;
    private Category[] _drive;

    private static final String NL = "\r\n";
    
    /**
     * Initializes all of the Category arrays.
     */
    public Data() {
        _general = new Category[0];
        _climbing = new Category[0];
        _shooter = new Category[0];
        _drive = new Category[0];
    }

    /**
     * Stores data in the General Category array.
     *
     * @param s The string of data to be stored.
     */
    public void storeGeneral(String s) {
        _general = addData(_general, s);
    }

    /**
     * Stores data in the Shooter Category array.
     *
     * @param s The string of data to be stored.
     */
    public void storeShooter(String s) {
        _shooter = addData(_shooter, s);
    }

    /**
     * Stores data in the Climbing Category array.
     *
     * @param s The string of data to be stored.
     */
    public void storeClimbing(String s) {
        _climbing = addData(_climbing, s);
    }

    /**
     * Stores data in the Drive Category array.
     *
     * @param s The string of data to be stored.
     */
    public void storeDrive(String s) {
        _drive = addData(_drive, s);
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
                a = addIndex(a);
                a[a.length - 1] = new Category(title);
                a[a.length - 1].add(info);
            }
        } else {
            a = addIndex(a);
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

            _contents += NL + currentTime() + "\t" + add;
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
            return NL + _name + " ********************" + _contents;
        }
    }

    /**
     * Adds a new index to a Category array
     *
     * @param in The Category array to be modified.
     * @return A Category array with a new empty index.
     */
    private Category[] addIndex(Category[] in) {
        Category[] temp = new Category[in.length + 1];
        System.arraycopy(in, 0, temp, 0, in.length);
        return temp;
    }

    /**
     * Formats and returns the Data class as a string.
     *
     * @return The data class as a string.
     */
    public String toString() {
        String all = new String();

        all += addArray(_general, "General") + addArray(_shooter, "Shooter") 
                + addArray(_climbing, "Climbing") + addArray(_drive, "Drive");

        return all;
    }

    /**
     * Combines all the contents of a Category array.
     *
     * @param c The Category array to be combined.
     * @return The full String of the Category array.
     */
    private String addArray(Category[] c, String title) {
        String a = new String();
        int l = c.length;
        if (l != 0) {
            a = NL + NL + NL + title + wrap("-", 25 - title.length()) + NL;
            for (int i = 0; i < l; i++) {
                a += c[i]+NL;
            }
            
        }

        return a;
    }
    
    /**
     * Adds a string a certain number of times
     * @param r String to be repeated
     * @param t Number of times to repeat it
     * @return The full string
     */
    private String wrap(String r, int t){
        String f = new String();
        
        for (int i = 0; i < t; i ++){
            f+=r;
        }
        return f;
    }
    
    /**
     * Returns the current Time (H:M:S)
     */
    public static String currentTime(){
        Calendar date = Calendar.getInstance();

        String time = (((date.get(Calendar.HOUR_OF_DAY) + 2)%24) + ":"
                    + ((date.get(Calendar.MINUTE)+20)%60) + ":" + date.get(Calendar.SECOND));
            
            return time;
    }
    
    /**
     * Returns the current day (M/D/Y)
     * @return 
     */
    public static String currentDay(){
        Calendar date = Calendar.getInstance();
        
        String currentDate = (date.get(Calendar.MONTH)) + "/" 
                + ((date.get(Calendar.DAY_OF_MONTH))) + "/" + 
                (date.get(Calendar.YEAR));
        return currentDate;
    }
    
}
