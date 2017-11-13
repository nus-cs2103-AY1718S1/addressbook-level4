//@@author tshradheya
package seedu.address.ui.util;

import java.util.HashMap;


/**
 * Singleton Class
 * Stores hashmap which maps color to the tag
 */
public class TagColor {

    private static TagColor tagColor = null;
    private HashMap<String, String> colorsMapping = new HashMap<String, String>();


    private TagColor() {

    }

    public static TagColor getInstance() {
        if (tagColor == null) {
            tagColor = new TagColor();
        }
        return tagColor;
    }


    public boolean containsTag(String tag) {
        return colorsMapping.containsKey(tag);
    }

    public void addColor(String tag, String color) {
        colorsMapping.put(tag, color);
    }

    public String getColor(String tag) {
        return colorsMapping.get(tag);
    }
}
