//@@author duyson98

package seedu.address.ui;

import javafx.beans.binding.Bindings;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays the full profile of a {@code Person}.
 */
public class PersonProfile extends UiPart<Region> {

    private static final String FXML = "PersonProfile.fxml";

    public final ReadOnlyPerson person;

    @FXML
    private HBox profilePane;
    @FXML
    private Label profileName;
    @FXML
    private TextArea name;
    @FXML
    private TextArea birthday;
    @FXML
    private TextArea phone;
    @FXML
    private TextArea email;
    @FXML
    private TextArea address;
    @FXML
    private FlowPane tags;

    public PersonProfile(ReadOnlyPerson person) {
        super(FXML);
        this.person = person;
        initTags(person);
        bindListeners(person);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        profileName.textProperty().bind(Bindings.convert(person.nameProperty()));
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
    }

    /**
     * @param person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
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
        PersonProfile profile = (PersonProfile) other;
        return person.equals(profile.person);
    }
}
