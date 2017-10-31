package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author hanselblack
/**
 * Parses input arguments and creates a new EmailCommand object
 */
public class EmailCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailCommand parse(String arguments) throws ParseException {
        String[] args = arguments.trim().split("\\s+");
        if (args.length == 2) {
            String email = args[1];
            try {
                Index index = ParserUtil.parseIndex(args[0]);
                return new EmailCommand(index, email);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
            }
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }
    }
}
