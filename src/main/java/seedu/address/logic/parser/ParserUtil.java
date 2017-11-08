package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.font.FontSize;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmailSubject;
import seedu.address.model.person.FacebookUsername;
import seedu.address.model.person.FileImage;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.ReminderDetails;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColor;

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
     * Parses a {@code Optional<String> details} into an {@code Optional<ReminderDetails>}
     * if {@code details} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ReminderDetails> parseDetails(Optional<String> details) throws IllegalValueException {
        requireNonNull(details);
        return details.isPresent() ? Optional.of(new ReminderDetails(details.get())) : Optional.empty();
    }
    /**
     * Parses a {@code Optional<String> priority} into an {@code Optional<Priority>} if {@code priority} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Priority> parsePriority(Optional<String> priority) throws IllegalValueException {
        requireNonNull(priority);

        return priority.isPresent() ? Optional.of(new Priority("Priority Level: " + priority.get())) : Optional.empty();
    }
    /**
     * Parses a {@code Optional<String> duedate} into an {@code Optional<DueDate>} if {@code duedate} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<DueDate> parseDueDate(Optional<String> dueDate) throws IllegalValueException {
        requireNonNull(dueDate);
        return dueDate.isPresent() ? Optional.of(new DueDate(dueDate.get())) : Optional.empty();
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
     * Parses a {@code Optional<String> date} into an {@code Optional<DateOfBirth>} if {@code dateOfBirth} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<DateOfBirth> parseDateOfBirth(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        return date.isPresent() ? Optional.of(new DateOfBirth(date.get())) : Optional.empty();
    }
    /**
     * Parses a {@code Optional<String> emailSubject}
     */
    public static Optional<EmailSubject> parseSubject(Optional<String> subject) throws IllegalValueException {
        requireNonNull(subject);
        return subject.isPresent() ? Optional.of(new EmailSubject(subject.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> date} into an {@code Optional<FacebookUsername>} if {@code dateOfBirth}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<FacebookUsername> parseUsername(Optional<String> username) throws IllegalValueException {
        requireNonNull(username);
        return username.isPresent() ? Optional.of(new FacebookUsername(username.get())) : Optional.empty();
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
     * Parses a {@code Optional<String> FilePath} into an {@code Optional<FileImage>} if {@code FilePath} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<FileImage> parseImage(Optional<String> image) throws IllegalValueException {
        requireNonNull(image);
        return image.isPresent() ? Optional.of(new FileImage(image.get())) : Optional.empty();
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
     * Parses a {@code Optional<String> tagColor} into an {@code Optional<TagColor>} if {@code tagColor} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<TagColor> parseTagColor(Optional<String> tagColor) throws IllegalValueException {
        requireNonNull(tagColor);
        return tagColor.isPresent() ? Optional.of(new TagColor(tagColor.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> fontSize} into an {@code Optional<FontSize>} if {@code fontSize} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<FontSize> parseFontSize(Optional<String> fontSize) throws IllegalValueException {
        requireNonNull(fontSize);
        return fontSize.isPresent() ? Optional.of(new FontSize(fontSize.get())) : Optional.empty();
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
