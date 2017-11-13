//@@author qihao27
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.XmlFileStorage;
import seedu.address.storage.XmlSerializableAddressBook;

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
        + "Example: " + COMMAND_WORD + " docs/MyAcquaiNote.xml"
        + "Example: " + COMMAND_WORD + " D:\\MyAcquaiNote.xml";

    public static final String MESSAGE_FILE_EXPORTED = "Addressbook exported : ";
    public static final String MESSAGE_STORAGE_ERROR = "Error exporting address book.";

    private final String userPrefsFilePath;

    public ExportCommand(String userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    @Override
    public CommandResult execute() {
        requireNonNull(model);

        ReadOnlyAddressBook localAddressBook = model.getAddressBook();

        File file = new File(userPrefsFilePath);

        try {
            FileUtil.createIfMissing(file);
        } catch (IOException e) {
            return new CommandResult(MESSAGE_STORAGE_ERROR);
        }

        try {
            XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBook(localAddressBook));
        } catch (FileNotFoundException e) {
            return new CommandResult(MESSAGE_STORAGE_ERROR);
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
