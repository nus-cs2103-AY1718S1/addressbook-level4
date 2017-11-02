package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.logic.commands.LogoutCommand;

//@@author jelneo
/**
 * Indicates a request for App logout.
 */
public class LogoutAppRequestEvent extends BaseEvent {

    private boolean hasLogoutSuccessfully = false;

    public LogoutAppRequestEvent(boolean hasLogoutSuccessfully) {
        this.hasLogoutSuccessfully = hasLogoutSuccessfully;
        LogoutCommand.setLogoutStatus(hasLogoutSuccessfully);
    }

    public boolean getLogoutStatus() {
        return hasLogoutSuccessfully;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
