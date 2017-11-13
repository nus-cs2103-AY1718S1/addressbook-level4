package seedu.address.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static List<String> colors = new ArrayList<String>();
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    public final ReadOnlyPerson person;
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */
    @FXML
    private HBox cardPane;
    @FXML
    private VBox personVbox;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone = new Label();
    @FXML
    private Label address = new Label();
    @FXML
    private Label email = new Label();
    @FXML
    private Label birthday = new Label();
    @FXML
    private HBox details = new HBox();
    @FXML
    private ImageView phoneImage = new ImageView();
    @FXML
    private ImageView locationImage = new ImageView();
    @FXML
    private ImageView emailImage = new ImageView();
    @FXML
    private ImageView giftImage = new ImageView();
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView pinImage;

    private String personCardIndex;

    //@@author aziziazfar
    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        this.personCardIndex = displayedIndex + "";
        id.setText(displayedIndex + ". ");
        initTags(person);
        initPin(person);
        bindListeners(person);
        /* add colors to colors list*/
        colors.add("cadetblue");
        colors.add("cornflowerblue");
        colors.add("dodgerblue");
        colors.add("deepskyblue");
        colors.add("mediumblue");
        colors.add("royalblue");
        colors.add("steelblue");
        colors.add("lightseagreen");
        colors.add("teal");
        colors.add("steelblue");
    }
    //@@author

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    @FXML
    private void bindListeners(ReadOnlyPerson person) {
        phone.setId("phone");
        address.setId("address");
        email.setId("email");
        birthday.setId("birthday");
        details.setId("details");

        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
        person.pinProperty().addListener((observable, oldValue, newValue) -> {
            if (person.isPinned()) {
                pinImage.setImage(new Image("/images/pin.png"));
            } else {
                pinImage.setImage(null);
            }
        });
        person.selectProperty().addListener((observable, oldValue, newValue) -> {
            details.getChildren().clear();
            if (person.isSelected()) {
                initDetails(person);
                showPersonDetails();
            } else {
                personVbox.getChildren().remove(details);
            }
        });
    }

    //@@author deep4k

    /**
     * Sets the additional info needed for their respective {@code person } properties
     * and adds it inside Hbox details. Hbox details will be added to PersonCard parent
     * node if {@code person} is selected
     */
    private void initDetails(ReadOnlyPerson person) {

        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));

        phone.setMaxHeight(Control.USE_COMPUTED_SIZE);
        phone.setWrapText(true);

        address.setMaxHeight(Control.USE_COMPUTED_SIZE);
        address.setWrapText(true);

        email.setMaxHeight(Control.USE_COMPUTED_SIZE);
        email.setWrapText(true);

        birthday.setMaxHeight(Control.USE_COMPUTED_SIZE);
        birthday.setWrapText(true);

        phoneImage.setImage(new Image("/images/telephone.png"));
        phoneImage.setSmooth(true);
        phoneImage.setPreserveRatio(true);
        phoneImage.setFitHeight(80);
        phoneImage.setFitWidth(40);

        locationImage.setImage(new Image("/images/location.png"));
        locationImage.setSmooth(true);
        locationImage.setPreserveRatio(true);
        locationImage.setFitHeight(100);
        locationImage.setFitWidth(40);

        giftImage.setImage(new Image("/images/gift.png"));
        giftImage.setSmooth(true);
        giftImage.setPreserveRatio(true);
        giftImage.setFitHeight(100);
        giftImage.setFitWidth(40);

        emailImage.setImage(new Image("/images/email.png"));
        emailImage.setSmooth(true);
        emailImage.setPreserveRatio(true);
        emailImage.setFitHeight(100);
        emailImage.setFitWidth(40);

        details.getChildren().add(phoneImage);
        details.getChildren().add(phone);
        details.getChildren().add(locationImage);
        details.getChildren().add(address);
        details.getChildren().add(emailImage);
        details.getChildren().add(email);
        details.getChildren().add(giftImage);
        details.getChildren().add(birthday);
        details.setSpacing(10);
        details.setMaxSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
        details.setAlignment(Pos.CENTER_LEFT);

    }
    //@@author

    /**
     * Shows the additional person details based on whether the person is selected or not
     */
    private void showPersonDetails() {
        personVbox.getChildren().add(details);
        personVbox.setAlignment(Pos.CENTER_LEFT);

    }

    //@@author aziziazfar

    /**
     * * Initialises the tags for {@code Person}
     * with the color properties.
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName)
                    + ";-fx-background-radius: 3 3 3 3");
            tags.getChildren().add(tagLabel);
        });
    }

    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue) && !colors.isEmpty()) {
            tagColors.put(tagValue, colors.get(0));
            colors.add(colors.get(0)); // keeps the loop
            colors.remove(0);
        }

        return tagColors.get(tagValue);
    }

    //@@author Alim95

    /**
     * Sets the image for pinned person
     */
    private void initPin(ReadOnlyPerson person) {
        if (person.isPinned()) {
            pinImage.setImage(new Image("/images/pin.png"));
        } else {
            pinImage.setImage(null);
        }
    }
    //@@author

    public String getPersonCardIndex() {
        return this.personCardIndex;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
