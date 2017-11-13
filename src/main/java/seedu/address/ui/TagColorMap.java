package seedu.address.ui;

import java.util.HashMap;

//@@author nbriannl
/**
 * The mapping of the tag colors to be shared across any UI components containing tags
 */
public class TagColorMap {
    private static TagColorMap instance;
    private static final String[] COLORS = {"Crimson", "orange", "DarkSalmon", "LightSeaGreen",
        "RoyalBlue", "MediumPurple", "Teal", "Sienna", "HotPink", "MediumSeaGreen",
        "DarkSlateBlue", "CadetBlue", "MidnightBlue", "LightPink", "LightSalmon", "LightSkyBlue", "PaleVioletRed "};
    private static final int NUM_COLORS = COLORS.length;
    private static int colorIndex = 0;

    private HashMap<String, String> tagColors = new HashMap<String, String>();

    private TagColorMap() {
    }

    public static TagColorMap getInstance() {
        if (instance == null) {
            instance = new TagColorMap();
        }
        return instance;
    }

    /**
     * Gets a random unused color for the new tagName, or returns the corresponding color of the old tagName
     * @param tagName
     * @return the color of the tag
     */
    public String getTagColor(String tagName) {
        if (!tagColors.containsKey(tagName)) {
            tagColors.put(tagName, COLORS[colorIndex]);
            updateColorIndex();
        }
        return tagColors.get(tagName);
    }


    //@@author April0616
    /**
     * Updates the color index to pick a new color for the new tag.
     */
    private static void updateColorIndex() {
        if (colorIndex == NUM_COLORS - 1) {
            colorIndex = 0;
        } else {
            colorIndex++;
        }
    }
}
