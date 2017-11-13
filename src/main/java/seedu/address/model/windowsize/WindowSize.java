package seedu.address.model.windowsize;

//@@author vivekscl
/**
 * Represents the window size.
 * Since all methods and attributes are static, an instance of this class will not be made.
 * Hence, the class is set to final with a default private constructor to prevent accidental creation of this class.
 * Guarantees: immutable; window size is valid as declared in {@link #isValidWindowSize(String)}
 */
public final class WindowSize {

    public static final String MESSAGE_WINDOW_SIZE_CONSTRAINTS = "The only allowed sizes are small, med and big";

    // Commonly used sizes
    public static final double DEFAULT_HEIGHT = 600;
    public static final double DEFAULT_WIDTH = 740;
    public static final double SMALL_HEIGHT = 600;
    public static final double SMALL_WIDTH = 800;
    public static final double MEDIUM_HEIGHT = 720;
    public static final double MEDIUM_WIDTH = 1024;
    public static final double BIG_HEIGHT = 1024;
    public static final double BIG_WIDTH = 1600;

    public static final String SMALL_WINDOW_SIZE_INPUT = "small";
    public static final String MEDIUM_WINDOW_SIZE_INPUT = "med";
    public static final String BIG_WINDOW_SIZE_INPUT = "big";

    private WindowSize() {}

    /**
     * Returns the appropriate width according to the given windowSize.
     */
    public static double getUserDefinedWindowWidth(String windowSize) {
        double width = DEFAULT_WIDTH;

        switch (windowSize.toLowerCase()) {
        case SMALL_WINDOW_SIZE_INPUT:
            width = SMALL_WIDTH;
            break;
        case MEDIUM_WINDOW_SIZE_INPUT:
            width = MEDIUM_WIDTH;
            break;
        case BIG_WINDOW_SIZE_INPUT:
            width = BIG_WIDTH;
            break;
        default:
            assert false : "Window size must be specified correctly";
            break;
        }

        return width;
    }

    /**
     * Returns the appropriate height according to the given windowSize.
     */
    public static double getUserDefinedWindowHeight(String windowSize) {
        double height = DEFAULT_HEIGHT;

        switch (windowSize.toLowerCase()) {
        case SMALL_WINDOW_SIZE_INPUT:
            height = SMALL_HEIGHT;
            break;
        case MEDIUM_WINDOW_SIZE_INPUT:
            height = MEDIUM_HEIGHT;
            break;
        case BIG_WINDOW_SIZE_INPUT:
            height = BIG_HEIGHT;
            break;
        default:
            assert false : "Window size must be specified correctly";
            break;
        }

        return height;
    }

    /**
     * Returns true if given windowSize is a valid window size.
     */
    public static boolean isValidWindowSize(String windowSize) {
        boolean isSmallWindowSize = windowSize.equalsIgnoreCase(SMALL_WINDOW_SIZE_INPUT);
        boolean isMediumWindowSize = windowSize.equalsIgnoreCase(MEDIUM_WINDOW_SIZE_INPUT);
        boolean isBigWindowSize = windowSize.equalsIgnoreCase(BIG_WINDOW_SIZE_INPUT);
        return isSmallWindowSize || isMediumWindowSize || isBigWindowSize;
    }


}
