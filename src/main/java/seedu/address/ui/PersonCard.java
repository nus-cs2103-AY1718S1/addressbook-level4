package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    public final ReadOnlyPerson person;
    public final Index index;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exceptions will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label company;
    @FXML
    private Label position;
    @FXML
    private Label priority;
    @FXML
    private FlowPane tags;
    @FXML
    private Pane pane;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        this.index = Index.fromZeroBased(displayedIndex);
        id.setText(displayedIndex + ". ");
        initTags(person);
        bindListeners(person);
        registerAsAnEventHandler(this);

        setPriorityTextFill();
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        //@@author sebtsh
        company.textProperty().bind(Bindings.convert(person.companyProperty()));
        position.textProperty().bind(Bindings.convert(person.positionProperty()));
        priority.textProperty().bind(Bindings.convert(person.priorityProperty()));
        //@@author
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
    //@@author sebtsh

    /**
     *Changes the CSS rules to apply different colours to different priorities.
     */
    private void setPriorityTextFill() {
        if (priority.textProperty().getValue().equals("H")) {
            pane.setId("highpriority");
        } else if (priority.textProperty().getValue().equals("M")) {
            pane.setId("mediumpriority");
        } else if (priority.textProperty().getValue().equals("L")) {
            pane.setId("lowpriority");
        }
    }

    /**
     * Checks the priority when the address book is changed and sets the text fill accordingly.
     * @param event
    */
    @Subscribe
    private void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.fine(LogsCenter.getEventHandlingLogMessage(event));
        setPriorityTextFill();
    }
}
