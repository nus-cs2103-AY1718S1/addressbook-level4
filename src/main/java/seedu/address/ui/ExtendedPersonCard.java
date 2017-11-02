package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author limcel
/**
 * Extended Person Card Panel that displays the details of a Person
 */
public class ExtendedPersonCard extends UiPart<Region> {

    private static final String FXML = "ExtendedPersonCard.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private ObservableList<ReadOnlyPerson> people;

    @FXML
    private HBox cardpane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label formClass;
    @FXML
    private Label grades;
    @FXML
    private Label postalCode;
    @FXML
    private Label email;
    @FXML
    private Label remark;

    public ExtendedPersonCard() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     * Updates person details as displayed on the extended person card panel
     */
    protected void loadPersonDetails(ReadOnlyPerson person) {
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().toString());
        address.setText(person.getAddress().toString());
        formClass.setText(person.getFormClass().toString());
        grades.setText(person.getGrades().toString());
        postalCode.setText(person.getPostalCode().toString());
        email.setText(person.getEmail().toString());
        remark.setText(person.getRemark().toString());
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonDetails(event.getNewSelection().person);
    }

}
