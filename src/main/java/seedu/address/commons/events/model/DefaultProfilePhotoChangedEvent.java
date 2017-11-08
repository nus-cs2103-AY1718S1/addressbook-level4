package seedu.address.commons.events.model;

/**
 * Indicates the User Preference has changed
 */
public class DefaultProfilePhotoChangedEvent extends PrefChangedEvent {

    public DefaultProfilePhotoChangedEvent(String oldValue, String newValue) {
        super("DefaultProfilePhoto", oldValue, newValue);
    }
}
