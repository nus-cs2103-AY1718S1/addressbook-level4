package seedu.address.model.tag;

import java.util.HashMap;
import java.util.Random;

import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Manages the displayed color of all tags.
 */
public class TagColorManager {
    /**
     * Stores the colors for all existing tags here so that the same tag always has the same color. Notice this
     * {@code HashMap} has to be declared as a class variable. See the {@code equal} method in {@link Tag} class.
     */
    private static HashMap<Tag, String> internalMap = new HashMap<>();

    // Random number generator (non-secure purpose)
    private static final Random randomGenerator = new Random();

    /**
     * The upper (exclusive) bound should be equal to {@code Math.pow(16, 6)}.
     */
    private static final int RGB_BOUND = 16777216;

    public static String getColor(Tag tag) throws TagNotFoundException {
        if (!internalMap.containsKey(tag)) {
            throw new TagNotFoundException();
        }

        return internalMap.get(tag);
    }

    public static boolean contains(Tag tag) {
        return internalMap.containsKey(tag);
    }

    /**
     * Changes the color of a specific {@link Tag}.
     *
     * @param tag is the tag whose displayed color will be changed.
     * @param color is the RGB value of its new color.
     */
    public static void setColor(Tag tag, String color) {
        internalMap.put(tag, color);
    }

    /**
     * Randomly assign a color to the given {@code tag}. Notice the selection of random color is not cryptographically
     * secured.
     */
    public static void setColor(Tag tag) {
        setColor(tag, Integer.toHexString(randomGenerator.nextInt(RGB_BOUND)));
    }
}
