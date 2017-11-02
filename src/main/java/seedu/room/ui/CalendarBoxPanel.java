package seedu.room.ui;

import java.time.YearMonth;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

//@@author Haozhe321

/**
 * Panel containing the calendar
 */
public class CalendarBoxPanel extends UiPart<Region> {
    private static final String FXML = "CalendarBox.fxml";

    @FXML
    private Pane calendarPane;

    public CalendarBoxPanel() {
        super(FXML);
        calendarPane.getChildren().add(new CalendarBox(YearMonth.now()).getView());
    }

    public void freeResources() {
        calendarPane = null;
    }
}
