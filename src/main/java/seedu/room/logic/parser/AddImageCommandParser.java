package seedu.room.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.room.commons.core.Messages.MESSAGE_INVALID_IMAGE_FORMAT;
import static seedu.room.logic.parser.CliSyntax.PREFIX_IMAGE_URL;

import seedu.room.commons.core.index.Index;
import seedu.room.logic.commands.AddImageCommand;
import seedu.room.logic.parser.exceptions.InvalidImageFormatException;
import seedu.room.logic.parser.exceptions.ParseException;

//@@author shitian007
/**
 * Parses the given {@code String} of arguments in the context of the AddImageCommand
 * and returns an AddImageCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class AddImageCommandParser implements Parser<AddImageCommand> {
    @Override
    public AddImageCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Index index;
        String url;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_IMAGE_URL);

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            url = validImageFormat(argMultimap.getValue(PREFIX_IMAGE_URL).get());
        } catch (InvalidImageFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_IMAGE_FORMAT,
                AddImageCommand.MESSAGE_VALID_IMAGE_FORMATS));
        } catch (Exception e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddImageCommand.MESSAGE_USAGE));
        }
        return new AddImageCommand(index, url);
    }

    /**
     * @param imageUrl actual image url
     * @return image url if it is of the valid format
     * @throws InvalidImageFormatException if image format is invalid
     */
    public String validImageFormat(String imageUrl) throws InvalidImageFormatException {
        String validFormatRegex = "(.+(\\.(?i)(jpg|jpeg|png|bmp))$)";
        if (imageUrl.matches(validFormatRegex)) {
            return imageUrl;
        } else {
            throw new InvalidImageFormatException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }
}
