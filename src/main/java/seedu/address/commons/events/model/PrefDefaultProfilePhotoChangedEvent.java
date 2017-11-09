package seedu.address.commons.events.model;

/**
 * Indicates the User Preference has changed
 */
public class PrefDefaultProfilePhotoChangedEvent extends PrefChangedEvent {

    public PrefDefaultProfilePhotoChangedEvent(String oldValue, String newValue) {
        super("DefaultProfilePhoto", oldValue, newValue);
    }
}
