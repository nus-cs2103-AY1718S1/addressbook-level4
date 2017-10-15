package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.util.ClusterUtil.getCluster;

import java.util.HashMap;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.util.ClusterUtil;

//@@author khooroko
/**
 * Represents a Person's cluster in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCluster(String)}
 */
public class Cluster {

    public static final String MESSAGE_CLUSTER_CONSTRAINTS =
            "Cluster should contain only alphabets, commas, and whitespaces with first 2 numbers registered";

    public static final String CLUSTER_VALIDATION_REGEX = "[A-Za-z,.'\\\"\\\\s]";

    public final String value;

    /**
     * Validates given cluster.
     *
     * @throws IllegalValueException if given cluster string is invalid.
     */
    public Cluster(PostalCode postalCode) throws IllegalValueException {
        requireNonNull(postalCode);
        String cluster = getCluster(postalCode.toString().substring(0, 2));
        if (cluster == null) {
            throw new IllegalValueException(MESSAGE_CLUSTER_CONSTRAINTS);
        }
        this.value = cluster;
    }

    /**
     * Returns true if a given string is a valid person cluster.
     */
    public static boolean isValidCluster(String test) {
        return test.matches(CLUSTER_VALIDATION_REGEX);
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
