package seedu.address.model.socialMedia.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate SocialMedia objects.
 */
public class DuplicateSocialMediaException extends DuplicateDataException {
    public DuplicateSocialMediaException() {
        super("Operation would result in duplicate socialMediaUrls");
    }
}
