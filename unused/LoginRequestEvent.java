package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author ReneeSeet

/**
 * LoginRequestEvent will be handled by the UIManager.
 * Reason why this was unused: I dedcided to remove the login page as it was not good
 * to store passsword and username and it caused coupling with the other features.
 */

/**
 * Indicates a request to Login after entering valid email and password
 */

public class LoginRequestEvent extends BaseEvent {

    public LoginRequestEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
