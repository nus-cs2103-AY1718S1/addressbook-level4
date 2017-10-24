//@@author A0155754X
package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

public class PersonPanel extends UiPart<Region>{
    private static final String FXML = "PersonPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private Label nameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label companyLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label priorityLabel;

    @FXML
    private Label noteLabel;

    @FXML
    private FlowPane tagsPane;

    @FXML
    private ImageView imageView;

    public PersonPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    private void showPersonDetails(ReadOnlyPerson person) { //called by PersonPanelSelectionChanged event
        nameLabel.setText(person.getName().toString());
        phoneLabel.setText(person.getPhone().toString());
        emailLabel.setText(person.getEmail().toString());
        addressLabel.setText(person.getAddress().toString());
        companyLabel.setText(person.getCompany().toString());
        positionLabel.setText(person.getPosition().toString());
        priorityLabel.setText(person.getPriority().toString());
        statusLabel.setText(person.getStatus().toString());
        noteLabel.setText(person.getNote().toString());
        tagsPane.getChildren().removeAll(tagsPane.getChildren());
        person.getTags().forEach(tag -> tagsPane.getChildren().add(new Label(tag.tagName)));
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showPersonDetails(event.getNewSelection().person);
    }
}
