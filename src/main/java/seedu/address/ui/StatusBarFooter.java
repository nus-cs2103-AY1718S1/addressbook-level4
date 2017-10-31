package seedu.address.ui;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.events.model.AddressBookChangedEvent;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private StatusBar totalPersons;

    public StatusBarFooter(int totalPersons) {
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
