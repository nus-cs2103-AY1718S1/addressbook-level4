package seedu.address.ui;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ReminderPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ShowProfileRequestEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.reminder.ReadOnlyReminder;


/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/maps/search/";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "/";
    private static final String FXML = "BrowserPanel.fxml";
    private Browser browser;
    private PersonProfile personProfile;
    private DisplayPanel displayPanel;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    //@@author cqhchan
    @FXML
    private StackPane browserPanel;

    public BrowserPanel() {
        super(FXML);
        getRoot().setOnKeyPressed(Event::consume);
        registerAsAnEventHandler(this);
        browser = new Browser();
        browserPanel.getChildren().add(browser.getRoot());
    }

    /**
     * @param person
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        try {
            browserPanel.getChildren().remove(personProfile.getRoot());
        } catch (Exception e) {
            logger.info("PersonProfilePanel does not exist");
        }
        try {
            browserPanel.getChildren().remove(displayPanel.getRoot());
        } catch (Exception e) {
            logger.info("DisplayPanel does not exist");
        }
        try {
            browserPanel.getChildren().remove(browser.getRoot());
        } catch (Exception e) {
            logger.info("BrowserPanel does not exist");
        }

        browserPanel.getChildren().add(browser.getRoot());
        String personUrl = GOOGLE_SEARCH_URL_PREFIX + person.getAddress().toString() + GOOGLE_SEARCH_URL_SUFFIX;
        browser.loadPage(personUrl);
    }

    /**
     * @param reminder
     */
    private void displayReminder(ReadOnlyReminder reminder) {
        try {
            browserPanel.getChildren().remove(personProfile.getRoot());
        } catch (Exception e) {
            logger.info("PersonProfilePanel does not exist");
        }
        try {
            browserPanel.getChildren().remove(displayPanel.getRoot());
        } catch (Exception e) {
            logger.info("DisplayPanel does not exist");
        }
        try {
            browserPanel.getChildren().remove(browser.getRoot());
        } catch (Exception e) {
            logger.info("BrowserPanel does not exist");
        }
        displayPanel = new DisplayPanel(reminder);
        browserPanel.getChildren().add(displayPanel.getRoot());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser.freeResources();
    }
    //@@author

    //@@author duyson98
    /**
     * @param person
     */
    private void loadPersonProfile(ReadOnlyPerson person) {
        try {
            browserPanel.getChildren().remove(personProfile.getRoot());
        } catch (Exception e) {
            logger.info("PersonProfilePanel does not exist");
        }
        try {
            browserPanel.getChildren().remove(displayPanel.getRoot());
        } catch (Exception e) {
            logger.info("DisplayPanel does not exist");
        }
        try {
            browserPanel.getChildren().remove(browser.getRoot());
        } catch (Exception e) {
            logger.info("BrowserPanel does not exist");
        }

        personProfile = new PersonProfile(person);
        browserPanel.getChildren().add(personProfile.getRoot());
    }

    @Subscribe
    private void handleShowProfileRequestEvent(ShowProfileRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonProfile(event.person);
    }
    //@@author

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }

    //@@author cqhchan
    @Subscribe
    private void handleReminderPanelSelectionChangedEvent(ReminderPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        displayReminder(event.getNewSelection().reminder);
    }
    //@@author
}
