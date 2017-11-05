package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Chirp! Unknown command! Press F2 for a list of commands.";
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
