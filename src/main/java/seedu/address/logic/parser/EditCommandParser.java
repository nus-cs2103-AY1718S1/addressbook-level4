package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRIC_NO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMETABLE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    //@@author nbriannl
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argsMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GENDER, PREFIX_MATRIC_NO,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TIMETABLE, PREFIX_TAG,
                        PREFIX_OLD_TAG, PREFIX_NEW_TAG, PREFIX_BIRTHDAY);
        String preamble = argsMultimap.getPreamble();

        if (preamble.matches("")) {
            return parseForTags(argsMultimap);
        } else if (preamble.matches("\\d+")) {
            return parseForPersonDetails(argsMultimap);
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    /**
     * Parses the old {@code Tag} and new {@code Tag} contained within {@code argMultimap} to return
     * a EditCommand object that executes a edit for Tag
     * @throws ParseException if the values mapped as a old or new tag does not conform as a valid tag
     * @see #parse(String)
     */
    private EditCommand parseForTags (ArgumentMultimap argsMultimap) throws ParseException {
        if (!arePrefixesPresent(argsMultimap, PREFIX_NEW_TAG, PREFIX_OLD_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        try {
            Tag oldTag = ParserUtil.parseSingleTag(argsMultimap.getValue(PREFIX_OLD_TAG)).get();
            Tag newTag = ParserUtil.parseSingleTag(argsMultimap.getValue(PREFIX_NEW_TAG)).get();
            return new EditCommand(oldTag, newTag);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    //@@author
    /**
     * Parses the various attributes of a {@code Person} contained within {@code argMultimap} to return
     * a EditCommand object that executes a edit for a Person
     * @throws ParseException if any attribute is not a valid value.
     * @see #parse(String)
     */
    private EditCommand parseForPersonDetails (ArgumentMultimap argsMultimap) throws ParseException {
        Index index;

        try {
            index = ParserUtil.parseIndex(argsMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        try {
            ParserUtil.parseName(argsMultimap.getValue(PREFIX_NAME)).ifPresent(editPersonDescriptor::setName);
            ParserUtil.parseGender(argsMultimap.getValue(PREFIX_GENDER)).ifPresent(editPersonDescriptor::setGender);
            ParserUtil.parseMatricNo(argsMultimap.getValue(PREFIX_MATRIC_NO))
                    .ifPresent(editPersonDescriptor::setMatricNo);
            ParserUtil.parsePhone(argsMultimap.getValue(PREFIX_PHONE)).ifPresent(editPersonDescriptor::setPhone);
            ParserUtil.parseEmail(argsMultimap.getValue(PREFIX_EMAIL)).ifPresent(editPersonDescriptor::setEmail);
            ParserUtil.parseAddress(argsMultimap.getValue(PREFIX_ADDRESS))
                    .ifPresent(editPersonDescriptor::setAddress);
            ParserUtil.parseTimetable(argsMultimap.getValue(PREFIX_TIMETABLE))
                    .ifPresent(editPersonDescriptor::setTimetable);
            parseTagsForEdit(argsMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
            ParserUtil.parseBirthday(argsMultimap.getValue(PREFIX_BIRTHDAY))
                    .ifPresent(editPersonDescriptor::setBirthday);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
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

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
