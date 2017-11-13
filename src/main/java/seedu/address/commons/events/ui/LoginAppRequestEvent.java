package seedu.address.commons.events.ui;

import static seedu.address.logic.commands.LoginCommand.setLoginStatus;

import seedu.address.commons.events.BaseEvent;

//@@author jelneo
/**
 * Indicates a request for App login.
 */
public class LoginAppRequestEvent extends BaseEvent {

    private boolean hasLoginSuccessfully = false;

    public LoginAppRequestEvent(boolean hasLoginSuccessfully) {
        this.hasLoginSuccessfully = hasLoginSuccessfully;
        setLoginStatus(hasLoginSuccessfully);
    }

    public boolean getLoginStatus() {
        return hasLoginSuccessfully;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
