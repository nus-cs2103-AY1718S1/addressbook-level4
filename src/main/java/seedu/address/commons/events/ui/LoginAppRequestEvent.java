package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.logic.commands.LoginCommand;

//@@author jelneo
/**
 * Indicates a request for App login.
 */
public class LoginAppRequestEvent extends BaseEvent {

    private boolean hasLoginSuccessfully = false;

    public LoginAppRequestEvent(boolean hasLoginSuccessfully) {
        this.hasLoginSuccessfully = hasLoginSuccessfully;
        LoginCommand.setLoginStatus(hasLoginSuccessfully);
    }

    public boolean getLoginStatus() {
        return hasLoginSuccessfully;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
