package seedu.address.logic.commands;

import seedu.address.storage.AddressBookStorage;

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
    public static final String MESSAGE_LOAD_ADDRESSBOOK_NOT_SUPPORTED = "This function is not yet supported";


    private final AddressBookStorage addressbook;

    public LoadCommand(AddressBookStorage loadedAddressbook) {
        this.addressbook = loadedAddressbook;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        // TODO create new instance of AddressBook and add contacts from there to the current address book
        return new CommandResult(MESSAGE_LOAD_ADDRESSBOOK_NOT_SUPPORTED);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof LoadCommand // instanceof handles nulls
            && this.addressbook.equals(((LoadCommand) other).addressbook)); // state check
    }

}
