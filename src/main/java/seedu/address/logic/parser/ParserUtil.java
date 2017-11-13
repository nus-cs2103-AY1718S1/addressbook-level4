package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfPic;
import seedu.address.model.schedule.ScheduleDate;
import seedu.address.model.schedule.ScheduleName;
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
    public static final String NOT_EXISTING = "---";

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
     * Parses a {@code Optional<String> groupName} into an {@code Optional<GroupName>} if {@code groupName} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<GroupName> parseGroupName(Optional<String> groupName) throws IllegalValueException {
        requireNonNull(groupName);
        return groupName.isPresent() ? Optional.of(new GroupName(groupName.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> scheduleName} into an {@code Optional<GroupName>}
     * if {@code scheduleName} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ScheduleName> parseScheduleName(Optional<String> scheduleName) throws IllegalValueException {
        requireNonNull(scheduleName);
        return scheduleName.isPresent() ? Optional.of(new ScheduleName((scheduleName.get()))) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> scheduleDate} into an {@code Optional<GroupName>}
     * if {@code scheduleDate} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ScheduleDate> parseDate(Optional<String> scheduleDate) throws IllegalValueException {
        requireNonNull(scheduleDate);
        return scheduleDate.isPresent() ? Optional.of(new ScheduleDate((scheduleDate.get()))) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> scheduleDetails} into an {@code Optional<GroupName>}
     * if {@code scheduleDate} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseScheduleDetails(Optional<String> scheduleDetails) throws IllegalValueException {
        requireNonNull(scheduleDetails);
        return scheduleDetails.isPresent() ? Optional.of((scheduleDetails.get())) : Optional.empty();
    }



    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * Used for edit command
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * If {@code Optional<String> phone} does not exist, create an {@code Optional<Phone>} with empty name "---"
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhoneAdd(Optional<String> phone) throws IllegalValueException {
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.of(new Phone(NOT_EXISTING));
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
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * If {@code Optional<String> address} does not exist, create an {@code Optional<Address>} with empty name "---"
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddressAdd(Optional<String> address) throws IllegalValueException {
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.of(new Address(NOT_EXISTING));
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
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * If {@code Optional<String> email} does not exist, create an {@code Optional<Email>} with empty name "---"
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmailAdd(Optional<String> email) throws IllegalValueException {
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.of(new Email(NOT_EXISTING));
    }

    /**
     * Parses a {@code Optional<ProfPic> filePath} into an {@code Optional<ProfPic>} if {@code filePath} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ProfPic> parseFilePath(Optional<String> filePath) throws IllegalValueException {
        requireNonNull(filePath);
        return filePath.isPresent() ? Optional.of(new ProfPic(filePath.get())) : Optional.empty();
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
     * Parses {@code Collection<String> groups} into a {@code Set<Group>}.
     */
    public static Set<Group> parseGroups(Collection<String> groups) throws IllegalValueException {
        requireNonNull(groups);
        final Set<Group> groupSet = new HashSet<>();
        for (String groupName : groups) {
            groupSet.add(new Group(groupName));
        }
        return groupSet;
    }

}
