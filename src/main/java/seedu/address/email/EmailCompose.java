package seedu.address.email;

import seedu.address.email.message.DraftList;
import seedu.address.email.message.MessageDraft;
import seedu.address.email.message.ReadOnlyMessageDraft;

//@@author awarenessxz
/**
 * Holds a list of messageDraft
 * Handles how messageDraft are created and edited
 */
public class EmailCompose {
    private DraftList emailDraftsList;

    /** Creates an EmailCompose with an empty draft list **/
    public EmailCompose() {
        emailDraftsList = new DraftList();
    }

    /**
     * Compose an Email or edit the current one
     *
     * @param message
     */
    public void composeEmail(MessageDraft message) {
        emailDraftsList.composeEmail(message);
    }

    public ReadOnlyMessageDraft getMessage() {
        return emailDraftsList.getMessage(0);
    }

    /**
     * Resets the existing data of this {@code emailDraftsList} with an empty draft list
     */
    public void resetData() {
        emailDraftsList = new DraftList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCompose // instanceof handles nulls
                && this.emailDraftsList.equals(((EmailCompose) other).emailDraftsList));
    }
}
