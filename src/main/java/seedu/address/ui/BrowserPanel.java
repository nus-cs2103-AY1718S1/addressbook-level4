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
import seedu.address.logic.commands.QrGenCallCommand;
import seedu.address.logic.commands.QrGenSaveContactCommand;
import seedu.address.logic.commands.QrGenSmsCommand;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {
    //@@author blaqkrow
    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/maps/place/";
    //@@author
    private static final String FXML = "BrowserPanel.fxml";
    private static BrowserPanel instance;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);
        browser.getEngine().setUserAgent(DEFAULT_PAGE.replace("Macintosh‌​; ", ""));
        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    public WebView getBrowser() {
        return browser;
    }
    //@@author blaqkrow
    /**
     * loads webpage
     */
    public void loadPersonPage(ReadOnlyPerson person) {
        browser.getEngine().setUserAgent("Mozilla/5.0 "
                + "(Windows NT x.y; Win64; x64; rv:10.0) Gecko/20100101 Firefox/10.0");
        loadPage(GOOGLE_SEARCH_URL_PREFIX + person.getAddress().toString().replaceAll(" ", "+"));
    }
    /**
     * Loads generated Call QR code
     */
    public void loadQrCode(ReadOnlyPerson person) {
        QrGenCallCommand qrGenCallCommand = new QrGenCallCommand();
        browser.getEngine().setUserAgent("Mozilla/5.0 "
                + "(Windows NT x.y; Win64; x64; rv:10.0) Gecko/20100101 Firefox/10.0");
        loadPage(qrGenCallCommand.qrCall(person.getPhone().toString()));
    }
    /**
     * Loads generated SMS QR Code
     */
    public void loadSmsQrCode(ReadOnlyPerson person) {
        QrGenSmsCommand qrGenSmsCommand = new QrGenSmsCommand();
        browser.getEngine().setUserAgent("Mozilla/5.0 "
                + "(Windows NT x.y; Win64; x64; rv:10.0) Gecko/20100101 Firefox/10.0");
        loadPage(qrGenSmsCommand.qrSms(person.getPhone().toString(), person.getName().fullName));
    }
    /**
     * Loads generated share QR code
     */
    public void loadSaveQrCode(ReadOnlyPerson person) {
        QrGenSaveContactCommand qrGenSaveContactCommand = new QrGenSaveContactCommand();
        browser.getEngine().setUserAgent("Mozilla/5.0 "
                + "(Windows NT x.y; Win64; x64; rv:10.0) Gecko/20100101 Firefox/10.0");
        loadPage(qrGenSaveContactCommand.qrSaveContact(person.getPhone().toString(), person.getName().fullName,
                person.getEmail().toString()));
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
        loadPersonPage(event.getNewSelection().person);
    }
}
