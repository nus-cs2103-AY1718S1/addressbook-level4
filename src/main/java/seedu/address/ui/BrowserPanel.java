package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ShowCalendarRequestEvent;
import seedu.address.model.person.Address;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLE_MAPS_URL_PREFIX = "https://www.google.com/maps/search/?api=1&query=";
    public static final String GOOGLE_MAPS_URL_SUFFIX = "&dg=dbrw&newdg=1";
    private static final String FXML = "BrowserPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());
    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Loads google maps web page locating person's address.
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        Address personAddress = person.getAddress();

        String urlEncodedAddressIntermediate = personAddress.toString().replaceAll("#", "%23");
        String urlEncodedAddressFinal = urlEncodedAddressIntermediate.replaceAll(" ", "+");

        loadPage(GOOGLE_MAPS_URL_PREFIX
                + urlEncodedAddressFinal
                + GOOGLE_MAPS_URL_SUFFIX);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Opens the Calendar window in the browser panel.
     */
    public void loadCalendar() {
        loadPage("https://www.timeanddate.com/calendar/");
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handleSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }

    @Subscribe
    private void handleCalendarRequestEvent(ShowCalendarRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadCalendar();
    }
}
