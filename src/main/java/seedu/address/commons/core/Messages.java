package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    //@@author srishag
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    //@@author
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String LOGIN_MESSAGE = "Please Login";

    public static final String MESSAGE_IMPORT_CONTACT = "%1$d contact/s imported!     %2$d contact/s failed to import!";
    public static final String MESSAGE_IMPORT_STATUS =
            "Contacts already existed : %1$d     Contacts not in the correct format : %2$d";
    public static final String MESSAGE_SYNC_CONTACT = "%1$d contact/s Synced!     %2$d contact/s failed to Sync!";
    public static final String MESSAGE_EXPORT_CONTACT = "%1$d contact/s exported!     ";
    public static final String MESSAGE_EXPORT_ERROR = "%1$d contact/s failed to export!";
    public static final String MESSAGE_ALPHABET_LISTED_OVERVIEW = "%1$d persons found!";
    public static final String MESSAGE_NO_ALPHABET_LISTED_OVERVIEW = "no results";
}
