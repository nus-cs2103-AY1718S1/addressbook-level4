package seedu.address.commons.events.external;

import seedu.address.commons.events.BaseEvent;

//@@author ReneeSeet

/**
 * Indicates a request for Sending of Emails
 */

public class SendEmailRequestEvent extends BaseEvent {

    private String subject;
    private String message;
    private String[] recipients;

    public SendEmailRequestEvent(String subject, String message, String[] recipients) {
        this.subject = subject;
        this.message = message;
        this.recipients = recipients;
    }

    public String getSubject() {
        return subject;
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
