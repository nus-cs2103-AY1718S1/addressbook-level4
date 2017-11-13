package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_THEME_NOT_FOUND;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.MainApp;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
//@@author kosyoz
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class ChangeThemeCommandParser implements Parser<ChangeThemeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ChangeThemeCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeThemeCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        File check;
        String filepath;
        try {
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeThemeCommand.MESSAGE_USAGE));
            }
            Path checkpath = Paths.get(MainApp.class.getResource("/view/" + trimmedArgs + ".css").toURI());
            check = checkpath.toFile();
            filepath = checkpath.toString();
            if (!check.exists()) {
                throw new ParseException(String.format(MESSAGE_THEME_NOT_FOUND));
            }
            try {
                if (!check.getCanonicalPath().equals(filepath)) {
                    throw new ParseException(String.format(MESSAGE_THEME_NOT_FOUND));
                }
            } catch (java.io.IOException ioe) {
                throw new ParseException(String.format(MESSAGE_THEME_NOT_FOUND));
            }
        } catch (URISyntaxException use) {
            throw new ParseException("");
        } catch (NullPointerException use) {
            throw new ParseException(String.format(MESSAGE_THEME_NOT_FOUND));
        }
        return new ChangeThemeCommand(trimmedArgs);
    }
}
