package seedu.address.ui;

import java.io.File;
import java.io.FileNotFoundException;

import java.net.URL;
import java.util.Scanner;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
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
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com/maps/search/?api=1&query=";
    public static final String GOOGLE_MAPS_SEARCH_URL_PREFIX = "https://www.google.com/maps/search/?api=1&query=";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    @FXML
    private TextArea taskDisplayed;

    @FXML
    private TabPane tabPane;


    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        displayTask();
        loadDefaultPage();
        displayOnTextArea();
        registerAsAnEventHandler(this);
    }
    //@@author chairz
    private void displayTask() {
        taskDisplayed.setStyle("-fx-font-family: monospace; -fx-background-color: #f8ecc2; -fx-font-size: 22px;");

    }


    private void loadPersonPage(ReadOnlyPerson person) {
        loadPage(GOOGLE_MAPS_SEARCH_URL_PREFIX + person.getAddress().toString()
                .replaceAll(" ", "+").replaceAll(",", "%2C"));
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    public TextArea getTaskDisplayed() {
        return taskDisplayed;
    }

    public TabPane getTabPane() {
        return tabPane;
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

    //@@author chairz
    /**
     *  Read and Display the task on the Text Area
     */
    public void displayOnTextArea() {
        try {
            String curr = System.getProperty("user.dir");
            Scanner s = new Scanner(new File(curr + "/taskData1.txt"));
            while (s.hasNext()) {

                taskDisplayed.appendText(s.next() + "\n");

            }

        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }


    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }
}
