package seedu.address.logic.parser;
//@@author Esilocke
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditPersonCommand;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditTaskCommand object
 */
public class EditTaskCommandParser extends EditCommandParser {
    /** Constructs a new EditTaskCommand that edits a Task object. **/
    public EditTaskCommand constructTaskDescriptor(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK, PREFIX_NAME, PREFIX_DESCRIPTION,
                        PREFIX_DEADLINE, PREFIX_PRIORITY, PREFIX_ADDRESS);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));
        }

        EditTaskCommand.EditTaskDescriptor editTaskDescriptor = new EditTaskCommand.EditTaskDescriptor();
        try {
            ParserUtil.parseTaskName(argMultimap.getValue(PREFIX_NAME)).ifPresent(editTaskDescriptor::setTaskName);
            ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION))
                    .ifPresent(editTaskDescriptor::setDescription);
            ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE)).ifPresent(editTaskDescriptor::setDeadline);
            ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY)).ifPresent(editTaskDescriptor::setPriority);
            ParserUtil.parseTaskAddress(argMultimap.getValue(PREFIX_ADDRESS))
                    .ifPresent(editTaskDescriptor::setTaskAddress);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPersonCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTaskCommand(index, editTaskDescriptor);
    }
}
