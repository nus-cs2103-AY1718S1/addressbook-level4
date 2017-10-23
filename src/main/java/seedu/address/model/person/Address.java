package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.StringTokenizer;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.address.Block;
import seedu.address.model.person.address.PostalCode;
import seedu.address.model.person.address.Street;
import seedu.address.model.person.address.Unit;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #hasValidAddressFormat(String)}
 */
public class Address {

    public static final String MESSAGE_ADDRESS_CONSTRAINTS =
            "Address must be in the following format:\n"
            + "BLOCK, STREET, [UNIT,] POSTAL CODE";

    public static final String ADDRESS_FORMAT_DELIMITER = ",";

    public final String value;
    private Block block;
    private Street street;
    private Unit unit;
    private PostalCode postalCode;


    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string follows invalid format.
     */
    public Address(String address) throws IllegalValueException {
        requireNonNull(address);
        String trimmedAddress = address.trim();

        if (!hasValidAddressFormat(trimmedAddress)) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }

        splitAddressString(trimmedAddress);

        this.value = trimmedAddress;
    }

    /**
     * Splits Address into Block, Street, Unit[optional parameter] and PostalCode.
     */
    private void splitAddressString(String trimmedAddress) throws IllegalValueException {
        String[] tokens = trimmedAddress.split(",");
        int numTokens = tokens.length;

        assert ((numTokens == 3) || (numTokens == 4)) : "The address should be split into 3 or 4 tokens.";

        block = new Block(tokens[0]);
        street = new Street(tokens[1]);

        if (numTokens == 3) {

            postalCode = new PostalCode(tokens[2]);

        } else if (numTokens == 4) {

            unit = new Unit (tokens[2]);
            postalCode = new PostalCode(tokens[3]);
        }
    }

    /**
     * Returns true if a given string follows the valid format for address.
     */
    public static boolean hasValidAddressFormat(String test) {
        StringTokenizer tokenizer = new StringTokenizer(test, ADDRESS_FORMAT_DELIMITER);

        return ((tokenizer.countTokens() == 3) || (tokenizer.countTokens() == 4));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Address // instanceof handles nulls
                && this.value.equals(((Address) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
