//@@author tbhbhbh
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SocialCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SocialCommand object
 */
public class SocialCommandParser implements Parser<SocialCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SocialCommand
     * and returns an SocialCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SocialCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String[] argsArr = args.trim().split(" ");
        Index index;
        String socialMedia;

        if (argsArr.length == 1 || argsArr.length == 0) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(argsArr[0]);
            socialMedia = argsArr[1];

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialCommand.MESSAGE_USAGE));
        }
        return new SocialCommand(index, socialMedia);
    }
}
