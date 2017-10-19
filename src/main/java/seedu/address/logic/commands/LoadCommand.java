package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Loads contacts from a pre-existing address book to the current one.
 * The pre-existing address book' name is given as a parameter.
 */
public class LoadCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "load";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Loads contacts from a pre-existing address "
        + "book to the current one. The pre-existing address book' name is given as a parameter.\n"
        + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
        + "Example: " + COMMAND_WORD + " myaddressbook.xml";

    public static final String MESSAGE_LOAD_ADDRESSBOOK_SUCCESS = "Successfully loaded the address book.";
    public static final String MESSAGE_ERROR_LOADING_ADDRESSBOOK = "The address book couldn't be read. "
        + "Make sure your file is in the right directory and that it's in the correct format.";

    private final ReadOnlyAddressBook addressBook;

    public LoadCommand(ReadOnlyAddressBook loadedAddressbook) {
        this.addressBook = loadedAddressbook;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        ObservableList<ReadOnlyPerson> persons = addressBook.getPersonList();

        for (ReadOnlyPerson person : persons) {
            try {
                model.addPerson(person);

            } catch (DuplicatePersonException dpe) {
                // don't have to do anything as the person is already in the address book
            }
        }

        return new CommandResult(MESSAGE_LOAD_ADDRESSBOOK_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof LoadCommand // instanceof handles nulls
            && this.addressBook.equals(((LoadCommand) other).addressBook)); // state check
    }

}
