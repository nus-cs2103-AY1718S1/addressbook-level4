package seedu.address.ui;

import java.util.HashMap;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.ReadOnlyPerson;

//@@author jacoblipech
/**
 * Extended person card show more details about a certain contact
 */
public class ExtendedPersonDisplay extends UiPart<Region> {

    private static final String FXML = "ExtendedPersonDisplay.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private VBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label birthday;

    public ExtendedPersonDisplay() {
        super(FXML);
        registerAsAnEventHandler(this);

    }

    /**
     * Updates the contact details displayed on the extended person display
     */
    private void loadMorePersonDetails (ReadOnlyPerson person) {
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().toString());
        email.setText(person.getEmail().toString());
        address.setText(person.getAddress().toString());
    }

    /**
     * Binds the birthday string together for each contact to display in a better format
     * so that it is clearer for the user.
     */
    private void initBirthdayLabel(ReadOnlyPerson person) {
        String initialBirthday = person.getBirthday().getBirthdayNumber();
        String birthdayToDisplay;

        if (!initialBirthday.equals(Birthday.DEFAULT_BIRTHDAY)) {
            birthdayToDisplay = generateBirthdayMsg(initialBirthday);
            birthday.setText(birthdayToDisplay);
        } else {
            birthday.setText(initialBirthday);
        }
    }

    /**
     * Returns the birthday in the format needed for display.
     */
    private String generateBirthdayMsg (String initialBirthday) {
        HashMap<String, String> findSelectedMonth = initializeMonthHashMap();
        String [] splitDates = initialBirthday.split("/", 5);
        String birthdayToDisplay;

        birthdayToDisplay = splitDates[0] + " " + findSelectedMonth.get(splitDates[1]) + " " + splitDates[2];

        return birthdayToDisplay;
    }

    /**
     * Initialize a @HashMap for every integer to the correct month.
     */
    private HashMap<String, String> initializeMonthHashMap () {

        HashMap<String, String> monthFormat = new HashMap<>();
        monthFormat.put("01", "Jan");
        monthFormat.put("02", "Feb");
        monthFormat.put("03", "Mar");
        monthFormat.put("04", "Apr");
        monthFormat.put("05", "May");
        monthFormat.put("06", "Jun");
        monthFormat.put("07", "Jul");
        monthFormat.put("08", "Aug");
        monthFormat.put("09", "Sep");
        monthFormat.put("10", "Oct");
        monthFormat.put("11", "Nov");
        monthFormat.put("12", "Dec");

        return monthFormat;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        initBirthdayLabel(event.getNewSelection().person);
        loadMorePersonDetails(event.getNewSelection().person);
    }

}
