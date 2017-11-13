package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_EVENTS_LISTED_OVERVIEW = "%1$d events listed!";
    public static final String MESSAGE_UNDO_ASSERTION_ERROR = "Undo is to revert a stage, it should not fail";
    public static final String MESSAGE_REDO_ASSERTION_ERROR = "The command has been successfully executed previously, "
        + "it should not fail now";

}
