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
import seedu.address.commons.events.ui.BrowserPanelLocateEvent;
import seedu.address.commons.events.ui.BrowserPanelNavigateEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";
    public static final String PRIVATE_NAME_CANNOT_SEARCH = "Cannot perform a search on that person. "
            + "Their name is private.";
    public static final String GOOGLE_MAPS_URL_PREFIX = "https://www.google.com.sg/maps/search/";
    public static final String GOOGLE_MAPS_URL_SUFFIX  = "/";
    public static final String PRIVATE_ADDRESS_CANNOT_SEARCH = "Cannot perform a search on that person's address. "
            + "Their address is private.";
    public static final String GOOGLE_MAPS_DIRECTIONS_PREFIX = "https://www.google.com.sg/maps/dir/?api=1";
    public static final String GOOGLE_MAPS_DIRECTIONS_SUFFIX  = "/";

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
     * Loads a google search for a person's name if their name is not private
     * Prints out a message on the result display otherwise
     * @param person The person we want to search for
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        if (person.getName().getIsPrivate()) {
            raise(new NewResultAvailableEvent(PRIVATE_NAME_CANNOT_SEARCH));
        } else {
            loadPage(GOOGLE_SEARCH_URL_PREFIX + person.getName().value.replaceAll(" ", "+")
                    + GOOGLE_SEARCH_URL_SUFFIX);
        }
    }

    //@@author jeffreygohkw
    /**
     * Loads a google search for a person's address if their address is not private
     * Prints out a message on the result display otherwise
     * @param person The person's address we want to search for
     */
    private void loadMapsPage(ReadOnlyPerson person) {
        if (person.getAddress().getIsPrivate()) {
            raise(new NewResultAvailableEvent(PRIVATE_ADDRESS_CANNOT_SEARCH));
        } else {
            loadPage(GOOGLE_MAPS_URL_PREFIX + person.getAddress().toString().replaceAll(" ", "+")
                + GOOGLE_MAPS_URL_SUFFIX);
        }
    }

    /**
     * Loads Google Maps with directions on how to go from one location to another
     * @param fromLocation The location we want to start from
     * @param toLocation The location we want to reach
     */
    private void loadDirectionsPage(String fromLocation, String toLocation) {
        loadPage(GOOGLE_MAPS_DIRECTIONS_PREFIX + "&origin="
                + fromLocation.replaceAll("#(\\w+)\\s*", "").replaceAll(" ", "+")
                .replaceAll("-(\\w+)\\s*", "")
                + "&destination="
                + toLocation.replaceAll("#(\\w+)\\s*", "").replaceAll(" ", "+")
                .replaceAll("-(\\w+)\\s*", "")
                + GOOGLE_MAPS_DIRECTIONS_SUFFIX);
    }

    //@@author
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



    //@@author jeffreygohkw
    @Subscribe
    private void handleBrowserPanelLocateEvent(BrowserPanelLocateEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadMapsPage(event.getNewSelection());
    }

    @Subscribe
    private void handleBrowserPanelNavigateEvent(BrowserPanelNavigateEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadDirectionsPage(event.getFromLocation().toString(), event.getToLocation().toString());
    }
}
