package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeWindowSizeRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author vivekscl
/**
 * Changes the window windowSize according to predefined sizes that the user can choose from
 */
public class ChangeWindowSizeCommand extends Command {

    // These are commonly used sizes
    public static final double DEFAULT_HEIGHT = 600;
    public static final double DEFAULT_WIDTH = 740;
    public static final double SMALL_HEIGHT = 600;
    public static final double SMALL_WIDTH = 800;
    public static final double MEDIUM_HEIGHT = 720;
    public static final double MEDIUM_WIDTH = 1024;
    public static final double BIG_HEIGHT = 1024;
    public static final double BIG_WIDTH = 1600;

    public static final String SMALL_WINDOW_SIZE_PARAM = "small";
    public static final String MEDIUM_WINDOW_SIZE_PARAM = "med";
    public static final String BIG_WINDOW_SIZE_PARAM = "big";
    public static final String INVALID_WINDOW_SIZE_PARAM = "invalid window size";

    public static final String COMMAND_WORD = "ws";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Modifies window windowSize"
            + "Parameters: WINDOWSIZE (Allowed sizes are small, med, big)\n"
            + "Example 1: " + COMMAND_WORD + " small\n"
            + "Example 2: " + COMMAND_WORD + " big";

    public static final String MESSAGE_SUCCESS = "Window sized has been changed to: ";
    public static final String MESSAGE_WINDOW_SIZE_CONSTRAINTS = "The only allowed sizes are small, med and big";

    private final String windowSize;

    public ChangeWindowSizeCommand(String windowSize) {
        this.windowSize = windowSize;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (isValidWindowSize(windowSize)) {
            double newWindowWidth = getUserDefinedWindowWidth(windowSize);
            double newWindowHeight = getUserDefinedWindowHeight(windowSize);
            EventsCenter.getInstance().post(new ChangeWindowSizeRequestEvent(newWindowWidth, newWindowHeight));
            return new CommandResult(MESSAGE_SUCCESS + newWindowWidth + " x " + newWindowHeight);
        } else {
            throw new CommandException(MESSAGE_WINDOW_SIZE_CONSTRAINTS);
        }

    }

    /*
     * Returns the appropriate width according to the given windowSize.
     */
    private double getUserDefinedWindowWidth(String windowSize) {
        double width = DEFAULT_WIDTH;

        switch (windowSize) {
        case SMALL_WINDOW_SIZE_PARAM:
            width = SMALL_WIDTH;
            break;
        case MEDIUM_WINDOW_SIZE_PARAM:
            width = MEDIUM_WIDTH;
            break;
        case BIG_WINDOW_SIZE_PARAM:
            width = BIG_WIDTH;
            break;
        default:
            assert false : "Window size must be specified";
            break;
        }

        return width;
    }

    /*
     * Returns the appropriate height according to the given windowSize.
     */
    private double getUserDefinedWindowHeight(String windowSize) {
        double height = DEFAULT_HEIGHT;

        switch (windowSize) {
        case SMALL_WINDOW_SIZE_PARAM:
            height = SMALL_HEIGHT;
            break;
        case MEDIUM_WINDOW_SIZE_PARAM:
            height = MEDIUM_HEIGHT;
            break;
        case BIG_WINDOW_SIZE_PARAM:
            height = BIG_HEIGHT;
            break;
        default:
            assert false : "Window size must be specified";
            break;
        }

        return height;
    }

    /*
     * Checks if the given windowSize is allowed.
     */
    private boolean isValidWindowSize(String windowSize) {
        return (windowSize.equalsIgnoreCase(SMALL_WINDOW_SIZE_PARAM)
                || windowSize.equalsIgnoreCase(MEDIUM_WINDOW_SIZE_PARAM)
                || windowSize.equalsIgnoreCase(BIG_WINDOW_SIZE_PARAM));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeWindowSizeCommand // instanceof handles nulls
                && windowSize.equals(((ChangeWindowSizeCommand) other).windowSize));

    }
}
