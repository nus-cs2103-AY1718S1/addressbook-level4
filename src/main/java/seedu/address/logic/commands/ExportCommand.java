package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.XmlAddressBookStorage;

/**
 * Exports existing contacts to external XML file
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports existing contacts.\n"
            + "Parameters: FILE PATH (must be a valid file path where the current user has write access\n"
            + "Example: " + COMMAND_WORD + " /Users/seedu/Documents";

    public static final String MESSAGE_EXPORT_CONTACTS_SUCCESS = "Contacts exported to: %1$s";
    public static final String MESSAGE_EXPORT_CONTACTS_FAILURE = "Unable to export contacts to: %1$s";

    private final String exportFilePath;

    public ExportCommand(String filePath) {
        exportFilePath = filePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        ReadOnlyAddressBook readOnlyAddressBook = model.getAddressBook();
        AddressBookStorage exportStorage = new XmlAddressBookStorage(exportFilePath);
        try {
            exportStorage.saveAddressBook(readOnlyAddressBook);
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_EXPORT_CONTACTS_FAILURE, exportFilePath));
        }
        return new CommandResult(String.format(MESSAGE_EXPORT_CONTACTS_SUCCESS, exportFilePath));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && this.exportFilePath.equals(((ExportCommand) other).exportFilePath)); // state check
    }
}
