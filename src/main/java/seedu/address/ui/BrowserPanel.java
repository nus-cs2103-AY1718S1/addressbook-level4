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
import seedu.address.commons.events.ui.ShowFacebookRequestEvent;
import seedu.address.commons.events.ui.ShowLocationRequestEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";

    //@@author LeeYingZheng
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";
    //@@author

    //@@author taojiashu
    public static final String FACEBOOK_SEARCH_URL = "https://m.facebook.com/search/people/?q=";

    public static final String MAPS_SEARCH_URL = "https://www.google.com.sg/maps/search/";
    //@@author

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

    //@@author taojiashu
    public void loadLocationPage(String address) {
        loadPage(MAPS_SEARCH_URL + address);
    }

    public void loadFacebookPage(String name) {
        loadPage(FACEBOOK_SEARCH_URL + name);
    }
    //@@author

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * reset SelectCommand.isInvoked to allow any subsequent web commands to be executed
     */


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

    //@@author taojiashu
    @Subscribe
    private void handleLocationRequest(ShowLocationRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadLocationPage(event.getAddress());
    }

    @Subscribe
    private void handleFacebookRequest(ShowFacebookRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadFacebookPage(event.getName());
    }
    //@@author
}
