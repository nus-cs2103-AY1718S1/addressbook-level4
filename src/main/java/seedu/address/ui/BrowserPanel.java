package seedu.address.ui;

import java.io.StringWriter;
import java.net.URL;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BrowserUrlChangeEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.FacebookAddAllFriendsCommand;
import seedu.address.logic.commands.FacebookAddCommand;
import seedu.address.logic.commands.FacebookConnectCommand;
import seedu.address.logic.commands.FacebookLinkCommand;
import seedu.address.logic.commands.FacebookPostCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.social.SocialInfo;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    //@@author keithsoc
    public static final String DEFAULT_PAGE_DAY = "defaultDay.html";
    public static final String DEFAULT_PAGE_NIGHT = "defaultNight.html";
    //@@author
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";

    private static final String FXML = "BrowserPanel.fxml";

    //@@author alexfoodw
    private static String processType;
    private static String trimmedArgs;
    //@@author

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    //@@author alexfoodw
    private Logic logic;
    private Label location;
    //@@author

    public BrowserPanel(Scene scene) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage(scene);

        FacebookConnectCommand.setWebEngine(browser.getEngine());
        location = new Label();
        location.textProperty().bind(browser.getEngine().locationProperty());
        setEventHandlerForBrowserUrlChangeEvent();
        setEventHandlerForBrowserUrlLoadEvent();
        registerAsAnEventHandler(this);
    }

    private void loadPersonPage(ReadOnlyPerson person) {
        loadPage(GOOGLE_SEARCH_URL_PREFIX + person.getName().fullName.replaceAll(" ", "+")
                + GOOGLE_SEARCH_URL_SUFFIX);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    //@@author alexfoodw
    /**
     * Sets the current logic manager
     * @param logic
     */
    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    /**
     * Identifies which facebook command process is being executed
     * @param type
     */
    public static void setProcessType(String type) {
        processType = type;
    }

    /**
     * Set arguments for the required facebook command
     * @param trimmedArgs
     */
    public static void setTrimmedArgs(String trimmedArgs) {
        BrowserPanel.trimmedArgs = trimmedArgs;
    }
    //@@author

    //@@author keithsoc
    /**
     * Loads a default HTML file with a background that matches the current theme.
     */
    public void loadDefaultPage(Scene scene) {
        URL defaultPage;
        if (scene.getStylesheets().get(0).equals(UiTheme.THEME_DAY)) {
            defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE_DAY);
        } else {
            defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE_NIGHT);
        }
        loadPage(defaultPage.toExternalForm());
    }
    //@@author

    //@@author alexfoodw
    //method to convert Document to String
    public String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (TransformerException ex) {
            new CommandException("Transform Doc to String Error.");
            return null;
        }
    }
    private void setEventHandlerForBrowserUrlLoadEvent() {
        browser.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            // Case: handle facebook related page loads
            if (Worker.State.SUCCEEDED.equals(newValue) && browser.getEngine().getLocation().contains("facebook")) {
                String currentContent = getStringFromDocument(browser.getEngine().getDocument());
                // handle invalid friend to be added
                if (currentContent.contains("Sorry, this content isn't available right now")
                        || currentContent.contains("This page isn't available")
                        || currentContent.contains("Sorry, this content isn't available at the moment")
                        || currentContent.contains("may have been expired")) {
                    FacebookAddAllFriendsCommand.setupNextFriend();
                }
            }
        });
    }

    private void setEventHandlerForBrowserUrlChangeEvent() {
        location.textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    // listens for post-accesstoken generation next step
                    if (newValue.contains("access_token")) {
                        switch (processType) {

                        case FacebookConnectCommand.COMMAND_WORD:
                        case FacebookConnectCommand.COMMAND_ALIAS:
                            logger.fine("browser url changed to : '" + newValue + "'");
                            raise(new BrowserUrlChangeEvent(FacebookConnectCommand.COMMAND_ALIAS));
                            break;

                        case FacebookPostCommand.COMMAND_WORD:
                        case FacebookPostCommand.COMMAND_ALIAS:
                            logger.fine("browser url changed to : '" + newValue + "'");
                            raise(new BrowserUrlChangeEvent(FacebookPostCommand.COMMAND_ALIAS));
                            break;

                        case FacebookLinkCommand.COMMAND_WORD:
                        case FacebookLinkCommand.COMMAND_ALIAS:
                            logger.fine("browser url changed to : '" + newValue + "'");
                            raise(new BrowserUrlChangeEvent(FacebookLinkCommand.COMMAND_ALIAS));
                            break;

                        case FacebookAddCommand.COMMAND_WORD:
                        case FacebookAddCommand.COMMAND_ALIAS:
                            logger.fine("browser url changed to : '" + newValue + "'");
                            raise(new BrowserUrlChangeEvent(FacebookAddCommand.COMMAND_ALIAS));
                            break;

                        case FacebookAddAllFriendsCommand.COMMAND_WORD:
                        case FacebookAddAllFriendsCommand.COMMAND_ALIAS:
                            logger.fine("browser url changed to : '" + newValue + "'");
                            raise(new BrowserUrlChangeEvent(FacebookAddAllFriendsCommand.COMMAND_ALIAS));
                            break;

                        default:
                            break;
                        }
                    } else if (newValue.contains("photo.php?fbid")) {
                        logger.fine("browser url changed to : '" + newValue + "'");
                        raise(new BrowserUrlChangeEvent(FacebookAddAllFriendsCommand.COMMAND_ALIAS));
                    }
                });
        // reset after execution
        processType = null;
    }
    //@@author

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    //@@author sarahnzx
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ReadOnlyPerson person = event.getNewSelection().person;
        String requestedSocialType = event.getSocialType();
        Person p = new Person(person);
        Iterator<SocialInfo> iterator = p.getSocialInfos().iterator();
        if (iterator.hasNext()) {
            // if there is SocialInfo stored
            SocialInfo social = iterator.next();
            String socialType = social.getSocialType();
            while (!socialType.equals(requestedSocialType) && iterator.hasNext() && requestedSocialType != null) {
                // if no social type is specified, the default social type shown will be Instagram
                social = iterator.next();
            }

            String url = social.getSocialUrl();
            loadPage(url);
        } else {
            loadPersonPage(event.getNewSelection().person);
        }
    }

    //@@author alexfoodw
    @Subscribe
    private void handleBrowserUrlChangeEvent(BrowserUrlChangeEvent event) throws CommandException {
        switch (event.getProcessType()) {

        case FacebookConnectCommand.COMMAND_WORD:
        case FacebookConnectCommand.COMMAND_ALIAS:
            logger.info(LogsCenter.getEventHandlingLogMessage(event));
            FacebookConnectCommand.completeAuth(browser.getEngine().getLocation());
            break;

        case FacebookPostCommand.COMMAND_WORD:
        case FacebookPostCommand.COMMAND_ALIAS:
            logger.info(LogsCenter.getEventHandlingLogMessage(event));
            FacebookConnectCommand.completeAuth(browser.getEngine().getLocation());
            FacebookPostCommand.completePost();
            break;

        case FacebookLinkCommand.COMMAND_WORD:
        case FacebookLinkCommand.COMMAND_ALIAS:
            logger.info(LogsCenter.getEventHandlingLogMessage(event));
            FacebookConnectCommand.completeAuth(browser.getEngine().getLocation());
            FacebookLinkCommand.completeLink();
            break;

        case FacebookAddCommand.COMMAND_WORD:
        case FacebookAddCommand.COMMAND_ALIAS:
            logger.info(LogsCenter.getEventHandlingLogMessage(event));
            FacebookConnectCommand.completeAuth(browser.getEngine().getLocation());
            FacebookAddCommand facebookAddCommand = new FacebookAddCommand(trimmedArgs);
            logic.completeFacebookAddCommand(facebookAddCommand, processType);
            break;

        case FacebookAddAllFriendsCommand.COMMAND_WORD:
        case FacebookAddAllFriendsCommand.COMMAND_ALIAS:
            logger.info(LogsCenter.getEventHandlingLogMessage(event));
            FacebookAddAllFriendsCommand.setUserId(browser.getEngine().getLocation());
            FacebookAddCommand facebookAddCommandForAddAll = new FacebookAddCommand(true);
            logic.completeFacebookAddCommand(facebookAddCommandForAddAll, processType);

            // go on to add next friend
            FacebookAddAllFriendsCommand.incrementTotalFriendsAdded();
            FacebookAddAllFriendsCommand.setupNextFriend();
            break;

        default:
            throw new CommandException("URL change error.");
        }
    }
    //@@author
}
