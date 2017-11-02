package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MRT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.*;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_MRT, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        FindCommand.FindPersonDescriptor personDescriptor = new FindCommand.FindPersonDescriptor();
        try {
            ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).ifPresent(personDescriptor::setName);
            ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).ifPresent(personDescriptor::setPhone);
            ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).ifPresent(personDescriptor::setEmail);
            ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).ifPresent(personDescriptor::setAddress);
            ParserUtil.parseMrt(argMultimap.getValue(PREFIX_MRT)).ifPresent(personDescriptor::setMrt);
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(personDescriptor::setTags);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new FindCommand(personDescriptor);
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

}
