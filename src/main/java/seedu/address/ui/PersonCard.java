package seedu.address.ui;

import java.util.HashMap;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    //@@author jin-ting
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */
    private static String[] colors = {"#009B77", "DD4124", "D65076", "#45BBAC", "#EFC050",
        "#5B5EA6", "#9B2335", "#55B4B0", "#E15D44", "#7FCDCD",
        "#BC243C", "#C3447A", "#98B4D4", "#DFCFBE"};
    private static HashMap<String, String> tagColors = new HashMap<>();
    private static Random random = new Random();

    public final ReadOnlyPerson person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label country;
    @FXML
    private Label address;
    @FXML
    private VBox emails;
    @FXML
    private FlowPane schedules;

    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        setStylesToNameAndId();
        initEmails(person);
        initTags(person);
        initSchedules(person);
        bindListeners(person);
    }

    //@@author 17navasaw
    /**
     * Sets the styles for id and name in the PersonCard.
     */
    private void setStylesToNameAndId() {
        id.getStyleClass().removeAll();
        id.getStyleClass().add("headers");
        name.getStyleClass().removeAll();
        name.getStyleClass().add("headers");
    }

    //@@author jin-ting
    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }

        return tagColors.get(tagValue);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        country.textProperty().bind(Bindings.convert(person.countryProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));

        person.emailProperty().addListener((observable, oldValue, newValue) -> {
            emails.getChildren().clear();
            initEmails(person);
        });

        //@@author CT15
        person.scheduleProperty().addListener((observable, oldValue, newValue) -> {
            schedules.getChildren().clear();
            initSchedules(person);
        });
        //@@author
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
    }

    //@@author CT15
    private void initSchedules(ReadOnlyPerson person) {
        person.getSchedules().forEach(schedule -> schedules.getChildren().add(new Label(
                "Date: " + schedule.getScheduleDate() + " Activity: " + schedule.getActivity())));
    }

    //@@author
    /**
     * Sets the person's emails to the respective UI labels upon startup.
     */
    private void initEmails(ReadOnlyPerson person) {
        person.getEmails().forEach(email -> {
            Label emailLabel = new Label(email.value);
            emails.getChildren().add(emailLabel);
        });
    }

    /**
     * Sets the color and content for each tag upon startup.
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
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
