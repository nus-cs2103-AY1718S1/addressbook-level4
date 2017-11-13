package seedu.address.ui;

import java.util.Random;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.ReadOnlyAddressBook;

// @@author johnweikangong
/**
 * The home panel of the App.
 */
public class HomePanel extends UiPart<Region> {

    private static final String FXML = "HomePanel.fxml";
    private static String[] tips = {
        "Not sure what to do? Type sos to find out more!",
        "Want to add a contact? Type a and let Bevy complete the rest!",
        "Wondering whose birthday is coming? Type bstats to find out!",
        "Want to know how many people has the same tag? Type tstats to find out!",
        "Did you know? Bevy can help you complete your commands!",
        "Type s to select a contact and see more of the person!",
        "Not sure how to edit? Type e and let Bevy do the rest!",
        "Did you know? Bevy can help you find your contacts."};
    private static Random random = new Random();

    @FXML
    private Label totalPersonsAndTags;

    @FXML
    private Text tipsText;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public HomePanel(ReadOnlyAddressBook readOnlyAddressBook) {
        super(FXML);
        setAppData(readOnlyAddressBook.getPersonList().size(), readOnlyAddressBook.getTagList().size());

        registerAsAnEventHandler(this);
    }

    private void setAppData(int totalPersons, int totalTags) {
        Platform.runLater(() -> {
            this.totalPersonsAndTags.setText("You have " + totalPersons + " friends and " + totalTags + " tags");
            this.tipsText.setText(tips[random.nextInt(tips.length)]);
        });
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        logger.info("Home panel new status: " + abce.data.getPersonList().size() + " persons ");
        setAppData(abce.data.getPersonList().size(), abce.data.getTagList().size());
    }
}
