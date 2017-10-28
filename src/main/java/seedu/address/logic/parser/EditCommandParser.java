package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LECTURER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_SLOT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditLessonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ListingUnit;
import seedu.address.model.lecturer.Lecturer;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    static final Pattern FIRST_INT_PATTERN = Pattern.compile("^(\\d+)");

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditCommand parse(String args) throws ParseException {

        if (ListingUnit.getCurrentListingUnit().equals(ListingUnit.LESSON)) {
            return parseEditLesson(args);
        } else {
            return parseEditAttribute(args);
        }
    }

    /**
     * Parse the input arguments given the current listing unit is Lesson.
     */
    public EditCommand parseEditLesson(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLASS_TYPE, PREFIX_VENUE, PREFIX_GROUP, PREFIX_TIME_SLOT,
                        PREFIX_MODULE_CODE, PREFIX_LECTURER);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditLessonDescriptor editLessonDescriptor = new EditLessonDescriptor();
        try {
            ParserUtil.parseClassType(argMultimap.getValue(PREFIX_CLASS_TYPE))
                    .ifPresent(editLessonDescriptor::setClassType);
            ParserUtil.parseLocation(argMultimap.getValue(PREFIX_VENUE))
                    .ifPresent(editLessonDescriptor::setLocation);
            ParserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP))
                    .ifPresent(editLessonDescriptor::setGroup);
            ParserUtil.parseTimeSlot(argMultimap.getValue(PREFIX_TIME_SLOT))
                    .ifPresent(editLessonDescriptor::setTimeSlot);
            ParserUtil.parseCode(argMultimap.getValue(PREFIX_MODULE_CODE))
                    .ifPresent(editLessonDescriptor::setCode);

            if (argMultimap.getValue(PREFIX_LECTURER).isPresent()
                    && argMultimap.getValue(PREFIX_LECTURER).get().isEmpty()) {
                throw new IllegalValueException(Lecturer.MESSAGE_LECTURER_CONSTRAINTS);
            }

            parseLecturersForEdit(argMultimap.getAllValues(PREFIX_LECTURER))
                    .ifPresent(editLessonDescriptor::setLecturer);

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editLessonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editLessonDescriptor);
    }

    /**
     * Parse the input arguments of given new attribute value
     */
    public EditCommand parseEditAttribute(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        Index index;
        String attributeValue;
        Matcher matcher = FIRST_INT_PATTERN.matcher(trimmedArgs);

        try {
            if (matcher.find()) {
                index = ParserUtil.parseIndex(matcher.group(0));
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }

            attributeValue = trimmedArgs.substring(matcher.group(0).length()).trim();

        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        return new EditCommand(index, attributeValue);
    }


    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Lecturer>} if {@code lecturers} is non-empty.
     * If {@code lecturer} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Lecturer>} containing zero tags.
     */
    private Optional<Set<Lecturer>> parseLecturersForEdit(Collection<String> lecturers) throws IllegalValueException {
        assert lecturers != null;

        if (lecturers.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> lecturersSet = lecturers.size() == 1
                && lecturers.contains("") ? Collections.emptySet() : lecturers;
        return Optional.of(ParserUtil.parseLecturer(lecturersSet));
    }

}
