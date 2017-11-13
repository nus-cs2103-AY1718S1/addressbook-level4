package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author moomeowroar
/**
 * Parses input arguments and creates a new ChangePwdCommand object
 */
public class ChangeThemeCommandParser implements Parser<ChangeThemeCommand> {

    public static final int NUM_OF_PARAM = 2; //Inclusive of command word

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an ChangePwdCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeThemeCommand parse(String args) throws ParseException {
        try {
            String[] array = ParserUtil.parseByDelimiter(args, " ");
            if (array.length == NUM_OF_PARAM) {
                switch (array[1]) {
                case "light":
                    return new ChangeThemeCommand("LightTheme");
                case "dark":
                    return new ChangeThemeCommand("DarkTheme");
                case "youtube":
                    return new ChangeThemeCommand("YoutubeTheme");
                default:
                    break;
                }
            }
            throw new ParseException(
                    ChangeThemeCommand.MESSAGE_NO_THEME + "\n" + ChangeThemeCommand.MESSAGE_USAGE);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    ChangeThemeCommand.MESSAGE_NO_THEME + "\n" + ChangeThemeCommand.MESSAGE_USAGE);
        }
    }

}
