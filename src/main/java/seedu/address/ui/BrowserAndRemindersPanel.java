package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BrowserAndRemindersPanelToggleEvent;
import seedu.address.commons.events.ui.LoadPersonWebpageEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.PopularContactPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.SendingEmailEvent;
import seedu.address.commons.events.ui.ShowDefaultPanelEvent;
import seedu.address.commons.events.ui.ShowLocationEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.DisplayPicture;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nickname;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PopularityCounter;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.reminders.ReadOnlyReminder;
import seedu.address.model.tag.Tag;

/**
 * The Browser Panel of the App.
 */
public class BrowserAndRemindersPanel extends UiPart<Region> {

    //@@author justinpoh
    /**
     * An Enumeration to differentiate between the child nodes and to keep track of which is
     * in front.
     */
    private enum Node {
        BROWSER, REMINDERS, DETAILS
    }
    //@@author

    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";
    public static final String GOOGLE_MAPS_URL = "https://www.google.com.sg/maps/place/";

    public static final String EMAIL_SERVICE_GMAIL = "gmail";
    public static final String EMAIL_SERVICE_OUTLOOK = "outlook";

    public static final String GMAIL_EMAIL_URL =
            "https://mail.google.com/mail/?view=cm&fs=1&tf=1&source=mailto&to=%1$s&su=%2$s&body=%3$s";

    public static final String OUTLOOK_EMAIL_URL =
            "https://outlook.office.com/?path=/mail/action/compose&to=%1$s&subject=%2$s&body=%3$s";

    private static final String FXML = "BrowserAndRemindersPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private BirthdayAndReminderListPanel birthdayAndReminderListPanel;
    private DetailsPanel personDetails;
    private Node currentlyInFront = Node.REMINDERS;

    @FXML
    private WebView browser;

    @FXML
    private StackPane remindersPanel;

    @FXML
    private AnchorPane detailsPanel;


    public BrowserAndRemindersPanel(ObservableList<ReadOnlyPerson> birthdayPanelFilteredPersonList,
                                    ObservableList<ReadOnlyReminder> reminderList) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();

        birthdayAndReminderListPanel = new BirthdayAndReminderListPanel(birthdayPanelFilteredPersonList, reminderList);
        //remindersPanel should be displayed first so no need to shift it to the back.
        remindersPanel.getChildren().add(birthdayAndReminderListPanel.getRoot());
        registerAsAnEventHandler(this);
        try {
            personDetails = new DetailsPanel(new Person(new Name("shradheya"), new Phone("00000"),
                    new Email("tshradheya@gmail.com"), new Address("something"), new Birthday("15-10-1998"),
                    new Nickname(""), new DisplayPicture(""), new PopularityCounter(), new TreeSet<Tag>()));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Wrong argument");
        }
        detailsPanel.getChildren().add(personDetails.getRoot());
        setUpToShowRemindersPanel();
        currentlyInFront = Node.REMINDERS;
        remindersPanel.toFront();
    }

    private void loadPersonPage(ReadOnlyPerson person) {
        loadPage(GOOGLE_SEARCH_URL_PREFIX + person.getName().fullName.replaceAll(" ", "+")
                + GOOGLE_SEARCH_URL_SUFFIX);
    }

    /**
     * Loads the specified url with cookies and javascript enabled
     * @param url of specified web page
     */
    public void loadPage(String url) {
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        URI uri = URI.create(url);
        Map<String, List<String>> headers = new LinkedHashMap<String, List<String>>();
        headers.put("Set-Cookie", Arrays.asList("name=value"));
        try {
            java.net.CookieHandler.getDefault().put(uri, headers);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        browser.getEngine().setJavaScriptEnabled(true);
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

    //@@author justinpoh
    /**
     * Check which child is currently at the front, and perform the appropriate toggling between the children nodes.
     */
    private void toggleBrowserPanel() {
        switch(currentlyInFront) {
        case BROWSER:
            setUpToShowRemindersPanel();
            remindersPanel.toFront();
            currentlyInFront = Node.REMINDERS;
            break;
        case REMINDERS:
            setUpToShowWebBrowser();
            browser.toFront();
            currentlyInFront = Node.BROWSER;
            break;
        case DETAILS:
            setUpToShowRemindersPanel();
            remindersPanel.toFront();
            currentlyInFront = Node.REMINDERS;
            break;
        default:
            throw new AssertionError("It should not be possible to land here");
        }
    }
    //@@author
    //@@author tshradheya
    private void setUpToShowRemindersPanel() {
        logger.info("Reminders Panel visible");
        detailsPanel.setVisible(false);
        remindersPanel.setVisible(true);
        browser.setVisible(false);
    }

    private void setUpToShowDetailsPanel() {
        logger.info("Details Panel visible");
        detailsPanel.setVisible(true);
        remindersPanel.setVisible(false);
        browser.setVisible(false);
    }

    /**
     * Set's up the UI to bring browser to front and show location
     */
    private void setUpToShowLocation() {
        setUpToShowWebBrowser();
        browser.toFront();
        currentlyInFront = Node.BROWSER;
    }

    /**
     * Set's up the UI to bring browser to front
     */
    private void setUpToShowWebBrowser() {
        logger.info("Browser Panel visible");
        browser.setVisible(true);
        detailsPanel.setVisible(false);
        remindersPanel.setVisible(false);
    }

    /**
     * Creates url from given address
     * @param address of the specified person
     */
    public String loadPersonLocation(String address) {

        String[] splitAddressByWords = address.split("\\s");

        String keywordsOfUrl = "";

        for (String word: splitAddressByWords) {
            keywordsOfUrl += word;
            keywordsOfUrl += "+";
        }

        loadPage(GOOGLE_MAPS_URL + keywordsOfUrl);
        return GOOGLE_MAPS_URL + keywordsOfUrl;
    }

    /**
     * Sets up email Url for processing Email in Browser panel
     * @param service mentioned email service
     * @param recipients formed recipients string
     * @param subject of the email (optional, can be empty)
     * @param body of the email (optional, can be empty)
     */
    private void setUpEmailUrl(String service, String recipients, String subject, String body) {
        if (service.equalsIgnoreCase(EMAIL_SERVICE_GMAIL)) {
            loadEmailUrlGmail(recipients, subject, body);
        } else if (service.equalsIgnoreCase(EMAIL_SERVICE_OUTLOOK)) {
            loadEmailUrlOutlook(recipients, subject, body);
        }
    }

    /**
     * Loads page to send email through gmail
     * @param recipients in terms of a tag ( can be only 1 tag)
     * @param subject of the email (optional)
     * @param body of the email (optional)
     */
    public void loadEmailUrlGmail(String recipients, String subject, String body) {
        try {
            Desktop.getDesktop().browse(new URI(String.format(GMAIL_EMAIL_URL, recipients, subject, body)));
        } catch (URISyntaxException urise) {
            assert false : "As long as google doesn't change its links this should not happen";
        } catch (IOException ioe) {
            assert false : "As long as google doesn't change its links this should not happen";
        }
    }

    /**
     * Loads page to send email through outlook
     * @param recipients in terms of a tag ( can be only 1 tag)
     * @param subject of the email (optional, can be empty)
     * @param body of the email( optional, can be empty)
     */
    private void loadEmailUrlOutlook(String recipients, String subject, String body) {
        try {
            Desktop.getDesktop().browse(new URI(String.format(OUTLOOK_EMAIL_URL, recipients, subject, body)));
        } catch (URISyntaxException urise) {
            assert false : "As long as outlook doesn't change its links this should not happen";
        } catch (IOException ioe) {
            assert false : "As long as outlook doesn't change its links this should not happen";
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setUpToShowDetailsPanel();
        detailsPanel.toFront();
        currentlyInFront = Node.DETAILS;
        personDetails = new DetailsPanel(event.getPerson());
        detailsPanel.getChildren().clear();
        detailsPanel.getChildren().add(personDetails.getRoot());
    }

    //@@author
    //@@author justinpoh
    @Subscribe
    private void handleBrowserPanelToggleEvent(BrowserAndRemindersPanelToggleEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        toggleBrowserPanel();
    }
    //@@author
    //@@author tshradheya

    @Subscribe
    private void handleShowLocationEvent(ShowLocationEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Processing Location of " + event.person.getName().fullName));
        setUpToShowLocation();
        String url = loadPersonLocation(event.person.getAddress().value);
    }

    @Subscribe
    private void handleSendingEmailEvent(SendingEmailEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Processing email through service of " + event.service.service));
        setUpEmailUrl(event.service.service, event.recipients, event.subject.subject, event.body.body);
    }

    @Subscribe
    private void handlePopularContactPanelSelectionChangedEvent(PopularContactPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setUpToShowDetailsPanel();
        detailsPanel.toFront();
        currentlyInFront = Node.DETAILS;
        personDetails = new DetailsPanel(event.getPerson());
        detailsPanel.getChildren().clear();
        detailsPanel.getChildren().add(personDetails.getRoot());
    }

    @Subscribe
    private void handleLoadPersonPageEvent(LoadPersonWebpageEvent event) {
        setUpToShowWebBrowser();
        currentlyInFront = Node.BROWSER;
        browser.toFront();
        loadPersonPage(event.getPerson());
    }

    @Subscribe
    private void handleShowDefaultPanelEvent(ShowDefaultPanelEvent event) {
        setUpToShowRemindersPanel();
        currentlyInFront = Node.REMINDERS;
        remindersPanel.toFront();
    }

    //@@author

}
