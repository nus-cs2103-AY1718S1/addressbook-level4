package seedu.address.ui;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ClearBrowserPanelEvent;
import seedu.address.commons.events.ui.FaceBookEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ShowPersonAddressEvent;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFUALT_PAGE_OF_BROWSER = "default.html";
    public static final String DEFAULT_PAGE = "https://nusmods.com/timetable/2017-2018/sem1";
    public static final String FACEBOOK_PROFILE_PAGE = "https://m.facebook.com/";
    public static final String NUSMODS_SEARCH_URL_PREFIX = "https://nusmods.com/timetable/2017-2018/sem1?";
    public static final String GOOGLE_MAP_SEARCH_URL_PREFIX = "https://www.google.com.sg/maps/search/";

    private static final String FXML = "BrowserPanel.fxml";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 5.1; rv:7.0.1) Gecko/20100101 Firefox/7.0.1";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private WebEngine engine;

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        setUpWebEngine();

        loadDefaultPage();

        registerAsAnEventHandler(this);
    }

    /**
     * Set up web engine
     */
    private void setUpWebEngine() {
        engine = browser.getEngine();
        engine.setUserAgent(USER_AGENT);
    }

    /**
     * The Browser Panel of the App.
     */
    private void loadPersonPage(ReadOnlyPerson person) {

        loadPage(NUSMODS_SEARCH_URL_PREFIX + person.getRemark().getParsedModuleLists());

    }

    /**
     * Method for load page.
     */
    public void loadPage(String url) {

        engine.load(url);

    }

    //@@author RonakLakhotia
    /**
     * Loads a default HTML file with a background that matches the general theme when the clear command is executed.
     */
    private void loadDefaultPage() {
        try {

            URL defaultPage = new URL(DEFAULT_PAGE);
            loadPage(defaultPage.toExternalForm());

        } catch (MalformedURLException e) {
            logger.info("Invalid URL");
        }

    }

    private void loadDeafultPageBrowser() {

        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFUALT_PAGE_OF_BROWSER);
        loadPage(defaultPage.toExternalForm());
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
    //@@author RonakLakhotia
    @Subscribe
    private void handleClearCommandExecutionEvent (ClearBrowserPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadDeafultPageBrowser();
    }
    //@@author
    @Subscribe
    private void handleShowPersonAddressEvent(ShowPersonAddressEvent event) {
        loadPage(GOOGLE_MAP_SEARCH_URL_PREFIX + event.getAddress());
    }

    //@@author RonakLakhotia
    /**
     * Shows Facebook profile picture of user
     */
    public void loadPersonFaceBookPage(ReadOnlyPerson person) throws ParseException {

        String url = FACEBOOK_PROFILE_PAGE + person.getUsername().toString();
        loadPage(url);

    }

    @Subscribe
    public void handleFaceBookEvent(FaceBookEvent event) throws ParseException {

        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        loadPersonFaceBookPage(event.getPerson());
    }
}
