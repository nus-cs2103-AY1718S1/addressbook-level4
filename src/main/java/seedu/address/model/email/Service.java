//@@author tshradheya
package seedu.address.model.email;

import static java.util.Objects.requireNonNull;

/**
 * Specifies email service to use for processing email command
 */
public class Service {
    public final String service;

    public Service(String service) {
        requireNonNull(service);
        String trimmedService = service.trim();

        this.service = trimmedService;
    }

    @Override
    public String toString() {
        return service;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Service // instanceof handles nulls
                && this.service.equals(((Service) other).service)); // state check
    }

    @Override
    public int hashCode() {
        return service.hashCode();
    }
}
