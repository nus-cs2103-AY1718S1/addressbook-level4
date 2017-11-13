package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.RadioCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author hanselblack
/**
 * Parses input arguments and creates a new RadioCommand object
 */
public class RadioCommandParser implements Parser<RadioCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the String of words
     * and returns an RadioCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RadioCommand parse(String arguments) throws ParseException {
        String[] args = arguments.trim().split("\\s+");
        if (args.length == 1) {
            return new RadioCommand(args[0]);
        } else if (args.length == 2) {
            return new RadioCommand(args[0], args[1]);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RadioCommand.MESSAGE_USAGE));
        }
    }
}
