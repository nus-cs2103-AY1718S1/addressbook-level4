package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.FileUtil.getExtension;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Picture;
import seedu.address.model.person.Website;
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

    static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    static final String MESSAGE_INVALID_FILE = "File must be an xml document.";

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

    //@@author chrisboo
    /**
     * Parse {@code path} into a {@code File} and returns it. Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the file does not exist
     */
    public static File parseFile(String path) throws IllegalValueException {
        String trimmedAddress = path.trim();
        File file = new File(trimmedAddress);
        if (!getExtension(file).equals(".xml")) {
            throw new IllegalValueException(MESSAGE_INVALID_FILE);
        }
        return file;
    }
    //@@author

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
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.of(new Phone(null));
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.of(new Address(null));
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.of(new Email(null));
    }

    /**
     * Parses a {@code Optional<String> website} into an {@code Optional<Website>} if {@code website} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Website> parseWebsite(Optional<String> website) throws IllegalValueException {
        return website.isPresent() ? Optional.of(new Website(website.get())) : Optional.of(new Website(null));
    }

    /**
     * Parses a {@code Optional<String> picture} into an {@code Optional<Picture>} if {@code picture} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Picture> parsePicture(Optional<String> picture) throws IllegalValueException {
        return picture.isPresent() ? Optional.of(new Picture(picture.get())) : Optional.of(new Picture(null));
    }

    /**
     * Parses a {@code Optional<String> birthday} into an {@code Optional<Birthday>} if {@code birthday} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Birthday> parseBirthday(Optional<String> birthday) throws IllegalValueException {
        return birthday.isPresent() ? Optional.of(new Birthday(birthday.get())) : Optional.of(new Birthday(null));
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
}
