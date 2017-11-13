package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ClearPersonListEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.OpenFaceBookWebViewEvent;
import seedu.address.commons.events.ui.OpenGithubWebViewEvent;
import seedu.address.commons.events.ui.OpenInstagramWebViewEvent;
import seedu.address.commons.events.ui.OpenNusModsWebViewEvent;
import seedu.address.commons.events.ui.OpenTwitterWebViewEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.event.Event;
import seedu.address.model.person.ReadOnlyPerson;

//@@author dalessr
/**
 * The Contact Details Panel of the App.
 */
public class PersonDetailsPanel extends UiPart<Region> {

    public static final String TWITTER_DEFAULT_URL = "https://twitter.com/search?q=news&src=typd";
    public static final String FACEBOOK_DEFAULT_URL = "https://www.facebook.com/people-search.php";
    public static final String NUSMODS_DEFAULT_URL = "https://nusmods.com/timetable/2017-2018/sem1";
    public static final String INSTAGRAM_DEFAULT_URL = "https://www.instagram.com/instagram/";
    public static final String GITHUB_DEFAULT_URL = "https://github.com/github";

    private static final String FXML = "PersonDetailsPanel.fxml";

    private ObservableList<ReadOnlyPerson> personList;
    private TabPane tabPane;

    private final Logger logger = LogsCenter.getLogger(PersonDetailsPanel.class);

    @FXML
    private GridPane personDetailsGrid;

    @FXML
    private Label nameLabel;

    @FXML
    private Label birthdayLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label addressLabelContinue;

    @FXML
    private VBox eventsBox;

    @FXML
    private TextArea eventsArea;

    public PersonDetailsPanel(ObservableList<ReadOnlyPerson> personList, TabPane tabPane) {
        super(FXML);
        this.personList = personList;
        this.tabPane = tabPane;
        registerAsAnEventHandler(this);
    }

    /**
     * Open another tab to show twitter webview
     */
    @FXML
    private void openTwitterWebView() {
        Tab tab = new Tab();
        tab.setText("twitter");
        tab.setClosable(true);
        WebView webView = new WebView();
        webView.getEngine().load(TWITTER_DEFAULT_URL);
        tab.setContent(webView);
        tabPane.getTabs().add(tab);
    }

    /**
     * Open another tab to show nusmods webview
     */
    @FXML
    private void openNusModsWebView() {
        Tab tab = new Tab();
        tab.setText("nusmods");
        tab.setClosable(true);
        WebView webView = new WebView();
        webView.getEngine().load(NUSMODS_DEFAULT_URL);
        tab.setContent(webView);
        tabPane.getTabs().add(tab);
    }

    /**
     * Open another tab to show facebook webview
     */
    @FXML
    private void openFaceBookWebView() {
        Tab tab = new Tab();
        tab.setText("facebook");
        tab.setClosable(true);
        WebView webView = new WebView();
        webView.getEngine().load(FACEBOOK_DEFAULT_URL);
        tab.setContent(webView);
        tabPane.getTabs().add(tab);
    }

    /**
     * Open another tab to show instagram webview
     */
    @FXML
    private void openInstagramWebView() {
        Tab tab = new Tab();
        tab.setText("instagram");
        tab.setClosable(true);
        WebView webView = new WebView();
        webView.getEngine().load(INSTAGRAM_DEFAULT_URL);
        tab.setContent(webView);
        tabPane.getTabs().add(tab);
    }

    /**
     * Open another tab to show github webview
     */
    @FXML
    private void openGitHubWebView() {
        Tab tab = new Tab();
        tab.setText("github");
        tab.setClosable(true);
        WebView webView = new WebView();
        webView.getEngine().load(GITHUB_DEFAULT_URL);
        tab.setContent(webView);
        tabPane.getTabs().add(tab);
    }

    @Subscribe
    private void handleClearPersonListEvent(ClearPersonListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        nameLabel.setText("Person Name");
        birthdayLabel.setText("");
        phoneLabel.setText("");
        emailLabel.setText("");
        addressLabel.setText("");
        addressLabelContinue.setText("");
        eventsArea.setText("");
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ReadOnlyPerson person = personList.get(event.targetIndex);
        nameLabel.setText(person.getName().fullName);
        if (person.getBirthday().value.equals("01/01/1900")) {
            birthdayLabel.setText("");
        } else {
            birthdayLabel.setText(person.getBirthday().toString());
        }
        phoneLabel.setText(person.getPhone().toString());
        emailLabel.setText(person.getEmail().toString());

        addressLabelContinue.setText("");
        String[] address = person.getAddress().toString().split(" ");
        StringBuilder firstBuilder = new StringBuilder();
        int index = 0;
        while (index < address.length && address[index].length() <= 32 - firstBuilder.length()) {
            firstBuilder.append(address[index]);
            firstBuilder.append(" ");
            index++;
        }
        String firstAddress = firstBuilder.toString();
        addressLabel.setText(firstAddress);
        StringBuilder secondBuilder = new StringBuilder();
        for (; index < address.length; index++) {
            secondBuilder.append(address[index]);
            secondBuilder.append(" ");
        }
        if (secondBuilder.length() != 0) {
            String secondAddress = secondBuilder.toString();
            addressLabelContinue.setText(secondAddress);
        }

        eventsArea.setText("");
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 1;
        for (Event e: person.getEvents()) {
            stringBuilder.append(counter)
                    .append(". ")
                    .append(e.getEventName().fullName)
                    .append(" -- ")
                    .append(e.getEventTime().toString())
                    .append("\n");
            counter++;
        }
        eventsArea.setText(stringBuilder.toString());
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ReadOnlyPerson person = event.getNewSelection().person;
        nameLabel.setText(person.getName().fullName);
        if (person.getBirthday().value.equals("01/01/1900")) {
            birthdayLabel.setText("");
        } else {
            birthdayLabel.setText(person.getBirthday().toString());
        }
        phoneLabel.setText(person.getPhone().toString());
        emailLabel.setText(person.getEmail().toString());

        addressLabelContinue.setText("");
        String[] address = person.getAddress().toString().split(" ");
        StringBuilder firstBuilder = new StringBuilder();
        int index = 0;
        while (index < address.length && address[index].length() <= 32 - firstBuilder.length()) {
            firstBuilder.append(address[index]);
            firstBuilder.append(" ");
            index++;
        }
        String firstAddress = firstBuilder.toString();
        addressLabel.setText(firstAddress);
        StringBuilder secondBuilder = new StringBuilder();
        for (; index < address.length; index++) {
            secondBuilder.append(address[index]);
            secondBuilder.append(" ");
        }
        if (secondBuilder.length() != 0) {
            String secondAddress = secondBuilder.toString();
            addressLabelContinue.setText(secondAddress);
        }

        eventsArea.setText("");
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 1;
        for (Event e: person.getEvents()) {
            stringBuilder.append(counter)
                    .append(". ")
                    .append(e.getEventName().fullName)
                    .append(" -- ")
                    .append(e.getEventTime().toString())
                    .append("\n");
            counter++;
        }
        eventsArea.setText(stringBuilder.toString());
    }

    @Subscribe
    private void handleOpenTwitterWebViewEvent(OpenTwitterWebViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        openTwitterWebView();
    }

    @Subscribe
    private void handleOpenNusModsWebViewEvent(OpenNusModsWebViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        openNusModsWebView();
    }

    @Subscribe
    private void handleOpenFaceBookWebViewEvent(OpenFaceBookWebViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        openFaceBookWebView();
    }

    @Subscribe
    private void handleOpenInstagramWebViewEvent(OpenInstagramWebViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        openInstagramWebView();
    }

    @Subscribe
    private void handleOpenGithubWebViewEvent(OpenGithubWebViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        openGitHubWebView();
    }
}
