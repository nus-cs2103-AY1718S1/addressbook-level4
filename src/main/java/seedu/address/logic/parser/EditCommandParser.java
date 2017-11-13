package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEBT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HANDPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOME_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTEREST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFICE_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_DEBT;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new {@code EditCommand} object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException, CommandException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_HANDPHONE, PREFIX_HOME_PHONE, PREFIX_OFFICE_PHONE,
                        PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_POSTAL_CODE, PREFIX_DEBT, PREFIX_TOTAL_DEBT,
                        PREFIX_INTEREST, PREFIX_DEADLINE, PREFIX_TAG);

        Index index;

        try {
            if (argMultimap.getPreamble().equals("")) {
                index = null;
            } else {
                index = ParserUtil.parseIndex(argMultimap.getPreamble());
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        try {
            ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).ifPresent(editPersonDescriptor::setName);
            ParserUtil.parseHandphone(argMultimap.getValue(PREFIX_HANDPHONE))
                    .ifPresent(editPersonDescriptor::setHandphone);
            ParserUtil.parseHomePhone(argMultimap.getValue(PREFIX_HOME_PHONE))
                    .ifPresent(editPersonDescriptor::setHomePhone);
            ParserUtil.parseOfficePhoneForEdit(argMultimap.getValue(PREFIX_OFFICE_PHONE))
                    .ifPresent(editPersonDescriptor::setOfficePhone);
            ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).ifPresent(editPersonDescriptor::setEmail);
            ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).ifPresent(editPersonDescriptor::setAddress);
            ParserUtil.parsePostalCode(argMultimap.getValue(PREFIX_POSTAL_CODE))
                    .ifPresent(editPersonDescriptor::setPostalCode);
            ParserUtil.parseDebt(argMultimap.getValue(PREFIX_DEBT)).ifPresent(editPersonDescriptor::setDebt);
            ParserUtil.parseTotalDebt(argMultimap.getValue(PREFIX_TOTAL_DEBT))
                    .ifPresent(editPersonDescriptor::setTotalDebt);
            ParserUtil.parseInterestForEdit(argMultimap.getValue(PREFIX_INTEREST))
                    .ifPresent(editPersonDescriptor::setInterest);
            ParserUtil.parseDeadlineForEdit(argMultimap.getValue(PREFIX_DEADLINE))
                    .ifPresent(editPersonDescriptor::setDeadline);
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            if (index == null) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            } else {
                throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
            }
        }

        if (index == null) {
            return new EditCommand(editPersonDescriptor);
        } else {
            return new EditCommand(index, editPersonDescriptor);
        }
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
