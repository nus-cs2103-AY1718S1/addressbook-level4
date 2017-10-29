package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_ORDER_PERSONS_INDEX = "The person indexes provided are not in "
            + "ascending order";
    public static final String MESSAGE_REPEATED_INDEXES = "Repeated person indexes are found";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";

    public static final String[] MESSAGE_AUTOCOMPLETE_LIST = {"add", "clear", "creategroup", "delete", "deletegroup",
        "exit", "find", "help", "undo", "redo", "pin", "unpin", "sort", "edit", "history", "list", "select",
        "setcolour", "sort name", "sort phone", "sort email"};


}
