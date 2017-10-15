package seedu.address.ui.util;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.PersonCard;
import seedu.address.ui.PersonListPanel;
import seedu.address.ui.UiPart;

/**
 * The Person Detail Panel of the App.
 */
public class PersonDetailPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private ObservableList<ReadOnlyPerson> personList;
    private PersonListPanel personListPanel;

    @FXML
    private HBox cardPane;

    @FXML
    private Label name;

    public PersonDetailPanel(PersonListPanel personListPanel) {
        super(FXML);
        this.personListPanel = personListPanel;
        initialiseFields();
        setEventHandlerForSelectionChangeEvent();
    }

    /**
     * Initialize the panel with empty fields
     */
    private void initialiseFields() {
        name.setText("");
    }

    private void setEventHandlerForSelectionChangeEvent() {
        personListPanel.getPersonList().getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    private void showPersonDetails(PersonCard personCard) {
        ReadOnlyPerson person = personCard.person;

        name.setText(person.getName().toString());
    }

}
