//@@author conantteo
package seedu.address.testutil;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

/**
 * A utility class to create Tag objects
 */
public class TagBuilder {

    public static final String DEFAULT_TAG_NAME = "friends";

    private Tag tag;

    public TagBuilder() {
        try {
            this.tag = new Tag(DEFAULT_TAG_NAME);
        } catch (IllegalValueException e) {
            throw new AssertionError("Default tag name is invalid");
        }
    }

    /**
     * Create a new Tag {@code tag} with the specified tag name {@code tagName}
     * @param tagName must be non-null and is a valid tag name
     * @return a new Tag of specified name.
     */
    public Tag withSpecifiedTagName(String tagName) {
        requireNonNull(tagName);
        try {
            tag = new Tag(tagName);
        } catch (IllegalValueException e) {
            throw new AssertionError("Specified tag name is invalid");
        }
        return tag;
    }

}
