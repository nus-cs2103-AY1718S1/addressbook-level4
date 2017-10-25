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
import seedu.address.commons.events.ui.WebsiteSelectionRequestEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";
    public static final String MAPS_SEARCH_URL_PREFIX = "https://www.google.com.sg/maps/search/";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private ReadOnlyPerson selectedPerson;

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

    private void loadPersonAddress(ReadOnlyPerson person) {
        loadPage(MAPS_SEARCH_URL_PREFIX + person.getAddress().toString().replaceAll(" ", "+"));
    }

    /**
     * Loads the selected Person's others page.
     */
    private void loadPersonPersonal(ReadOnlyPerson selectedPerson) {
        selectedPerson.getWebLinks().forEach(webLink -> {
            if (webLink.webLinkTag.equals("others")) {
                loadPage(webLink.webLinkInput);
                return;
            }
        });
    }

    /**
     * Loads the selected Person's social page.
     */
    private void loadPersonSocial(ReadOnlyPerson selectedPerson, String websiteRequested) {
        selectedPerson.getWebLinks().forEach(webLink -> {
            if (websiteRequested.toLowerCase() == webLink.webLinkTag.trim().toLowerCase()) {
                loadPage(webLink.webLinkInput);
                return;
            }
        });
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
        selectedPerson = event.getNewSelection().person;
        loadPersonPage(selectedPerson);
    }

    /**
     * Called when the user clicks on the button bar buttons or via the "web" command.
     */
    @Subscribe
    private void handleWebsiteSelectionEvent(WebsiteSelectionRequestEvent event) {
        switch (event.getWebsiteRequested()) {
        case "mapsView":
            loadPersonAddress(selectedPerson);
            break;
        case "searchView":
            loadPersonPage(selectedPerson);
            break;
        case "othersView":
            loadPersonPersonal(selectedPerson);
            break;
        default:
            loadPersonSocial(selectedPerson, event.getWebsiteRequested());
            break;
        }
    }
}
