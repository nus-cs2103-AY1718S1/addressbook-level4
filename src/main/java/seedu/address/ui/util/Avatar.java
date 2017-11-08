package seedu.address.ui.util;

import java.awt.Color;
import java.util.HashMap;
import java.util.Random;

/**
 * Stores the information of all person avatars
 */
public class Avatar {
    private static HashMap<String, String> avatarColors = new HashMap<>();
    private static Random random = new Random();
    private static String[] colors = ColorsUtil.getColors();

    private static final int COLOR_OFFSET_BOUND = 50;

    private Avatar() {}

    public static String getColor(String name) {
        if (!avatarColors.containsKey(name)) {
            Color defaultColor = Color.decode(colors[random.nextInt(colors.length)]);
            String newColor = generateNewColor(defaultColor);
            avatarColors.put(name, newColor);
        }
        return avatarColors.get(name);
    }

    public static String getInitial(String name) {
        return name.substring(0, 1);
    }

    /**
     * Generates a new color with a random offset from {@code defaultColor}.
     */
    private static String generateNewColor(Color defaultColor) {
        int r = defaultColor.getRed();
        int g = defaultColor.getGreen();
        int b = defaultColor.getBlue();
        int newR = Math.max(0, Math.min(255, (r + generateOffset())));
        int newG = Math.max(0, Math.min(255, (g + generateOffset())));
        int newB = Math.max(0, Math.min(255, (b + generateOffset())));

        return String.format("#%02x%02x%02x", newR, newG, newB);
    }

    /**
     * Generates an offset in the range [-1 * COLOR_OFFSET_BOUND, COLOR_OFFSET_BOUND].
     */
    private static int generateOffset() {
        return random.nextInt(COLOR_OFFSET_BOUND * 2) - COLOR_OFFSET_BOUND;
    }
}
