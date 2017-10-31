package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ResizeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author newalter
/**
 * Parses input arguments and creates a new ResizeCommand object
 */
public class ResizeCommandParser implements Parser<ResizeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ResizeCommand
     * and returns an ResizeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format and precondition
     */
    public ResizeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.matches("\\d+\\s\\d+")) {
            throwParserException();
        }

        String[] sizeParameters = trimmedArgs.split("\\s+");
        int width = Integer.parseInt(sizeParameters[0]);
        int height = Integer.parseInt(sizeParameters[1]);
        if (width > ResizeCommand.MAX_WIDTH || height > ResizeCommand.MAX_HEIGHT) {
            throwParserException();
        }

        return new ResizeCommand(width, height);
    }

    private void throwParserException() throws ParseException {
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResizeCommand.MESSAGE_USAGE));
    }

}
