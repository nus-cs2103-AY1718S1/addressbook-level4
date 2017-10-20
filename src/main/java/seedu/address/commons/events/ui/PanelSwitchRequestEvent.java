package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to switch the panel of the StackPane.
 */
public class PanelSwitchRequestEvent extends BaseEvent {

    public final String wantedPanel;

    public PanelSwitchRequestEvent(String wantedPanel) {
        this.wantedPanel = wantedPanel;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
