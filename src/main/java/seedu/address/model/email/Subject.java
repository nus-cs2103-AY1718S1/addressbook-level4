//@@author tshradheya
package seedu.address.model.email;

import static java.util.Objects.requireNonNull;

/**
 * Represent's subject of the Email being sent
 */
public class Subject {

    public final String subject;

    public Subject(String subject) {
        requireNonNull(subject);
        String trimmedSubject = subject.trim();

        this.subject = trimmedSubject;
    }

    @Override
    public String toString() {
        return subject;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Subject // instanceof handles nulls
                && this.subject.equals(((Subject) other).subject)); // state check
    }

    @Override
    public int hashCode() {
        return subject.hashCode();
    }
}
