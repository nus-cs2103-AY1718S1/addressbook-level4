package seedu.address.ui;

import java.net.URL;
import java.util.Random;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public static final String GOOGLE_MAP_URL_PREFIX = "https://www.google.com.sg/maps/search/";
    //public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";
    private static String[] motivationPages = {"motivation1.jpg", "motivation2.jpg", "motivation3.jpg",
        "motivation4.jpg", "motivation5.jpg", "motivation6.jpg", "motivation7.jpg", "motivation8.png"};
    private static Random random = new Random();
    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;
    @FXML
    private ImageView splashPage;

    public BrowserPanel() {
        super(FXML);
        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    private void loadPersonPage(ReadOnlyPerson person) {
        loadPage(GOOGLE_MAP_URL_PREFIX + person.getAddress().toString().replaceAll(" ", "+"));
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
        setUpMotivationPage();
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
        splashPage.setVisible(false);
        loadPersonPage(event.getNewSelection().person);
    }
    //@@author wishingmaid
    /** randomises which motivational page is used from the resource images folder */
    private void setUpMotivationPage()  {
        Image image = new Image(getClass().getResource("/images/"
                + motivationPages[random.nextInt(motivationPages.length)]).toExternalForm());
        splashPage.setImage(image);
    }
    //@@author
}
