package seedu.room.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import seedu.room.commons.core.LogsCenter;
import seedu.room.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.room.logic.Logic;
import seedu.room.model.person.Person;
import seedu.room.model.person.ReadOnlyPerson;

//@@author shitian007
/**
 * An UI Component that displays additional information for a selected {@code Person}
 */
public class PersonPanel extends UiPart<Region> {

    private static final String FXML = "PersonPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private final Logic logic;

    private ReadOnlyPerson resident;

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
    @FXML
    private Button addImageButton;
    @FXML
    private Button resetImageButton;

    public PersonPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        loadDefaultScreen();
        registerAsAnEventHandler(this);
    }

    /**
     * Sets the default parameters when the app starts up and no one is selected
     */
    private void loadDefaultScreen() {
        name.textProperty().setValue("No Resident Selected");
        phone.textProperty().setValue("-");
        address.textProperty().setValue("-");
        email.textProperty().setValue("-");
        enableButtons(false);
    }

    /**
     * Loads the properties of the selected {@code Person}
     * This method is called whenever an update is made to the selected resident or a new resident is selected
     * @param resident whose properties are updated in the Person Panel UI
     */
    private void loadPersonInformation(ReadOnlyPerson resident) {
        this.resident = updatePersonFromLogic(resident);
        name.textProperty().setValue(resident.getName().toString());
        phone.textProperty().setValue(resident.getPhone().toString());
        address.textProperty().setValue(resident.getRoom().toString());
        email.textProperty().setValue(resident.getEmail().toString());
        initTags();
        initImage();
        enableButtons(true);
    }

    /**
     * @param state of button set
     */
    private void enableButtons(boolean state) {
        this.addImageButton.setDisable(!state);
        this.resetImageButton.setDisable(!state);
    }

    /**
     * @param resident whose image is to be updated within the filtered persons list
     * @return a {@code Person} that is the updated resident
     */
    private ReadOnlyPerson updatePersonFromLogic(ReadOnlyPerson resident) {
        List<ReadOnlyPerson> personList = logic.getFilteredPersonList();
        for (ReadOnlyPerson p : personList) {
            if (p.getName().toString().equals(resident.getName().toString())
                    && p.getPhone().toString().equals(resident.getPhone().toString())) {
                return p;
            }
        }
        return null;
    }

    /**
     * Sets a background color for each tag of the selected {@code Person}
     */
    private void initTags() {
        tags.getChildren().clear();
        resident.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + tag.getTagColor());
            tags.getChildren().add(tagLabel);
        });
    }

    /**
     * Initializes image for the selected resident {@code Person}
     */
    private void initImage() {
        try {
            initProjectImage();
        } catch (Exception pfnfe) {
            try {
                initJarImage();
            } catch (Exception jfnfe) {
                ;
            }
        }
        picture.setFitHeight(resident.getPicture().PIC_HEIGHT);
        picture.setFitWidth(resident.getPicture().PIC_WIDTH);
    }

    /**
     * GUI Button handler for adding image to resident
     */
    @FXML
    private void handleAddImage() {
        FileChooser picChooser = new FileChooser();
        File selectedPic = picChooser.showOpenDialog(null);
        if (selectedPic != null) {
            try {
                resident.getPicture().setPictureUrl(resident.getName().toString()
                    + resident.getPhone().toString() + ".jpg");
                logic.updatePersonListPicture((Person) resident);
                FileInputStream fileStream;
                if (resident.getPicture().checkJarResourcePath()) {
                    ImageIO.write(ImageIO.read(selectedPic), "jpg",
                        new File(resident.getPicture().getJarPictureUrl()));
                    fileStream = new FileInputStream(resident.getPicture().getJarPictureUrl());
                } else {
                    ImageIO.write(ImageIO.read(selectedPic), "jpg",
                        new File(resident.getPicture().getPictureUrl()));
                    fileStream = new FileInputStream(resident.getPicture().getPictureUrl());
                }
                Image newPicture = new Image(fileStream);
                picture.setImage(newPicture);
            } catch (Exception e) {
                System.out.println(e + "Cannot set Image of resident");
            }
        }
    }

    /**
     * GUI Button handler for resetting a resident's image
     */
    @FXML
    private void handleResetImage() {
        try {
            resident.getPicture().resetPictureUrl();
            if (resident.getPicture().checkJarResourcePath()) {
                initJarImage();
            } else {
                initProjectImage();
            }
        } catch (Exception e) {
            System.out.println("Placeholder Image not found");
        }
    }

    /**
     * Handle loading of image during development
     * @throws FileNotFoundException when image url is invalid
     */
    public void initProjectImage() throws FileNotFoundException {
        File picFile = new File(resident.getPicture().getPictureUrl());
        FileInputStream fileStream = new FileInputStream(picFile);
        Image personPicture = new Image(fileStream);
        picture.setImage(personPicture);
        informationPane.getChildren().add(picture);
    }

    /**
     * Handle loading of image in production (i.e. from jar file)
     * @throws FileNotFoundException when image url is invalid
     */
    public void initJarImage() throws FileNotFoundException {
        InputStream in = this.getClass().getResourceAsStream(resident.getPicture().getJarPictureUrl());
        Image personPicture = new Image(in);
        picture.setImage(personPicture);
        resident.getPicture().setJarResourcePath();
        informationPane.getChildren().add(picture);
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
