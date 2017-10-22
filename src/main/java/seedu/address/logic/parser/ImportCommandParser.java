package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Parses input arguments and creates a new ImportCommand object
 * @throws ParseException if its an illegal value or the file name is not an alphanumeric xml.
 * This is to prevent directory traversal attacks through the import command, by creating a whitelist of accepted
 * commands through a validation regex.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    public static final String FILE_NAME_VALIDATION_REGEX = "^([A-z0-9])\\w+[.][x][m][l]";
    public static final String MESSAGE_FILE_NAME_INVALID = "File name should be an xml file that only contains "
            + "alphanumeric characters";

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format, or the storage file is not able to
     * be found or it is in the wrong data format.
     */
    public ImportCommand parse(String arg) throws ParseException {
        String trimmedArgument = arg.trim();

        // preventing directory traversal attack
        if (!isValidFileName(trimmedArgument)) {
            throw new ParseException(MESSAGE_FILE_NAME_INVALID);
        }

        try {
            ReadOnlyAddressBook readOnlyAddressBook = ParserUtil.parseImportFilePath("./data/import/"
                    + trimmedArgument);
            return new ImportCommand(readOnlyAddressBook.getParcelList());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE) + "\nMore Info: "
                            + ive.getMessage());
        }
    }

    /**
     * checks if the file is alphanumeric name
     */
    public boolean isValidFileName(String fileName) {
        return fileName.matches(FILE_NAME_VALIDATION_REGEX);
    }

}
