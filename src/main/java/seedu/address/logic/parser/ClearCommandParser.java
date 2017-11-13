package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearPersonCommand;
import seedu.address.logic.commands.ClearTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClearCommand object
 */
public class ClearCommandParser implements Parser<ClearCommand> {

    /**
     * Parses the given {@code String} of arguments and returns a {@code ClearCommand}
     * that either clears the {@code Person} list, the {@code Task} list, or both.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClearCommand parse(String args) throws ParseException {
        if (args == null || args.isEmpty()) {
            return new ClearCommand();
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TASK, PREFIX_PERSON);

        if (argMultimap.getValue(PREFIX_PERSON).isPresent() && argMultimap.getValue(PREFIX_TASK).isPresent()) {
            return new ClearCommand();
        } else if (argMultimap.getValue(PREFIX_PERSON).isPresent()) {
            return new ClearPersonCommand();
        } else if (argMultimap.getValue(PREFIX_TASK).isPresent()) {
            return new ClearTaskCommand();
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
        }
    }
}
