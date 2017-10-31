package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    private static final Integer ONE_OBJECT = 1;

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
     * Parses {@code Collection<String> names} into a {@code List<Name>}.
     */
    public static List<Name> parseNameFind(Collection<String> names) throws IllegalValueException {
        requireNonNull(names);
        final List<Name> nameList = new ArrayList<>();
        for (String name : names) {
            nameList.add(new Name(name));
        }
        return nameList;
    }

    /**
     * Parses {@code Collection<String> phones} into a {@code List<Phone>}.
     */
    public static List<Phone> parsePhoneFind(Collection<String> phones) throws IllegalValueException {
        requireNonNull(phones);
        final List<Phone> phoneList = new ArrayList<>();
        for (String phoneName : phones) {
            phoneList.add(new Phone(phoneName));
        }
        return phoneList;
    }

    /**
     * Parses {@code Collection<String> emails} into a {@code List<Email>}.
     */
    public static List<Email> parseEmailFind(Collection<String> emails) throws IllegalValueException {
        requireNonNull(emails);
        final List<Email> emailList = new ArrayList<>();
        for (String emailName : emails) {
            emailList.add(new Email(emailName));
        }
        return emailList;
    }

    /**
     * Parses {@code Collection<String> addresses} into a {@code List<Address>}.
     */
    public static List<Address> parseAddressFind(Collection<String> addresses) throws IllegalValueException {
        requireNonNull(addresses);
        final List<Address> addressList = new ArrayList<>();
        for (String addressName : addresses) {
            addressList.add(new Address(addressName));
        }
        return addressList;
    }

    /**
     * Parses {@code Collection<String> remarks} into a {@code ArrayList<Remark>}.
     */
    public static ArrayList<Remark> parseRemarks(Collection<String> remarks) throws IllegalValueException {
        requireNonNull(remarks);
        final ArrayList<Remark> remarkArrayList = new ArrayList<>();
        for (String remarkString : remarks) {
            remarkArrayList.add(new Remark(remarkString));
        }
        if (remarkArrayList.size() < ONE_OBJECT) {
            remarkArrayList.add(new Remark(""));
        }
        return remarkArrayList;
    }

    /**
     * Parses {@code Collection<Integer> indexes} into a {@code ArrayList<Integer>}.
     */
    public static ArrayList<Integer> parseIndexes(Collection<Integer> indexes) throws IllegalValueException {
        requireNonNull(indexes);
        final ArrayList<Integer> indexArrayList = new ArrayList<>();
        for (Integer index : indexes) {
            indexArrayList.add(index);
        }

        return indexArrayList;
    }
}
