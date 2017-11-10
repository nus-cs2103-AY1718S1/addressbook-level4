package seedu.address.logic.commands;

import java.io.IOException;

import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyAddressBook;

//@@author LimeFallacie
/**
 *  Imports a contacts list as an .xml document and resets the current addressbook into the written one
 */
public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports a contacts list from chosen directory and\n"
            + "resets the current directory to the provided list.\n"
            + "Parameters: directory\\filename.xml\n"
            + "Example for export: " + COMMAND_WORD + " C:\\desktop\\addressbook.xml\n";

    public static final String MESSAGE_SUCCESS = "Contacts list imported successfully.";

    public static final String MESSAGE_WRITE_ERROR = "The file could not be imported.";

    private String fileLocation;
    private Optional<ReadOnlyAddressBook> importData;

    public ImportCommand(String fileLocation) {
        this.fileLocation = fileLocation;
        //Importing slave object here
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            importData = storage.readAddressBook(fileLocation);
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_WRITE_ERROR);
        } catch (DataConversionException dce) {
            throw new CommandException(MESSAGE_WRITE_ERROR);
        }

        ReadOnlyAddressBook newAddressBook = importData.orElse(null);

        if (newAddressBook != null) {
            model.resetData(newAddressBook);
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_WRITE_ERROR);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && this.fileLocation.equals(((ImportCommand) other).fileLocation)); // state check
    }
}
