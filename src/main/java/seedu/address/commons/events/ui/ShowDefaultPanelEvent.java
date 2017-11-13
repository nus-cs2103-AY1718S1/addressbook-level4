//@@author tshradheya
package seedu.address.commons.events.ui;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.BaseEvent;

/**
 * Triggers event to show default panel on the `BrowserAndReminderPanel` of UI
 */
public class ShowDefaultPanelEvent extends BaseEvent {

    private static final Logger logger = LogsCenter.getLogger(ShowDefaultPanelEvent.class);

    @Override
    public String toString() {
        logger.info("Defalut panel will be displayed");
        return this.getClass().getSimpleName();
    }
}
