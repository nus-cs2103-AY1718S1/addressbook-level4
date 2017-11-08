//@@author fustilio
package seedu.address.model;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToTabRequestEvent;

/**
 * SelectionListener listens for events that select a parcel card.
 */
public class ModelListener {

    private Model model = null;

    /**
     * Initializes a SelectionLister with the given model.
     */
    public ModelListener(Model model) {
        this.model = model;
        registerAsAnEventHandler(this);
    }

    /**
     * Registers the object as an event handler at the {@link EventsCenter}
     * @param handler usually {@code this}
     */
    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    /**
     * Triggers when there is a JumpToTabRequestEvent and sets the tabIndex in the model
     * to keep track of which tab the model is "on".
     */
    @Subscribe
    private void handleJumpToTabEvent(JumpToTabRequestEvent event) {
        model.setTabIndex(Index.fromZeroBased(event.targetIndex));
    }
}
//@@author
