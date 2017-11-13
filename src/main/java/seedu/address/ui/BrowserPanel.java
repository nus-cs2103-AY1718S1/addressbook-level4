package seedu.address.ui;

import static seedu.address.logic.commands.SocialCommand.INSTAGRAM_URL_PREFIX;
import static seedu.address.logic.commands.SocialCommand.TWITTER_URL_PREFIX;

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
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";

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

        // Allows Google sign in to work in WebView as
        // Same-Origin Policy would prevent it otherwise
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
    }
    //@@author tbhbhbh
    /**
     * Loads the person's Instagram or Twitter depending on if the person has his/her social media fields filled in.
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        if (!person.getInstagramName().toString().isEmpty()) {
            loadPage(INSTAGRAM_URL_PREFIX + person.getInstagramName());
        } else if (!person.getTwitterName().toString().isEmpty()) {
            loadPage(TWITTER_URL_PREFIX + person.getTwitterName());
        }
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
    }
}
