package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author derrickchua

/**
 * Adds a person to the address book.
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";
    public static final String COMMAND_ALIAS = "lo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logout of Google Contacts ";

    public static final String MESSAGE_SUCCESS = "Logged out!";
    public static final String MESSAGE_FAILURE = "Failure logging out. Are you sure you are logged in?";

    /** Directory to store user credentials. */
    private final java.io.File dataStoreDir =
            new java.io.File(System.getProperty("user.home"), ".store/addressbook/StoredCredential");

    private final java.io.File syncedIDs =
            new java.io.File("syncedIDs.dat");

    @Override
    public CommandResult execute() throws CommandException {
        syncedIDs.delete();
        if (dataStoreDir.delete()) {
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } else {
            throw new CommandException(String.format(MESSAGE_FAILURE));
        }


    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof LogoutCommand; // instanceof handles null
    }
}
