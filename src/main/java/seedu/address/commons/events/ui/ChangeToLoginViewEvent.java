package seedu.address.commons.events.ui;

import static seedu.address.ui.LoginView.setShowingLoginView;

import seedu.address.commons.events.BaseEvent;

//@@author jelneo
/**
 * Indicates a request to display login text fields.
 */
public class ChangeToLoginViewEvent extends BaseEvent {

    public ChangeToLoginViewEvent () {
        setShowingLoginView(true);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
