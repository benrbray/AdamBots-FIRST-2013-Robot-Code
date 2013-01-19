/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Ben
 */
public class MathUtils {
    //// MATH CONSTANTS --------------------------------------------------------
    
    public static final double TWO_PI           = Math.PI * 2;
    public static final double PI_OVER_FOUR     = Math.PI / 4;
    public static final double PI_OVER_TWO      = Math.PI / 2;
    public static final double PI_OVER_180      = Math.PI / 180;
    public static final double PI_UNDER_180     = 180 / Math.PI;
    
    //// ROUNDING --------------------------------------------------------------
    
    /**
     * Rounds a double to a certain number of decimal places.
     * @param n The number to round.
     * @param digit 10 to round to tens, 0.1 to round to tenths, etc.
     * @return 
     */
    public static double roundTo(double n, double digit){
        return Math.floor(n / digit + 0.5) * digit;
    }
    
    /**
     * Rounds down a double to a certain number of decimal places.
     * @param n The number to round.
     * @param digit 10 to round to tens, 0.1 to round to tenths, etc.
     * @return 
     */
    public static double floorTo(double n, double digit){
        return Math.floor(n / digit) * digit;
    }
    
    /**
     * Rounds up a double to a certain number of decimal places.
     * @param n The number to round.
     * @param digit .1 to round to tens, 10 to round to tenths.
     * @return 
     */
    public static double ceilTo(double n, double digit){
        return Math.ceil(n / digit) * digit;
    }
    
    //// SIGN ------------------------------------------------------------------
    
    public static double sign(double n){
        return (n > 0.0) ? 1.0 : ( (n < 0.0) ? -1.0 : 0.0 );
    }
    
    public static float sign(float n){
        return (n > 0.0f) ? 1.0f : ( (n < 0.0f) ? -1.0f : 0.0f );
    }
    
    public static int sign(int n){
        return (n > 0) ? 1 : ( (n < 0) ? -1 : 0 );
    }
}
