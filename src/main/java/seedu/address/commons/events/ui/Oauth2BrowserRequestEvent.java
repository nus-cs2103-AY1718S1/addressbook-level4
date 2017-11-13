package seedu.address.commons.events.ui;

import java.io.IOException;

import seedu.address.commons.events.BaseEvent;

//@@author philemontan
/**
 * This event is used by all Oauth2Command implementations to trigger the BrowserPanel authentication process
 */
public class Oauth2BrowserRequestEvent extends BaseEvent {
    public static final String INVALID_INPUT_MESSAGE = "This event must be created with a COMMAND_TYPE";
    private final String commandType;
    private final String requestUrl;

    public Oauth2BrowserRequestEvent(String inputType, String inputRequestUrl) throws IOException {
        if (inputType == null) {
            throw new IOException(INVALID_INPUT_MESSAGE);
        }
        this.requestUrl = inputRequestUrl;
        this.commandType = inputType;
    }

    public Oauth2BrowserRequestEvent() throws IOException {
        throw new IOException(INVALID_INPUT_MESSAGE);
    }

    public String getCommandType() {
        return this.commandType;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
