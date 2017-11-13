package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SwitchThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author ZhangH795

/**
 * Parses input arguments and creates a new SwitchThemeCommand object
 */
public class SwitchThemeCommandParser implements Parser<SwitchThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SwitchThemeCommand
     * and returns an SwitchThemeCommand object for execution.
     * @throws ParseException if the user input does not provide any input
     */
    public SwitchThemeCommand parse(String args) throws ParseException {
        String userInput = args.trim();
        if (userInput.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchThemeCommand.MESSAGE_USAGE));
        } else {
            return new SwitchThemeCommand(userInput);
        }
    }
}
