//@@author tbhbhbh
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.UserName;

/**
 * Represents a change in the browser to view the person's social media profile, whether it is Facebook/Instagram.
 */
public class SocialRequestEvent extends BaseEvent {

    private final UserName userName;
    private final String socialMediaLink;

    public SocialRequestEvent(UserName userName, String socialMediaLink) {
        this.userName = userName;
        this.socialMediaLink = socialMediaLink;
    }

    public UserName getUserName() {
        return userName;
    }

    public String getSocialMediaLink() {
        return socialMediaLink;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
