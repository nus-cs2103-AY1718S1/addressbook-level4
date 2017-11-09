package seedu.room.ui;

import java.time.YearMonth;

import com.google.common.eventbus.Subscribe;

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
    private Logic logic;

    public CalendarBoxPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        calendarBox = new CalendarBox(YearMonth.now(), logic);
        calendarPane.getChildren().add(calendarBox.getView());
    }

    @Subscribe
    public void handleCalenderBoxPanelChange() {
        calendarBox.populateCalendar(YearMonth.now(), this.logic.getFilteredEventList());
    }

    public CalendarBox getCalendarBox() {
        return calendarBox;
    }


    public void freeResources() {
        calendarPane = null;
    }
}
