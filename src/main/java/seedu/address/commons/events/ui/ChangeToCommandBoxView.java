package seedu.address.commons.events.ui;

import static seedu.address.ui.LoginView.setShowingLoginView;

import seedu.address.commons.events.BaseEvent;

//@@author jelneo
/**
 * Indicate request to change to command box view.
 */
public class ChangeToCommandBoxView extends BaseEvent {

    public ChangeToCommandBoxView () {
        setShowingLoginView(false);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
