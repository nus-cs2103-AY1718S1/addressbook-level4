package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.PersonCard;

/**
 * Indicates a request for opening nusmods webview
 */
public class OpenNusModsWebViewEvent extends BaseEvent {

    private final PersonCard newSelection;

    public OpenNusModsWebViewEvent(PersonCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PersonCard getNewSelection() {
        return newSelection;
    }
}
