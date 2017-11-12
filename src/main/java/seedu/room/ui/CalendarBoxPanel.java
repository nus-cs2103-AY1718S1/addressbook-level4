package seedu.room.ui;

import java.time.YearMonth;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import seedu.room.logic.Logic;

//@@author Haozhe321

/**
 * Panel containing the calendar
 */
public class CalendarBoxPanel extends UiPart<Region> {
    private static final String FXML = "CalendarBox.fxml";

    @FXML
    private Pane calendarPane;

    private CalendarBox calendarBox;

    public CalendarBoxPanel(Logic logic) {
        super(FXML);
        calendarBox = new CalendarBox(YearMonth.now(), logic);
        calendarPane.getChildren().add(calendarBox.getView());
    }

    public CalendarBox getCalendarBox() {
        return calendarBox;
    }


    public void freeResources() {
        calendarPane = null;
    }
}
