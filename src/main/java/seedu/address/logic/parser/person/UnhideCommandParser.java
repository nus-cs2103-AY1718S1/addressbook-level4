package seedu.address.logic.parser.person;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.person.UnhideCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnhideCommand object
 */
public class UnhideCommandParser implements Parser<UnhideCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the HideCommand
     * and returns an HideCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnhideCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnhideCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnhideCommand.MESSAGE_USAGE));
        }
    }

    @Override
    public String getCommandWord() {
        return UnhideCommand.COMMAND_WORD;
    }
}
