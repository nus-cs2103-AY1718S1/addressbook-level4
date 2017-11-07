package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.property.Property;
import seedu.address.ui.person.PersonDetailsPanel;

//@@author yunpengn
/**
 * Represents the {@link Avatar} image of each {@link Person}. This is a one-to-one relationship, meaning that each
 * {@link Person} should have at most one {@link Avatar}.<br>
 *
 * Notice {@link Avatar} is not a {@link Property}. This is because it is indeed different from other fields of
 * {@link Person}. It is not shown as a row in the {@link PersonDetailsPanel}. Meanwhile, the input validation is
 * done by separate methods rather than a single regular expression (the complexity is not at the same level).
 */
public class Avatar {
    private static final String INVALID_URL_MESSAGE = "The provided URL is invalid.";
    private static final String IMG_URL_PATTERN =
            "^(https?://)?(?:[a-z0-9\\-]+\\.)+[a-z]{2,6}(?:/[^/#?]+)+\\.(?:jpe?g|gif|png)$";

    private String url;

    public Avatar(String url) throws IllegalValueException {
        if (!isValidImageUrl(url)) {
            throw new IllegalValueException(INVALID_URL_MESSAGE);
        }
        this.url = url;
    }

    /**
     * An all-in-one checking for the path of the provided image.
     */
    private boolean isValidAvatarPath(String path) {
        return !FileUtil.hasConsecutiveExtensionSeparators(path)
                && !FileUtil.hasConsecutiveNameSeparators(path)
                && !FileUtil.hasInvalidNames(path)
                && !FileUtil.hasInvalidNameSeparators(path);
    }

    /**
     * Checks whether a given string is a valid URL and it points to an image.
     */
    private boolean isValidImageUrl(String url) {
        return url.matches(IMG_URL_PATTERN);
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Avatar // instanceof handles nulls
                && this.url.equals(((Avatar) other).url));
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public String toString() {
        return "Avatar from " + url;
    }
}
