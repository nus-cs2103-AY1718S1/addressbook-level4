package seedu.address.logic.commands;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

//@@author freesoup
/**
 * Imports contact from a .vcf file.
 * Adds the contacts into the address book.
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";
    public static final String COMMAND_USAGE = COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports contacts from a .vcf file.";

    public static final String MESSAGE_SUCCESS = "Successfully imported contacts. %1$s duplicates were found";
    public static final String MESSAGE_WRONG_FORMAT = "File chosen is not of .vcf or .xml type";
    public static final String MESSAGE_FILE_CORRUPT = "File is corrupted. Please check.";
    public static final String MESSAGE_FILE_NOT_FOUND = "File was not found in specified directory.";
    public static final String MESSAGE_IMPORT_CANCELLED = "Import cancelled";
    public static final String MESSAGE_FILE_INVALID = "File might not have been exported from Rubrika"
            + " and contains missing fields.";
    public static final String XML_EXTENSION = ".xml";
    public static final String VCF_EXTENSION = ".vcf";

    public final List<ReadOnlyPerson> toImport;

    public ImportCommand (List<ReadOnlyPerson> importList) {
        toImport = importList;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        int duplicate = 0;
        for (ReadOnlyPerson toAdd : toImport) {
            try {
                model.addPerson(toAdd);
            } catch (DuplicatePersonException dpe) {
                duplicate++;
            }
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, duplicate));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && this.toImport.equals(((ImportCommand) other).toImport)); // state check
    }
}
