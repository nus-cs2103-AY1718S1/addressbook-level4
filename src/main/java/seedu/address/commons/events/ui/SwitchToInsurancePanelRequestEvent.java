package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author RSJunior37
/**
 * Request MainApp to middle panel to Insurance
 */
public class SwitchToInsurancePanelRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
