package seedu.address.ui;

import java.util.Random;

/**
 * Contains the Styles / Colors that can be used in UI
 */

public class CSSStyle {

    private static CSSStyle instance = null;

    public static String STYLEBACKGROUNDCOLOR = "-fx-background-color: ";
    private static String HEXCOLOR = "#%1$s";
    // Max hex color "FFFFFF" in integer
    private static int MAXHEXCOLOR = 16777216;


    private static Random random = new Random();

    public static CSSStyle getInstance()
    {
        if(instance == null){
            instance = new CSSStyle();
        }

        return instance;
    }

    public static String getRandomHexColor()
    {
        return String.format(HEXCOLOR, Integer.toHexString(random.nextInt(MAXHEXCOLOR)));
    }

    public static String getSpecificHexColor(String hexString) {
        return String.format(HEXCOLOR, hexString);
    }

    /**
     *
     * @param color either color in String "red,blue,etc.." or hexadecimal color
     * @return String to indicate background color
     */
    public static String getBackgroundStyle(String color){
        return CSSStyle.STYLEBACKGROUNDCOLOR + color;
    }

}
