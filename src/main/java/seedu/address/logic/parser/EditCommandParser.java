package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ModelParserUtil.buildParsedArguments;
import static seedu.address.logic.parser.ModelParserUtil.parseExistingRemarkPrefixes;
import static seedu.address.logic.parser.ModelParserUtil.parseExistingTagPrefixes;
import static seedu.address.logic.parser.ModelParserUtil.parseMandatoryAddress;
import static seedu.address.logic.parser.ModelParserUtil.parseMandatoryEmail;
import static seedu.address.logic.parser.ModelParserUtil.parseMandatoryIndex;
import static seedu.address.logic.parser.ModelParserUtil.parseMandatoryName;
import static seedu.address.logic.parser.ModelParserUtil.parseMandatoryPhone;
import static seedu.address.logic.parser.ModelParserUtil.parsePossibleTagWords;
import static seedu.address.model.ModelManager.getLastRolodexSize;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseArgsException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseArgsException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseArgsException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_REMARK, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseArgsException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        try {
            ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).ifPresent(editPersonDescriptor::setName);
            ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).ifPresent(editPersonDescriptor::setPhone);
            ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).ifPresent(editPersonDescriptor::setEmail);
            ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).ifPresent(editPersonDescriptor::setAddress);
            ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).ifPresent(editPersonDescriptor::setRemark);
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
        } catch (IllegalValueException ive) {
            throw new ParseArgsException(ive.getMessage(), ive);
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseArgsException(EditCommand.MESSAGE_NOT_EDITED);
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
     * Returns a formatted argument string given unformatted {@code rawArgs}
     * or a {@code null} {@code String} if not formattable.
     * Uses a simple flow by checking the {@code Model}'s data in the following order:
     * 1. Index of person (mandatory)
     * 2. {@code Phone} (optional)
     * 3. {@code Email} (optional)
     * 4. Existing {@code Tags} (optional)
     * 5. {@code Tags} prefixes (optional)
     * 6. {@code Remark} prefixes (optional)
     * 7. {@code Address} (optional)
     * 8. {@code Name} (remaining)
     */
    public static String parseArguments(String commandWord, String rawArgs) {
        String remaining = rawArgs;

        // Check for Mandatory Index
        String index = "";
        Optional<Pair<String, String>> mandatoryIndexArgs = parseMandatoryIndex(remaining, getLastRolodexSize());
        Optional<Pair<String, String>> mandatoryIndexCommand = parseMandatoryIndex(commandWord, getLastRolodexSize());
        if (mandatoryIndexArgs.isPresent()) {
            index = mandatoryIndexArgs.get().getKey();
            remaining = mandatoryIndexArgs.get().getValue();
        } else if (mandatoryIndexCommand.isPresent()) {
            index = mandatoryIndexCommand.get().getKey();
        } else {
            return null;
        }

        // Check for Optional Phone
        String phone = "";
        Optional<Pair<String, String>> mandatoryPhone = parseMandatoryPhone(remaining);
        if (mandatoryPhone.isPresent()) {
            phone = mandatoryPhone.get().getKey();
            remaining = mandatoryPhone.get().getValue();
        } else {
            remaining = remaining.replaceAll(PREFIX_PHONE.toString(), "");
        }

        // Check for Optional Email
        String email = "";
        Optional<Pair<String, String>> mandatoryEmail = parseMandatoryEmail(remaining);
        if (mandatoryEmail.isPresent()) {
            email = mandatoryEmail.get().getKey();
            remaining = mandatoryEmail.get().getValue();
        } else {
            remaining = remaining.replaceAll(PREFIX_EMAIL.toString(), "");
        }

        // Check for possible existing Tags without prefixes
        StringBuilder tags = new StringBuilder();
        Optional<Pair<String, String>> optionalPossibleTags = parsePossibleTagWords(remaining);
        if (optionalPossibleTags.isPresent()) {
            tags.append(optionalPossibleTags.get().getKey());
            remaining = optionalPossibleTags.get().getValue();
        }

        // Check for existing tag prefix using tokenizer
        Optional<Pair<String, String>> optionalExistingTags = parseExistingTagPrefixes(remaining);
        if (optionalExistingTags.isPresent()) {
            tags.append(optionalExistingTags.get().getKey());
            remaining = optionalExistingTags.get().getValue();
        } else {
            remaining = remaining.replaceAll(PREFIX_TAG.toString(), "");
        }

        // Check for existing remark prefix using tokenizer
        String remark = "";
        Optional<Pair<String, String>> optionalExistingRemarks = parseExistingRemarkPrefixes(remaining);
        if (optionalExistingRemarks.isPresent()) {
            remark = optionalExistingRemarks.get().getKey();
            remaining = optionalExistingRemarks.get().getValue();
        } else {
            remaining = remaining.replaceAll(PREFIX_REMARK.toString(), "");
        }

        // Check for Optional Address till end of remainder string
        String address = "";
        Optional<Pair<String, String>> mandatoryAddress = parseMandatoryAddress(remaining);
        if (mandatoryAddress.isPresent()) {
            address = mandatoryAddress.get().getKey();
            remaining = mandatoryAddress.get().getValue();
        } else {
            remaining = remaining.replaceAll(PREFIX_ADDRESS.toString(), "");
        }

        // Check for alphanumeric Name in remainder string
        String name = "";
        Optional<String> mandatoryName = parseMandatoryName(remaining);
        if (mandatoryName.isPresent()) {
            name = mandatoryName.get();
        }

        return buildParsedArguments(index, name, phone, email, remark, address, tags.toString());
    }

}
