//@@author sebtsh
package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.EventPanelSelectionChangedEvent;
import seedu.address.logic.Logic;
import seedu.address.model.event.ReadOnlyEvent;

/**
 * The main panel of the app that displays all the details of an event in the address book.
 */
public class EventPanel extends UiPart<Region> {
    private static final String FXML = "EventPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private ReadOnlyEvent storedEvent;
    private Index storedEventIndex;
    private Logic logic;

    @FXML
    private Label nameLabel;

    @FXML
    private Label timeslotLabel;

    @FXML
    private Label descriptionLabel;


    public EventPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        registerAsAnEventHandler(this);
    }

    /**
     * Shows the details of the event selected. Called by the handleEventPanelSelectionChangedEvent event listener,
     * as well as the handleAddressBookChanged event listener.
     * @param event
     */
    private void showEventDetails(Index index, ReadOnlyEvent event) {
        nameLabel.setText(event.getTitle().toString());
        timeslotLabel.setText(event.getTimeslot().toString());
        descriptionLabel.setText(event.getDescription().toString());
    }

    /**
     * Calls showEventDetails when an event is selected in the event panel, causing their details to be displayed
     * in the main window.
     * @param event
     */
    @Subscribe
    private void handleEventPanelSelectionChangedEvent(EventPanelSelectionChangedEvent event) {
        Index index = event.getNewSelection().getIndex();
        ReadOnlyEvent onlyEvent = event.getNewSelection().event;
        storedEvent = onlyEvent;
        storedEventIndex = index;
        logger.info("Event Panel: stored index becomes " + index
                .getZeroBased());

        logger.info(LogsCenter.getEventHandlingLogMessage
                (event) + " for index " + index.getZeroBased());
        showEventDetails(index, onlyEvent);
    }

    /**
     * Calls showEventDetails when the address book is changed. This results in any edits to the currently displayed
     * event being refreshed immediately, instead of the user having to click away and click back to see the changes.
     * @param event
     */
    @Subscribe
    private void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        for (ReadOnlyEvent dataEvent : event.data.getEventList()) {
            if (storedEvent.getTitle().equals(dataEvent.getTitle())) {
                showEventDetails(storedEventIndex, dataEvent);
                storedEvent = dataEvent;
                break;
            }
        }
    }
}
