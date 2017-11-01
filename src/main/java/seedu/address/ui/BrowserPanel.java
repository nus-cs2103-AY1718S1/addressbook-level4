package seedu.address.ui;

import static seedu.address.ui.BrowserSearchMode.GOOGLE_SEARCH_ADDRESS;
import static seedu.address.ui.BrowserSearchMode.GOOGLE_SEARCH_EMAIL;
import static seedu.address.ui.BrowserSearchMode.GOOGLE_SEARCH_NAME;
import static seedu.address.ui.BrowserSearchMode.GOOGLE_SEARCH_PHONE;

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
import seedu.address.commons.events.ui.ChangeSearchEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";
    public static final String GOOGLE_MAP_URL_PREFIX = "https://www.google.com.sg/maps/place/";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    private int searchMode;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Set default search mode.
        searchMode = GOOGLE_SEARCH_NAME;

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    private void loadPersonPage(ReadOnlyPerson person) {
        loadPage(getUrl(person));
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * @return the url string according to {@code searchMode}.
     */
    public String getUrl(ReadOnlyPerson person) {
        switch (searchMode) {
        case GOOGLE_SEARCH_NAME:
            return GOOGLE_SEARCH_URL_PREFIX + person.getName().fullName.replaceAll(" ", "+")
                    + GOOGLE_SEARCH_URL_SUFFIX;
        case GOOGLE_SEARCH_PHONE:
            return GOOGLE_SEARCH_URL_PREFIX + person.getPhone().value.replaceAll(" ", "+")
                    + GOOGLE_SEARCH_URL_SUFFIX;
        case GOOGLE_SEARCH_EMAIL:
            return GOOGLE_SEARCH_URL_PREFIX + person.getEmail().value.replaceAll(" ", "+")
                    + GOOGLE_SEARCH_URL_SUFFIX;
        case GOOGLE_SEARCH_ADDRESS:
            return GOOGLE_MAP_URL_PREFIX + person.getAddress().value.replaceAll(" ", "+");
        default:
            return GOOGLE_SEARCH_URL_PREFIX + GOOGLE_SEARCH_URL_SUFFIX;
        }
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
    private void handleChangeSearchEvent(ChangeSearchEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        searchMode = event.searchMode;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }
}
