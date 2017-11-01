//@@author qihao27
package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.logic.commands.exceptions.CommandException;
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
        + ": Export the AddressBook into preferred location with customizable file name.\n"
        + "New folder will be created if the specified folder is not present in the directory.\n"
        + "Parameters: FILEPATH (must be a xml file path)\n"
        + "Example: " + COMMAND_WORD + " docs/MyAddressBook.xml"
        + "Example: " + COMMAND_WORD + " C:\\MyAddressBook.xml";

    public static final String MESSAGE_FILE_EXPORTED = "Addressbook exported : ";

    private final String userPrefsFilePath;

    public ExportCommand(String userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        ReadOnlyAddressBook localAddressBook = model.getAddressBook();
        AddressBookStorage exportStorage = new XmlAddressBookStorage("data/addressbook.xml");

        try {
            exportStorage.saveAddressBook(localAddressBook, userPrefsFilePath);
        } catch (IOException e) {
            assert false : "IO error";
        }

        return new CommandResult(MESSAGE_FILE_EXPORTED + userPrefsFilePath);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ExportCommand // instanceof handles nulls
            && this.userPrefsFilePath.equals(((ExportCommand) other).userPrefsFilePath)); // state check
    }
}
