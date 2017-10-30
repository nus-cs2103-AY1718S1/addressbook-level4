package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.XmlAddressBookStorage;

/**
 * Export a copy of the address book to a user-specified filepath.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_ALIAS = "x";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Export the addressbook into preferred file path.\n"
        + "Parameters: FILEPATH (must be a xml file path)\n"
        + "Example: " + COMMAND_WORD + " data/addressbook.xml";

    public static final String MESSAGE_SUCCESS = "Addressbook exported to: ";

    private final String userPrefsFilePath;

    public ExportCommand(String userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    @Override
    public CommandResult execute() {
        ReadOnlyAddressBook localAddressBook = model.getAddressBook();
        AddressBookStorage exportStorage = new XmlAddressBookStorage("data/addressbook.xml");

        try {
            exportStorage.saveAddressBook(localAddressBook, userPrefsFilePath);
        } catch (IOException e) {
            assert false: "Target filepath should not be missing.";
        }

        return new CommandResult(MESSAGE_SUCCESS + userPrefsFilePath);
    }

}
