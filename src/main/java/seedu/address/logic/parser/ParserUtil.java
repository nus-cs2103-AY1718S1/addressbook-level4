package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.StringUtil.replaceBackslashes;
import static seedu.address.model.person.Email.EMAIL_VALIDATION_REGEX;
import static seedu.address.model.person.Phone.PHONE_VALIDATION_REGEX;
import static seedu.address.storage.util.RolodexStorageUtil.FILEPATH_REGEX_NON_STRICT;
import static seedu.address.storage.util.RolodexStorageUtil.ROLODEX_FILE_EXTENSION;
import static seedu.address.storage.util.RolodexStorageUtil.isValidRolodexStorageFilepath;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
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
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.empty();
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
     * Attempts to parse a {@code String} to an {@code Integer}.
     * Looks for numbers given a value and parses the first instance of the number.
     *
     * @return {@code true} if successfully parsed,
     * {@code false} otherwise.
     */
    public static boolean tryParseInt(String value) {
        try {
            parseFirstInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns the first integer found in a {@code String}.
     * @param value to be parsed.
     * @return {@code int} positive, non-zero value of the integer.
     * @throws NumberFormatException if no integer was found.
     */
    public static int parseFirstInt(String value) throws NumberFormatException {
        Pattern numbers = Pattern.compile("-?\\d+");
        Matcher m = numbers.matcher(value);
        if (m.find()) {
            int firstInt = Integer.parseInt(m.group());
            if (firstInt != 0) {
                return Math.abs(firstInt);
            }
        }
        throw new NumberFormatException();
    }

    /**
     * Returns a {@code String} after the first integer has been removed.
     * @param value to be parsed.
     * @return a {@code String} without the first integer.
     */
    public static String parseRemoveFirstInt(String value) {
        String firstInt;
        try {
            firstInt = Integer.toString(parseFirstInt(value));
        } catch (NumberFormatException e) {
            firstInt = "";
        }
        return value.substring(0, value.indexOf(firstInt)).trim()
                .concat(" ")
                .concat(value.substring(value.indexOf(firstInt) + firstInt.length()).trim()).trim();
    }

    /**
     * Attempts to parse a {@code String} to a file path.
     * Looks for a regex given a value and parses the first instance of the file path.
     *
     * @return {@code true} if successfully parsed,
     * {@code false} otherwise.
     */
    public static boolean tryParseFilePath(String value) {
        try {
            parseFirstFilePath(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Returns the file path found in a {@code String}.
     * @param value to be parsed.
     * @return {@code String} value of the file path appended with the file extension.
     * @throws IllegalArgumentException if no file path was found.
     */
    public static String parseFirstFilePath(String value) throws IllegalArgumentException {
        Pattern filepath = Pattern.compile(FILEPATH_REGEX_NON_STRICT);
        Matcher m = filepath.matcher(replaceBackslashes(value).trim());
        if (m.find() && isValidRolodexStorageFilepath(m.group())) {
            return m.group().replaceAll(ROLODEX_FILE_EXTENSION, "").trim() + ROLODEX_FILE_EXTENSION;
        }
        throw new IllegalArgumentException();
    }

    /**
     * Attempts to parse a {@code String} to a phone.
     * Looks for a regex given a value and parses the first instance of the phone.
     *
     * @return {@code true} if successfully parsed,
     * {@code false} otherwise.
     */
    public static boolean tryParsePhone(String value) {
        try {
            parseFirstPhone(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Returns the phone found in a {@code String}.
     * @param value to be parsed.
     * @return {@code String} value of the phone.
     * @throws IllegalArgumentException if no phone was found.
     */
    public static String parseFirstPhone(String value) throws IllegalArgumentException {
        Pattern phone = Pattern.compile(PHONE_VALIDATION_REGEX);
        Matcher m = phone.matcher(value);
        if (m.find()) {
            return m.group();
        }
        throw new IllegalArgumentException();
    }

    /**
     * Returns a {@code String} after the first phone has been removed.
     * @param value to be parsed.
     * @return a {@code String} without the first phone.
     */
    public static String parseRemoveFirstPhone(String value) {
        String firstPhone;
        try {
            firstPhone = parseFirstPhone(value);
        } catch (IllegalArgumentException e) {
            firstPhone = "";
        }
        return value.substring(0, value.indexOf(firstPhone)).trim()
                .concat(" ")
                .concat(value.substring(value.indexOf(firstPhone) + firstPhone.length()).trim()).trim();
    }

    /**
     * Attempts to parse a {@code String} to a email.
     * Looks for a regex given a value and parses the first instance of the email.
     *
     * @return {@code true} if successfully parsed,
     * {@code false} otherwise.
     */
    public static boolean tryParseEmail(String value) {
        try {
            parseFirstEmail(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Returns the email found in a {@code String}.
     * @param value to be parsed.
     * @return {@code String} value of the email.
     * @throws IllegalArgumentException if no email was found.
     */
    public static String parseFirstEmail(String value) throws IllegalArgumentException {
        Pattern email = Pattern.compile(EMAIL_VALIDATION_REGEX);
        Matcher m = email.matcher(value);
        if (m.find()) {
            return m.group();
        }
        throw new IllegalArgumentException();
    }

    /**
     * Returns a {@code String} after the first email has been removed.
     * @param value to be parsed.
     * @return a {@code String} without the first email.
     */
    public static String parseRemoveFirstEmail(String value) {
        String firstEmail;
        try {
            firstEmail = parseFirstEmail(value);
        } catch (IllegalArgumentException e) {
            firstEmail = "";
        }
        return value.substring(0, value.indexOf(firstEmail)).trim()
                .concat(" ")
                .concat(value.substring(value.indexOf(firstEmail) + firstEmail.length()).trim()).trim();
    }
}
