package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object
 */

public class ExportCommandParser implements Parser<ExportCommand> {

    public  static final String INVALID_DIRECTORY = "The directory given is invalid.";
    public  static final String INVALID_FILE_NAME = "The format for the file name is invalid.";
    public static final String VCF_EXTENSION = "vcf";
    public static final String CSV_EXTENSION = "csv";
    public static final String INVALID_EXTENSION = "File created should end with .vcf or .csv extension.";



    //@@author stan789
    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public ExportCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();

        trimmedArgs = trimmedArgs.replace("\\", "/");

        if (args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
        String directory = trimmedArgs.substring(0, trimmedArgs.lastIndexOf("/") + 1);
        File dir = new File(directory);
        if (!dir.isDirectory()) {
            throw new ParseException(INVALID_DIRECTORY);
        }

        String fileName = trimmedArgs.substring(trimmedArgs.lastIndexOf("/") + 1, trimmedArgs.lastIndexOf("."));
        if (!fileName.matches("[A-Za-z0-9.-_]+")) {
            throw new ParseException(INVALID_FILE_NAME);
        }

        String extension = trimmedArgs.substring(trimmedArgs.lastIndexOf(".") + 1, trimmedArgs.length());
        if (!extension.equals(VCF_EXTENSION) && !extension.equals(CSV_EXTENSION)) {
            throw new ParseException(INVALID_EXTENSION);
        }

        return new ExportCommand(trimmedArgs, fileName, extension);
    }
}

