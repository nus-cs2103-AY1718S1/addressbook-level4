//@@author tshradheya
package seedu.address.logic.parser.exceptions;

import java.io.IOException;

/**
 * Represents a image reading error encountered by a parser.
 */
public class ImageException extends IOException {

    public ImageException(String message) {
        super(message);
    }
}
