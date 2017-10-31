package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
    private Label addressLabelContinue;

    @FXML
    private VBox eventsBox;

    @FXML
    private TextArea eventsArea;

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
        addressLabelContinue.setText("");
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

        addressLabelContinue.setText("");
        String[] address = person.getAddress().toString().split(" ");
        StringBuffer firstBuffer = new StringBuffer();
        int index = 0;
        while (index < address.length && address[index].length() <= 32 - firstBuffer.length()) {
            firstBuffer.append(address[index]);
            firstBuffer.append(" ");
            index++;
        }
        String firstAddress = firstBuffer.toString();
        addressLabel.setText(firstAddress);
        StringBuffer secondBuffer = new StringBuffer();
        for (; index < address.length; index++) {
            secondBuffer.append(address[index]);
            secondBuffer.append(" ");
        }
        if (secondBuffer.length() != 0) {
            String secondAddress = secondBuffer.toString();
            addressLabelContinue.setText(secondAddress);
        }

        eventsArea.setText("");
        StringBuffer stringBuffer = new StringBuffer();
        int counter = 1;
        for (Event e: person.getEvents()) {
            stringBuffer.append(counter + ". " + e.getEventName().fullName + " -- "
                    + e.getEventTime().toString() + "\n");
            counter++;
        }
        eventsArea.setText(stringBuffer.toString());
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

        addressLabelContinue.setText("");
        String[] address = person.getAddress().toString().split(" ");
        StringBuffer firstBuffer = new StringBuffer();
        int index = 0;
        while (index < address.length && address[index].length() <= 32 - firstBuffer.length()) {
            firstBuffer.append(address[index]);
            firstBuffer.append(" ");
            index++;
        }
        String firstAddress = firstBuffer.toString();
        addressLabel.setText(firstAddress);
        StringBuffer secondBuffer = new StringBuffer();
        for (; index < address.length; index++) {
            secondBuffer.append(address[index]);
            secondBuffer.append(" ");
        }
        if (secondBuffer.length() != 0) {
            String secondAddress = secondBuffer.toString();
            addressLabelContinue.setText(secondAddress);
        }

        eventsArea.setText("");
        StringBuffer stringBuffer = new StringBuffer();
        int counter = 1;
        for (Event e: person.getEvents()) {
            stringBuffer.append(counter + ". " + e.getEventName().fullName + " -- "
                    + e.getEventTime().toString() + "\n");
            counter++;
        }
        eventsArea.setText(stringBuffer.toString());
    }
}
