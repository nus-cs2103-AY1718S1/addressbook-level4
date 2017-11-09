package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ToggleChangedEvent;
import seedu.address.model.person.Address;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com/maps/search/?api=1&query=";
    public static final String SOCIAL_MEDIA_URL_PREFIX = "https://";

    private static Boolean browserMode = true;
    private static final String FXML = "BrowserPanel.fxml";

    private Address lastAddress;
    private String lastUrl;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    @FXML
    private Label field;

    @FXML
    private Label value;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    //@@author seankwekjk
    /**
     * Loads the social media page of the contact selected.
     * @param pers
     */
    private void loadSocialPage(ReadOnlyPerson pers) {
        loadPage(SOCIAL_MEDIA_URL_PREFIX + pers.getSocialMedia());
        setSocial(pers);
        setField("Social Media");
    }

    /**
     * Loads the social media page of the contact selected through last saved url.
     * Meant to be called by ToggleCommand.
     */
    private void loadSocialPage() {
        loadPage(SOCIAL_MEDIA_URL_PREFIX + lastUrl);
        value.setText(lastUrl);
        field.setText("Social Media");
    }

    /**
     * Loads the address of the contact selected and corresponding google maps page.
     * @param pers
     */
    private void loadPersonPage(ReadOnlyPerson pers) {
        loadPage(GOOGLE_SEARCH_URL_PREFIX
                + pers.getAddress().value.replaceAll(" ", "+").replaceAll(",", "%2C"));
        setAddress(pers);
        setField("Address");
    }

    /**
     * Loads the address of the contact selected and corresponding google maps page through last saved address.
     * Meant to be called by ToggleCommand.
     */
    private void loadPersonPage() {
        loadPage(GOOGLE_SEARCH_URL_PREFIX + lastAddress.value.replaceAll(" ", "+")
                .replaceAll(",", "%2C"));
        value.setText(lastAddress.value);
        field.setText("Address");
    }

    /**
    * Set Label on BrowserPanel to address
     */
    private void setAddress(ReadOnlyPerson pers) {
        value.setText(pers.getAddress().value);
    }

    /**
     * Set Label on BrowserPanel to social media url
     */
    private void setSocial(ReadOnlyPerson pers) {
        value.setText(pers.getSocialMedia());
    }

    /**
     * Get Label text on BrowserPanel for testing purposes
     */
    public String getValue() {
        return value.getText();
    }

    /**
     * Set type of field to the BrowserPanel
     */
    private void setField(String type) {
        field.setText(type);
    }

    /**
     * Get Label field on BrowserPanel for testing purposes
     */
    public String getField() {
        return field.getText();
    }

    public static Boolean getBrowserMode() {
        return browserMode;
    }

    public static void setBrowserMode() {
        browserMode = !browserMode;
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }
    //@@author

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

    //@@author seankwekjk
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (browserMode) {
            loadPersonPage(event.getNewSelection().person);
        }   else {
            loadSocialPage(event.getNewSelection().person);
        }
        lastAddress = event.getNewSelection().person.getAddress();
        lastUrl = event.getNewSelection().person.getSocialMedia();
    }

    @Subscribe
    private void handleToggleChangedEvent(ToggleChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (lastAddress == null) {
            return;
        }
        if (browserMode) {
            loadPersonPage();
        }   else {
            loadSocialPage();
        }
    }
}
