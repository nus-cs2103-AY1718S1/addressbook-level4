package seedu.address.ui;

import java.time.Clock;
import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {
    public static final String SYNC_STATUS_INITIAL = "Not updated yet in this session";
    public static final String SYNC_STATUS_UPDATED = "Last Updated: %s";
    public static final String SYNC_TOTAL_PERSONS = "%d person(s) total";
    public static final String SYNC_TOTAL_EVENTS = "%d event(s) total";

    /**
     * Used to generate time stamps.
     *
     * TODO: change clock to an instance variable.
     * We leave it as a static variable because manual dependency injection
     * will require passing down the clock reference all the way from MainApp,
     * but it should be easier once we have factories/DI frameworks.
     */
    private static Clock clock = Clock.systemDefaultZone();

    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private StatusBar syncStatus;
    @FXML
    private StatusBar totalPersons;
    @FXML
    private StatusBar totalEvents;
    @FXML
    private StatusBar saveLocationStatus;


    public StatusBarFooter(String saveLocation, int totalPersons, int totalEvents) {
        super(FXML);
        setSyncStatus(SYNC_STATUS_INITIAL);
        setSaveLocation("./" + saveLocation);
        registerAsAnEventHandler(this);
        setTotalPersons(totalPersons);
        setTotalEvents(totalEvents);
    }

    /**
     * Sets the clock used to determine the current time.
     */
    public static void setClock(Clock clock) {
        StatusBarFooter.clock = clock;
    }

    /**
     * Returns the clock currently in use.
     */
    public static Clock getClock() {
        return clock;
    }

    private void setSaveLocation(String location) {
        Platform.runLater(() -> this.saveLocationStatus.setText(location));
    }

    private void setSyncStatus(String status) {
        Platform.runLater(() -> this.syncStatus.setText(status));
    }

    private void setTotalPersons(int num) {
        Platform.runLater(() -> this.totalPersons.setText(String.format(SYNC_TOTAL_PERSONS, num)));
    }
    private void setTotalEvents(int num) {
        Platform.runLater(() -> this.totalEvents.setText(String.format(SYNC_TOTAL_EVENTS, num)));
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        String lastUpdated = new Date(clock.millis()).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Setting last updated status to " + lastUpdated));
        setSyncStatus(String.format(SYNC_STATUS_UPDATED, lastUpdated));
        setTotalPersons(event.data.getPersonList().size());
        setTotalEvents(event.data.getEventList().size());
    }
}
