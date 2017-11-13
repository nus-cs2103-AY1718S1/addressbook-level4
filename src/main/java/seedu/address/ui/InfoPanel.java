package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.PersonAddressDisplayDirectionsEvent;
import seedu.address.commons.events.model.PersonAddressDisplayMapEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.PersonSelectedEvent;
import seedu.address.commons.events.ui.TimetableDisplayEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.timetable.Timetable;

//@@author zacharytang
/**
 * Container for both browser panel and person information panel
 */
public class InfoPanel extends UiPart<Region> {

    private static final String FXML = "InfoPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private BrowserPanel browserPanel;
    private TimetableDisplay timetableDisplay;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane timetablePlaceholder;

    public InfoPanel() {
        super(FXML);

        browserPanel = new BrowserPanel();
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        timetableDisplay = new TimetableDisplay(null);
        timetablePlaceholder.getChildren().add(timetableDisplay.getRoot());

        timetablePlaceholder.toFront();
        registerAsAnEventHandler(this);
    }

    public void freeResources() {
        browserPanel.freeResources();
    }

    @Subscribe
    public void handlePersonPanelSelectionChangeEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        timetablePlaceholder.getChildren().removeAll();

        ArrayList<Timetable> timetableToDisplay = new ArrayList<>();
        timetableToDisplay.add(event.getNewSelection().person.getTimetable());
        timetableDisplay = new TimetableDisplay(timetableToDisplay);
        timetablePlaceholder.getChildren().add(timetableDisplay.getRoot());

        timetablePlaceholder.toFront();
    }

    @Subscribe
    public void handlePersonSelectedEvent(PersonSelectedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        timetablePlaceholder.getChildren().removeAll();

        ArrayList<Timetable> timetableToDisplay = new ArrayList<>();
        timetableToDisplay.add(event.person.getTimetable());
        timetableDisplay = new TimetableDisplay(timetableToDisplay);
        timetablePlaceholder.getChildren().add(timetableDisplay.getRoot());

        timetablePlaceholder.toFront();
    }

    @Subscribe
    private void handleTimetableDisplayEvent(TimetableDisplayEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        ArrayList<Timetable> timetablesToDisplay = new ArrayList<>();

        for (ReadOnlyPerson person : event.personsToDisplay) {
            timetablesToDisplay.add(person.getTimetable());
        }

        timetablePlaceholder.getChildren().removeAll();
        timetableDisplay = new TimetableDisplay(timetablesToDisplay);
        timetablePlaceholder.getChildren().add(timetableDisplay.getRoot());

        timetablePlaceholder.toFront();
    }

    @Subscribe
    public void handlePersonAddressDisplayDirectionsEvent(PersonAddressDisplayDirectionsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        browserPlaceholder.toFront();
    }

    @Subscribe
    public void handlePersonAddressDisplayMapEvent(PersonAddressDisplayMapEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        browserPlaceholder.toFront();
    }
}
