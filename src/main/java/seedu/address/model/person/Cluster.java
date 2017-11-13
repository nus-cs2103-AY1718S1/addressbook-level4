package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.person.PostalCode.MESSAGE_POSTAL_CODE_CONSTRAINTS;
import static seedu.address.model.person.PostalCode.isValidPostalCode;
import static seedu.address.model.util.ClusterUtil.getCluster;

//@@author khooroko
/**
 * Represents a Person's {@code Cluster} in the address book.
 * Guarantees: immutable; can only be declared with a valid {@code PostalCode}
 */
public class Cluster {

    public final int clusterNumber;
    public final String value;

    /**
     * Validates given cluster. Can only be called with a validated postal code.
     */
    public Cluster(PostalCode postalCode) {
        requireNonNull(postalCode);
        if (!isValidPostalCode(postalCode.toString())) {
            throw new AssertionError(MESSAGE_POSTAL_CODE_CONSTRAINTS);
        }
        String cluster = getCluster(postalCode.toString());
        clusterNumber = Integer.parseInt(cluster.substring(0, 2));
        this.value = cluster.substring(4);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Cluster // instanceof handles nulls
                && this.clusterNumber == ((Cluster) other).clusterNumber
                && this.value.equals(((Cluster) other).value)); // state check
    }

    public int compareTo(Cluster other) {
        return this.clusterNumber - other.clusterNumber;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
