package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import seedu.address.commons.core.LogsCenter;

import seedu.address.commons.events.ui.EventPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.EventPanelUnselectEvent;

import seedu.address.logic.Logic;
import seedu.address.model.event.ReadOnlyEvent;

//@@author DarrenCzen
/**
 * The Details Panel of the App that displays full information of a {@code Event}.
 */
public class EventsDetailsPanel extends UiPart<Region> {

    private static final String FXML = "EventsDetailsPanel.fxml";
    private static final String PREFIX_ADDRESS_FIELD = "Address: ";
    private static final String PREFIX_DATE_FIELD = "Date: ";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Logic logic;


    @FXML
    private Pane pane;
    @FXML
    private Label name;
    @FXML
    private Text addressField;
    @FXML
    private Label address;
    @FXML
    private Text dateField;
    @FXML
    private Label date;

    public EventsDetailsPanel() {
        super(FXML);
        this.logic = logic;
        loadBlankPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Loads the full info of the Event
     * @param event the selected event to display the full info of.
     */
    public void loadEventInfo(ReadOnlyEvent event) {
        addressField.setText(PREFIX_ADDRESS_FIELD);
        dateField.setText(PREFIX_DATE_FIELD);
        bindListeners(event);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Event} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyEvent event) {
        name.textProperty().bind(Bindings.convert(event.nameProperty()));
        address.textProperty().bind(Bindings.convert(event.addressProperty()));
        date.textProperty().bind(Bindings.convert(event.dateProperty()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventsDetailsPanel)) {
            return false;
        }

        EventsDetailsPanel eventsDetailsPanel = (EventsDetailsPanel) other;
        return name.getText().equals(eventsDetailsPanel.name.getText())
                && address.getText().equals(eventsDetailsPanel.address.getText())
                && date.getText().equals(eventsDetailsPanel.date.getText());
    }

    /**
     * Sets all info fields to not display anything when the app is just started.
     */
    public void loadBlankPage() {
        Label label;
        Text text;
        for (Node node: pane.getChildren()) {
            if (node instanceof Label) {
                label = (Label) node;
                label.setText("");
            } else if (node instanceof Text) {
                text = (Text) node;
                text.setText("");
            } else if (node instanceof TextFlow) {
                for (Node subNode: ((TextFlow) node).getChildren()) {
                    if (subNode instanceof Text) {
                        text = (Text) subNode;
                        text.setText("");
                    }
                    if (subNode instanceof Label) {
                        label = (Label) subNode;
                        label.setText("");
                    }
                }
            }
        }
    }

    @Subscribe
    private void handleEventPanelSelectionChangedEvent(EventPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadEventInfo(event.getNewSelection().event);
    }

    @Subscribe
    private void handleUnselectOfEventCardEvent(EventPanelUnselectEvent event) {
        unregisterAsAnEventHandler(this);
    }
}
