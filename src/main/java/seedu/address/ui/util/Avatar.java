package seedu.address.ui.util;

import java.util.HashMap;
import java.util.Random;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Stores the color information of all person avatars
 */
public class Avatar {
    private static HashMap<ReadOnlyPerson, String> avatarColors = new HashMap<>();
    private static Random random = new Random();
    private static String[] colors = ColorsUtil.getColors();

    private Avatar() {}

    public static String getColor(ReadOnlyPerson person) {
        if (!avatarColors.containsKey(person)) {
            avatarColors.put(person, colors[random.nextInt(colors.length)]);
        }
        return avatarColors.get(person);
    }

    public static String getInitial(String name) {
        return name.substring(0, 1);
    }
}
