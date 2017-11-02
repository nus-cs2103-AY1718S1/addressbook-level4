package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_VALUE_ARGUMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.predicates.AddressContainsKeywordsPredicate;
import seedu.address.model.person.predicates.AnyContainsKeywordsPredicate;
import seedu.address.model.person.predicates.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicates.FavoritePersonsPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.predicates.TagContainsKeywordsPredicate;
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
        String trimmedArgs = args.trim();
        String[] keywords;
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_REMARK, PREFIX_TAG);
        //@@author A0143832J
        try {
            Optional<Name> name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME));
            Optional<Phone> phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE));
            Optional<Address> address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS));
            Optional<Email> email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL));
            Optional<Set<Tag>> tags = parseTagsForFind(argMultimap.getAllValues(PREFIX_TAG));

            if (name.isPresent()) {
                keywords = name.get().toString().trim().split("\\s+");
                return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
            } else if (phone.isPresent()) {
                keywords = phone.get().toString().trim().split("\\s+");
                return new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(keywords)));
            } else if (address.isPresent()) {
                keywords = address.get().toString().trim().split("\\s+");
                return new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList(keywords)));
            } else if (email.isPresent()) {
                keywords = email.get().toString().trim().split("\\s+");
                return new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList(keywords)));
            } else if (tags.isPresent()) {
                return new FindCommand(new TagContainsKeywordsPredicate(tags.get()));
            } else if ("favorite".equals(trimmedArgs) || "unfavorite".equals(trimmedArgs)) {
                return new FindCommand(new FavoritePersonsPredicate(trimmedArgs));
            } else {
                keywords = trimmedArgs.split("\\s+");
                return new FindCommand(new AnyContainsKeywordsPredicate(Arrays.asList(keywords)));
            }
            //@@author
        } catch (IllegalValueException ive) {
            throw new ParseException(MESSAGE_INVALID_VALUE_ARGUMENT, ive.getMessage(), ive);
        }

    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForFind(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
