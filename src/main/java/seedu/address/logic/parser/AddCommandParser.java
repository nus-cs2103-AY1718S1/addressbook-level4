package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_FULL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK, PREFIX_TAG_FULL);

        if (arePrefixesPresent(argMultimap, PREFIX_TASK, PREFIX_TAG_FULL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        } else if (arePrefixesPresent(argMultimap, PREFIX_TASK)) {
            ReadOnlyTask taskToAdd = AddTaskCommandParser.constructTask(args);
            return new AddTaskCommand(taskToAdd);
        } else if (arePrefixesPresent(argMultimap, PREFIX_TAG_FULL)) {
            String filteredArgs = args.replace(PREFIX_TAG_FULL.getPrefix(), " ");
            return new AddTagCommandParser().parse(filteredArgs);
        } else {
            ReadOnlyPerson personToAdd = AddPersonCommandParser.constructPerson(args);
            return new AddPersonCommand(personToAdd);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
