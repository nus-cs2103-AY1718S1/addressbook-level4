package seedu.address.commons.events.external;

import seedu.address.commons.events.BaseEvent;

//@@author zameschua
/**
 * Indicates a request for sending an SMS
 */
public class SendSmsRequestEvent extends BaseEvent {

    private String message;
    private String[] recipients;

    public SendSmsRequestEvent(String message, String[] recipients) {
        this.message = message;
        this.recipients = recipients;
    }

    public String getMessage() {
        return message;
    }

    public String[] getRecipients() {
        return recipients;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
