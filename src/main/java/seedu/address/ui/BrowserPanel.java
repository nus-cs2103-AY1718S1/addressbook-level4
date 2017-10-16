package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    @FXML
    private GridPane mainDetailsPane;

    @FXML
    private GridPane socialIconsPane;

    @FXML
    private StackPane socialIcon1Placeholder, socialIcon2Placeholder, socialIcon3Placeholder, socialIcon4Placeholder, contactImagePlaceholder;

    @FXML
    private VBox contactDetailsVBox;

    @FXML
    private ImageView contactImage;


    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        setContactImage();
        setContactDetails();
        setIcons();
        registerAsAnEventHandler(this);
    }

    private void setContactImage(){
        contactImagePlaceholder.setStyle(
                "-fx-background-image: url(\"images/emptyavatar.png\");"
        );
    }

    private void setContactDetails(){
        contactDetailsVBox.setSpacing(30);
        contactDetailsVBox.getChildren().addAll(
                new Label("Name: name here"),
                new Label("Phone: phone here"),
                new Label("Address: address here"),
                new Label("Email: email here")
        );
        contactDetailsVBox.setStyle("-fx-alignment: center-left; -fx-padding: 0 0 0 20");
    }

    private void setIcons(){
        StackPane[] socialIconPlaceholders = {socialIcon1Placeholder, socialIcon2Placeholder, socialIcon3Placeholder, socialIcon4Placeholder};
        String[] imgURLs = {"images/facebook.png", "images/twitter.png", "images/instagram.png", "images/googleplus.png"};
        for(int i=0;i<4;i++){
            socialIconPlaceholders[i].setStyle("-fx-background-image: url("+imgURLs[i]+"); ");
        }
    }

    private void loadPersonPage(ReadOnlyPerson person) {
        loadPage(GOOGLE_SEARCH_URL_PREFIX + person.getName().fullName.replaceAll(" ", "+")
                + GOOGLE_SEARCH_URL_SUFFIX);
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
