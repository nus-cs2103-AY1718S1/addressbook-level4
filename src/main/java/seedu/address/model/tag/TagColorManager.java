package seedu.address.model.tag;

import java.util.HashMap;

/**
 * Manages the displayed color of all tags.
 */
public class TagColorManager {
    private static HashMap<Tag, String> internalMap = new HashMap<>();

    public static String getColor(Tag tag) {
        return internalMap.get(tag);
    }
}
