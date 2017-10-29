package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebView;
import javafx.util.Duration;
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
    private Circle contactImageCircle;

    @FXML
    private BorderPane socialIcon1Placeholder;

    @FXML
    private BorderPane socialIcon2Placeholder;

    @FXML
    private BorderPane socialIcon3Placeholder;

    @FXML
    private BorderPane socialIcon4Placeholder;

    @FXML
    private BorderPane contactImagePlaceholder;

    @FXML
    private VBox contactDetailsVBox;

    //This has a getter method as MainWindow.java needs to
    // access this node to populate it with Logic.getFilteredScheduleList().
    @FXML
    private StackPane schedulePlaceholder;

    private ParallelTransition pt;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        loadDefaultPage();
        //Setup needed JFX nodes which will be updated upon selecting persons
        setupContactImageCircle();
        setupContactDetailsVBox();
        setupScheduleListViewPlaceholder();
        registerAsAnEventHandler(this);
    }

    private void setContactImage() {
        Image img = new Image("images/maleIcon.png");
        contactImageCircle.setVisible(true);
        contactImageCircle.setFill(new ImagePattern(img));
        easeIn(contactImageCircle);
    }

    private void setupContactImageCircle() {
        contactImageCircle = new Circle(250, 250, 90);
        contactImageCircle.setStroke(Color.valueOf("#3fc380"));
        contactImageCircle.setStrokeWidth(5);
        contactImageCircle.radiusProperty().bind(Bindings.min(
                contactImagePlaceholder.widthProperty().divide(3),
                contactImagePlaceholder.heightProperty().divide(3))
        );
        contactImagePlaceholder.setCenter(contactImageCircle);
        contactImageCircle.setVisible(false);
    }

    private void setupContactDetailsVBox() {
        contactDetailsVBox.setSpacing(0);
        contactDetailsVBox.getChildren().addAll(
                new Label(""),
                new Label(""),
                new Label(""),
                new Label("")
        );
        contactDetailsVBox.setStyle("-fx-alignment: center-left; -fx-padding: 0 0 0 10");
    }

    private void setIcons() {
        BorderPane[] socialIconPlaceholders = {
            socialIcon1Placeholder,
            socialIcon2Placeholder,
            socialIcon3Placeholder,
            socialIcon4Placeholder
        };
        String[] imgUrls = {
            "images/facebook.png",
            "images/twitter.png",
            "images/instagram.png",
            "images/googleplus.png"
        };

        for (int i = 0; i < 4; i++) {
            Circle cir = new Circle(250, 250, 30);
            cir.setStroke(Color.valueOf("#3fc380"));
            cir.setStrokeWidth(5);
            cir.radiusProperty().bind(Bindings.min(
                    socialIconPlaceholders[i].widthProperty().divide(3),
                    socialIconPlaceholders[i].heightProperty().divide(3))
            );
            cir.setFill(new ImagePattern(new Image(imgUrls[i])));
            socialIconPlaceholders[i].setCenter(cir);
            easeIn(cir);
        }
    }

    private void setupScheduleListViewPlaceholder() {
        schedulePlaceholder.setVisible(false);
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
        setContactImage();
        setContactDetails(event.getNewSelection().person);
        setIcons();
        setSchedule();
    }

    private void setContactDetails(ReadOnlyPerson person) {
        //Set up name label separately as it has no icons
        contactDetailsVBox.setSpacing(0);
        contactDetailsVBox.getChildren().addAll();

        Label name = (Label) contactDetailsVBox.getChildren().get(0);
        name.setText("" + person.getName());
        name.setStyle("-fx-font-size: 60;");
        name.setWrapText(true);
        easeIn(name);

        //Set values of other labels
        Label phone = (Label) contactDetailsVBox.getChildren().get(1);
        phone.setText("" + person.getPhone());
        Label email = (Label) contactDetailsVBox.getChildren().get(2);
        email.setText("" + person.getEmail());
        Label address = (Label) contactDetailsVBox.getChildren().get(3);
        address.setText("" + person.getAddress());

        //Add images to these labels
        Label[] labels = {phone, email, address};
        String[] iconUrls = {"images/phone.png", "images/mail.png", "images/homeBlack.png"};
        for (int i = 0; i < labels.length; i++) {
            ImageView icon = new ImageView(iconUrls[i]);
            icon.setImage(new Image(iconUrls[i]));
            icon.setPreserveRatio(true);
            icon.setFitWidth(20);
            labels[i].setGraphic(icon);
            labels[i].setWrapText(true);
            labels[i].setStyle("-fx-font-size: 17");
            easeIn(labels[i]);
        }
    }

    private void setSchedule() {
        schedulePlaceholder.setVisible(true);
        //scheduleListView.setStyle("-fx-alignment: center-left; -fx-padding: 0 0 0 10;");
        easeIn(schedulePlaceholder);
    }

    /**
     * Animates any node passed into this method with an ease-in
     */
    private void easeIn(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(400), node);
        ft.setFromValue(0);
        ft.setToValue(1);
        TranslateTransition tt = new TranslateTransition();
        tt.setNode(node);
        tt.setFromX(20);
        tt.setToX(0);
        tt.setDuration(Duration.millis(400));
        tt.setInterpolator(Interpolator.EASE_IN);
        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(ft, tt);
        pt.play();
    }

    public StackPane getSchedulePlaceholder() {
        return schedulePlaceholder;
    }
}
