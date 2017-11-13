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
import seedu.address.commons.events.ui.LocateCommandEvent;
import seedu.address.commons.events.ui.LocateMrtCommandEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

// @@author derickjw

/**
 * The Map Panel of the App.
 */
public class MapPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String FACEBOOK_SEARCH_URL_PREFIX = "https://www.facebook.com/search/people/?q=";
    public static final String GOOGLE_MAP_URL_PREFIX = "https://www.google.com.sg/maps/search/";
    public static final String GOOGLE_MRT_URL_SUFFIX = "+MRT";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    public MapPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    private void loadPersonPage(ReadOnlyPerson person) {
        loadPage(FACEBOOK_SEARCH_URL_PREFIX + person.getName().fullName.replaceAll(" ", "+"));
    }

    //@@author

    //@@author YuchenHe98
    private void loadLocationPage(ReadOnlyPerson person) {
        loadPage(GOOGLE_MAP_URL_PREFIX + person.getAddress().value.replaceAll(" ", "+"));
    }
    //@@author

    private void loadMrtPage(ReadOnlyPerson person) {
        loadPage(GOOGLE_MAP_URL_PREFIX + person.getMrt().value.replaceAll("", "+")
                + GOOGLE_MRT_URL_SUFFIX);
    }

    //@@author derickjw

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

    //@@author

    //@@author YuchenHe98
    @Subscribe
    private void handleLocateCommandEvent(LocateCommandEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadLocationPage(event.getPerson());
    }
    //@@author

    @Subscribe
    private void handleLocateMrtCommandEvent(LocateMrtCommandEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadMrtPage(event.getPerson());
    }
}
