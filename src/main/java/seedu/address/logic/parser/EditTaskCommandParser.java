package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_BY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_ON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_AT;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.tasks.AddTaskCommand;
import seedu.address.logic.commands.tasks.EditTaskCommand;
import seedu.address.logic.commands.tasks.EditTaskCommand.EditTaskDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.DateTimeValidator;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.EventTime;

//@@author raisa2010
/**
 * Parses input arguments and creates a new EditTaskCommand object
 */
public class EditTaskCommandParser implements Parser<EditTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTaskCommand
     * and returns an EditTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DEADLINE_BY, PREFIX_DEADLINE_ON, PREFIX_DEADLINE_FROM,
                        PREFIX_TIME_AT, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(getIndexForEdit(argMultimap.getPreamble()));
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));
        }

        if (!AddTaskCommandParser.isSinglePrefixPresent(argMultimap)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskCommand.EditTaskDescriptor();
        try {
            parseDescriptionForEdit(argMultimap.getPreamble()).ifPresent(editTaskDescriptor::setDescription);
            parseDeadlineForEdit(argMultimap.getAllValues(PREFIX_DEADLINE_BY, PREFIX_DEADLINE_FROM, PREFIX_DEADLINE_ON))
                    .ifPresent(editTaskDescriptor::setDeadline);
            parseEventTimesForEdit(argMultimap.getAllValues(PREFIX_TIME_AT))
                    .ifPresent(editTaskDescriptor::setEventTimes);
            ParserUtil.parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editTaskDescriptor::setTags);

            if (editTaskDescriptor.getStartTime().isPresent() && editTaskDescriptor.getEndTime().isPresent()
                    && !DateTimeValidator.isStartTimeBeforeEndTime(editTaskDescriptor.getStartTime().get(),
                    editTaskDescriptor.getEndTime().get())) {
                throw new IllegalValueException(DateTimeValidator.MESSAGE_TIME_CONSTRAINTS);
            }

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTaskCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTaskCommand(index, editTaskDescriptor);
    }

    /**
     * Parses {@code List<String> dates} into a {@code Optional<Deadline>} containing the last date in the list,
     * if {@code dates} is non-empty.
     * If {@code dates} contain only one element which is an empty string, it will be parsed into a
     * {@code Optional<Deadline>} containing an empty date.
     */
    public Optional<Deadline> parseDeadlineForEdit(List<String> dates) throws IllegalValueException {
        assert dates != null;

        if (dates.isEmpty()) {
            return Optional.empty();
        }
        return dates.size() == 1 && dates.contains("")
                ? Optional.of(new Deadline(""))
                : ParserUtil.parseDeadline(Optional.of(dates.get(dates.size() - 1)));

    }

    /**
     * Parses {@code List<String> times} into a {@code Optional<EventTime[]>} containing the last two times in
     * the list if the {@code times} is non-empty. All single times are counted as end times.
     *  If {@code times} contain only one element which is an empty string, it will be parsed into a
     * {@code Optional<EventTime[]>} containing empty times.
     */
    public Optional<EventTime[]> parseEventTimesForEdit(List<String> times) throws IllegalValueException {
        assert times != null;

        if (times.isEmpty()) {
            return Optional.empty();
        }

        return times.size() == 1 && times.contains("")
                ? Optional.of(new EventTime[]{new EventTime(""), new EventTime("")})
                : ParserUtil.parseEventTimes(Optional.of(times.get(times.size() - 1)));
    }

    /**
     * Separates the index from the description in the preamble.
     * @param preamble (string before any valid prefix) received from the argument multimap of the tokenized edit
     * task command.
     * @return the index of the task to be edited.
     */
    public String getIndexForEdit(String preamble) {
        String trimmedPreamble = preamble.trim();
        return (trimmedPreamble.indexOf(' ') == -1) ? trimmedPreamble
                : trimmedPreamble.substring(0, trimmedPreamble.indexOf(' '));
    }

    /**
     * Separates the description from the index in the preamble.
     * @param preamble (string before any valid prefix) received from the argument multimap of the tokenized edit
     * task command.
     * @return {@code Optional<Description>} (parsed) of the task to be edited if it is present in the preamble,
     * otherwise an empty Optional is returned.
     */
    public Optional<Description> parseDescriptionForEdit(String preamble)
            throws IllegalValueException {
        int indexLength = getIndexForEdit(preamble).length();
        String description = (indexLength == preamble.length()) ? ""
                : preamble.substring(indexLength, preamble.length());
        return description.isEmpty() ? Optional.empty() : ParserUtil.parseDescription(description);
    }
}
