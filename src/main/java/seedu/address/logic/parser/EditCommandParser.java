package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_FULL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TASK, PREFIX_TAG_FULL);

        if (argMultimap.getValue(PREFIX_TASK).isPresent() && argMultimap.getValue(PREFIX_TAG_FULL).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        } else if (argMultimap.getValue(PREFIX_TASK).isPresent()) {
            String filteredArgs = args.replace(PREFIX_TASK.getPrefix(), " ");
            return new EditTaskCommandParser().constructTaskDescriptor(filteredArgs);
        } else if (argMultimap.getValue(PREFIX_TAG_FULL).isPresent()) {
            String filteredArgs = args.replace(PREFIX_TAG_FULL.getPrefix(), " ");
            return new EditTagCommandParser().parse(filteredArgs);
        } else {
            return new EditPersonCommandParser().constructPersonDescriptor(args);
        }
    }
}
