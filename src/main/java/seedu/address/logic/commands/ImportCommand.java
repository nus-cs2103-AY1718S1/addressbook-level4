package seedu.address.logic.commands;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.AddressBookImportEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.XmlFileStorage;

/**
 * Imports addressbook based on given file path
 */
//@@author Choony93
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports XML file into addressbook from given filepath.\n"
            + "Parameters: FILEPATH (must be absolute)\n"
            + "Example: " + COMMAND_WORD + " FILEPATH";

    public static final String MESSAGE_IMPORT_SUCCESS = "Addressbook successfully imported from: %1$s";
    public static final String MESSAGE_INVALID_IMPORT_FILE_ERROR = "Problem reading file: %1$s";
    public static final String MESSAGE_INVALID_XML_FORMAT_ERROR = "XML syntax not well-formed: %1$s";

    private final String filePath;

    public ImportCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        ReadOnlyAddressBook addressBookOptional;
        try {
            addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
        } catch (DataConversionException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_XML_FORMAT_ERROR, filePath));
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_IMPORT_FILE_ERROR, filePath));
        }

        EventsCenter.getInstance().post(new AddressBookImportEvent(filePath, addressBookOptional));
        return new CommandResult(String.format(MESSAGE_IMPORT_SUCCESS, this.filePath));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && this.filePath.equals(((ImportCommand) other).filePath)); // state check
    }
}
