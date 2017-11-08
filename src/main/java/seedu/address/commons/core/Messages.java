package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_PARENT_COMMAND = "Chirp! Unknown command! "
            + "Press F12 for a list of commands and ESC to close it.";
    public static final String MESSAGE_UNKNOWN_CHILD_COMMAND = "Chirp! Unknown command!\n "
            + "Want to add a new friend or task? use \"add\"\n"
            + "Want to view list of friends or tasks? use \"list\" or \"listpin\"\n"
            + "Want to find your friends or tasks? use \"find\"\n"
            + "Made a mistake adding friends or tasks? use \"undo\"";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Oh no! The format you typed is not correct. "
            + "Scroll down for the correct format.\n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "Oh no! This index provided seems to be "
            + "invalid.";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "Oh no! The task index provided seems to be "
            + "invalid.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_TASK_LISTED_OVERVIEW = "%1$d tasks listed!";
    public static final String MESSAGE_PERSON_ALREADY_PINNED = "Chirp! This person is already pinned.";
    public static final String MESSAGE_PERSON_ALREADY_UNPINNED = "Chirp! This person is not pinned.";
}
