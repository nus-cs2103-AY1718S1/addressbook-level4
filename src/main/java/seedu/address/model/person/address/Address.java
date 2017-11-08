package seedu.address.model.person.address;

import static java.util.Objects.requireNonNull;

import java.util.StringTokenizer;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #hasValidAddressFormat(String)}
 */
public class Address {

    public static final String MESSAGE_ADDRESS_CONSTRAINTS =
            "Address must be in the following format:\n"
            + "BLOCK, STREET, [UNIT,] POSTAL CODE";

    public static final String ADDRESS_FORMAT_DELIMITER = ",";
    public static final int ADDRESS_TOKENS_WITHOUT_UNIT = 3;
    public static final int ADDRESS_TOKENS_WITH_UNIT = 4;

    public final String value;
    //@@author 17navasaw
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

        assert ((numTokens == ADDRESS_TOKENS_WITHOUT_UNIT)
                || (numTokens == ADDRESS_TOKENS_WITH_UNIT)) : "The address should be split into 3 or 4 tokens.";

        block = new Block(tokens[0]);
        street = new Street(tokens[1]);

        if (numTokens == ADDRESS_TOKENS_WITHOUT_UNIT) {

            postalCode = new PostalCode(tokens[2]);

        } else if (numTokens == ADDRESS_TOKENS_WITH_UNIT) {

            unit = new Unit (tokens[2]);
            postalCode = new PostalCode(tokens[3]);
        }
    }

    /**
     * Returns true if a given string follows the valid format for address.
     */
    public static boolean hasValidAddressFormat(String test) {
        StringTokenizer tokenizer = new StringTokenizer(test, ADDRESS_FORMAT_DELIMITER);

        return ((tokenizer.countTokens() == ADDRESS_TOKENS_WITHOUT_UNIT)
                || (tokenizer.countTokens() == ADDRESS_TOKENS_WITH_UNIT));
    }

    //@@author
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
