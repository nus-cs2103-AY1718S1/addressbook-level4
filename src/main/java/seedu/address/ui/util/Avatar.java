package seedu.address.ui.util;

import java.util.HashMap;
import java.util.Random;

/**
 * Stores the color information of all person avatars
 */
public class Avatar {
    private static HashMap<String, String> avatarColors = new HashMap<>();
    private static Random random = new Random();
    private static String[] colors = ColorsUtil.getColors();

    private Avatar() {}

    public static String getColor(String name) {
        if (!avatarColors.containsKey(name)) {
            avatarColors.put(name, colors[random.nextInt(colors.length)]);
        }
        return avatarColors.get(name);
    }

    public static String getInitial(String name) {
        return name.substring(0, 1);
    }
}
