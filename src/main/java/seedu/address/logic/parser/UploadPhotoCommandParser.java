package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UploadPhotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UploadImageCommand object
 */
public class UploadPhotoCommandParser implements Parser<UploadPhotoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UploadImageCommand
     * and returns an UploadImageCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UploadPhotoCommand parse(String args) throws ParseException {
        try {
            String[] argsArr = args.trim().split(" ");

            String indexString = argsArr[0];
            String filePath = "";
            if (argsArr.length > 1) {
                filePath = argsArr[1];
            }
            Index index = ParserUtil.parseIndex(indexString);
            return new UploadPhotoCommand(index, filePath);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UploadPhotoCommand.MESSAGE_USAGE));
        }
    }
}

