package seedu.address.ui;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.FaceBookEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ShowPersonAddressEvent;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "https://nusmods.com/timetable/2017-2018/sem1";
    public static final String FACEBOOK_PROFILE_PAGE = "https://m.facebook.com/";
    public static final String NUSMODS_SEARCH_URL_PREFIX = "https://nusmods.com/timetable/2017-2018/sem1?";
    public static final String GOOGLE_MAP_SEARCH_URL_PREFIX = "https://www.google.com.sg/maps/search/";

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
     * The Browser Panel of the App.
     */
    private void loadPersonPage(ReadOnlyPerson person) {

        loadPage(NUSMODS_SEARCH_URL_PREFIX + person.getRemark().getParsedModuleLists());

    }

    /**
     * Method for load page.
     */
    public void loadPage(String url) {

        Platform.runLater(() -> browser.getEngine().load(url));

    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        //URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        try {

            URL defaultPage = new URL(DEFAULT_PAGE);
            loadPage(defaultPage.toExternalForm());

        } catch (MalformedURLException e) {

            logger.info("Invalid URL");

        }

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
    private void handleShowPersonAddressEvent(ShowPersonAddressEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPage(GOOGLE_MAP_SEARCH_URL_PREFIX + event.getAddress());
    }

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
