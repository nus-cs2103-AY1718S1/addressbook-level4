package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyPerson person;
    private PersonListPanel personListPanel;

    @FXML
    private HBox cardPane;
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
    //@@author risashindo7
    @FXML
    private Label comment;
    @FXML
    private Label appoint;
    //@@author
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView avatar;
    @FXML
    private CheckBox checkBox;

    public PersonCard(ReadOnlyPerson person, int displayedIndex, PersonListPanel personListPanel) {
        super(FXML);
        this.person = person;
        this.personListPanel = personListPanel;
        id.setText(displayedIndex + ". ");
        initTags(person);
        bindListeners(person);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        //@@author vsudhakar
        avatar.imageProperty().bind(person.getAvatar().avatarImageProperty());
        //@@author
        comment.textProperty().bind(Bindings.convert(person.commentProperty()));
        appoint.textProperty().bind(Bindings.convert(person.appointProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        });
    }

    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
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

    /**
     * actions when checkbox is clicked: this person is added to tickedPersons in personListPanel
     */
    @FXML
    private void onCheckBoxClicked() {
        if (checkBox.isSelected()) {
            personListPanel.getTickedPersons().add(this);
        } else {
            personListPanel.getTickedPersons().remove(this);
        }
    }

    public String getEmail() {
        return email.getText();
    }

    public boolean isTicked() {
        return checkBox.isSelected();
    }

    public String getName() {
        return name.getText();
    }
}
