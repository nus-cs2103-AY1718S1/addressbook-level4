//@@author shitian007
package seedu.room.ui;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import seedu.room.commons.core.LogsCenter;
import seedu.room.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.room.model.person.ReadOnlyPerson;

/**
 * The person information panel of the app.
 */
public class PersonPanel extends UiPart<Region> {

    private static final String FXML = "PersonPanel.fxml";
    private static String[] colors = {"red", "yellow", "blue", "orange", "brown", "green", "pink", "black", "grey"};
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private ReadOnlyPerson person;

    @FXML
    private ImageView picture;
    @FXML
    private VBox informationPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public PersonPanel() {
        super(FXML);
        loadDefaultScreen();
        registerAsAnEventHandler(this);
    }

    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }

        return tagColors.get(tagValue);
    }

    /**
     * Sets the default parameters when the app starts up and no one is selected
     */
    private void loadDefaultScreen() {
        name.textProperty().setValue("No Resident Selected");
        phone.textProperty().setValue("-");
        address.textProperty().setValue("-");
        email.textProperty().setValue("-");
    }

    /**
     * loads the selected person's information to be displayed.
     * @param person
     */
    private void loadPersonInformation(ReadOnlyPerson person) {
        this.person = person;
        name.textProperty().setValue(person.getName().toString());
        phone.textProperty().setValue(person.getPhone().toString());
        address.textProperty().setValue(person.getRoom().toString());
        email.textProperty().setValue(person.getEmail().toString());
        initTags();
        initImage();
    }

    /**
     * Sets a background color for each tag.
     * @param
     */
    private void initTags() {
        tags.getChildren().clear();
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    /**
     * Initializes image for every person in person card
     */
    private void initImage() {
        try {
            File picFile = new File(person.getPicture().getPictureUrl());
            FileInputStream fileStream = new FileInputStream(picFile);
            Image personPicture = new Image(fileStream);
            picture.setFitHeight(person.getPicture().PIC_HEIGHT);
            picture.setFitWidth(person.getPicture().PIC_WIDTH);
            picture.setImage(personPicture);
            informationPane.getChildren().add(picture);
            picture.setOnMouseClicked((MouseEvent e) -> {
                System.out.println("Image clicked");
                handleAddImage();
            });
        } catch (Exception e) {
            System.out.println("Image not found");
        }
    }

    /**
     * Handler for adding image to person
     */
    @FXML
    private void handleAddImage() {
        FileChooser picChooser = new FileChooser();
        File selectedPic = picChooser.showOpenDialog(null);
        if (selectedPic != null) {
            try {
                person.getPicture().setPictureUrl(person.getName().toString() + person.getPhone().toString() + ".jpg");
                ImageIO.write(ImageIO.read(selectedPic), "jpg", new File(person.getPicture().getPictureUrl()));
                FileInputStream fileStream = new FileInputStream(person.getPicture().getPictureUrl());
                Image newPicture = new Image(fileStream);
                picture.setImage(newPicture);
            } catch (Exception e) {
                System.out.println(e + "Invalid File");
            }
        } else {
            System.out.println("Invalid File");
        }
    }

    /**
     * Handler for deleting a person's image
     */
    @FXML
    private void handleDeleteImage() {
        try {
            person.getPicture().resetPictureUrl();
            File picFile = new File(person.getPicture().getPictureUrl());
            FileInputStream fileStream = new FileInputStream(picFile);
            Image personPicture = new Image(fileStream);
            picture.setImage(personPicture);
        } catch (Exception e) {
            System.out.println("Placeholder Image not found");
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonInformation(event.getNewSelection().person);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonPanel)) {
            return false;
        }

        return false;
    }
}
