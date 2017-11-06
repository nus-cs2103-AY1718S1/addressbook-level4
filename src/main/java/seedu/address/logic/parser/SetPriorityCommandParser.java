package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SetPriorityCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the given {@code String} of arguments in the context of the SetPriorityCommand
 * and returns an SetPriorityCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class SetPriorityCommandParser implements Parser<SetPriorityCommand> {
    @Override
    public SetPriorityCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PRIORITY);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPriorityCommand.MESSAGE_USAGE));
        }

        String priorityString = argMultimap.getValue(PREFIX_PRIORITY).orElse(null);
        if (priorityString == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPriorityCommand.MESSAGE_USAGE));
        }

        Integer priority = Integer.parseInt(priorityString);

        return new SetPriorityCommand(index, priority);
    }
}
