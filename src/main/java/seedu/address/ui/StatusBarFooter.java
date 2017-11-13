package seedu.address.ui;

import java.io.IOException;
import java.time.Clock;
import java.util.Date;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

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
    private static final String NO_INTERNET_CONNECTION_WARNING = "Weather report available once connect to internet :)";

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
    private StatusBar weatherReport;
    @FXML
    private StatusBar totalPersons;

    //@@author eeching
    public StatusBarFooter(int totalPersons) throws JAXBException, IOException {
        super(FXML);
        setSyncStatus(SYNC_STATUS_INITIAL);
        setTotalPersons(totalPersons);
        setSaveWeather(getWeather());
        registerAsAnEventHandler(this);
    }

    /**
     * Set the total number of person in the current address Book
     */
    private void setTotalPersons(int totalPersons) {
        this.totalPersons.setText(totalPersons + " person(s) in total");
    }
    //@@author
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
    //@@author eeching
    private void setSaveWeather(String weather) {
        Platform.runLater(() -> this.weatherReport.setText(weather));
    }
    //@@author
    private void setSyncStatus(String status) {
        Platform.runLater(() -> this.syncStatus.setText(status));
    }

    //@@author eeching
    private String getWeather() throws JAXBException {
        try {
            WeatherRequest request = new WeatherRequest();
            return request.getSgWeather();
        } catch (IOException e) {
            logger.warning("no internet connection");
            return NO_INTERNET_CONNECTION_WARNING;
        }

    }
    //@@author
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus(String.format(SYNC_STATUS_UPDATED, lastUpdated));
        setTotalPersons(abce.data.getPersonList().size());
    }
}
