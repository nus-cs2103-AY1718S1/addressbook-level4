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
 * The person information panel of the app.
 */
public class PersonPanel extends UiPart<Region> {

    private static final String FXML = "PersonPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private final Logic logic;

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
     * loads the selected person's information to be displayed.
     */
    private void loadPersonInformation(ReadOnlyPerson person) {
        this.person = updatePersonFromLogic(person);
        name.textProperty().setValue(person.getName().toString());
        phone.textProperty().setValue(person.getPhone().toString());
        address.textProperty().setValue(person.getRoom().toString());
        email.textProperty().setValue(person.getEmail().toString());
        initTags();
        initImage();
        enableButtons(true);
    }

    /**
     * @param state Set button status
     */
    private void enableButtons(boolean state) {
        this.addImageButton.setDisable(!state);
        this.resetImageButton.setDisable(!state);
    }

    /**
     * @param person whose image is to be updated within the filtered persons list
     * @return the updated person
     */
    private ReadOnlyPerson updatePersonFromLogic(ReadOnlyPerson person) {
        List<ReadOnlyPerson> personList = logic.getFilteredPersonList();
        for (ReadOnlyPerson p : personList) {
            if (p.getName().toString().equals(person.getName().toString())
                    && p.getPhone().toString().equals(person.getPhone().toString())) {
                return p;
            }
        }
        return null;
    }

    /**
     * Sets a background color for each tag.
     */
    private void initTags() {
        tags.getChildren().clear();
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + tag.getTagColor());
            tags.getChildren().add(tagLabel);
        });
    }

    /**
     * Initializes image for every person in person card
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
        picture.setFitHeight(person.getPicture().PIC_HEIGHT);
        picture.setFitWidth(person.getPicture().PIC_WIDTH);
    }

    /**
     * Button handler for adding image to person
     */
    @FXML
    private void handleAddImage() {
        FileChooser picChooser = new FileChooser();
        File selectedPic = picChooser.showOpenDialog(null);
        if (selectedPic != null) {
            try {
                person.getPicture().setPictureUrl(person.getName().toString() + person.getPhone().toString() + ".jpg");
                logic.updatePersonListPicture((Person) person);
                if (person.getPicture().checkJarResourcePath()) {
                    ImageIO.write(ImageIO.read(selectedPic), "jpg", new File(person.getPicture().getJarPictureUrl()));
                    FileInputStream fileStream = new FileInputStream(person.getPicture().getJarPictureUrl());
                    Image newPicture = new Image(fileStream);
                    picture.setImage(newPicture);
                } else {
                    ImageIO.write(ImageIO.read(selectedPic), "jpg", new File(person.getPicture().getPictureUrl()));
                    FileInputStream fileStream = new FileInputStream(person.getPicture().getPictureUrl());
                    Image newPicture = new Image(fileStream);
                    picture.setImage(newPicture);
                }
            } catch (Exception e) {
                System.out.println(e + "Cannot set Image of person");
            }
        }
    }

    /**
     * Button handler for resetting a person's image
     */
    @FXML
    private void handleResetImage() {
        try {
            person.getPicture().resetPictureUrl();
            if (person.getPicture().checkJarResourcePath()) {
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
     */
    public void initProjectImage() throws FileNotFoundException {
        File picFile = new File(person.getPicture().getPictureUrl());
        FileInputStream fileStream = new FileInputStream(picFile);
        Image personPicture = new Image(fileStream);
        picture.setImage(personPicture);
        informationPane.getChildren().add(picture);
    }

    /**
     * Handle loading of image in production (i.e. from jar file)
     */
    public void initJarImage() throws FileNotFoundException {
        InputStream in = this.getClass().getResourceAsStream(person.getPicture().getJarPictureUrl());
        Image personPicture = new Image(in);
        picture.setImage(personPicture);
        person.getPicture().setJarResourcePath();
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
