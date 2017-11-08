package seedu.address.ui;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.util.FxViewUtil;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    public static final String SYNC_STATUS_INITIAL = "Not updated yet in this session";
    public static final String SYNC_STATUS_UPDATED = "Last Updated: %s";
    public static final String SYNC_NUMBER_STATUS = "%d person(s) total and %d task(s) total";
    /**
     * Used to generate time stamps.
     * <p>
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
    private StatusBar saveLocationStatus;
    @FXML
    private StatusBar numberStatus;
    @FXML
    private StatusBar timeStatus;

    public StatusBarFooter(String saveLocation, int totalPerson, int totalTask) {
        super(FXML);
        setSyncStatus(SYNC_STATUS_INITIAL);
        setSaveLocation("./" + saveLocation);
        registerAsAnEventHandler(this);
        numberStatus.setText(String.format(SYNC_NUMBER_STATUS, totalPerson, totalTask));
        setTimeStatus();
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

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus(String.format(SYNC_STATUS_UPDATED, lastUpdated));
        numberStatus.setText(String.format(SYNC_NUMBER_STATUS, abce.data.getPersonList().size(),
                abce.data.getTaskList().size()));
    }

    private void setTimeStatus() {
        long now = clock.millis();
        Label timeStatusLabel = new TimeClock();
        FxViewUtil.applyAnchorBoundaryParameters(timeStatusLabel, 0.0, 0.0, 0.0, 0.0);
        timeStatus.setGraphic(timeStatusLabel);
        timeStatusLabel.setFont(Font.font("Verdana", FontPosture.ITALIC, 10));
    }
}

//@@author deep4k
/**
 * Current clock and date display in statusfootbar
 */
class TimeClock extends Label {

    private static final String DATE_TIME_PATTERN = "HH:mm, EEE d MMM yyyy";

    public TimeClock() {
        getTime();
    }

    private void getTime() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), actionEvent -> {
            Calendar time = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_PATTERN);
            setText(simpleDateFormat.format(time.getTime()));
            setTextFill(Color.web("#ffffff"));
        }), new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
