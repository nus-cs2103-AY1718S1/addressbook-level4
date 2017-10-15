package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Password;
import seedu.address.logic.Username;
import seedu.address.model.person.Address;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Debt;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PostalCode;
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
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Debt>} if {@code debt} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Debt> parseDebt(Optional<String> debt) throws IllegalValueException {
        requireNonNull(debt);
        return debt.isPresent() ? Optional.of(new Debt(debt.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
    }

    //@@author khooroko
    /**
     * Parses a {@code Optional<String> postalCode} into an {@code Optional<PostalCode>} if {@code postalCode}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<PostalCode> parsePostalCode(Optional<String> postalCode) throws IllegalValueException {
        requireNonNull(postalCode);
        return postalCode.isPresent() ? Optional.of(new PostalCode(postalCode.get())) : Optional.empty();
    }

    //@@author
    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.empty();
    }

    //@@author lawwman
    /**
     * Parses a {@code Optional<String> deadLine} into an {@code Optional<Deadline>} if {@code Deadline}
     * is present.
     * Meant for parsing for Add command.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Deadline> parseDeadLine(Optional<String> deadLine) throws IllegalValueException {
        requireNonNull(deadLine);
        return deadLine.isPresent() ? Optional.of(new Deadline(deadLine.get()))
                : Optional.of(new Deadline(Deadline.NO_DEAD_LINE_SET));
    }
    /**
     * Parses a {@code Optional<String> deadLine} into an {@code Optional<Deadline>} if {@code Deadline}
     * is present.
     * Meant for parsing for Edit command.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Deadline> parseDeadLineForEdit(Optional<String> deadLine)
            throws IllegalValueException {
        requireNonNull(deadLine);
        return deadLine.isPresent() ? Optional.of(new Deadline(deadLine.get())) : Optional.empty();
    }

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

    //@@author jelneo
    /**
     * Parses {@code username} into a {@code Username} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * @throws IllegalValueException if the username does not meet length requirement and/or contains illegal characters
     */
    public static Username parseUsername(String username) throws IllegalValueException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        return new Username(trimmedUsername);
    }

    /**
     * Parses {@code password} into a {@code Password} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * @throws IllegalValueException if the password does not meet length requirement and/or contains illegal characters

     */
    public static Password parsePassword(String password) throws IllegalValueException {
        requireNonNull(password);
        String trimmedPassword = password.trim();
        return new Password(trimmedPassword);
    }
}
