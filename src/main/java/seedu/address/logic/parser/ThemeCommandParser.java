//@@author Jemereny
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the input arguments and creates a new ThemeCommand object
 */
public class ThemeCommandParser implements Parser<ThemeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of ThemeCommand
     * and returns a ThemeCommand object for execution
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ThemeCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()
                    || (!trimmedArgs.equalsIgnoreCase(ThemeCommand.LIGHT_THEME)
                    && !trimmedArgs.equalsIgnoreCase(ThemeCommand.DARK_THEME))) {
                throw new IllegalValueException("");
            } else {
                return new ThemeCommand(trimmedArgs);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                    ThemeCommand.MESSAGE_USAGE));
        }
    }
}
