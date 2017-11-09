package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_BY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_ON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.SUFFIX_NO_RECUR_INTERVAL;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.tasks.EditTaskCommand;
import seedu.address.logic.commands.tasks.EditTaskCommand.EditTaskDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.TaskDates;

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
                ArgumentTokenizer.tokenize(args, PREFIX_STARTDATE, PREFIX_DEADLINE_BY, PREFIX_DEADLINE_ON, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(getIndexForEdit(argMultimap.getPreamble()));
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskCommand.EditTaskDescriptor();
        try {
            parseDescriptionForEdit(argMultimap.getPreamble()).ifPresent(editTaskDescriptor::setDescription);
            parseStartDateForEdit(argMultimap.getAllValues(PREFIX_STARTDATE))
                    .ifPresent(editTaskDescriptor::setStartDate);
            parseDeadlineForEdit(argMultimap.getAllValues(PREFIX_DEADLINE_BY, PREFIX_DEADLINE_ON))
                    .ifPresent(editTaskDescriptor::setDeadline);
            ParserUtil.parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editTaskDescriptor::setTags);

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!TaskDates.isStartDateBeforeDeadline(editTaskDescriptor.getStartDate(), editTaskDescriptor.getDeadline())) {
            throw new ParseException(TaskDates.MESSAGE_DATE_CONSTRAINTS);
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTaskCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTaskCommand(index, editTaskDescriptor);
    }

    /**
     * Parses {@code List<String> dates} into a {@code Optional<StartDate>} containing the last date in the list,
     * if {@code dates} is non-empty.
     * If {@code dates} contain only one element which is an empty string, it will be parsed into a
     * {@code Optional<StartDate>} containing an empty date.
     */
    public Optional<StartDate> parseStartDateForEdit(List<String> dates) throws IllegalValueException {
        assert dates != null;

        if (dates.isEmpty()) {
            return Optional.empty();
        }

        return dates.size() == 1 && dates.contains("")
                ? Optional.of(new StartDate("", SUFFIX_NO_RECUR_INTERVAL))
                : ParserUtil.parseStartDate(Optional.of(dates.get(dates.size() - 1)));
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
                ? Optional.of(new Deadline("", SUFFIX_NO_RECUR_INTERVAL))
                : ParserUtil.parseDeadline(Optional.of(dates.get(dates.size() - 1)));

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
