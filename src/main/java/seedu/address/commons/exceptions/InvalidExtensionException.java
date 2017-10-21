package seedu.address.commons.exceptions;

import java.io.IOException;

/**
 * Signals an error caused by loading a file without the official .rldx extension.
 */
public class InvalidExtensionException extends IOException {
    public InvalidExtensionException(String message) {
        super(message);
    }
}
