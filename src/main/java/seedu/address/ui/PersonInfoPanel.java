package seedu.address.ui;

import java.io.File;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;

import javafx.scene.shape.Circle;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

//@@author liliwei25
/**
 * Shows the selected Person's info
 */
public class PersonInfoPanel extends UiPart<Region> {

    private static final String FXML = "PersonInfoPanel.fxml";
    private static final String EMPTY = "";
    private static final String DEFAULT = "profiles/default.png";
    private static final String DEFAULT_TEXT = "default";
    private static final int RADIUS = 100;
    private final Logger logger = LogsCenter.getLogger(PersonInfoPanel.class);
    private final ReadOnlyPerson person;
    private final Circle circle = new Circle(RADIUS, RADIUS, RADIUS);

    @FXML
    private ImageView profileImage;
    @FXML
    private Label name;
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

    public PersonInfoPanel(ReadOnlyPerson person) {
        super(FXML);
        setConnections(person);
        this.person = person;
        registerAsAnEventHandler(this);
        setupProfileImage();
    }

    /**
     * Make ImageView round
     */
    private void setupProfileImage() {
        profileImage.setClip(circle);
    }

    /**
     * Show default values when no Person is selected
     */
    private void setDefaultConnections() {
        setConnections(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, null, DEFAULT_TEXT);
    }

    /**
     * Set connections based on Person selected
     *
     * @param person Selected Person
     */
    private void setConnections(ReadOnlyPerson person) {
        if (noPersonSelected(person)) {
            setDefaultConnections();
        } else {
            setConnections(person.getName().fullName, person.getPhone().value, person.getAddress().value,
                    person.getEmail().value, person.getBirthday().value, person.getRemark().value, person.getTags(),
                    person.getPicture().getLocation());
        }
    }

    /**
     * Display selected Person's details on PersonInfoPanel
     */
    private void setConnections(String name, String phone, String address, String email, String birthday,
                                String remark, Set<Tag> tags, String loc) {
        this.name.setText(name);
        this.phone.setText(phone);
        this.address.setText(address);
        this.email.setText(email);
        this.birthday.setText(birthday);
        this.remark.setText(remark);
        this.tags.getChildren().clear();
        if (tags != null) {
            setTags(tags);
        }
        setImage(loc);
    }

    /**
     * Determines if Person selected is valid
     *
     * @param person Selected Person
     * @return True if selected person is null
     */
    private boolean noPersonSelected(ReadOnlyPerson person) {
        return person == null;
    }

    /**
     * Displays selected Person's Tags on PersonInfoPanel
     *
     * @param tagList List of selected Person's Tags
     */
    private void setTags(Set<Tag> tagList) {
        tagList.forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Displays the selected Person's Profile picture on PersonInfoPanel
     *
     * @param loc Image location of selected Person
     */
    private void setImage(String loc) {
        try {
            Image image = getProfileImage(loc);
            profileImage.setImage(image);
        } catch (IllegalArgumentException iae) {
            profileImage.setImage(new Image(DEFAULT));
        }
    }

    /**
     * Gets Image based on image location from selected Person
     *
     * @param loc Image location from selected Person
     * @return Profile Image of selected Person
     */
    private Image getProfileImage(String loc) {
        Image image;
        if (loc.equals(DEFAULT_TEXT)) {
            image = new Image(DEFAULT);
        } else {
            image = getImage(loc);
        }
        return image;
    }

    /**
     * Get image from given location
     *
     * @param loc Given location
     * @return Image at given location or default image if location is invalid
     */
    private Image getImage(String loc) {
        Image image;
        File img = new File(loc);
        if (img.exists()) {
            image = new Image(img.toURI().toString());
        } else {
            image = new Image(DEFAULT);
        }
        return image;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.getNewSelection() == null) {
            setDefaultConnections();
        } else {
            setConnections(event.getNewSelection().person);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonInfoPanel)) {
            return false;
        }

        // state check
        PersonInfoPanel card = (PersonInfoPanel) other;
        return name.getText().equals(card.name.getText())
                && phone.getText().equals(card.phone.getText())
                && address.getText().equals(card.address.getText())
                && email.getText().equals(card.email.getText())
                && birthday.getText().equals(card.birthday.getText())
                && remark.getText().equals(card.remark.getText())
                && person.equals(card.person);
    }
}
