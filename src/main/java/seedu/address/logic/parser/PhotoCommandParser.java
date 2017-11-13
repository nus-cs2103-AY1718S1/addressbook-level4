package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_IMAGE;

import java.io.File;

import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.PhotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
//@@author RonakLakhotia
/**
 * Parses input arguments and creates a new PhotoCommand object
 */
public class PhotoCommandParser implements Parser<PhotoCommand> {

    private final Logger logger = LogsCenter.getLogger(PhotoCommandParser.class);
    /**
     * Parses the given {@code String} of arguments in the context of the PhotoCommand
     * and returns an PhotoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PhotoCommand parse(String args) throws ParseException {

        logger.info("----------------[USER COMMAND][" + args + "]");
        String trimmedArgs = args.trim();
        String regex = "[\\s]+";
        String[] keywords = trimmedArgs.split(regex, 2);
        boolean isInvalidNumberOfArgs = checkIfInvalidNumberOfArgs(keywords);

        if (isInvalidNumberOfArgs) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhotoCommand.MESSAGE_USAGE)
            );
        }
        String inputPathForImage = keywords[1];
        boolean isFileExists = checkIfFileExists(inputPathForImage);
        if (isFileExists) {
            try {
                Index index = ParserUtil.parseIndex(keywords[0]);
                return new PhotoCommand(index, inputPathForImage);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhotoCommand.MESSAGE_USAGE));
            }

        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_IMAGE, PhotoCommand.MESSAGE_USAGE));
        }

    }
    /**
     * Returns true if the number of arguments entered is invalid.
     */
    public static boolean checkIfInvalidNumberOfArgs(String [] keywords) {
        if (keywords.length != 2) {
            return true;
        }
        return false;
    }
    /**
     * Returns true if the image is present in the path entered, else returns false.
     */
    public static boolean checkIfFileExists(String inputFilePath) {

        String url = inputFilePath + "";
        File file = new File(url);
        boolean isFileExists = file.exists();

        if (url.equalsIgnoreCase("delete")) {

            isFileExists = true;
        }
        return isFileExists;

    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
