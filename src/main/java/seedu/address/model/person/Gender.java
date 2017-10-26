package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.EmptyFieldException;
/**
 * Represents a Person's gender in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Gender {

    /**
     * Different ENUM constants available for gender type
     */
    enum GenderType {
        NOT_SPECIFIED, MALE, FEMALE, OTHER
    }

    public static final String MESSAGE_GENDER_CONSTRAINTS =
            "Person's gender can take either Male , Female or Other and it should not be blank";

    /*
     * The first character of the gender must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String GENDER_VALIDATION_REGEX = "(?i)\\b(female|f|male|m|other|o)\\b";

    public final GenderType value;

    /**
     * Initialise a Gender object with value of empty String. This can ONLY be used in the default field of
     * {@code AddPersonOptionalFieldDescriptor}
     */
    public Gender() {
        this.value = GenderType.NOT_SPECIFIED;
    }

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given gender string is invalid.
     */
    public Gender(String gen) throws IllegalValueException {
        requireNonNull(gen);
        if (gen.isEmpty()) {
            throw new EmptyFieldException(PREFIX_GENDER);
        }
        if (!isValidGender(gen)) {
            throw new IllegalValueException(MESSAGE_GENDER_CONSTRAINTS);
        }
        switch (gen.toLowerCase()) {
        case ("female"):
            this.value = GenderType.FEMALE;
            break;
        case ("f"):
            this.value = GenderType.FEMALE;
            break;
        case ("male"):
            this.value = GenderType.MALE;
            break;
        case ("m"):
            this.value = GenderType.MALE;
            break;
        case ("other"):
            this.value = GenderType.OTHER;
            break;
        case("o"):
            this.value = GenderType.OTHER;
            break;
        default:
            this.value = GenderType.NOT_SPECIFIED;
        }
    }

    /**
     * Returns true if a given string is a valid person gender.
     */
    public static boolean isValidGender(String test) {
        return test.matches(GENDER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gender // instanceof handles nulls
                && this.value.equals(((Gender) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
