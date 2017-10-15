package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.person.PostalCode.MESSAGE_POSTAL_CODE_CONSTRAINTS;
import static seedu.address.model.person.PostalCode.isValidPostalCode;
import static seedu.address.model.util.ClusterUtil.getCluster;

import java.util.HashMap;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.util.ClusterUtil;

//@@author khooroko
/**
 * Represents a Person's cluster in the address book.
 * Guarantees: immutable; can only be declared with a valid {@code PostalCode}
 */
public class Cluster {

    public static final String CLUSTER_VALIDATION_REGEX = "[A-Za-z,.'\\\"\\\\s]";

    public final String value;

    /**
     * Validates given cluster. Can only be called with a validated postal code.
     *
     * @throws IllegalValueException if given cluster string is invalid.
     */
    public Cluster(PostalCode postalCode) {
        requireNonNull(postalCode);
        if (!isValidPostalCode(postalCode.toString())) {
            throw new AssertionError(MESSAGE_POSTAL_CODE_CONSTRAINTS);
        }
        String cluster = getCluster(postalCode.toString());
        this.value = cluster;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Cluster // instanceof handles nulls
                && this.value.equals(((Cluster) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
