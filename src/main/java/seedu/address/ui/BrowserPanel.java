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
import seedu.address.commons.events.ui.AccessLocationRequestEvent;
import seedu.address.commons.events.ui.AccessWebsiteRequestEvent;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String DEFAULT_LIGHT_PAGE = "defaultLight.html";
    public static final String DARK_THEME_PAGE = "DarkTheme.css";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/maps?safe=off&q=";
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

    /**
     * Access website through browser panel based on person's link
     * @param website
     */
    public void handleWebsiteAccess(String website) {
        loadPage(website);
    }

    private void loadPersonLocation(String location) {
        loadPage(GOOGLE_SEARCH_URL_PREFIX + location.replaceAll(" ", "+")
                + GOOGLE_SEARCH_URL_SUFFIX);
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
     * Sets the default HTML file based on the current theme.
     */
    public void setDefaultPage(String currentTheme) {
        URL defaultPage;
        if (currentTheme.contains(DARK_THEME_PAGE)) {
            defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        } else {
            defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_LIGHT_PAGE);
        }
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handleAccessWebsiteEvent(AccessWebsiteRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleWebsiteAccess(event.website);
    }

    @Subscribe
    private void handleAccessLocationEvent(AccessLocationRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonLocation(event.location);
    }
}
