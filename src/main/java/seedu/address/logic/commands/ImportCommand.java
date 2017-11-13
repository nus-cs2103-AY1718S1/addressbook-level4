package seedu.address.logic.commands;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.storage.exceptions.EmptyFileException;

/**
 * Imports a VCard file to the address book.
 */

public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": import contacts from VCard file.\n"
            + "Parameters: KEYWORD [FILE DIRECTORY]\n"
            + "Example for import from VCard file: " + COMMAND_WORD + " C:\\Users\\user\\desktop\\add.vcf(Windows)\n"
            + "Example for import from VCard file: " + COMMAND_WORD + " /Users/user/Desktop/add.vcf(MAC OS)\n";

    public static final String MESSAGE_SUCCESS = "%1$s contact/contacts have been imported successfully.";

    public static final String MESSAGE_EMPTY_FILE = "The file is empty.";

    public static final String MESSAGE_FILE_INVALID = "The file is invalid.";

    public static final String MESSAGE_DUPLICATE = "\nThere is/are duplicated contacts.";

    public static final String MESSAGE_ILLEGAL_VALUE =
            "\nThere is/are contacts with information that have invalid format.";

    private Path fileLocation;

    public ImportCommand(Path fileLocation) {
        this.fileLocation = fileLocation;
    }

    //@@author stan789
    @Override
    public CommandResult execute() throws CommandException {
        ImportAnalysis importAnalysis = new ImportAnalysis();

        try {
            model.importFile(fileLocation, importAnalysis);

        } catch (EmptyFileException e) {
            throw new CommandException(MESSAGE_EMPTY_FILE);

        } catch (IOException e) {
            throw new CommandException(MESSAGE_FILE_INVALID);
        }
        String feedbackToUser = String.format(MESSAGE_SUCCESS, importAnalysis.getNumContacts());

        if (importAnalysis.isDuplicateContacts()) {
            feedbackToUser = feedbackToUser.concat(MESSAGE_DUPLICATE);
        }
        if (importAnalysis.isIllegalValue()) {
            feedbackToUser = feedbackToUser.concat(MESSAGE_ILLEGAL_VALUE);
        }
        return new CommandResult(feedbackToUser);
    }

    //@@author stan789
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && this.fileLocation.equals(((ImportCommand) other).fileLocation)); // state check
    }
}
