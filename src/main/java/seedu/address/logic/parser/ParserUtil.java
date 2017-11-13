package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Homepage;
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
    public static final String MESSAGE_INVALID_ARG = "Argument provided is invalid.";
    public static final String STRING_IF_EMPTY = "";

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

    //@@author karrui
    /**
     * Parses a {@code Optional<String> Remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.map(Remark::new);
    }

    /**
     * Parses a {@code Optional<String> homepage} into an {@code Optional<Homepage>} if {@code homepage} is present.
     * If {@code homepage} is "", returns default homepage constructor.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Homepage> parseHomepage(Optional<String> homepage) throws IllegalValueException {
        requireNonNull(homepage);
        if (homepage.isPresent()) {
            if (homepage.get().equals("")) {
                return Optional.of(new Homepage());
            } else {
                return Optional.of(new Homepage(homepage.get()));
            }
        } else {
            return Optional.empty();
        }
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

    //@@author yewshengkai
    /**
     * Parses a {@code Collection<String> Detail} into an {@code ArrayList<String>}.
     */
    public static ArrayList<String> parseAllDetail(Collection<String> detail, String commandType)
            throws IllegalValueException {
        requireNonNull(detail);
        ArrayList<String> detailList = new ArrayList<String>();
        String[] detailString = detail.toString().split("\\s+");
        for (String string : detailString) {
            string = string.replaceAll("['\\[\\]']", "");
            switch (commandType) {
            case FindCommand.COMMAND_WORD_ADDRESS:
                if (!Address.isValidAddress(string) || string.isEmpty()) {
                    throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
                }
                break;
            case FindCommand.COMMAND_WORD_EMAIL:
                if (!Email.isValidEmail(string) || string.isEmpty()) {
                    throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
                }
                break;
            case FindCommand.COMMAND_WORD_PHONE:
                if (!Phone.isValidPhone(string)) {
                    throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
                }
                break;
            case FindCommand.COMMAND_WORD_HOMEPAGE:
                if (!Homepage.isValidHomepage(string)) {
                    throw new IllegalValueException(Homepage.MESSAGE_HOMEPAGE_CONSTRAINTS);
                }
                break;
            case FindCommand.COMMAND_WORD_TAG:
                if (!Tag.isValidTagName(string)) {
                    throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
                }
                break;
            default:
                break;
            }
            detailList.add(string);
        }
        return detailList;
    }
    //@@author

    //@@author karrui
    /** Parses {@code sortOrder} into a {@code boolean} and returns it.
    * @throws IllegalValueException if the specified parameter is invalid (not "a", "d", or "").
    */
    public static boolean parseSort(String sortOrder) throws IllegalValueException {
        String trimmedSortParameter = sortOrder.trim();
        switch (trimmedSortParameter) {
        case "":
        case "a":
            return false;
        case "d":
            return true;
        default:
            throw new IllegalValueException(MESSAGE_INVALID_ARG);
        }
    }

    /**
     * Parse parameters provided, if exist, return value. If value does not exist, return empty string.
     */
    public static Optional<String> parseValues(Optional<String> value) {
        return Optional.of(value.orElse(STRING_IF_EMPTY));
    }

    //@@author yewshengkai
    /**
     * Parse parameters tag (singular), if exist, return value. If tag does not exist, return empty string.
     */
    public static Optional<Tag> parseTag(Optional<String> tag) throws IllegalValueException {
        requireNonNull(tag);
        if (tag.isPresent()) {
            return Optional.of(new Tag(tag.get()));
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
    }
}
