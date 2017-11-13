//@@author keloysiusmak
package seedu.address.logic.parser;

import seedu.address.logic.commands.SetThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SetThemeCommand object
 */
public class SetThemeCommandParser implements Parser<SetThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetThemeCommand
     * and returns an SetThemeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetThemeCommand parse(String theme) throws ParseException {
        return new SetThemeCommand(theme.trim());
    }
}
