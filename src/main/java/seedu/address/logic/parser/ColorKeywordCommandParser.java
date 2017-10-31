package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ColorKeywordCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ColorKeywordCommand object
 */
public class ColorKeywordCommandParser implements Parser<ColorKeywordCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ColorKeywordCommand
     * and returns an ColorKeywordCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ColorKeywordCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (isValidAttribute(trimmedArgs)) {
            return new ColorKeywordCommand(trimmedArgs);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ColorKeywordCommand.MESSAGE_USAGE));
        }

    }

    private boolean isValidAttribute(String args) {
        return args.equalsIgnoreCase(ColorKeywordCommand.DISABLE_COLOR)
                || args.equalsIgnoreCase(ColorKeywordCommand.ENABLE_COLOR);
    }
}
