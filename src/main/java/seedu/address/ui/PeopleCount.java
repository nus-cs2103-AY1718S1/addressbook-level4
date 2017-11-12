package seedu.address.ui;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.events.model.AddressBookChangedEvent;

//@@author 500poundbear
/**
 * A ui for the people count that is displayed at the header of the application.
 */
public class PeopleCount extends UiPart<Region> {

    private static final String FXML = "PeopleCount.fxml";

    @FXML
    private StatusBar totalPersons;

    public PeopleCount(int totalPersons) {
        super(FXML);
        setTotalPersons(totalPersons);
        registerAsAnEventHandler(this);
    }

    private void setTotalPersons(int totalPersons) {
        this.totalPersons.setText(Integer.toString(totalPersons));
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        setTotalPersons(abce.data.getPersonList().size());
    }
}
