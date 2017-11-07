package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author JYL123
/**
 * Parses input arguments and creates a new EditTaskCommand object
 */
public class EditTaskCommandParser implements Parser<EditTaskCommand> {

    /**
    * Parses the given {@code String} of arguments in the context of the EditCommand
    * and returns an EditCommand object for execution.
    * @throws ParseException if the user input does not conform the expected format
    */
    public EditTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize
            (args, PREFIX_DESCIPTION, PREFIX_PRIORITY, PREFIX_DUEDATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            //ParserUtil.parseDescription(argMultimap.getPreamble());
            ParserUtil.parseDescriptionOptional(argMultimap.getValue(PREFIX_DESCIPTION));
            ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY))
                    .ifPresent(editTaskDescriptor::setPriority);
            ParserUtil.parseDueDate(argMultimap.getValue(PREFIX_DUEDATE))
                    .ifPresent(editTaskDescriptor::setDueDate);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTaskCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTaskCommand(index, editTaskDescriptor);
    }
}
