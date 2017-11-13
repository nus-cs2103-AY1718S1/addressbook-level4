package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_FILE_NOT_FOUND;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FILE_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHOTO;

import java.io.FileNotFoundException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.PhotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author April0616
/**
 * Parses input arguments and creates a new PhotoCommand object
 */
public class PhotoCommandParser implements Parser<PhotoCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the PhotoCommand
     * and returns an PhotoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PhotoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PHOTO);

        Index index;
        String localPhotoPath;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            localPhotoPath = argMultimap.getValue(PREFIX_PHOTO).orElse("");


        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhotoCommand.MESSAGE_USAGE));
        }

        try {
            return new PhotoCommand(index, localPhotoPath);
        } catch (FileNotFoundException fnfe) {
            throw new ParseException(
                    String.format(MESSAGE_FILE_NOT_FOUND, PhotoCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_FILE_PATH, PhotoCommand.MESSAGE_USAGE));
        }

    }
}
