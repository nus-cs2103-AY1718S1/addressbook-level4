//@@author tshradheya
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DisplayPictureCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DisplayPicture;

/**
 * Parses input arguments and creates a new DisplayPictureCommand object
 */
public class DisplayPictureCommandParser implements Parser<DisplayPictureCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DisplayPictureCommand
     * and returns an DisplayPictureCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplayPictureCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String regex = "[\\s]+";
        String[] splitArgs = args.trim().split(regex, 2);

        Index index;
        try {
            index = ParserUtil.parseIndex(splitArgs[0]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DisplayPictureCommand.MESSAGE_USAGE));
        }

        String path;
        if (splitArgs.length > 1) {
            path = splitArgs[1];
        } else {
            path = "";
        }


        return new DisplayPictureCommand(index, new DisplayPicture(path));
    }
}
