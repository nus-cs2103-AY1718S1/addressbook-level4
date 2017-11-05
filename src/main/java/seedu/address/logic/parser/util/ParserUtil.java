package seedu.address.logic.parser.util;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.Parser;
import seedu.address.model.property.Address;
import seedu.address.model.property.DateTime;
import seedu.address.model.property.Email;
import seedu.address.model.property.Name;
import seedu.address.model.property.Phone;
import seedu.address.model.property.Property;
import seedu.address.model.property.exceptions.PropertyNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various {@link Parser} classes.<br>
 *
 * {@code ParserUtil} contains methods that take in {@code Optional} as parameters. However, it goes against Java's
 * convention (see https://stackoverflow.com/a/39005452) as {@code Optional} should only be used a return type.<br>
 *
 * <b>Justification:</b> The methods in concern receive {@code Optional} return values from other methods as parameters
 * and return {@code Optional} values based on whether the parameters were present. Therefore, it is redundant to unwrap
 * the initial {@code Optional} before passing to {@code ParserUtil} as a parameter and then re-wrap it into an
 * {@code Optional} return value inside {@code ParserUtil} methods.
 */
public class ParserUtil {
    /* Messages ready for use. */
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if none of the prefixes is present in the given @code ArgumentMultimap}.
     */
    public static boolean arePrefixesAbsent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).noneMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

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

    //@@author yunpengn
    /**
     * Parses all properties in the given {@code HashMap}.
     *
     * @return a set containing all properties parsed.
     */
    public static Set<Property> parseProperties(HashMap<Prefix, String> values)
            throws IllegalValueException, PropertyNotFoundException {
        requireNonNull(values);
        Set<Property> properties = new HashSet<>();

        for (Map.Entry<Prefix, String> entry: values.entrySet()) {
            properties.add(new Property(entry.getKey().getPrefixValue(), entry.getValue()));
        }

        return properties;
    }
    //@@author

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     *
     * TODO: Use parseProperties method to replace all methods below.
     */
    public static Optional<Name> parseName(Optional<String> name)
            throws IllegalValueException, PropertyNotFoundException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone)
            throws IllegalValueException, PropertyNotFoundException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address)
            throws IllegalValueException, PropertyNotFoundException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email)
            throws IllegalValueException, PropertyNotFoundException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.empty();
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
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<DateTime> parseTime(Optional<String> name)
            throws IllegalValueException, PropertyNotFoundException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(new DateTime(name.get())) : Optional.empty();
    }

    //@@author low5545
    /**
     * Parses and trims {@code filePath} if it is not empty.
     * @throws IllegalValueException if the {@code filePath} is not empty.
     */
    public static String parseFilePath(String filePath) throws IllegalValueException {
        requireNonNull(filePath);
        filePath = filePath.trim();

        if (filePath.isEmpty()) {
            throw new IllegalValueException("File path is required");
        }
        return filePath;
    }
    //@@author
}
