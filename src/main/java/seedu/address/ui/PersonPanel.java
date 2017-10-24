//@@author A0155754X
package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The main panel of the app that displays all the details of a person in the address book.
 */
public class PersonPanel extends UiPart<Region> {
    private static final String FXML = "PersonPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private ReadOnlyPerson storedPerson;

    @FXML
    private Rectangle defaultScreen;

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

    /**
     * Shows the details of the person selected. Called by the handlePersonPanelSelectionChangedEvent event listener,
     * as well as the handleAddressBookChanged event listener.
     * @param person
     */
    private void showPersonDetails(ReadOnlyPerson person) {
        defaultScreen.setOpacity(0);

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

        storedPerson = person;
    }

    /**
     * Calls showPersonDetails when a person is selected in the person panel, causing their details to be displayed
     * in the main window.
     * @param event
     */
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showPersonDetails(event.getNewSelection().person);
    }

    /**
     * Calls showPersonDetails when the address book is changed. This results in any edits to the currently displayed
     * person being refreshed immediately, instead of the user having to click away and click back to see the changes.
     * @param event
     */
    @Subscribe
    private void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        for (ReadOnlyPerson dataPerson : event.data.getPersonList()) {
            if (storedPerson.getName().equals(dataPerson.getName())) {
                showPersonDetails(dataPerson);
                storedPerson = dataPerson;
                break;
            }
        }
    }
}
