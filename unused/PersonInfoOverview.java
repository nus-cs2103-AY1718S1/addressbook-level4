package seedu.address.ui;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.PersonSelectedEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.timetable.Timetable;

//@@author zacharytang-unused
/**
 * A UI component that displays a person's data on the main panel
 */
public class PersonInfoOverview extends UiPart<Region> {

    private static final String FXML = "PersonInfoOverview.fxml";
    private ReadOnlyPerson person;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private TimetableDisplay timetableDisplay;

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
    private AnchorPane contactPhotoPane;
    @FXML
    private ImageView contactPhoto;

    @FXML
    private AnchorPane timetablePlaceholder;

    public PersonInfoOverview() {
        super(FXML);

        this.person = null;
        loadDefaultPerson();

        contactPhoto.fitWidthProperty().bind(contactPhotoPane.widthProperty());
        contactPhoto.fitHeightProperty().bind(contactPhotoPane.heightProperty());
        registerAsAnEventHandler(this);
    }

    /**
     * Loads the default person when the app is first started
     */
    private void loadDefaultPerson() {
        name.setText("No person selected");
        gender.setText("");
        matricNo.setText("");
        phone.setText("");
        address.setText("");
        email.setText("");
        birthday.setText("");
        remark.setText("");

        setDefaultContactPhoto();

        timetableDisplay = new TimetableDisplay(null);
        timetablePlaceholder.getChildren().add(timetableDisplay.getRoot());
    }

    /**
     * Updates info with person selected
     */
    private void loadPerson(ReadOnlyPerson person) {
        this.person = person;

        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        gender.textProperty().bind(Bindings.convert(person.genderProperty()));
        matricNo.textProperty().bind(Bindings.convert(person.matricNoProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));

        loadPhoto(person);

        timetablePlaceholder.getChildren().removeAll();

        ArrayList<Timetable> timetableToDisplay = new ArrayList<>();
        timetableToDisplay.add(person.getTimetable());
        timetableDisplay = new TimetableDisplay(timetableToDisplay);
        timetablePlaceholder.getChildren().add(timetableDisplay.getRoot());
    }

    //@@author April0616-unused
    /**
     * Set the default contact photo to the default person.
     */
    public void setDefaultContactPhoto() {
        String defaultPhotoPath = "src/main/resources/images/help_icon.png";
        File defaultPhoto = new File(defaultPhotoPath);
        URI defaultPhotoUri = defaultPhoto.toURI();
        Image defaultImage = new Image(defaultPhotoUri.toString());
        centerImage(defaultImage);
        contactPhoto.setImage(defaultImage);
    }

    /**
     * Load the photo of the specified person.
     * @param person
     */
    public void loadPhoto(ReadOnlyPerson person) {
        String photoPath = person.getPhotoPath().value;
        File photo = new File(photoPath);
        URI photoUri = photo.toURI();
        Image image = new Image(photoUri.toString());

        contactPhoto.setPreserveRatio(true);
        centerImage(image);
        contactPhoto.setImage(image);
    }

    //@@author

    /**
     * Put the image at the center of imageView
     * Credit to trichetriche (Stack Overflow https://stackoverflow.com/users/4676340/trichetriche)
     * https://stackoverflow.com/questions/32781362/centering-an-image-in-an-imageview
     */
    public void centerImage(Image img) {
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = contactPhoto.getFitWidth() / img.getWidth();
            double ratioY = contactPhoto.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            contactPhoto.setX((contactPhoto.getFitWidth() - w) / 2);
            contactPhoto.setY((contactPhoto.getFitHeight() - h) / 2);
        }
    }

    //@@author zacharytang-unused
    @Subscribe
    private void handlePersonSelectedEvent(PersonSelectedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPerson(event.person);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangeEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPerson(event.getNewSelection().person);
    }
}
