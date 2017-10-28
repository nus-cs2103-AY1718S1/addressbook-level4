package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ClearPersonListEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.event.Event;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Contact Details Panel of the App.
 */
public class PersonDetailsPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailsPanel.fxml";

    private ObservableList<ReadOnlyPerson> personList;

    private final Logger logger = LogsCenter.getLogger(PersonDetailsPanel.class);

    @FXML
    private GridPane personDetailsGrid;

    @FXML
    private Label nameLabel;

    @FXML
    private Label birthdayLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private VBox eventsBox;

    public PersonDetailsPanel(ObservableList<ReadOnlyPerson> personList) {
        super(FXML);
        this.personList = personList;
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleClearPersonListEvent(ClearPersonListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        nameLabel.setText("Person Name");
        birthdayLabel.setText("");
        phoneLabel.setText("");
        emailLabel.setText("");
        addressLabel.setText("");
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ReadOnlyPerson person = personList.get(event.targetIndex);
        nameLabel.setText(person.getName().fullName);
        if (person.getBirthday().value.equals("01/01/1900")) {
            birthdayLabel.setText("");
        } else {
            birthdayLabel.setText(person.getBirthday().toString());
        }
        phoneLabel.setText(person.getPhone().toString());
        emailLabel.setText(person.getEmail().toString());
        addressLabel.setText(person.getAddress().toString());

        eventsBox.getChildren().clear();
        Label title = new Label();
        title.setText("Events:");
        title.setStyle("-fx-font-size: 32px");
        eventsBox.getChildren().add(title);
        ObservableList<String> events = FXCollections.observableArrayList();
        for (Event e: person.getEvents()) {
            Label label = new Label();
            label.setText(e.getEventName().fullName + " -- " + e.getEventTime().toString());
            label.setStyle("-fx-font-size: 20px");
            eventsBox.getChildren().add(label);
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ReadOnlyPerson person = event.getNewSelection().person;
        nameLabel.setText(person.getName().fullName);
        if (person.getBirthday().value.equals("01/01/1900")) {
            birthdayLabel.setText("");
        } else {
            birthdayLabel.setText(person.getBirthday().toString());
        }
        phoneLabel.setText(person.getPhone().toString());
        emailLabel.setText(person.getEmail().toString());
        addressLabel.setText(person.getAddress().toString());

        eventsBox.getChildren().clear();
        Label title = new Label();
        title.setText("Events:");
        title.setStyle("-fx-font-size: 32px");
        eventsBox.getChildren().add(title);
        ObservableList<String> events = FXCollections.observableArrayList();
        for (Event e: person.getEvents()) {
            Label label = new Label();
            label.setText(e.getEventName().fullName + " -- " + e.getEventTime().toString() + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            label.setStyle("-fx-font-size: 20px");
            eventsBox.getChildren().add(label);
        }
    }
}
