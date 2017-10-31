package seedu.address.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Profile Panel of the app
 */
public class ProfilePanel extends UiPart<Region> {

    private static final String FXML = "ProfilePanel.fxml";
    private static final String DEFAULT_IMAGE_STORAGE_PREFIX = "data/";
    private static final String DEFAULT_IMAGE_STORAGE_SUFFIX = ".png";
    private static final String DEFAULT_PROFILE_PICTURE_PATH = "/images/default_profile_picture.png";
    private static String[] colors = { "red", "yellow", "blue", "orange", "indigo", "green", "violet", "black" };
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private final FileChooser fileChooser = new FileChooser();
    private ReadOnlyPerson person;
    private Stage primaryStage;

    @FXML
    private HBox profilePane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label occupation;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label remark;
    @FXML
    private Label website;
    @FXML
    private FlowPane tags;
    @FXML
    private javafx.scene.image.ImageView profilePicture;
    @FXML
    private Button changePictureButton;

    public ProfilePanel(Stage primaryStage) {
        super(FXML);
        this.person = new Person();
        this.primaryStage = primaryStage;
        initChangePictureButton();
        initStyle();
        refreshState();
        registerAsAnEventHandler(this);
    }

    private void refreshState() {
        bindListeners();
        initPicture();
        initTags();
    }

    /**
     * Initialize change picture button
     */
    private void initChangePictureButton() {
        changePictureButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        setExtFilters(fileChooser);
                        File file = fileChooser.showOpenDialog(primaryStage);
                        if (file != null) {
                            saveImageToStorage(file);
                            refreshState();
                        }
                    }
                }
        );
    }

    private void setExtFilters(FileChooser chooser) {
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    /**
     * Save a given image file to storage
     * @param file
     */
    private void saveImageToStorage(File file) {
        Image image = new Image(file.toURI().toString());
        String phoneNum = person.getPhone().value;

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png",
                    new File(DEFAULT_IMAGE_STORAGE_PREFIX + phoneNum + DEFAULT_IMAGE_STORAGE_SUFFIX));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Initialize profile picture
     */
    private void initPicture() {
        BufferedImage bufferedImage;
        String phoneNum = person.getPhone().value;

        try {
            bufferedImage = ImageIO.read(new File(DEFAULT_IMAGE_STORAGE_PREFIX + phoneNum
                    + DEFAULT_IMAGE_STORAGE_SUFFIX));
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            profilePicture.setImage(image);
        } catch (IOException ioe1) {
            Image image = new Image(MainApp.class.getResourceAsStream(DEFAULT_PROFILE_PICTURE_PATH));
            profilePicture.setImage(image);
        }
    }

    /**
     * Initialize panel's style such as color
     */
    private void initStyle() {
        profilePane.setStyle("-fx-background-color: #FFFFFF;");
    }

    /**
     * Change current displayed profile
     * @param person
     */
    public void changeProfile(ReadOnlyPerson person) {
        this.person = person;
        refreshState();
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners() {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        occupation.textProperty().bind(Bindings.convert(person.occupationProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
        website.textProperty().bind(Bindings.convert(person.websiteProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            //person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
            initTags();
        });
    }

    /**
     *javadoc comment
     */
    private void initTags() {
        //person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        tags.getChildren().clear();
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    //The method below retrieves the color for the specific tag
    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }
        return tagColors.get(tagValue);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        changeProfile(event.getNewSelection().person);
    }
}
