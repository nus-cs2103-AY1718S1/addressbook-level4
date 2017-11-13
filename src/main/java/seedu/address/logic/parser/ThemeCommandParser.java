package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ThemeCommand.MESSAGE_USAGE;

import seedu.address.logic.commands.ThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author teclu
/**
 * Parses input arguments and creates a new ThemeCommand object.
 */
public class ThemeCommandParser implements Parser<ThemeCommand> {

    /**
     * Parses the given (@code String) in the context of a ThemeCommand.
     * @return ThemeCommand Object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public ThemeCommand parse(String userInput) throws ParseException {
        if (userInput.length() == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        return new ThemeCommand(userInput);
    }
}
