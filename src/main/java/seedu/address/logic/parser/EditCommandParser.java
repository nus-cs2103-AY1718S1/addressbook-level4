package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.isParsableAddressTillEnd;
import static seedu.address.logic.parser.ParserUtil.isParsableEmail;
import static seedu.address.logic.parser.ParserUtil.isParsableName;
import static seedu.address.logic.parser.ParserUtil.isParsablePhone;
import static seedu.address.logic.parser.ParserUtil.parseAddressTillEnd;
import static seedu.address.logic.parser.ParserUtil.parseFirstEmail;
import static seedu.address.logic.parser.ParserUtil.parseFirstPhone;
import static seedu.address.logic.parser.ParserUtil.parseRemainingName;
import static seedu.address.logic.parser.ParserUtil.parseRemoveAddressTillEnd;
import static seedu.address.logic.parser.ParserUtil.parseRemoveFirstEmail;
import static seedu.address.logic.parser.ParserUtil.parseRemoveFirstPhone;
import static seedu.address.logic.parser.ParserUtil.parseRemoveTags;
import static seedu.address.model.ModelManager.hasAnyExistingTags;
import static seedu.address.model.ModelManager.isKnownTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
     * 1. {@code Phone} (mandatory)
     * 2. {@code Email} (mandatory)
     * 3. {@code Tags} (optional)
     * 4. {@code Address} (mandatory)
     * 5. {@code Name} (remaining)
     */
    public static String parseArguments(String rawArgs) {
        String remaining = rawArgs;

        // Check for Phone & Email
        String phone;
        String email;
        if (isParsablePhone(remaining) && isParsableEmail(remaining)) {
            phone = parseFirstPhone(remaining);
            remaining = parseRemoveFirstPhone(remaining).trim().replaceAll(PREFIX_PHONE.toString(), "");
            email = parseFirstEmail(remaining);
            remaining = parseRemoveFirstEmail(remaining).trim().replaceAll(PREFIX_EMAIL.toString(), "");
        } else {
            return null;
        }

        // Check for Existing Tags
        StringBuilder tags = new StringBuilder();
        String[] words = remaining.split(" ");
        if (hasAnyExistingTags(words)) {
            List<String> tagsAdded = new ArrayList<>();
            Arrays.stream(words).forEach(word -> {
                if (isKnownTag(word) && !tagsAdded.contains(word)) {
                    tags.append(" " + PREFIX_TAG + word);
                    tagsAdded.add(word);
                }
            });
            remaining = parseRemoveTags(remaining, tagsAdded);
        }

        // Check for Address till end of remainder string
        String address;
        if (isParsableAddressTillEnd(remaining)) {
            address = parseAddressTillEnd(remaining);
            remaining = parseRemoveAddressTillEnd(remaining).trim().replaceAll(PREFIX_ADDRESS.toString(), "");
        } else {
            return null;
        }

        // Check for alphanumeric Name in remainder string
        String name;
        if (isParsableName(remaining)) {
            name = parseRemainingName(remaining);
        } else {
            return null;
        }

        return " ".concat(PREFIX_NAME.toString()).concat(name)
                .concat(" ").concat(PREFIX_PHONE.toString()).concat(phone)
                .concat(" ").concat(PREFIX_EMAIL.toString()).concat(email)
                .concat(" ").concat(PREFIX_ADDRESS.toString()).concat(address)
                .concat(tags.toString());
    }

}
