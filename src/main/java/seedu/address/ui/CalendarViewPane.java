package seedu.address.ui;

import java.time.YearMonth;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;

//@@author kaiyu92

/**
 * The UI component that is responsible for containing the CalendarView
 */
public class CalendarViewPane extends UiPart<Region> {

    private static final String FXML = "CalendarView.fxml";

    @FXML
    private Pane calendarPane;

    private CalendarView calendarView;
    private Logic logic;

    public CalendarViewPane(Logic logic) {
        super(FXML);
        this.logic = logic;
        setConnections();
        ;
    }

    private void setConnections() {
        calendarView = new CalendarView(logic, logic.getFilteredEventList(), YearMonth.now());
        calendarPane.getChildren().add(calendarView.getView());
    }

    public CalendarView getCalendarPane() {
        return calendarView;
    }
}
