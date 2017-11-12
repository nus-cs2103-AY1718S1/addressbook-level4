package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FacebookCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author taojiashu
/**
 * Parses input arguments and creates a new FacebookCommand object.
 */
public class FacebookCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the FacebookCommand
     * and returns a FacebookCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public FacebookCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new FacebookCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FacebookCommand.MESSAGE_USAGE));
        }
    }
}
