package seedu.address.logic.commands.imports;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.InvalidFileExtensionException;
import seedu.address.commons.exceptions.InvalidFilePathException;
import seedu.address.commons.exceptions.InvalidNameException;
import seedu.address.commons.exceptions.InvalidNameSeparatorException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.Storage;

//@@author low5545
/**
 * Imports data from a xml file (in BoNUS-specific format) to the application.
 */
public class ImportXmlCommand extends ImportCommand {

    public ImportXmlCommand(String path) {
        super(path, ImportType.XML);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            Optional<ReadOnlyAddressBook> importedAddressBookOptional = storage.readAddressBook(path);
            if (!importedAddressBookOptional.isPresent()) {
                throw new CommandException(MESSAGE_FILE_NOT_FOUND);
            }
            model.addData(importedAddressBookOptional.get());
            return new CommandResult(String.format(MESSAGE_IMPORT_SUCCESS, path));
        } catch (IOException e) {
            throw new CommandException(MESSAGE_PROBLEM_READING_FILE);
        } catch (InvalidFileExtensionException e) {
            throw new CommandException(MESSAGE_NOT_XML_FILE);
        } catch (InvalidNameException e) {
            throw new CommandException(MESSAGE_INVALID_NAME);
        } catch (InvalidNameSeparatorException e) {
            throw new CommandException(MESSAGE_INVALID_NAME_SEPARATOR);
        } catch (InvalidFilePathException e) {
            throw new CommandException(MESSAGE_CONSECUTIVE_SEPARATOR);
        } catch (DataConversionException e) {
            throw new CommandException(MESSAGE_INVALID_XML_DATA_FORMAT);
        }
    }

    @Override
    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
