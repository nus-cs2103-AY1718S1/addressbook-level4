package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.module.Module;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
import seedu.address.model.task.Appointment;
import seedu.address.model.task.Date;
import seedu.address.model.task.StartTime;

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

    // @@author ahmadalkaff
    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Set<Phone> parsePhones(Collection<String> phones) throws IllegalValueException {
        requireNonNull(phones);
        final Set<Phone> phoneSet = new HashSet<>();
        for (String phoneNum : phones) {
            phoneSet.add(new Phone(phoneNum));
        }
        return phoneSet;
    }
    // @@author

    /**
     * Parses a {@code Optional<String> Address} into an {@code Optional<Address>} if {@code Address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
    }

    //@@author viviantan95
    /**
     * Parses a {@code Optional<String> birthday} into an {@code Optional<Birthday>} if {@code birthday} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Birthday> parseBirthday(Optional<String> birthday) throws IllegalValueException {
        requireNonNull(birthday);
        return birthday.isPresent() ? Optional.of(new Birthday(birthday.get())) : Optional.empty();
    }
    //@@author

    // @@author ahmadalkaff
    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Set<Email> parseEmails(Collection<String> emails) throws IllegalValueException {
        requireNonNull(emails);
        final Set<Email> emailSet = new HashSet<>();
        for (String e : emails) {
            emailSet.add(new Email(e));
        }
        return emailSet;
    }
    // @@author

    /**
     * Parses {@code Collection<String> modules} into a {@code Set<Module>}.
     */
    public static Set<Module> parseModules(Collection<String> modules) throws IllegalValueException {
        requireNonNull(modules);
        final Set<Module> moduleSet = new HashSet<>();
        for (String moduleName : modules) {
            moduleSet.add(new Module(moduleName));
        }
        return moduleSet;
    }

    //@@author viviantan95
    /**
     * Parses {@code Optional<String> photo} into a {@code Optional<Photo>}.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Photo> parsePhoto(Optional<String> photo) throws IllegalValueException {
        requireNonNull(photo);
        return photo.isPresent() ? Optional.of(new Photo(photo.get())) : Optional.empty();
    }
    // @@author tanchc

    /**
     * Parses a {@code Optional<String> appointment} into an {@code Optional<Appointment>} if {@code appointment}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Appointment> parseAppointment(Optional<String> appointment) throws IllegalValueException {
        requireNonNull(appointment);
        return appointment.isPresent() ? Optional.of(new Appointment(appointment.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> date} into an {@code Optional<Date>} if {@code date} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Date> parseDate(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        return date.isPresent() ? Optional.of(new Date(date.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> startTime} into an {@code Optional<StartTime>} if {@code startTime} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<StartTime> parseStartTime(Optional<String> startTime) throws IllegalValueException {
        requireNonNull(startTime);
        return startTime.isPresent() ? Optional.of(new StartTime(startTime.get())) : Optional.empty();
    }
}
