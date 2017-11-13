package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.WebsiteSelectionRequestEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author hansiang93

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    //@@author bladerail
    public static final String GOOGLE_SEARCH_CAPTCHA_PREFIX = "https://ipv4.google.com/sorry/index?continue=";
    //@@author
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

        browser.getEngine().setJavaScriptEnabled(true);
        loadDefaultPage();
        registerAsAnEventHandler(this);
        browser.getEngine().getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            logger.info("Loaded this page: " + browser.getEngine().getLocation());
                        }

                    }
                });
    }


    /**
     * Loads the selected Person's Google search by name page.
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        loadPage(GOOGLE_SEARCH_URL_PREFIX + person.getName().fullName.replaceAll(" ", "+")
                + GOOGLE_SEARCH_URL_SUFFIX);
        logger.info("Loading Google search of " + person.getName());
    }

    /**
     * Loads the selected Person's address search via Google Maps search.
     */
    private void loadPersonAddress(ReadOnlyPerson person) {
        loadPage(MAPS_SEARCH_URL_PREFIX + person.getAddress().toString().replaceAll(" ", "+"));
        logger.info("Loading Address search of " + person.getName());
    }

    /**
     * Loads the selected Person's others page.
     */
    private void loadPersonPersonal(ReadOnlyPerson selectedPerson) {
        selectedPerson.getWebLinks().forEach(webLink -> {
            if (webLink.toStringWebLinkTag().equals("others")) {
                loadPage(webLink.toStringWebLink());
                logger.info("Loading Personal Page of " + selectedPerson.getName());
                return;
            }
        });
    }

    /**
     * Loads the selected Person's social page.
     */
    private void loadPersonSocial(ReadOnlyPerson selectedPerson, String websiteRequested) {
        selectedPerson.getWebLinks().forEach(webLink -> {
            if (websiteRequested.toLowerCase() == webLink.toStringWebLinkTag().trim().toLowerCase()) {
                loadPage(webLink.toStringWebLink());
                logger.info("Loading " + websiteRequested + " page of " + selectedPerson.getName());
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
        logger.info("Loading Landing Page...");
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
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
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
