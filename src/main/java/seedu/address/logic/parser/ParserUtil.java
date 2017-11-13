package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
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
import seedu.address.model.person.Gender;
import seedu.address.model.person.MatricNo;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.timetable.Timetable;
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
    public static final String REGEX_COMMA = ",";
    public static final String REGEX_MULTIPLE_WHITESPACE = " +";

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

    //@@author April0616
    /**
     * Parses {@code oneBasedIndexes} separated with commas into a {@Code ArrayList<Index>} and returns it.
     * Leading and trailing whitespaces are trimmed.
     * @param splitString is the sign used to separate indexes, can be either comma or whitespace(s)
     * @throws IllegalValueException if one of the specified indexes is invalid (not non-zero unsigned integer).
     *
     */
    public static ArrayList<Index> parseIndexes(String oneBasedIndexes, String splitString)
            throws IllegalValueException {
        String trimmedIndexes = oneBasedIndexes.trim();
        String[] indexes;
        if (splitString.equals(REGEX_COMMA)) {
            indexes = trimmedIndexes.split(splitString);
        } else {
            indexes = trimmedIndexes.split(REGEX_MULTIPLE_WHITESPACE);
        }

        ArrayList<Index> deletePersons = new ArrayList<>();

        for (String index : indexes) {
            index = index.trim();
            if (!StringUtil.isNonZeroUnsignedInteger(index)) {
                throw new IllegalValueException(MESSAGE_INVALID_INDEX);
            }

            Index thisIndex = Index.fromOneBased(Integer.valueOf(index));
            if (!deletePersons.contains(thisIndex)) {
                deletePersons.add(thisIndex);
            }
        }

        return deletePersons;
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

    //@@author April0616
    /**
     * Parses a {@code Optional<String> gender} into an {@code Optional<Gender>} if {@code gender} is present.
     * @See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Gender> parseGender(Optional<String> gender) throws IllegalValueException {
        requireNonNull(gender);
        return gender.isPresent() ? Optional.of(new Gender(gender.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> matricNo} into an {@code Optional<MatricNo>} if {@code matricNo} is present.
     * @See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<MatricNo> parseMatricNo(Optional<String> matricNo) throws IllegalValueException {
        requireNonNull(matricNo);
        return matricNo.isPresent() ? Optional.of(new MatricNo(matricNo.get())) : Optional.empty();
    }
    //@@author

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

    //@@author CindyTsai1
    /**
     * Parses {@code Optional<int> birthday} into a {@code HashMap<Birthday>} if {@code birthday} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Birthday> parseBirthday(Optional<String> birthday) throws IllegalValueException {
        requireNonNull(birthday);
        return birthday.isPresent() ? Optional.of(new Birthday(birthday.get())) : Optional.empty();
    }

    //@@author nbriannl
    /**
     * Parses a single {@code Optional<String> tag} into an {@code Optional<Tag>} if {@code tag} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Tag> parseSingleTag(Optional<String> tag) throws IllegalValueException {
        requireNonNull(tag);
        return tag.isPresent() ? Optional.of(new Tag(tag.get())) : Optional.empty();
    }

    //@@author
    /**
     * Parses a {@code Optional<String> url} into an {@code Optional<Timetable>} if {@code url} is present.
     */
    public static Optional<Timetable> parseTimetable(Optional<String> url) throws IllegalValueException {
        requireNonNull(url);
        return url.isPresent() ? Optional.of(new Timetable(url.get())) : Optional.empty();
    }
}
