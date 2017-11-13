package seedu.address.ui;

import java.io.File;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.PersonAddressDisplayDirectionsEvent;
import seedu.address.commons.events.model.PersonAddressDisplayMapEvent;
import seedu.address.commons.events.ui.PersonHasBeenDeletedEvent;
import seedu.address.commons.events.ui.PersonHasBeenModifiedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.PersonSelectedEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author April0616

/**
 * A UI component that displays a person's data on the main panel
 */
public class PersonInfoPanel extends UiPart<Region> {

    private static final String FXML = "PersonInfoPanel.fxml";
    private static String DEFAULT_PHOTO_PATH = "/images/defaultPhoto.jpg";

    private ReadOnlyPerson person;
    private ReadOnlyPerson currentlyViewedPerson;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private Circle photoCircle;
    @FXML
    private Label name;
    @FXML
    private Label gender;
    @FXML
    private Label matricNo;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label birthday;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;

    /**
     * Initializes the person information panel.
     */
    public PersonInfoPanel() {
        super(FXML);
        this.person = null;
        loadDefaultPerson();

        registerAsAnEventHandler(this);
    }

    /**
     * Loads the default person when the app is first started
     */
    private void loadDefaultPerson() {
        name.setText("Person X");
        gender.setText("");
        matricNo.setText("");
        phone.setText("");
        address.setText("");
        email.setText("");
        birthday.setText("");
        remark.setText("");
        tags.getChildren().clear();

        setDefaultContactPhoto();
        currentlyViewedPerson = null;
        logger.info("Currently Viewing: Default Person");
    }

    /**
     * Updates info with person selected
     */
    private void loadPerson(ReadOnlyPerson person) {
        this.person = person;
        tags.getChildren().clear();
        initTags(person);

        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        gender.textProperty().bind(Bindings.convert(person.genderProperty()));
        matricNo.textProperty().bind(Bindings.convert(person.matricNoProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });

        loadPhoto(person);

        currentlyViewedPerson = person;
        logger.info("Currently Viewing: " + currentlyViewedPerson.getName());
    }

    //@@author nbriannl
    /**
     * Clears the binds to allow to loadDefaultPerson() again
     */
    private void clearBind() {
        name.textProperty().unbind();;
        gender.textProperty().unbind();
        matricNo.textProperty().unbind();
        phone.textProperty().unbind();
        address.textProperty().unbind();
        email.textProperty().unbind();
        birthday.textProperty().unbind();
        remark.textProperty().unbind();
    }

    //@@author
    /**
     * Initializes the tags for person list
     * @param person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
                Label tagLabel = new Label(tag.tagName);
                tagLabel.setStyle("-fx-background-color: " + TagColorMap.getInstance().getTagColor(tag.tagName));
                tags.getChildren().add(tagLabel);
            }
        );
    }

    //@@author April0616
    /**
     * Sets the default contact photo.
     */
    public void setDefaultContactPhoto() {
        Image defaultImage = new Image(MainApp.class.getResourceAsStream(DEFAULT_PHOTO_PATH));
        photoCircle.setFill(new ImagePattern(defaultImage));
    }

    /**
     * Loads the photo of the specified person.
     * @param person
     */
    public void loadPhoto(ReadOnlyPerson person) {
        String prefix = "src/main/resources";
        //String photoPath = person.getPhotoPath().value.substring(prefix.length());
        String photoPathString = person.getPhotoPath().value;
        Image image;

        if (photoPathString.equals("")) {  //default male and female photos
            image = getDefaultPhotoByGender();
        } else {
            File contactImg = new File(photoPathString);
            if (contactImg.exists() && !contactImg.isDirectory()) {
                String url = contactImg.toURI().toString();
                image = new Image(url);
            } else {
                image = getDefaultPhotoByGender();
            }
        }

        photoCircle.setFill(new ImagePattern(image));
    }

    /**
     * Gets the default photo by gender. If the gender is not specifed, then return the default photo without gender.
     * @return Image of the according default photo
     */
    private Image getDefaultPhotoByGender() {
        String photoPathString = "";

        if (person.getGender().toString().equals("Male")) {
            photoPathString = "/images/default_male.jpg";
        } else if (person.getGender().toString().equals("Female")) {
            photoPathString = "/images/default_female.jpg";
        } else {
            photoPathString = "/images/defaultPhoto.jpg";
        }
        return new Image(MainApp.class.getResourceAsStream(photoPathString));
    }

    //@@author zacharytang
    @Subscribe
    private void handlePersonAddressDisplayMapEvent(PersonAddressDisplayMapEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPerson(event.person);
    }

    @Subscribe
    private void handlePersonAddressDisplayDirectionsEvent(PersonAddressDisplayDirectionsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPerson(event.person);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangeEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPerson(event.getNewSelection().person);
    }

    @Subscribe
    private void handlePersonSelectedEvent(PersonSelectedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPerson(event.person);
    }

    //@@author nbriannl
    @Subscribe
    private void handlePersonHasBeenModifiedEvent(PersonHasBeenModifiedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (currentlyViewedPerson != null && currentlyViewedPerson.equals(event.oldPerson)) {
            loadPerson(event.newPerson);
        }
    }

    //@@author nbriannl
    @Subscribe
    private void handlePersonHasBeenDeletedEvent(PersonHasBeenDeletedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (currentlyViewedPerson != null && currentlyViewedPerson.equals(event.deletedPerson)) {
            clearBind();
            loadDefaultPerson();
        }
    }

    //@@author nbriannl
    @Subscribe
    private void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.data.getPersonList().size() == 0 && event.data.getTagList().size() == 0) {
            clearBind();
            loadDefaultPerson();
        }
    }
}
