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
import seedu.address.model.customfields.CustomField;
import seedu.address.model.group.Group;
import seedu.address.model.person.Address;
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

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
    }

    //@@author sofarsophie
    /**
     * Parses a {@code Collection<String> names} into a {@code List<Name>}
     */
    public static List<Name> parseNames(Collection<String> names) throws IllegalValueException {
        requireNonNull(names);
        final List<Name> nameList = new ArrayList<>();
        for (String name : names) {
            nameList.add(new Name(name));
        }
        return nameList;
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

    //@@author sofarsophie
    /**
     * Parses a {@code Collection<String> phones} into a {@code List<Phone>}
     */
    public static List<Phone> parsePhones(Collection<String> phones) throws IllegalValueException {
        requireNonNull(phones);
        final List<Phone> phoneList = new ArrayList<>();
        for (String phone : phones) {
            phoneList.add(new Phone(phone));
        }
        return phoneList;
    }
    //@@author

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
    }

    //@@author sofarsophie
    /**
     * Parses a {@code Collection<String> addresses} into a {@code List<Address>}
     */
    public static List<Address> parseAddresses(Collection<String> addresses) throws IllegalValueException {
        requireNonNull(addresses);
        final List<Address> addressList = new ArrayList<>();
        for (String address : addresses) {
            addressList.add(new Address(address));
        }
        return addressList;
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

    //@@author sofarsophie
    /**
     *
     * Parses a {@code Collection<String> emails} into a {@code List<Email>}
     */
    public static List<Email> parseEmails(Collection<String> emails) throws IllegalValueException {
        requireNonNull(emails);
        final List<Email> emailList = new ArrayList<>();
        for (String email : emails) {
            emailList.add(new Email(email));
        }
        return emailList;
    }
    //@@author

    //@@author kengying
    /**
     * Parses a {@code Optional<String> group} into an {@code Optional<Group>} if {@code group} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Group> parseGroup(Optional<String> group) throws IllegalValueException {
        requireNonNull(group);
        return group.isPresent() ? Optional.of(new Group(group.get())) : Optional.empty();
    }

    /**
     *
     * Parses a {@code Collection<String> group} into a {@code List<Group>}
     */
    public static List<Group> parseGroup(Collection<String> groups) throws IllegalValueException {
        requireNonNull(groups);
        final List<Group> groupList = new ArrayList<>();
        for (String group : groups) {
            groupList.add(new Group(group));
        }
        return groupList;
    }
    //@@author

    //@@author syy94
    /**
     *
     * Parses {@code Collection<String> fields} into a {@code Set<{@link CustomField}>}.
     */
    public static Set<CustomField> parseCustomFields(Collection<String> fields) throws IllegalValueException {
        requireNonNull(fields);

        final Set<CustomField> fieldSet = new HashSet<>();
        for (String field : fields) {
            fieldSet.add(parseCustomField(field));
        }
        return fieldSet;
    }

    private static CustomField parseCustomField(String field) throws IllegalValueException {
        requireNonNull(field);
        return new CustomField(field);
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
}
