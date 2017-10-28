package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.commandidentifier.CommandIdentifier;
import seedu.address.model.person.Address;
import seedu.address.model.person.Country;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.email.Email;
import seedu.address.model.schedule.Activity;
import seedu.address.model.schedule.ScheduleDate;
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

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> country} into an {@code Optional<Country>} if {@code country} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Country> parseCountry(Optional<String> country) throws IllegalValueException {
        requireNonNull(country);
        return country.isPresent() ? Optional.of(new Country(country.get())) : Optional.empty();
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
     * * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Set<Email> parseEmails(Collection<String> emails) throws IllegalValueException {
        requireNonNull(emails);
        final Set<Email> emailSet = new HashSet<>();
        for (String emailName : emails) {
            emailSet.add(new Email(emailName));
        }
        return emailSet;
    }

    /**
     * Parses a {@code Optional<String> scheduleDate} into an {@code Optional<ScheduleDate>}
     * if {@code scheduleDate} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ScheduleDate> parseScheduleDate(Optional<String> scheduleDate) throws IllegalValueException {
        requireNonNull(scheduleDate);
        return scheduleDate.isPresent() ? Optional.of(new ScheduleDate(scheduleDate.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String activity} into an {@code Optional<Activity>} if {@code activity} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Activity> parseActivity(Optional<String> activity) throws IllegalValueException {
        requireNonNull(activity);
        return activity.isPresent() ? Optional.of(new Activity(activity.get())) : Optional.empty();
    }

    /**
     * Parses {@code commandWord} into an {@code CommandWord} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static CommandIdentifier parseCommandIdentifier(String commandWord) throws IllegalValueException {
        requireNonNull(commandWord);
        return new CommandIdentifier(commandWord.trim());
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
