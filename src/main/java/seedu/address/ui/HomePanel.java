package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * The home panel of the App.
 */
public class HomePanel extends UiPart<Region> {

    private static final String FXML = "HomePanel.fxml";

    @FXML
    private Label totalPersonsAndTags;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public HomePanel(ReadOnlyAddressBook readOnlyAddressBook) {
        super(FXML);
        setData(readOnlyAddressBook.getPersonList().size(), readOnlyAddressBook.getTagList().size());

        registerAsAnEventHandler(this);
    }

    private void setData(int totalPersons, int totalTags) {
        this.totalPersonsAndTags.setText("You have " + totalPersons + " friends and " + totalTags + " tags");
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        logger.info("Home panel new status: " + abce.data.getPersonList().size() + " persons and "
                + abce.data.getTagList().size());
        setData(abce.data.getPersonList().size(), abce.data.getTagList().size());
    }
}
