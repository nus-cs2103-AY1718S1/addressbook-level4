package seedu.address.model.tag;

import java.util.HashMap;
import java.util.Random;

import seedu.address.model.tag.exceptions.TagNotFoundException;

//@@author yunpengn
/**
 * Manages the displayed color of all tags.
 *
 * TODO: Should we extract color out to be a {@code Color} class?
 */
public class TagColorManager {
    /**
     * Stores the colors for all existing tags here so that the same tag always has the same color. Notice this
     * {@code HashMap} has to be declared as a class variable. See the {@code equal} method in {@link Tag} class.
     */
    private static HashMap<Tag, String> internalMap = new HashMap<>();

    // Random number generator (non-secure purpose)
    private static final Random randomGenerator = new Random();

    private static final String TAG_NOT_FOUND = "The provided tag does not exist.";

    /**
     * The upper (exclusive) bound should be equal to {@code Math.pow(16, 6)}. The lower (inclusive) bound should be
     * equal to {@code Math.pow(16, 5)}. Thus, the interval is {@code Math.pow(16, 6) - Math.pow(16, 5)}.
     */
    private static final int RGB_INTERVAL = 15728640;
    private static final int RGB_LOWER_BOUND = 1048576;

    public static String getColor(Tag tag) throws TagNotFoundException {
        if (!internalMap.containsKey(tag)) {
            throw new TagNotFoundException(TAG_NOT_FOUND);
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
        int randomColorCode = randomGenerator.nextInt(RGB_INTERVAL) + RGB_LOWER_BOUND;
        setColor(tag, "#" + Integer.toHexString(randomColorCode));
    }
}
