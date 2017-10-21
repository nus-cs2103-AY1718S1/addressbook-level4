package seedu.address.ui.util;

import java.util.HashMap;
import java.util.Random;

/**
 * Stores the color information of all tags
 */
public class TagColors {
    private static HashMap<String, String> tagColors = new HashMap<>();;
    private static Random random = new Random();
    private static String[] colors = ColorsUtil.getTagColors();

    private TagColors() {}

    public static String getTagColor(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }
        return tagColors.get(tagValue);
    }

}
