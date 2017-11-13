package seedu.address.logic.parser;
//@@author zhoukai07
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class ChangeThemeCommandParser implements Parser<ChangeThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeThemeCommand parse(String args) throws ParseException {
        try {
            int index = ParserUtil.parseTheme(args);
            return new ChangeThemeCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeThemeCommand.MESSAGE_USAGE));
        }
    }
}
//@@author
