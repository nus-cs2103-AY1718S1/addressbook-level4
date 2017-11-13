package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.TimetableDisplayEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.timetable.Timetable;

//@@author zacharytang-unused
/**
 * Display for the combined timetable command
 */
public class CombinedTimetableDisplay extends UiPart<Region> {

    public static final String FXML = "CombinedTimetableDisplay.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private TimetableDisplay timetableDisplay;

    @FXML
    private Label names;

    @FXML
    private AnchorPane timetablePlaceholder;

    public CombinedTimetableDisplay() {
        super(FXML);

        timetableDisplay = new TimetableDisplay(null);
        timetablePlaceholder.getChildren().add(timetableDisplay.getRoot());

        registerAsAnEventHandler(this);
    }

    /**
     * Refreshes the timetable display upon command
     */
    private void loadPersons(List<ReadOnlyPerson> persons) {
        names.setText(generateNamesString(persons));

        timetablePlaceholder.getChildren().removeAll();

        ArrayList<Timetable> timetables = new ArrayList<>();
        for (ReadOnlyPerson person : persons) {
            timetables.add(person.getTimetable());
        }
        timetableDisplay = new TimetableDisplay(timetables);
        timetablePlaceholder.getChildren().add(timetableDisplay.getRoot());
    }

    /**
     * Creates a string to display all names for the combined timetable displayed
     */
    private String generateNamesString(List<ReadOnlyPerson> persons) {
        StringBuilder names = new StringBuilder();
        for (ReadOnlyPerson person : persons) {
            names.append("[");
            names.append(person.getName().toString());
            names.append("] ");
        }
        return names.toString();
    }

    @Subscribe
    private void handleTimetableDisplayEvent(TimetableDisplayEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersons(event.personsToDisplay);
    }
}
