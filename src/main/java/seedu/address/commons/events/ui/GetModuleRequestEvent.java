//@@author jshoung
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the module page.
 */
public class GetModuleRequestEvent extends BaseEvent {
    public final String module;

    public GetModuleRequestEvent(String module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
