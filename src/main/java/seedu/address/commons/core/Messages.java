package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_PARENT_COMMAND = "Chirp! Unknown command! "
            + "Press F2 for a list of commands and ESC to close it.";
    public static final String MESSAGE_UNKNOWN_CHILD_COMMAND = "Chirp! Unknown command!\n "
            + "Want to add a new contact or task? use \"add\"\n"
            + "Want to see your contact's full details? use \"select\"\n"
            + "Want to view list of contact or tasks? use \"list\" or \"listpin\"\n"
            + "Want to find your contact or tasks? use \"find\"\n"
            + "Want to find pinned contact? use \"findpinned\"\n"
            + "Want to add remark to your contact? use \"remark\"\n"
            + "Made a mistake adding contact or tasks? use \"undo\"\n"
            + "Want to view list of tasks? use \"task\"\n"
            + "Want to view list of contact? use \"person\"";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Oh no! The format you typed is not correct. "
            + "Scroll down for the correct format.\n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "Oh no! This index provided seems to be "
            + "invalid.";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "Oh no! The task index provided seems to be "
            + "invalid.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_TASK_LISTED_OVERVIEW = "%1$d tasks listed!";
    public static final String MESSAGE_PERSON_ALREADY_PINNED = "Chirp! This person is already pinned.";
    public static final String MESSAGE_PERSON_ALREADY_SELECTED = "Chirp! This person is already selected.";
    public static final String MESSAGE_PERSON_ALREADY_UNPINNED = "Chirp! This person is not pinned.";
    public static final String MESSAGE_PERSON_ALREADY_HIDDEN = "Chirp! This person is already hidden";
    public static final String MESSAGE_PERSON_ALREADY_UNHIDDEN = "Chirp! This person is not hidden";
    public static final String MESSAGE_PERSON_BIRTHDAY_TODAY = "Chirp! Here are the birthdays for today.";
}
