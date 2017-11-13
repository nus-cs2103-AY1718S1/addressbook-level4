package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportCommand object
 */

public class ImportCommandParser implements Parser<ImportCommand> {

    public static final String NO_FILE_FOUND = "NO FILE FOUND";
    public static final String FILE_WRONG_FORMAT = "FILE IN WRONG FORMAT. FILE SHOULD BE in .vcf FORMAT";
    public static final String VCF_EXTENSION = "vcf";
    private Path fileLocation;

    //@@author stan789
    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public ImportCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();

        trimmedArgs = trimmedArgs.replace("\\", "/");

        if (args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }
        fileLocation = Paths.get(trimmedArgs);
        File file = new File(trimmedArgs);
        if (!file.isFile()) {
            throw new ParseException(NO_FILE_FOUND);
        }
        String filename = file.getName();
        String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
        if (!extension.equals(VCF_EXTENSION)) {
            throw new ParseException(FILE_WRONG_FORMAT);
        }
        return new ImportCommand(fileLocation);
    }
}
