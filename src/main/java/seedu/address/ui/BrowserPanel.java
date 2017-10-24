package seedu.address.ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ReminderPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.reminder.ReadOnlyReminder;

import javax.swing.*;
import javax.swing.text.Element;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/maps/search/";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "/";
    private Browser browser;
    private DisplayPanel displayPanel;
    private static final String FXML = "BrowserPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());


    @FXML
    private StackPane browserPanel;

    public BrowserPanel() {
        super(FXML);
        getRoot().setOnKeyPressed(Event::consume);
        registerAsAnEventHandler(this);
        browser = new Browser();
        browserPanel.getChildren().add(browser.getRoot());
    }

    private void loadPersonPage(ReadOnlyPerson person) {
        try {
            browserPanel.getChildren().remove(displayPanel.getRoot());
        }
        catch (Exception e){
            logger.info("DisplayPanel does not exist");
        }
        try {
            browserPanel.getChildren().remove(browser.getRoot());
        }
        catch (Exception e){
            logger.info("BrowserPanel does not exist");
        }

        browserPanel.getChildren().add(browser.getRoot());
        String personUrl = GOOGLE_SEARCH_URL_PREFIX + person.getAddress().toString() + GOOGLE_SEARCH_URL_SUFFIX;
        browser.loadPage(personUrl);


    }

    private void DisplayReminder(ReadOnlyReminder reminder) {
        try {
            browserPanel.getChildren().remove(displayPanel.getRoot());
        }
        catch (Exception e){
            logger.info("DisplayPanel does not exist");
        }
        try {
            browserPanel.getChildren().remove(browser.getRoot());
        }
        catch (Exception e){
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

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }

    @Subscribe
    private void handleReminderPanelSelectionChangedEvent(ReminderPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        DisplayReminder(event.getNewSelection().reminder);
    }
}
