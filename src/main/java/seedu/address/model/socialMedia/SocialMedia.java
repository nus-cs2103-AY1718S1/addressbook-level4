package seedu.address.model.socialMedia;

import static java.util.Objects.requireNonNull;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a SocialMedia in an address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class SocialMedia implements ReadOnlySocialMedia {

    private ObjectProperty<SocialMediaUrl> socialMediaUrlsName;

    /**
     * Every field must be present and not null.
     */
    public SocialMedia(SocialMediaUrl name) {
        requireNonNull(name);
        this.socialMediaUrlsName = new SimpleObjectProperty<>(name);
    }

    /**
     * Every field must be present and not null.
     */
    public SocialMedia(String name) throws IllegalValueException {
        requireNonNull(name);
        this.socialMediaUrlsName = new SimpleObjectProperty<>(new SocialMediaUrl(name));
    }
    /**
     * Creates a copy of the given ReadOnlySocialMedia.
     */
    public SocialMedia(ReadOnlySocialMedia source) {
        this(source.getName());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SocialMedia // instanceof handles nulls
                && this.socialMediaUrlsName.toString().equals(((SocialMedia) other).socialMediaUrlsName
                .toString())); // state check
    }

    @Override
    public int hashCode() {
        return socialMediaUrlsName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return getAsText();
    }


    @Override
    public ObjectProperty<SocialMediaUrl> nameProperty() {
        return socialMediaUrlsName;
    }

    @Override
    public SocialMediaUrl getName() {
        return socialMediaUrlsName.get();
    }

    public void setSocialMediaUrl(SocialMediaUrl name) {
        this.socialMediaUrlsName.set(requireNonNull(name));
    }

}
