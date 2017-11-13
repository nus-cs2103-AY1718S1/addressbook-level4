package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Facebook;
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
     * Parses a {@code String} of one-based indexes that may contain single or range indexes into a {@code Set}.
     * Indexes must be separated by "," or whitespaces, and index range must be linked by "-".
     * @throws IllegalValueException if indexes are invalid, or the input {@code String} is not in valid format.
     */
    public static List<Index> parseRangeIndexList(String oneBasedIndexList) throws IllegalValueException {
        oneBasedIndexList = oneBasedIndexList.replaceAll("\\s*-\\s*", "-");
        List<String> filteredIndexStrings = Arrays.stream(oneBasedIndexList.split("(,+)|(\\s+)"))
            .map(String::trim)
            .filter(((Predicate<String>) String::isEmpty).negate())
            .collect(Collectors.toList());

        Set<Integer> oneBasedIndexSet = new HashSet<>();
        for (String s : filteredIndexStrings) {
            if (s.matches("^(\\d+)-(\\d+)$")) {
                oneBasedIndexSet.addAll(parseRangeIndex(s));
            } else if (s.matches("^(\\d+)$")) {
                oneBasedIndexSet.add(Integer.parseInt(s));
            } else {
                throw new IllegalValueException(MESSAGE_INVALID_INDEX);
            }
        }

        List<Index> indexList = new ArrayList<>();
        for (int index : oneBasedIndexSet) {
            if (index <= 0) {
                throw new IllegalValueException(MESSAGE_INVALID_INDEX);
            }
            indexList.add(Index.fromOneBased(index));
        }
        return indexList;
    }

    /**
     * Parses a {@code oneBasedRangeIndex} into a List of {@code Index}.
     */
    private static List<Integer> parseRangeIndex(String oneBasedRangeIndex) {
        int separatorIndex = oneBasedRangeIndex.indexOf('-');
        int firstIndex = Integer.parseInt(oneBasedRangeIndex.substring(0, separatorIndex).trim());
        int secondIndex = Integer.parseInt(oneBasedRangeIndex.substring(separatorIndex + 1).trim());
        int start = Math.min(firstIndex, secondIndex);
        int end = Math.max(firstIndex, secondIndex);
        List<Integer> indexes = new ArrayList<>();
        for (Integer i = start; i <= end; i++) {
            indexes.add(i);
        }
        return indexes;
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
    public static Optional<Birthday> parseBirthday(Optional<String> birthday) throws IllegalValueException {
        requireNonNull(birthday);
        return birthday.isPresent() ? Optional.of(new Birthday(birthday.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> birthday} into an {@code Optional<Birthday>} if {@code birthday} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> facebook} into an {@code Optional<Facebook>} if {@code facebook} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Facebook> parseFacebook(Optional<String> facebook) throws IllegalValueException {
        requireNonNull(facebook);
        return facebook.isPresent() ? Optional.of(new Facebook(facebook.get())) : Optional.empty();
    }

    //@@author zenghou
    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.empty();
    }
    //@@author

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            if (tagName.trim().isEmpty()) { // skip empty tag
                continue;
            }
            tagSet.add(new Tag(tagName));
        }
        return tagSet;
    }
}
