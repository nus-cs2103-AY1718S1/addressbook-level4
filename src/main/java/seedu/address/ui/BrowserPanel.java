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
import seedu.address.commons.events.ui.BrowserPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.MapPersonEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {
    //@@author Sri-vatsa
    public static final String DEFAULT_PAGE = "default.html";
    public static final String LINKEDIN_SEARCH_URL_PREFIX = "https://www.linkedin.com/search/results/";
    public static final String LINKEDIN_SEARCH_PEOPLE = "people/";
    public static final String LINKEDIN_SEARCH_PARAM_LOCATION = "?facetGeoRegion=%5B%22sg%3A0%22%5D";
    public static final String LINKEDIN_SEARCH_PARAM_FIRST_NAME = "&firstName=";
    public static final String LINKEDIN_SEARCH_PARAM_LAST_NAME = "&lastName=";
    public static final String LINKEDIN_URL_SUFFIX = "&origin=FACETED_SEARCH";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";
    public static final String GOOGLE_MAPS_URL_PREFIX = "https://www.google.com.sg/maps?safe=off&q=";
    //@@author
    private static final String FXML = "BrowserPanel.fxml";

    private static ReadOnlyPerson personSelected;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private boolean hasLinkedinBeenChosen = false;
    private boolean hasMapsBeenChosen = false;

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }
    //@@author Sri-vatsa
    /***
     * Loads person page
     * @param person
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        personSelected = person;
        if (hasLinkedinBeenChosen) {
            try {
                loadLinkedIn();
            } catch (CommandException e) {
                e.printStackTrace();
            }
        } else if (hasMapsBeenChosen) {
            try {
                loadPersonMap(person);
            } catch (CommandException e) {
                e.printStackTrace();
            }
        } else {
            loadPage(GOOGLE_SEARCH_URL_PREFIX + person.getName().fullName.replaceAll(" ", "+")
                    + GOOGLE_SEARCH_URL_SUFFIX);
        }
    }

    //@@author martyn-wong
    /***
     * Loads google map of person
     * @param person
     */
    private void loadPersonMap(ReadOnlyPerson person) throws CommandException {
        if (personSelected == null) {
            throw new CommandException("Please select a person");
        }
        setMapsChosenTrue();
        setLinkedinChosenFalse();
        loadPage(GOOGLE_MAPS_URL_PREFIX + person.getAddress().toString().replaceAll(" ", "+")
                + GOOGLE_SEARCH_URL_SUFFIX);
    }

    //@@author Sri-vatsa

    /***
     * Loads pages based on choose command selection
     */
    private void loadLinkedIn() throws CommandException {
        if (personSelected == null) {
            throw new CommandException("Please select a person");
        }
        setLinkedinChosenTrue();
        setMapsChosenFalse();
        String[] name = personSelected.getName().fullName.split(" ");

        loadPage(LINKEDIN_SEARCH_URL_PREFIX + LINKEDIN_SEARCH_PEOPLE + LINKEDIN_SEARCH_PARAM_LOCATION
                + LINKEDIN_SEARCH_PARAM_FIRST_NAME + name[0] + LINKEDIN_SEARCH_PARAM_LAST_NAME + name[1]
                + LINKEDIN_URL_SUFFIX);
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
    //@@author Sri-vatsa

    /**
     * Setter method to set the Boolean value of hasLinkedinBeenChosen
     */
    public void setLinkedinChosenTrue () {
        hasLinkedinBeenChosen = true;
    }

    public void setLinkedinChosenFalse () {
        hasLinkedinBeenChosen = false;
    }
    //@@author

    //@@author martyn-wong
    /**
     * Setter method to set the Boolean value of hasMapsBeenChosen
     */
    public void setMapsChosenTrue () {
        hasMapsBeenChosen = true;
    }

    public void setMapsChosenFalse () {
        hasMapsBeenChosen = false;
    }
    //@@author

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

    //@@author Sri-vatsa
    @Subscribe
    private void handleBrowserPanelSelectionChangedEvent(BrowserPanelSelectionChangedEvent event)
            throws CommandException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.getBrowserSelection().equals("linkedin")) {
            loadLinkedIn();
        } else if (event.getBrowserSelection().equals("google")) {
            hasLinkedinBeenChosen = false;
            hasMapsBeenChosen = false;
            loadPersonPage(personSelected);
        } else if (event.getBrowserSelection().equals("maps")) {
            loadPersonMap(personSelected);
        }
    }

    //@author martyn-wong
    @Subscribe
    private void handleMapPanelEvent(MapPersonEvent event) throws CommandException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonMap(event.getPerson());
    }
}
