package seedu.address.model.property.exceptions;

import static seedu.address.commons.core.Messages.PROPERTY_NOT_FOUND;

//@@author yunpengn
/**
 * Signals that the required property has not been defined yet.
 */
public class PropertyNotFoundException extends Exception {
    public PropertyNotFoundException() {
        super("Property not found.");
    }

    public PropertyNotFoundException(String shortName) {
        super(String.format(PROPERTY_NOT_FOUND, shortName));
    }
}
