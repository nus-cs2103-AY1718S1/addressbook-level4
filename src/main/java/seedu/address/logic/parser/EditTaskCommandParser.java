package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;

/**
 * Parses input arguments and creates a new EditTaskCommand object
 */
public class EditTaskCommandParser implements Parser<EditTaskCommand> {

    //@@author ShaocongDong
    /**
     * Parses the given {@code String} of arguments in the context of the EditTaskCommand
     * and returns an EditTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_START_DATE_TIME,
                        PREFIX_END_DATE_TIME, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            Optional<String> parserName = ParserUtil.parseString(argMultimap.getValue(PREFIX_NAME));
            if (parserName.isPresent()) {
                editTaskDescriptor.setName(new Name(parserName.get()));
            }
            Optional<String> parserDescription = ParserUtil.parseString(argMultimap.getValue(PREFIX_DESCRIPTION));
            if (parserDescription.isPresent()) {
                editTaskDescriptor.setDescription(new Description(parserDescription.get()));
            }

            Optional<String> parserStart = ParserUtil.parseString(argMultimap.getValue(PREFIX_START_DATE_TIME));
            DateTime startDateTime = null;
            if (parserStart.isPresent()) {
                //editTaskDescriptor.setStart(new DateTime(parserStart.get()));
                startDateTime = new DateTime(parserStart.get());
                editTaskDescriptor.setStart(new DateTime(parserStart.get()));
            }

            Optional<String> parserEnd = ParserUtil.parseString(argMultimap.getValue(PREFIX_END_DATE_TIME));
            DateTime endDateTime = null;
            if (parserEnd.isPresent()) {
                //editTaskDescriptor.setEnd(new DateTime(parserEnd.get()));
                endDateTime = new DateTime(parserEnd.get());
                editTaskDescriptor.setEnd(new DateTime(parserEnd.get()));
            }

            // additional checking for dateTime validity
            if (startDateTime != null && endDateTime != null) {
                if (startDateTime.compareTo(endDateTime) == -1) {
                    throw new ParseException(EditTaskCommand.MESSAGE_DATE_TIME_TASK);
                }
            }

            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editTaskDescriptor::setTags);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTaskCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTaskCommand(index, editTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}

