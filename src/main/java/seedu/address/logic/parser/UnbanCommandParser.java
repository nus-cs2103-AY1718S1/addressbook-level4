package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UnbanCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author jaivigneshvenugopal
/**
 * Parses input arguments and creates a new {@code UnbanCommand} object
 */
public class UnbanCommandParser implements Parser<UnbanCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UnbanCommand
     * and returns an UnbanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnbanCommand parse(String args) throws ParseException, CommandException {
        try {
            if (args.trim().equals("")) {
                return new UnbanCommand();
            } else {
                Index index = ParserUtil.parseIndex(args);
                return new UnbanCommand(index);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnbanCommand.MESSAGE_USAGE));
        }
    }
}

