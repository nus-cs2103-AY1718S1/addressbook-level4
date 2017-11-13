package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.parcel.ReadOnlyParcel;

//@@author kennard123661
/**
 * Parses input arguments and creates a new ImportCommand object
 *
 * @throws ParseException if its an illegal value or the file name contains non-alphanumeric or non-underscore
 * characters.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    public static final String IMPORT_FILE_DIRECTORY = "./data/import/";

    public static final String FILE_NAME_VALIDATION_REGEX = "([a-zA-Z0-9_]+)";
    public static final String FILE_NAME_CONSTRAINTS = "File should be an xml file with a naming convention that only "
            + "contains alphanumeric or underscore characters";

    public static final String MESSAGE_INVALID_FILE_NAME = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            ImportCommand.MESSAGE_USAGE) + "\nMore Info: " + FILE_NAME_CONSTRAINTS;

    /**
     * Parses the given {@code String} of arguments in the context of the {@link ImportCommand}
     * and returns an {@link ImportCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format, or the storage file is not able to
     * be found or it is in the wrong data format.
     */
    public ImportCommand parse(String arg) throws ParseException {
        String trimmedArgument = arg.trim();

        if (!isValidFileName(trimmedArgument)) {
            throw new ParseException(MESSAGE_INVALID_FILE_NAME);
        }

        try {
            String fullPath = IMPORT_FILE_DIRECTORY + trimmedArgument + ".xml";
            ReadOnlyAddressBook readOnlyAddressBook = ParserUtil.parseImportFilePath(fullPath);

            List<ReadOnlyParcel> parcels = readOnlyAddressBook.getParcelList();

            return new ImportCommand(parcels);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(ive.getMessage()));
        }
    }

    /**
     * returns true if the file is a valid file name using {@code FILE_NAME_VALIDATION_REGEX}
     */
    public static boolean isValidFileName(String fileName) {
        return fileName.matches(FILE_NAME_VALIDATION_REGEX);
    }

}
