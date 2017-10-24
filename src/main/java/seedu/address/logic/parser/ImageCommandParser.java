package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ImageCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Image;

/**
 * Parser for the image command
 */
public class ImageCommandParser implements Parser<ImageCommand> {


    @Override
    public ImageCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        String regex = "[\\s]+";
        String[] splitArgs = userInput.trim().split(regex, 2);
        Index index;

        try {
            index = ParserUtil.parseIndex(splitArgs[0]);
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImageCommand.MESSAGE_USAGE));
        }

        String path;
        if (splitArgs.length > 1) {
            path = splitArgs[1];
        } else {
            path = "";
        }

        return new ImageCommand(index, new Image(path));
    }
}
