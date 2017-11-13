package seedu.address.ui;

import static seedu.address.logic.commands.InstaCommand.INSTA_TAB;
import static seedu.address.logic.commands.NusmodsCommand.NUSMODS_TAB;

import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BrowserPanelChangeActiveTabEvent;
import seedu.address.commons.events.ui.PersonPanelDeselectionEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;


/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String NO_TIMETABLE = "noTimetable.html";
    public static final String NUSMODS_SEARCH_URL_PREFIX = "https://nusmods.com/timetable/";

    private static final String FXML = "BrowserPanel.fxml";
    private static int activeTab = 1;

    private String semester;
    private String academicYear;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private TabPane browserPanel;

    @FXML
    private Tab nusModsTab;

    @FXML
    private Tab instaTab;

    @FXML
    private WebView browser;

    @FXML
    private WebView instaBrowser;

    public BrowserPanel(Config config) {
        super(FXML);

        setAcademicYearSemester(config);
        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);

    }

    //@@author ritchielq
    /**
     * Loads nusmods website base on person object
     * @param person
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        // Loads website if nusModule is not null or empty
        if (person.getNusModules() != null && !person.getNusModules().value.isEmpty()) {
            loadPage(NUSMODS_SEARCH_URL_PREFIX + academicYear + "/sem" + semester + "?"
                    + person.getNusModules().toString());
        } else {
            loadNoTimetablePage();
        }
    }

    public String getSemester() {
        return semester;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    //@@author
    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    //@@author ritchielq
    private void setAcademicYearSemester(Config config) {
        academicYear = config.getAcademicYear();
        semester = config.getSemester();
    }

    //@@author ritchielq-reuse
    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadNoTimetablePage() {
        URL noTimetablePage = MainApp.class.getResource(FXML_FILE_FOLDER + NO_TIMETABLE);
        loadPage(noTimetablePage.toExternalForm());
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

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
        loadInsta(event.getNewSelection().person);
        setActiveTab();
    }

    //@@author ritchielq
    /**
     * Sets active tab to {@code activeTab}
     */
    private void setActiveTab() {
        switch (activeTab) {
        case INSTA_TAB:
            browserPanel.getSelectionModel().select(instaTab);
            break;
        case NUSMODS_TAB:
            browserPanel.getSelectionModel().select(nusModsTab);
            break;
        default:
            break;
        }
    }

    //@@author hengyu95
    /**
     * Loads Instagram page on Instagram tab
     */
    public void loadInsta(ReadOnlyPerson person) {

        if (person.getUserId().value.equals("-")) {
            Platform.runLater(() -> instaBrowser.getEngine().load("https://www.instagram.com/"));
        } else {
            Platform.runLater(() -> instaBrowser.getEngine().load(new StringBuilder()
                    .append("https://www.instagram.com/").append(person.getUserId()).toString()));
        }

    }

    @Subscribe
    private void handlePersonPanelDeselectionEvent(BrowserPanelChangeActiveTabEvent event) {
        activeTab = event.targetTab;
        setActiveTab();
    }

    //@@author ritchielq-reuse
    @Subscribe
    private void handlePersonPanelDeselectionEvent(PersonPanelDeselectionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadDefaultPage();
    }
}
