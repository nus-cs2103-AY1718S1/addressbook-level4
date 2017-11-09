package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.SUFFIX_NO_RECUR_INTERVAL;
import static seedu.address.logic.parser.CliSyntax.SUFFIX_RECURRING_DATE_MONTHLY;
import static seedu.address.logic.parser.CliSyntax.SUFFIX_RECURRING_DATE_WEEKLY;
import static seedu.address.logic.parser.CliSyntax.SUFFIX_RECURRING_DATE_YEARLY;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.nlp.parse.DateGroup;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.TaskDates;

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
     * Parses multiple indices from {@code oneBasedIndices} into an array of {@code Index} containing indices which are
     * non zero unsigned integers.
     * @throws IllegalValueException if all indices given are invalid.
     */
    public static Index[] parseIndices(String... oneBasedIndices) throws IllegalValueException {
        Index[] parsedIndices = Arrays.stream(oneBasedIndices)
                .filter(index -> StringUtil.isNonZeroUnsignedInteger(index.trim()))
                .map(validIndex -> Index.fromOneBased(Integer.parseInt(validIndex.trim())))
                .toArray(Index[]::new);
        if (parsedIndices.length == 0) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return parsedIndices;
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

    //@@author eryao95
    /**
     * Parses a {@code Optional<String> birthday} into an {@code Optional<birthday>} if {@code birthday} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Birthday> parseBirthday(Optional<String> birthday) throws IllegalValueException {
        requireNonNull(birthday);
        return birthday.isPresent() ? Optional.of(new Birthday(birthday.get())) : Optional.empty();
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

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    public static Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    //@@author raisa2010
    /**
     * Parses a {@code Optional<String> description} into an {@code Optional<Description>} if {@code description}
     * is present.
     */
    public static Optional<Description> parseDescription(String description) throws IllegalValueException {
        requireNonNull(description);
        return description.isEmpty() ? Optional.empty()
                : Optional.of(new Description(description.replace("\"", "")));
    }

    /**
     * Parses a {@code Optional<String> date} into an {@code Optional<StartDate>} if {@code date} is present.
     */
    public static Optional<StartDate> parseStartDate(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        if (date.isPresent() && !TaskDates.getDottedFormat(date.get()).isEmpty()) {
            return Optional.of(new StartDate(TaskDates.formatDate(parseDottedDate(date.get())),
                    parseRecurInterval(date.get())));
        }
        return (date.isPresent() && !date.get().isEmpty())
                ? Optional.of(new StartDate(TaskDates.formatDate(parseDate(date.get())),
                parseRecurInterval(date.get()))) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> date} into an {@code Deadline} if {@code date} is present.
     */
    public static Optional<Deadline> parseDeadline(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        if (date.isPresent() && !TaskDates.getDottedFormat(date.get()).isEmpty()) {
            return Optional.of(new Deadline(TaskDates.formatDate(parseDottedDate(date.get())),
                    parseRecurInterval(date.get())));
        }
        return (date.isPresent() && !date.get().isEmpty())
                ? Optional.of(new Deadline(TaskDates.formatDate(parseDate(date.get())), parseRecurInterval(date.get())))
                : Optional.empty();
    }

    /**
     * Parses a {@code String naturalLanguageInput} using PrettyTime NLP, into a {@code Date}.
     * @throws IllegalValueException if the date cannot be parsed from the phrase or if the given date is invalid.
     */
    public static Date parseDate(String naturalLanguageInput) throws IllegalValueException {
        List<DateGroup> dateGroup = new PrettyTimeParser().parseSyntax(naturalLanguageInput.trim());
        if (dateGroup.isEmpty() | !TaskDates.isDateValid(naturalLanguageInput)) {
            throw new IllegalValueException(TaskDates.MESSAGE_DATE_CONSTRAINTS);
        }
        List<Date> dates = dateGroup.get(dateGroup.size() - 1).getDates();
        return dates.get(dates.size() - 1);
    }

    /**
     * Parses the {@code String inputDate} into a {@code Date} if the input is given in (M)M.(d)d.(yy)yy format,
     * which cannot be parsed by the PrettyTime NLP.
     */
    public static Date parseDottedDate(String inputDate) throws IllegalValueException {
        try {
            return new SimpleDateFormat(TaskDates.getDottedFormat(inputDate)).parse(inputDate);
        } catch (ParseException p) {
            throw new IllegalValueException(TaskDates.MESSAGE_DATE_CONSTRAINTS);
        }
    }

    /**
     * Parses the {@code String dateString} of a date into a {@code Suffix} specifying its recur interval.
     */
    public static Suffix parseRecurInterval(String dateString) {
        return (dateString.contains(SUFFIX_RECURRING_DATE_WEEKLY.toString()) ? SUFFIX_RECURRING_DATE_WEEKLY
                : (dateString.contains(SUFFIX_RECURRING_DATE_MONTHLY.toString())) ? SUFFIX_RECURRING_DATE_MONTHLY
                : (dateString.contains(SUFFIX_RECURRING_DATE_YEARLY.toString())) ? SUFFIX_RECURRING_DATE_YEARLY
                : SUFFIX_NO_RECUR_INTERVAL);
    }
}
