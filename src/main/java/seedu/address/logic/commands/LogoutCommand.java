package seedu.address.logic.commands;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

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
        SyncCommand.client = null;
        SyncCommand.clientFuture = null;
        syncedIDs.delete();
        try {
            resetIDs();
        } catch (Exception e) {
            assert false;
        }

        if (dataStoreDir.delete()) {
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } else {
            throw new CommandException(String.format(MESSAGE_FAILURE));
        }


    }

    /** Removes all IDs from linked contacts
     *
     * @throws DuplicatePersonException
     * @throws PersonNotFoundException
     */
    private void resetIDs () throws DuplicatePersonException, PersonNotFoundException {
        List<ReadOnlyPerson> personList = model.getFilteredPersonList();

        for (ReadOnlyPerson person : personList) {
            Person updated = SyncCommand.setId(person, "");
            model.updatePerson(person, updated);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof LogoutCommand; // instanceof handles null
    }
}
