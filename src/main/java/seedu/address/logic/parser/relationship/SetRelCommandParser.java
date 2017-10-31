//@@A0160452N
package seedu.address.logic.parser.relationship;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPTY_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHOTO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.relationship.SetRelCommand.EditPersonDescriptor;
import seedu.address.logic.commands.relationship.SetRelCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.tag.Tag;

/**
 * Parses all input arguments and create a new AddRelCommand object
 */
public class SetRelCommandParser implements Parser<SetRelCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddRelCommand
     * and returns an AddRelCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetRelCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ADD_RELATIONSHIP,
            PREFIX_DELETE_RELATIONSHIP, PREFIX_EMPTY_RELATIONSHIP);
        Index indexOne;
        Index indexTwo;
        String value;
        String[] indexes;

        value = argMultimap.getPreamble();
        try {
            indexes = value.split("\\s+");
            indexOne = ParserUtil.parseIndex(indexes[0]);
            indexTwo = ParserUtil.parseIndex(indexes[1]);
            //relation = indexes[2];
            //relationList = ParserUtil.parseRel(argMultimap.getAllValues(PREFIX_RELATIONSHIP));
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetRelCommand.MESSAGE_USAGE));
        }
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        try {
            ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).ifPresent(editPersonDescriptor::setName);
            ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).ifPresent(editPersonDescriptor::setPhone);
            ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).ifPresent(editPersonDescriptor::setEmail);
            ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).ifPresent(editPersonDescriptor::setAddress);
            ParserUtil.parseCompany(argMultimap.getValue(PREFIX_COMPANY)).ifPresent(editPersonDescriptor::setCompany);
            ParserUtil.parsePosition(argMultimap.getValue(PREFIX_POSITION))
                .ifPresent(editPersonDescriptor::setPosition);
            ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS)).ifPresent(editPersonDescriptor::setStatus);
            ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY))
                .ifPresent(editPersonDescriptor::setPriority);
            ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE)).ifPresent(editPersonDescriptor::setNote);
            ParserUtil.parsePhoto(argMultimap.getValue(PREFIX_PHOTO)).ifPresent(editPersonDescriptor::setPhoto);
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
            parseRelForEdit(argMultimap.getAllValues(PREFIX_ADD_RELATIONSHIP)).ifPresent
                (editPersonDescriptor::setToAdd);
            parseRelForEdit(argMultimap.getAllValues(PREFIX_DELETE_RELATIONSHIP)).ifPresent
                (editPersonDescriptor::setToRemove);
            editPersonDescriptor.setClearRels(argMultimap.containsPrefix(PREFIX_EMPTY_RELATIONSHIP));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(SetRelCommand.MESSAGE_NOT_EDITED);
        }

        return new SetRelCommand(indexOne, indexTwo, editPersonDescriptor);
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
     * @@A0160452N
     * Parses {@code Collection<String> relationships} into a {@code Set<Relationship>}
     * if {@code relationships} is non-empty.
     * If {@code relationship} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Relationship>} containing zero relationships.
     */
    private Optional<Set<Relationship>> parseRelForEdit(Collection<String> rel) throws IllegalValueException {
        assert rel != null;

        if (rel.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> relSet = rel.size() == 1 && rel.contains("") ? Collections.emptySet() : rel;
        return Optional.of(ParserUtil.parseRel(relSet));
    }
}

