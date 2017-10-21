package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class EditTaskCommandParser {
    
    /**
     * Parses the given {@code String} of arguments in the context of the EditTaskCommand
     * and returns an EditTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_START_DATE, PREFIX_DEADLINE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getIndex());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));
        }

        EditTaskCommand.EditTaskDescriptor editTaskDescriptor = new EditTaskCommand.EditTaskDescriptor();
        try {
            String description = argMultimap.getDescription();
            if (!description.isEmpty()) {
                editTaskDescriptor.setDescription(ParserUtil.parseDescription(description));
            }
            Optional<String> startDate = argMultimap.getValue(PREFIX_START_DATE);
            if (startDate.isPresent()) {
                ParserUtil.parseStartDate(startDate);
            }
            Optional<String> deadline = argMultimap.getValue(PREFIX_DEADLINE);
            if (startDate.isPresent()) {
                ParserUtil.parseStartDate(deadline);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTaskCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTaskCommand(index, editTaskDescriptor);
    }
}
