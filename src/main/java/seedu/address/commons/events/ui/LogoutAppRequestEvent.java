package seedu.address.commons.events.ui;

import static seedu.address.logic.commands.LogoutCommand.setLogoutStatus;

import seedu.address.commons.events.BaseEvent;

//@@author jelneo
/**
 * Indicates a request for App logout.
 */
public class LogoutAppRequestEvent extends BaseEvent {

    private boolean hasLogoutSuccessfully = false;

    public LogoutAppRequestEvent(boolean hasLogoutSuccessfully) {
        this.hasLogoutSuccessfully = hasLogoutSuccessfully;
        setLogoutStatus(hasLogoutSuccessfully);
    }

    public boolean getLogoutStatus() {
        return hasLogoutSuccessfully;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
