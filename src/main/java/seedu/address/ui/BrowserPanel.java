package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BrowserPanelLocateEvent;
import seedu.address.commons.events.ui.PersonFacebookOpenEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.SearchMajorEvent;
import seedu.address.commons.events.ui.SearchNameEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String GOOGLE_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    public static final String GOOGLE_URL_SUFFIX = "&cad=h";

    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h&dg=dbrw&newdg=1";
    public static final String GOOGLE_MAP_URL_PREFIX = "https://www.google.com/maps/search/?api=1&query=";
    public static final String GOOGLE_MAP_DIR_URL_PREFIX = "https://www.google.com.sg/maps/dir/";
    public static final String GOOGLE_MAP_URL_SUFFIX = "/";
    public static final String GOOGLE_MAP_URL_END = "?dg=dbrw&newdg=1";
    public static final String FACEBOOK_PREFIX = "https://m.facebook.com/";
    public static final String DEFAULT_PAGE = "http://www.nus.edu.sg/";
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
        loadPage(GOOGLE_MAP_URL_PREFIX
                + StringUtil.partiallyEncode(person.getAddress().value)
                    + GOOGLE_SEARCH_URL_SUFFIX);
    }

    //@@author majunting
    /**
     * Loads google map url with a search of direction from start address to end address
     * @param start start address
     * @param end end address
     */
    private void loadLocatePage(String start, String end) {
        loadPage(GOOGLE_MAP_DIR_URL_PREFIX
                + StringUtil.partiallyEncode(start) + GOOGLE_MAP_URL_SUFFIX
                + StringUtil.partiallyEncode(end) + GOOGLE_MAP_URL_SUFFIX
                + GOOGLE_MAP_URL_END);
    }
    //@@author


    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    //@@author heiseish

    private void loadFacebookPage(ReadOnlyPerson person) {
        loadPage(FACEBOOK_PREFIX + StringUtil.partiallyEncode(person.getFacebook().value));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE);
    }

    public void googleSearch(String url) {
        loadPage(GOOGLE_URL_PREFIX + StringUtil.partiallyEncode(url) + GOOGLE_URL_SUFFIX);
    }
    //@@author
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

    //@@author majunting
    @Subscribe
    private void handleBrowserPanelLocateEvent(BrowserPanelLocateEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadLocatePage(event.getStartAddress(), event.getEndAddress());
    }
    //@@author heiseish
    @Subscribe
    private void handlePersonFacebookOpenEvent(PersonFacebookOpenEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadFacebookPage(event.getNewSelection());
    }

    @Subscribe
    private void handleSearchNameEvent(SearchNameEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        googleSearch(event.getName());
    }

    @Subscribe
    private void handleSearchMajorEvent(SearchMajorEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        googleSearch("NUS " + event.getMajor());
    }
    //@@author

}
