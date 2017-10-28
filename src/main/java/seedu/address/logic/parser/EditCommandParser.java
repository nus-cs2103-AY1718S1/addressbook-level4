package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.mod.Mod;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_BIRTHDAY, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_MOD);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        try {
            ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).ifPresent(editPersonDescriptor::setName);
            parsePhonesForEdit(argMultimap.getAllValues(PREFIX_PHONE)).ifPresent(editPersonDescriptor::setPhones);
            ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY))
                    .ifPresent(editPersonDescriptor::setBirthday);
            parseEmailsForEdit(argMultimap.getAllValues(PREFIX_EMAIL)).ifPresent(editPersonDescriptor::setEmails);
            ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).ifPresent(editPersonDescriptor::setAddress);
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_MOD)).ifPresent(editPersonDescriptor::setMods);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> phones} into a {@code Set<Phone>} if {@code phones} is non-empty.
     * If {@code phones} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Phone>} containing zero phones.
     */
    private Optional<Set<Phone>> parsePhonesForEdit(Collection<String> phones) throws IllegalValueException {
        assert phones != null;

        if (phones.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> phoneSet = phones.size() == 1 && phones.contains("") ? Collections.emptySet() : phones;
        return Optional.of(ParserUtil.parsePhones(phoneSet));
    }

    /**
     * Parses {@code Collection<String> emails} into a {@code Set<Email>} if {@code emails} is non-empty.
     * If {@code emails} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Email>} containing zero emails.
     */
    private Optional<Set<Email>> parseEmailsForEdit(Collection<String> emails) throws IllegalValueException {
        assert emails != null;

        if (emails.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> emailSet = emails.size() == 1 && emails.contains("") ? Collections.emptySet() : emails;
        return Optional.of(ParserUtil.parseEmails(emailSet));
    }

    /**
     * Parses {@code Collection<String> mods} into a {@code Set<Mod>} if {@code mods} is non-empty.
     * If {@code mods} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Mod>} containing zero mods.
     */
    private Optional<Set<Mod>> parseTagsForEdit(Collection<String> mods) throws IllegalValueException {
        assert mods != null;

        if (mods.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> modSet = mods.size() == 1 && mods.contains("") ? Collections.emptySet() : mods;
        return Optional.of(ParserUtil.parseMods(modSet));
    }

}
