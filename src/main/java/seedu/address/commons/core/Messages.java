package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";

    //@@author derickjw
    public static final String[] AUTOCOMPLETE_SUGGESTIONS = {"createDefaultAcc", "removeLogin", "changepw",
                                                             "changeuser", "convenientStation",
                                                             "visualize", "arrange", "list", "locateMrt", "edit",
                                                             "find", "findByAddress", "findByPhone", "findByEmail",
                                                             "findByTags", "delete", "locate", "select", "history",
                                                             "sort", "undo", "clear", "exit", "schedule", };
}
