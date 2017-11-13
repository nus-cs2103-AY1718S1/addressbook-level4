package seedu.address.commons.events.logic;

import seedu.address.commons.events.BaseEvent;

//@@author philemontan
/**
 * An event fired indicating a successful GoogleCommand authentication
 * Attributes String commandType & String authCode are used to store information on the GoogleCommand subclass
 * firing this event, and the authentication token extracted from the URL, respectively
 */
public class GoogleAuthenticationSuccessEvent extends BaseEvent {
    private String commandType;
    private String authCode;

    public GoogleAuthenticationSuccessEvent(String inputCommandType, String inputAuthCode) {
        commandType = inputCommandType;
        authCode = inputAuthCode;
    }

    public String getCommandType() {
        return commandType;
    }
    public String getAuthCode () {
        return authCode;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
