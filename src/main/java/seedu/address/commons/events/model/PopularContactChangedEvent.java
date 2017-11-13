//@@author tshradheya
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.Model;

/**
 * Indicates change of popular contact list
 */
public class PopularContactChangedEvent extends BaseEvent {

    private Model model;

    public PopularContactChangedEvent(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    @Override
    public String toString() {
        return "Updated Popular Contact List";
    }
}
