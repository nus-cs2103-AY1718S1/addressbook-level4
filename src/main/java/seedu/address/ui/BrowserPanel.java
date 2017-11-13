package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PlacePanelSelectionChangedEvent;
import seedu.address.model.place.ReadOnlyPlace;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "http://www.visitsingapore.com/en/";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";

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

    //@@author thanhson16198
    /**
     * Load the url to the `BrowserPanel` in `MainWindow.java`
    */
    private void loadPlacePage(ReadOnlyPlace place) {
        // Check if the website of the location is left blank
        if (place.getWebsite().toString().contains("www.-.com")) {
            loadPage(GOOGLE_SEARCH_URL_PREFIX + place.getName().fullName.replaceAll(" ", "+")
                    + GOOGLE_SEARCH_URL_SUFFIX);
        } else {
            loadPage(place.getWebsite().toString().replaceAll(" ", "+"));
        }
    }
    //@@author

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default webpage for tourists
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE);
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handlePlacePanelSelectionChangedEvent(PlacePanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPlacePage(event.getNewSelection().place);
    }
}
