//@@author tshradheya
package seedu.address.model.email;

import static java.util.Objects.requireNonNull;

/**
 * Represent's the body of an Email
 */
public class Body {

    public final String body;

    public Body(String body) {
        requireNonNull(body);
        String trimmedBody = body.trim();

        this.body = trimmedBody;
    }

    @Override
    public String toString() {
        return body;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Body // instanceof handles nulls
                && this.body.equals(((Body) other).body)); // state check
    }

    @Override
    public int hashCode() {
        return body.hashCode();
    }
}
