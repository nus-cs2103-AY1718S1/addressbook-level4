package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.event.Description;
import seedu.address.model.event.Title;
import seedu.address.model.event.timeslot.Timeslot;
import seedu.address.model.person.Address;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
import seedu.address.model.person.Position;
import seedu.address.model.person.Priority;
import seedu.address.model.person.Status;
import seedu.address.model.relationship.Relationship;
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
     * Parses a {@code Optional<String> company} into an {@code Optional<Company>} if {@code company} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Company> parseCompany(Optional<String> company) throws IllegalValueException {
        requireNonNull(company);
        return company.isPresent() ? Optional.of(new Company(company.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> position} into an {@code Optional<Position>} if {@code position} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Position> parsePosition(Optional<String> position) throws IllegalValueException {
        requireNonNull(position);
        return position.isPresent() ? Optional.of(new Position(position.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> status} into an {@code Optional<Status>} if {@code status} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Status> parseStatus(Optional<String> status) throws IllegalValueException {
        requireNonNull(status);
        return status.isPresent() ? Optional.of(new Status(status.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> priority} into an {@code Optional<Priority>} if {@code priority} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Priority> parsePriority(Optional<String> priority) throws IllegalValueException {
        requireNonNull(priority);
        return priority.isPresent() ? Optional.of(new Priority(priority.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> note} into an {@code Optional<Note>} if {@code note} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Note> parseNote(Optional<String> note) throws IllegalValueException {
        requireNonNull(note);
        return note.isPresent() ? Optional.of(new Note(note.get())) : Optional.empty();
    }

    //@@author a0107442n
    /**
     * Parses a {@code Optional<String> photo} into an {@code Optional<Photo>}
     * if {@code photo} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Photo> parsePhoto(Optional<String> filePath)
            throws IllegalValueException {
        requireNonNull(filePath);
        String originalFilePath;
        String destFilePath = null;
        if (filePath.isPresent()) {
            originalFilePath = filePath.get();
            String s = File.separator;
            int lastDelimiterPosition = originalFilePath.lastIndexOf(s);
            String fileName = originalFilePath.substring
                    (lastDelimiterPosition + 1);
            if (lastDelimiterPosition == -1 || !fileName.matches
                    ("[\\w\\/\\-\\_\\.\\h\\\\]+\\.(jpg|png|jpeg)")) {
                throw new IllegalValueException(Photo.MESSAGE_PHOTO_CONSTRAINTS);
            } else {
                try {
                    destFilePath = "src" + s +"main" + s + "resources" + s
                            + "images" + s + fileName;
                    File originalFile = new File(originalFilePath);
                    File destFile = new File(destFilePath);
                    FileUtil.copyFile(originalFile, destFile);
                } catch (IOException e) {
                    throw new IllegalValueException("Invalid file path. "
                            + "Please try again.");
                }
            }
        }
        return  filePath.isPresent() ? Optional.of(new Photo(destFilePath))
                : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> photoURL} into an {@code
     * Optional<Photo>} if {@code photoURL} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Photo> parsePhotoUrl(Optional<String> photoUrl)
            throws IllegalValueException {
        requireNonNull(photoUrl);
        return photoUrl.isPresent() ? Optional.of(new Photo(photoUrl.get()))
                : Optional.empty();
    }

    //@@author a0107442n
    /**
     * Parses a {@code Optional<String> title} into an {@code Optional<Title>} if {@code title} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Title> parseTitle(Optional<String> title) throws IllegalValueException {
        requireNonNull(title);
        return title.isPresent() ? Optional.of(new Title(title.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> timeslot} into an {@code Optional<Timeslot>} if {@code timeslot} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Timeslot> parseTimeslot(Optional<String> timeslot)
            throws IllegalValueException {
        requireNonNull(timeslot);
        return timeslot.isPresent() ? Optional.of(new Timeslot(timeslot.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> description} into an {@code Optional<Description>} if {@code description} is
     * present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Description> parseDescription(Optional<String> description) throws IllegalValueException {
        requireNonNull(description);
        return description.isPresent() ? Optional.of(new Description(description.get())) : Optional.empty();
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
     * Parses {@code Collection<String> relationship} into a {@code Set<Relationship>}.
     */
    public static Set<Relationship> parseRel(Collection<String> relation) throws IllegalValueException {
        requireNonNull(relation);
        final Set<Relationship> relationSet = new HashSet<>();
        for (String relationType : relation) {
            relationSet.add(new Relationship(relationType));
        }
        return relationSet;
    }
}
