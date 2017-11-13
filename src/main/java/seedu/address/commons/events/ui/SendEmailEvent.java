//@@author srishag
package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * Event raised on SendEmail command's successful execution
 */
public class SendEmailEvent extends BaseEvent {

    public final Index recipient;
    public final String subject;
    public final String body;

    public SendEmailEvent(Index recipient, String subject, String body) {
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

