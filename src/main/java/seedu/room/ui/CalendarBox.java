package seedu.room.ui;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.room.logic.Logic;
import seedu.room.model.event.Event;
import seedu.room.model.event.ReadOnlyEvent;

//@@author Haozhe321-reused

/**
 * Create a CalendarBox Object to pass to the CalendarBoxPanel to be displayed
 */
public class CalendarBox {

    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
    private VBox view;
    private Text calendarTitle;
    private YearMonth currentYearMonth;
    private Logic logic;


    /**
     * Create a calendar view
     * @param yearMonth year month to create the calendar of
     */
    public CalendarBox(YearMonth yearMonth, Logic logic) {
        this.logic = logic;
        currentYearMonth = yearMonth;
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(500, 500);
        calendar.setGridLinesVisible(true);
        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(200, 200);
                calendar.add(ap, j, i);
                allCalendarDays.add(ap);
            }
        }
        // Days of the week labels
        Text[] dayNames = new Text[]{ new Text("Sunday"), new Text("Monday"), new Text("Tuesday"),
            new Text("Wednesday"), new Text("Thursday"), new Text("Friday"),
            new Text("Saturday") };
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(600);
        Integer col = 0;
        for (Text txt : dayNames) {
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 10);
            ap.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            dayLabels.add(ap, col++, 0);
        }
        // Create calendarTitle and buttons to change current month
        calendarTitle = new Text();
        Button previousMonth = new Button(" << ");
        previousMonth.setOnAction(e -> previousMonth());
        Button nextMonth = new Button(" >> ");
        nextMonth.setOnAction(e -> nextMonth());
        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        HBox.setMargin(previousMonth, new Insets(0, 5, 0, 0));
        HBox.setMargin(nextMonth, new Insets(0, 5, 0, 5));
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Populate calendar with the appropriate day numbers
        logic.getFilteredEventList();
        populateCalendar(yearMonth, logic.getFilteredEventList());
        // Create the calendar view
        view = new VBox(titleBar, dayLabels, calendar);
        VBox.setMargin(titleBar, new Insets(0, 0, 10, 0));
    }

    /**
     * Set the days of the calendar to correspond to the appropriate date
     * @param yearMonth year and month of month to render
     */
    public void populateCalendar(YearMonth yearMonth) {
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

            //make today's date light up
            if (calendarDate.equals(LocalDate.now())) {
                ap.lightUpToday();
            }

            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            ap.setDate(calendarDate);
            ap.setTopAnchor(txt, 5.0);
            ap.setLeftAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            calendarDate = calendarDate.plusDays(1);


        }
        // Change the title of the calendar
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }

    public void populateCalendar(YearMonth yearMonth, ObservableList<ReadOnlyEvent> eventList) {
        HashMap<LocalDate, ArrayList<ReadOnlyEvent>> hashEvents = new HashMap<LocalDate, ArrayList<ReadOnlyEvent>>();
        for(ReadOnlyEvent event: eventList) {
            if(hashEvents.containsKey(event.getDatetime().getLocalDateTime().toLocalDate())) {
                hashEvents.get(event.getDatetime().getLocalDateTime().toLocalDate()).add(event);
            } else {
                ArrayList<ReadOnlyEvent> newEventList = new ArrayList<ReadOnlyEvent>();
                newEventList.add(event);
                hashEvents.put(event.getDatetime().getLocalDateTime().toLocalDate(), newEventList);
            }
        }
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
            ap.getChildren().clear();
            //make today's date light up
            if (calendarDate.equals(LocalDate.now())) {
                ap.lightUpToday();
            } else {
                ap.unlightGrid();
            }

            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            ap.setDate(calendarDate);
            ap.setTopAnchor(txt, 10.0);
            ap.setLeftAnchor(txt, 5.0);
            ap.getChildren().add(txt);


            if (hashEvents.containsKey(calendarDate)) {
                double anchorValue = 5.0;
                ArrayList<ReadOnlyEvent> eventInADay = hashEvents.get(calendarDate);

                int numEvents = 0;
                String allEventTitle = "";
                //go through the list of events and add them to the grid
                for(ReadOnlyEvent actualEvent: eventInADay) {

                    //if number of events is already more than 2, populate only 2 and tell users there are more events
                    if(numEvents == 2) {
                        allEventTitle = allEventTitle + "and more...";
                        break;
                    }
                    String eventTitle = actualEvent.getTitle().toString();
                    if(eventTitle.length() > 8) {
                        eventTitle = eventTitle.substring(0, 8) + "...";
                    }
                    allEventTitle = allEventTitle + eventTitle + "\n";
                    numEvents++;

                }
                Text eventText = new Text(allEventTitle);
                ap.setBottomAnchor(eventText, anchorValue);
                ap.setLeftAnchor(eventText, 5.0);
                ap.getChildren().add(eventText);

            }

            calendarDate = calendarDate.plusDays(1);

        }
        // Change the title of the calendar
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }



    /**
     * Move the month back by one. Repopulate the calendar with the correct dates.
     */
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        populateCalendar(currentYearMonth, logic.getFilteredEventList());
    }

    /**
     * Move the month forward by one. Repopulate the calendar with the correct dates.
     */
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        populateCalendar(currentYearMonth, logic.getFilteredEventList());
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
}

