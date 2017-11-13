package seedu.address.ui;

//@@author chernghann
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.PopulateMonthEvent;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.UniqueEventList;

/**
 * The UI component that is responsible for implemented Calendar.
 */
public class Calendar {

    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
    private VBox view;
    private Text calendarTitle;
    private YearMonth currentYearMonth;

    /**
     * Create a calendar view
     * @param yearMonth year month to create the calendar of
     */
    public Calendar(YearMonth yearMonth, ObservableList<ReadOnlyEvent> eventList) {
        currentYearMonth = yearMonth;
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(600, 400);
        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(200, 200);
                calendar.add(ap, j, i);
                allCalendarDays.add(ap);
                ap.getStyleClass().add("calendar-color");
            }
        }

        // Days of the week labels
        Text[] dayNames = new Text[]{new Text("Sunday"), new Text("Monday"),
                                     new Text("Tuesday"), new Text("Wednesday"), new Text("Thursday"),
                                     new Text("Friday"), new Text("Saturday")};

        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(600);
        Integer col = 0;
        for (Text txt : dayNames) {
            txt.getStyleClass().add("calendar-color");
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 10);
            ap.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            dayLabels.add(ap, col++, 0);
        }

        // Create calendarTitle and buttons to change current month
        calendarTitle = new Text();
        calendarTitle.getStyleClass().add("calendar-color");
        Button previousMonth = new Button("<");
        previousMonth.setOnAction(e -> previousMonth());
        Button nextMonth = new Button(">");
        nextMonth.setOnAction(e -> nextMonth());
        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        titleBar.setSpacing(5);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Populate calendar with the appropriate day numbers
        populateCalendar(yearMonth, eventList);
        // Create the calendar view
        view = new VBox(titleBar, dayLabels, calendar);
    }

    /**
     * Set the days of the calendar to correspond to the appropriate date
     * Also, used to populate the calendar when switching different months
     * @param yearMonth year and month of month to render
     */
    public void populateCalendar(YearMonth yearMonth, ObservableList<ReadOnlyEvent> events) {
        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }
        // Populate the calendar with day numbers
        for (AnchorPaneNode ap : allCalendarDays) {
            if (ap.getChildren().size() != 0) {
                ap.getChildren().remove(0);
            }
            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            txt.getStyleClass().add("calendar-color");
            ap.setDate(calendarDate);
            ap.setTopAnchor(txt, 5.0);
            ap.setLeftAnchor(txt, 5.0);
            ap.setStyle("-fx-background-color: transparent;");
            ap.getChildren().add(txt);
            calendarDate = calendarDate.plusDays(1);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            String newDate = formatter.format(ap.getDate());
            for (ReadOnlyEvent event : events) {
                String date = event.getDate().toString();
                if (newDate.equals(date)) {
                    ap.getChildren();
                    ap.setStyle("-fx-background-color: #ffebcd;");
                }
            }
        }
        // Change the title of the calendar
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }

    /**
     * Move the month back by one. Repopulate the calendar with the correct dates.
     */
    public void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        EventsCenter.getInstance().post(new PopulateMonthEvent(currentYearMonth));
    }

    /**
     * Move the month forward by one. Repopulate the calendar with the correct dates.
     */
    public void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        EventsCenter.getInstance().post(new PopulateMonthEvent(currentYearMonth));
    }

    public VBox getView() {
        return view;
    }

    public ArrayList<AnchorPaneNode> getAllCalendarDays() {
        return allCalendarDays;
    }

    public void setAllCalendarDays(ArrayList<AnchorPaneNode> allCalendarDays) {
        this.allCalendarDays = allCalendarDays;
    }

    /**
     * populating updated calendar for the switch buttons for the add of events
     * @param eventList
     */
    public void populateUpdatedCalendar(UniqueEventList eventList) {
        for (AnchorPaneNode ap : allCalendarDays) {
            ap.setStyle("-fx-background-color: transparent;");
            for (Event event1 : eventList) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                String newDate = formatter.format(ap.getDate());
                if (newDate.equals(event1.getDate().toString())) {
                    ap.getChildren();
                    ap.setStyle("-fx-background-color: #ffebcd;");
                }
            }
        }
    }
}
