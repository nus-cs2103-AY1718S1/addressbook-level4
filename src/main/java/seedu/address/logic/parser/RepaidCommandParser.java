package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RepaidCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author jaivigneshvenugopal
/**
 * Parses input arguments and creates a new {@ code RepaidCommand} object
 */
public class RepaidCommandParser implements Parser<RepaidCommand> {
    /**
    * Parses the given {@code String} of arguments in the context of the RepaidCommand
    * and returns an RepaidCommand object for execution.
    * @throws ParseException if the user input does not conform the expected format
    */
    public RepaidCommand parse(String args) throws ParseException, CommandException {
        try {
            if (args.trim().equals("")) {
                return new RepaidCommand();
            } else {
                Index index = ParserUtil.parseIndex(args);
                return new RepaidCommand(index);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RepaidCommand.MESSAGE_USAGE));
        }
    }
}
