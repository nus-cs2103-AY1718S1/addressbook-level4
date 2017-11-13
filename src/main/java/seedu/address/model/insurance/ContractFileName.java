package seedu.address.model.insurance;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTRACT_FILE_NAME;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.EmptyFieldException;

//@@author OscarWang114
/**
 * Represents a contract file name of an insurance in LISA.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class ContractFileName {

    public static final String MESSAGE_NAME_CONSTRAINTS = "Contract file names should start with "
            + "alphanumeric characters. It can only contain underscores, hyphens, spaces, "
            + "an optional file extension, and should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}_\\-]*(\\.[\\p{Alnum}]+)?";

    public final String fullName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public ContractFileName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (trimmedName.isEmpty()) {
            throw new EmptyFieldException(PREFIX_CONTRACT_FILE_NAME);
        }
        if (!isValidName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.fullName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid contract file name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContractFileName // instanceof handles nulls
                && this.fullName.equals(((ContractFileName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
