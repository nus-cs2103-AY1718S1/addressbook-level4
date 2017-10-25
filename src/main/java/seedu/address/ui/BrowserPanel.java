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
import seedu.address.commons.events.ui.BrowserPanelFindRouteEvent;
import seedu.address.commons.events.ui.BrowserPanelShowLocationEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";
    public static final String GOOGLE_MAP_SEARCH_URL_PREFIX = "https://www.google.com.sg/maps/place/";
    public static final String GOOGLE_MAP_SEARCH_URL_SUFFIX = "/";
    public static final String GOOGLE_MAP_DIRECTION_URL_PREFIX = "https://www.google.com.sg/maps/dir/";

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

    private void loadPersonPage(ReadOnlyPerson person) {
        loadPage(GOOGLE_SEARCH_URL_PREFIX + person.getName().fullName.replaceAll(" ", "+")
                + GOOGLE_SEARCH_URL_SUFFIX);
    }

    /**
     * Loads the google map page on the browser specifying the location of the person selected.
     * @param person the person whose location is to be shown on the map
     */
    public void loadLocationPage(ReadOnlyPerson person) {
        loadPage(GOOGLE_MAP_SEARCH_URL_PREFIX + person.getAddress().toString().replaceAll(" ", "+")
                + GOOGLE_MAP_SEARCH_URL_SUFFIX);
    }

    /**
     * Loads the google map page on the browser specifying the direction from current location to
     * the selected person's address.
     * @param person whose location is the destination
     * @param address starting location
     */
    public void loadRoutePage(ReadOnlyPerson person, String address) {
        String startLocation = address.trim().replaceAll(" ", "+");
        String endLocation = person.getAddress().toString().trim().replaceAll(" ", "+");
        loadPage(GOOGLE_MAP_DIRECTION_URL_PREFIX + startLocation
                + GOOGLE_MAP_SEARCH_URL_SUFFIX + endLocation
                + GOOGLE_MAP_SEARCH_URL_SUFFIX);
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
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }

    @Subscribe
    private void handleBrowserPanelShowLocationEvent(BrowserPanelShowLocationEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadLocationPage(event.getNewSelection());
    }

    @Subscribe
    private  void handleBrowserPanelFindRouteEvent(BrowserPanelFindRouteEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadRoutePage(event.getSelectedPerson(), event.getAddress());
    }
}
