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
import seedu.address.commons.events.ui.MapToListRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.PersonSideCardRequestEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";
    public static final String GOOGLE_MAP_SEARCH_URL_PREFIX = "https://maps.google.com.sg/?q=";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        browser.setOpacity(0);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Attempts to load a person's homepage, if no person is selected, the default page will load
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        if (person == null) {
            loadDefaultPage();
            browser.setOpacity(0);
        } else {
            loadPage(person.getHomepage().toString());
        }
    }

    //@@author yewshengkai
    private void loadPersonMap(ReadOnlyPerson targetPerson) {
        loadPage(GOOGLE_MAP_SEARCH_URL_PREFIX + targetPerson.getAddress().value
                .replaceAll(",", ""));
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

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
        browser.setOpacity(100);
    }

    //@@author yewshengkai
    @Subscribe
    private void handlePersonSideCardPanelChangedEvent(PersonSideCardRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        browser.setOpacity(100);
        loadPersonPage(event.targetPerson);
    }

    @Subscribe
    private void handlePersonPanelGmapChangedEvent(MapToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonMap(event.targetPerson);
        browser.setOpacity(100);
    }
}
