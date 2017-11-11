package seedu.address.model.socialmedia;

import javafx.beans.property.ObjectProperty;

/**
 * A read-only immutable interface for a SocialMedia in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlySocialMedia {

    ObjectProperty<SocialMediaUrl> nameProperty();
    SocialMediaUrl getName();

    /**
     * Formats the SocialMedia as text, showing socialMediaUrls name.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" SocialMedia Name: ")
                .append(getName());
        return builder.toString();
    }

}
