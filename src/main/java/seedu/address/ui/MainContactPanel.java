package seedu.address.ui;

import java.io.File;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class MainContactPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";

    private static final String FXML = "MainContactPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

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

    @FXML
    private StackPane schedulePlaceholder;

    //This is needed for setting the click listener in setIcons(), if not the
    //circles won't be able to pass a parameter for the method it calls inside
    //its listener.
    private ReadOnlyPerson currentPerson;

    private ParallelTransition pt;

    public MainContactPanel() {
        super(FXML);
        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        //Setup needed JFX nodes which will be updated upon selecting persons
        setupContactImageCircle();
        setupContactDetailsVBox();
        setupScheduleListViewPlaceholder();
        registerAsAnEventHandler(this);
    }

    private void setContactImage(ReadOnlyPerson person) throws MalformedURLException {
        Image img;
        if ("maleIcon.png".equals(person.getProfPic().getPath())) {
            img = new Image("images/maleIcon.png");
        } else {
            try {
                File tmp = new File("images/" + person.getProfPic().getPath());
                if (tmp.exists()) {
                    img = new Image(new File("images/" + person.getProfPic().getPath()).toURI().toURL().toString());
                } else { // Failsafe to set contact's image to default if set image is missing
                    img = new Image("images/maleIcon.png");
                }
            } catch (MalformedURLException e) {
                throw new MalformedURLException("URL is malformed in setContactImage()");
            }
        }

        contactImageCircle.setVisible(true);
        contactImageCircle.setFill(new ImagePattern(img));
        easeIn(contactImageCircle);
        currentPerson = person;
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
            //Set up mouse click listeners to run method to open social pages
            cir.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    openSocialIconPage(currentPerson);
                }
            });
            cir.setFill(new ImagePattern(new Image(imgUrls[i])));
            socialIconPlaceholders[i].setCenter(cir);
            easeIn(cir);
        }
    }

    /**
     * Loads the social page in a new window.
     * There is no controller file for the social media window fxml
     * as it is essentially only a WebView.
     */
    private void openSocialIconPage(ReadOnlyPerson person) {
        try {
            //Load the component
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/SocialMediaPageWindow.fxml"));
            AnchorPane parent = fxmlLoader.load();
            //Get the webview from the loaded component then put URL
            WebView socialPageView = (WebView) parent.getChildren().get(0);
            String temp = person.getSocialMedia().iterator().next().getName().url;
            socialPageView.getEngine().load(temp);
            //Create the scene and stage
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            //Setup window size based on the user's screen size
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setWidth(screenBounds.getWidth() / 1.4);
            stage.setHeight(screenBounds.getHeight() / 1.2);
            //Set title and show the scene
            stage.setTitle("Social Media Window");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new window for social media page.", e);
        }
    }

    private void setupScheduleListViewPlaceholder() {
        schedulePlaceholder.setVisible(false);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event)
            throws MalformedURLException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setContactImage(event.getNewSelection().person);
        setContactDetails(event.getNewSelection().person);
        setIcons();
        setSchedule(event.getNewSelection().person);
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
        currentPerson = person;
    }

    private void setSchedule(ReadOnlyPerson person) {
        schedulePlaceholder.setVisible(true);
        ScheduleListPanel scheduleList = new ScheduleListPanel(person.scheduleProperty().get().asObservableList());
        schedulePlaceholder.getChildren().add(scheduleList.getRoot());
        //scheduleListView.setStyle("-fx-alignment: center-left; -fx-padding: 0 0 0 10;");
        //easeIn(schedulePlaceholder);
        easeIn(schedulePlaceholder);
        currentPerson = person;
    }

    /**
     * Animates any node passed into this method with an ease-in
     */
    private void easeIn(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(500), node);
        ft.setFromValue(0);
        ft.setToValue(1);
        TranslateTransition tt = new TranslateTransition();
        tt.setNode(node);
        tt.setFromY(20);
        tt.setToY(0);
        tt.setDuration(Duration.millis(500));
        tt.setInterpolator(Interpolator.EASE_IN);
        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(ft, tt);
        pt.play();
    }

    public StackPane getSchedulePlaceholder() {
        return schedulePlaceholder;
    }
}
