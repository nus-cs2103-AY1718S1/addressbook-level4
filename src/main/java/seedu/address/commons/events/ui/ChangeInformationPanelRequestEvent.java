package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request for Information Panel change
 */
public class ChangeInformationPanelRequestEvent extends BaseEvent {

    private String panelRequestEvent;

    public ChangeInformationPanelRequestEvent(String panelRequestEvent) {
        this.panelRequestEvent = panelRequestEvent;
    }

    public String getPanelRequestEvent() {
        return panelRequestEvent;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
