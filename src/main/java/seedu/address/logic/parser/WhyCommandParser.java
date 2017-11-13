//@@author arnollim
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.WhyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * WhyCommandParser: Parses the User input into a valid Why Command
 */
public class WhyCommandParser implements Parser<WhyCommand> {
    /**
     * Parses the given {@code Index} of arguments in the context of the WhyCommnad
     * and returns a WhyCommand Object with the specified index.
     * @throws ParseException if the user input does not conform the expected format
     */
    public WhyCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new WhyCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, WhyCommand.MESSAGE_USAGE));
        }
    }

}
