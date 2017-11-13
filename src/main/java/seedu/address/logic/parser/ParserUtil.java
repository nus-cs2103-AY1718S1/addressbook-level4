package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.alias.Keyword;
import seedu.address.model.alias.Representation;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 * {@code ParserUtil} contains methods that take in {@code Optional} as parameters. However, it goes against Java's
 * convention (see https://stackoverflow.com/a/39005452) as {@code Optional} should only be used a return type.
 * Justification: The methods in concern receive {@code Optional} return values from other methods as parameters and
 * return {@code Optional} values based on whether the parameters were present. Therefore, it is redundant to unwrap the
 * initial {@code Optional} before passing to {@code ParserUtil} as a parameter and then re-wrap it into an
 * {@code Optional} return value inside {@code ParserUtil} methods.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    //@@author aziziazfar
    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name, String command) throws IllegalValueException {
        requireNonNull(name);
        if (command.equals("add")) {
            return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.of(new Name(0));
        } else {
            return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
        }
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone, String command) throws IllegalValueException {
        requireNonNull(phone);
        if (command.equals("add")) {
            return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.of(new Phone(0));
        } else {
            return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.empty();
        }
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address, String command)
            throws IllegalValueException {
        requireNonNull(address);
        if (command.equals("add")) {
            return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.of(new Address(0));
        } else {
            return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
        }
    }

    //@@author wynkheng
    /**
     * Parses a {@code Optional<String> birthday} into an {@code Optional<Birthday>} if {@code birthday} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Birthday> parseBirthday(Optional<String> birthday, String command)
            throws IllegalValueException {
        requireNonNull(birthday);
        if (command.equals("add")) {
            return birthday.isPresent() ? Optional.of(new Birthday(birthday.get())) : Optional.of(new Birthday(0));
        } else {
            return birthday.isPresent() ? Optional.of(new Birthday(birthday.get())) : Optional.empty();
        }
    }
    //@@author

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email, String command) throws IllegalValueException {
        requireNonNull(email);
        if (command.equals("add")) {
            return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.of(new Email(0));
        } else {
            return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.empty();
        }
    }
    //@@author

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code Optional<String> keyword} into an {@code Optional<Keyword>} if {@code keyword} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Keyword> parseKeyword(Optional<String> keyword) throws IllegalValueException {
        requireNonNull(keyword);
        return keyword.isPresent() ? Optional.of(new Keyword(keyword.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> representation} into an {@code Optional<Representation>}
     * if {@code representation} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Representation> parseRepresentation(Optional<String> representation)
            throws IllegalValueException {
        requireNonNull(representation);
        return representation.isPresent() ? Optional.of(new Representation(representation.get())) : Optional.empty();
    }
}
