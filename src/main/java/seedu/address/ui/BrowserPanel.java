package seedu.address.ui;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BrowserJumpToHomePage;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    //@@author Linus
    public static final String BROWSER_PAGE = "BrowserPanel.html";
    //@@author Linus
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    //@@author Linus
    private int backToHomePage = 0;
    //@@author Linus

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

    /**
     * Loads the located address page of the user's address.
     */
    private void loadBrowserPage(ReadOnlyPerson person) throws IOException {
        //@@author Linus
        URL addressPage = MainApp.class.getResource(FXML_FILE_FOLDER + BROWSER_PAGE);
        //@@author Linus
        loadPage(addressPage.toExternalForm());
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    public void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    //@@author Linus
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) throws IOException {

        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ReadOnlyPerson p = event.getNewSelection().person;

        String address = p.getAddress().toString();
        String name = p.getName().toString();
        String emails = p.getEmail().toString();
        String phones = p.getPhone().toString();
        String tags = p.getOnlyTags().toString();
        String avatar = p.getAvatarPic().toString();

        backToHomePage = event.getBackToHomePageValue();

        browser.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED && backToHomePage == 0) {
                WebEngine panel = browser.getEngine();

                panel.executeScript("document.setName(\"" + name + "\")");
                panel.executeScript("document.setAddress(\"" + address + "\")");
                panel.executeScript("document.setEmail(\"" + emails + "\")");
                panel.executeScript("document.setPhone(\"" + phones + "\")");
                panel.executeScript("document.setTags(\"" + tags + "\")");
                panel.executeScript("document.setAvatar(\"" + avatar + "\")");
            }
        });

        loadBrowserPage(event.getNewSelection().person);

    }

    @Subscribe
    private void handleGoBackToHomePageEvent(BrowserJumpToHomePage event) throws IOException {

        browser.getEngine().load(event.getHomeUrl().toExternalForm());
        backToHomePage = 1;

    }
    //@@author Linus


}
