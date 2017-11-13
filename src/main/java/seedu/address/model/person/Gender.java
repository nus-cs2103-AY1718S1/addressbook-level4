//@@author Pujitha97
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
    private static final String GENDER_VALIDATION_REGEX = "(?i)\\b(female|f|male|m|other|o|"
            + "notspecified|not_specified|not specified)\\b";

    public final GenderType value;
    private boolean genderset;

    /**
     * Initialise a Gender object with value of empty String. This can ONLY be used in the default field of
     * {@code AddPersonOptionalFieldDescriptor}
     */
    public Gender() {
        this.genderset = false;
        this.value = GenderType.NOT_SPECIFIED;
    }

    /**
     * Validates given gender.
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
        case ("f"):
            this.value = GenderType.FEMALE;
            break;
        case ("male"):
        case ("m"):
            this.value = GenderType.MALE;
            break;
        case ("other"):
        case("o"):
            this.value = GenderType.OTHER;
            break;
        default:
            this.value = GenderType.NOT_SPECIFIED;
        }
        this.genderset = true;
    }

    /**
     * Returns true if a given string is a valid person gender.
     */
    public static boolean isValidGender(String test) {
        return test.matches(GENDER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return genderset ? value.toString() : "";
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
