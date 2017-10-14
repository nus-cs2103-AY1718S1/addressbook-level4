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
import seedu.address.model.module.TimeSlot;
import seedu.address.model.module.Location;
import seedu.address.model.module.ClassType;
import seedu.address.model.module.Group;
import seedu.address.model.module.Code;
import seedu.address.model.lecturer.Lecturer;

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
     * Parses a {@code Optional<String> code} into an {@code Optional<Code>} if {@code code} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Code> parseCode(Optional<String> code) throws IllegalValueException {
        requireNonNull(code);
        return code.isPresent() ? Optional.of(new Code(code.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> location} into an {@code Optional<Location>} if {@code location} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Location> parseLocation(Optional<String> location) throws IllegalValueException {
        requireNonNull(location);
        return location.isPresent() ? Optional.of(new Location(location.get())) : Optional.empty();
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
     * Parses a {@code Optional<String> group} into an {@code Optional<Group>} if {@code group} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Group> parseGroup(Optional<String> group) throws IllegalValueException {
        requireNonNull(group);
        return group.isPresent() ? Optional.of(new Group(group.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> timeSlot} into an {@code Optional<TimeSlot>} if {@code timeSlot} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<TimeSlot> parseTimeSlot(Optional<String> timeSlot) throws IllegalValueException {
        requireNonNull(timeSlot);
        return timeSlot.isPresent() ? Optional.of(new TimeSlot(timeSlot.get())) : Optional.empty();
    }


    /**
     * Parses a {@code Optional<String> classType} into an {@code Optional<ClassType>} if {@code classType} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ClassType> parseClassType(Optional<String> classType) throws IllegalValueException {
        requireNonNull(classType);
        return classType.isPresent() ? Optional.of(new ClassType(classType.get())) : Optional.empty();
    }


    /**
     * Parses {@code Collection<String> lecturer} into a {@code Set<Lecturer>}.
     */
    public static Set<Lecturer> parseLecturer(Collection<String> lecturers) throws IllegalValueException {
        requireNonNull(lecturers);
        final Set<Lecturer> lecturerSet = new HashSet<>();
        for (String lecturerName : lecturers) {
            lecturerSet.add(new Lecturer(lecturerName));
        }
        return lecturerSet;
    }
}
