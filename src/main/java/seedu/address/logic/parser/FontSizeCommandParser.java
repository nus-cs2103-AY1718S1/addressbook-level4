package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.FontSizeCommand.MESSAGE_INVALID_INPUT;

import seedu.address.logic.commands.FontSizeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author charlesgoh
/**
 * Parses input arguments and creates a new SortCommand object based on the field and order parameters provided
 */
public class FontSizeCommandParser implements Parser<FontSizeCommand> {
    public static final int PARAMETER_POSITION = 0;

    /**
     * Parses the given {@code String} of arguments in the context of the FontSizeCommand
     * and returns a FontSizeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FontSizeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FontSizeCommand.MESSAGE_USAGE));
        }

        // Converts arg arrays to lower case to account for caps entries
        String[] argKeywords = trimmedArgs.split("\\s");
        for (int i = 0; i < argKeywords.length; i++) {
            argKeywords[i] = argKeywords[i].toLowerCase();
        }

        if (argKeywords.length != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FontSizeCommand.MESSAGE_USAGE));
        }

        if (!FontSizeCommand.ACCEPTED_PARAMETERS.contains(argKeywords[PARAMETER_POSITION])
                || !FontSizeCommand.ACCEPTED_PARAMETERS.contains(argKeywords[PARAMETER_POSITION])
                || !FontSizeCommand.ACCEPTED_PARAMETERS.contains(argKeywords[PARAMETER_POSITION])) {
            throw new ParseException(String.format(MESSAGE_INVALID_INPUT, FontSizeCommand.MESSAGE_USAGE));
        }

        // If there are no problems with the input, return a new instance of SortCommand
        return new FontSizeCommand(argKeywords[PARAMETER_POSITION]);

    }

}
