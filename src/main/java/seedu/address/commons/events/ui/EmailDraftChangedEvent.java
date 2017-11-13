package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.email.message.ReadOnlyMessageDraft;

//@@author awarenessxz
/**
 * Indicates that the email draft have changed
 */
public class EmailDraftChangedEvent extends BaseEvent {

    public final ReadOnlyMessageDraft message;

    public EmailDraftChangedEvent(ReadOnlyMessageDraft message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
