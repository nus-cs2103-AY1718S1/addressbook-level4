package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.SORT_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.SORT_EMAIL;
import static seedu.address.logic.parser.CliSyntax.SORT_NAME;
import static seedu.address.logic.parser.CliSyntax.SORT_TAG;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthdate;
import seedu.address.model.person.EmailAddress;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
import seedu.address.model.person.UserId;
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
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>}
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>}
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>}
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>}
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Photo> parsePhoto(Optional<String> photo) throws IllegalValueException {
        requireNonNull(photo);
        return photo.isPresent() ? Optional.of(new Photo(photo.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>}
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EmailAddress> parseEmail(Optional<String> emailAddress) throws IllegalValueException {
        requireNonNull(emailAddress);
        return emailAddress.isPresent() ? Optional.of(new EmailAddress(emailAddress.get())) : Optional.empty();
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

    /**
     * Parses a {@code Optional<String> birthdate} into an {@code Optional<Birthdate>}
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */

    public static Optional<Birthdate> parseBirthdate(Optional<String> birthdate) throws IllegalValueException {
        requireNonNull(birthdate);
        return birthdate.isPresent() ? Optional.of(new Birthdate(birthdate.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> id} into an {@code Optional<UserId>}
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */

    public static Optional<UserId> parseUserId(Optional<String> id) throws IllegalValueException {
        requireNonNull(id);
        return id.isPresent() ? Optional.of(new UserId(id.get())) : Optional.empty();
    }

    /**
     *  Parse {@code Optional<String> keywords} into a {@code String}
     */
    public static Optional<String> parseKeywords(Optional<String> keywords) throws IllegalValueException {
        requireNonNull(keywords);
        return keywords.isPresent() ? Optional.of(keywords.get()) : Optional.empty();
    }

    //@@author awarenessxz
    /**
     * Parses {@code Optional<String> sort} into an {@code int} and returns it.
     * @throws IllegalValueException if the specified sort is invalid
     */
    public static int parseSortOrder(Optional<String> sort) throws IllegalValueException {
        requireNonNull(sort);
        int sortOrder = 0;
        if (sort.isPresent()) {
            switch(sort.get().trim()) {
            case SORT_NAME:
                sortOrder = 0;
                break;
            case SORT_TAG:
                sortOrder = 1;
                break;
            case SORT_EMAIL:
                sortOrder = 2;
                break;
            case SORT_ADDRESS:
                sortOrder = 3;
                break;
            default:
                sortOrder = -1;
                break;
            }
        } else {
            sortOrder = -1;
        }
        return sortOrder;
    }

    /**
     * Parses a {@code Optional<String> message} into an {@code String}
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static String parseEmailMessage(Optional<String> message) throws IllegalValueException {
        requireNonNull(message);
        return message.isPresent() ? message.get() : "";
    }

    /**
     * Parses a {@code Optional<String> subject} into an {@code String}
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static String parseEmailSubject(Optional<String> subject) throws IllegalValueException {
        requireNonNull(subject);
        return subject.isPresent() ? subject.get() : "";
    }

    /**
     * Parses a {@code Optional<String> loginDetails} into an {@code String}
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static String parseLoginDetails(Optional<String> loginDetails) throws IllegalValueException {
        requireNonNull(loginDetails);
        return loginDetails.isPresent() ? loginDetails.get() : "";
    }

    /**
     * Parses a {@code Optional<String> task} into an {@code String}
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static String parseEmailTask(Optional<String> task) throws IllegalValueException {
        requireNonNull(task);
        return task.isPresent() ? task.get() : "";
    }
    //@@author
}
