package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ThemeCommand object
 */
public class ThemeCommandParser implements Parser<ThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ThemeCommand
     * and returns an ThemeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ThemeCommand parse(String args) throws ParseException {
        OptionBearingArgument opArgs = new OptionBearingArgument(args);
        String trimmedArgs = opArgs.getRawArgs();

        if (trimmedArgs.isEmpty()
                || (!opArgs.getOptions().contains(ThemeCommand.COMMAND_OPTION_DAY)
                && !opArgs.getOptions().contains(ThemeCommand.COMMAND_OPTION_NIGHT))) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
        }

        return new ThemeCommand(trimmedArgs);
    }
}
