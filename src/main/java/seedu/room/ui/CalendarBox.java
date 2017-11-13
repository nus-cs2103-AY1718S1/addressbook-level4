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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import seedu.room.logic.Logic;
import seedu.room.model.event.ReadOnlyEvent;

//@@author Haozhe321

/**
 * Create a CalendarBox Object to pass to the CalendarBoxPanel to be displayed
 */
public class CalendarBox {

    private ArrayList<AnchorPaneNode> allCalendarDays;
    private VBox view;
    private Text calendarTitle;
    private GridPane calendar;
    private GridPane dayLabels;
    private HBox titleBar;
    private YearMonth currentYearMonth;
    private final Color yellow = Color.web("#CA9733");
    private Logic logic;
    private HashMap<LocalDate, ArrayList<ReadOnlyEvent>> hashEvents;
    private Text[] dayNames = new Text[]{ new Text("Sunday"), new Text("Monday"), new Text("Tuesday"),
        new Text("Wednesday"), new Text("Thursday"), new Text("Friday"),
        new Text("Saturday") };


    /**
     * Create a month-based calendar filled with dates and events
     * @param yearMonth the month of the calendar to create the calendar
     * @param logic containing the events to populate
     */
    public CalendarBox(YearMonth yearMonth, Logic logic) {
        this.logic = logic;
        currentYearMonth = yearMonth;
        allCalendarDays = new ArrayList<>(35);

        makeCalendarSkeleton();
        makeCalendarNavigationTool();
        populateCalendar(yearMonth, logic.getFilteredEventList());

        // Create the calendar view
        view = new VBox(titleBar, dayLabels, calendar);
        VBox.setMargin(titleBar, new Insets(0, 0, 10, 0));
    }


    /**
     * Set the days of the calendar to correspond to the appropriate date, with the events populated
     * @param yearMonth year and month of month to render
     * @param eventList list of events to populate
     */
    public void populateCalendar(YearMonth yearMonth, ObservableList<ReadOnlyEvent> eventList) {
        hashEvents = new HashMap<LocalDate, ArrayList<ReadOnlyEvent>>();
        hashEvents = eventsHashMap(eventList);

        LocalDate calendarDate = dateForCalendarPage(yearMonth);
        populateDays(calendarDate);
        changeCalenderTitle(yearMonth);
    }

    //////////////////////////////////// Methods to create the calendar ///////////////////////////////////////////////

    /**
     * Create the title of the calendar and set style
     */
    private void makeCalenderTitle() {
        this.calendarTitle = new Text();
        calendarTitle.setFill(yellow);
        calendarTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
    }

    //Change the title of the calendar according to the month of the calendar
    private void changeCalenderTitle(YearMonth yearMonth) {
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }


    /**
     * Make the buttons for users to press to go previous month or next month
     * @param previousMonth Button to go to previous month
     * @param nextMonth Button to go to next month
     */
    private void makeButtons(Button previousMonth, Button nextMonth) {
        previousMonth.setOnAction(e -> previousMonth());
        nextMonth.setOnAction(e -> nextMonth());

    }

    /**
     * Create the title bar for the calendar above the calendar grids
     * @param titleBar titleBar represented by a HBox
     * @param previousMonth Button for previous month
     * @param nextMonth Button for next month
     */
    private void makeCalendarTitleBar(HBox titleBar, Button previousMonth, Button nextMonth) {
        HBox.setMargin(previousMonth, new Insets(0, 13, 0, 13));
        HBox.setMargin(nextMonth, new Insets(0, 13, 0, 13));
        titleBar.setAlignment(Pos.BASELINE_CENTER);
    }

    /**
     * Create the entire navigation tool for the calender, i.e. title, previous-month button, next-month button
     */
    private void makeCalendarNavigationTool() {
        makeCalenderTitle();

        Button previousMonth = new Button(" PREV ");
        Button nextMonth = new Button(" NEXT ");

        makeButtons(previousMonth, nextMonth);

        this.titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        makeCalendarTitleBar(titleBar, previousMonth, nextMonth);
    }

    /**
     * Make the skeleton for the calendar, i.e. grids for one month, and label for days of the week
     */
    private void makeCalendarSkeleton() {
        // Create the calendar grid pane
        this.calendar = new GridPane();
        createGrid(calendar);

        // Create the days of the weeks from Sunday to Saturday
        this.dayLabels = new GridPane();
        makeDays(dayNames, dayLabels);
    }

    /**
     * Make the days in a week on the calendar
     * @param dayNames a Text array containing all the days in a week
     * @param gridPane the overall pane for the calendar
     */
    private void makeDays(Text[] dayNames, GridPane gridPane) {
        gridPane.setPrefWidth(600);
        int col = 0;
        for (Text txt : dayNames) {
            txt.setFill(Color.WHITE);
            AnchorPane ap = new AnchorPane();
            ap.setId("calendarDaysPane");
            ap.setPrefSize(200, 10);
            ap.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            txt.setTextAlignment(TextAlignment.CENTER);
            ap.setStyle("-fx-text-align: center;");
            gridPane.add(ap, col++, 0);
        }
    }

    /**
     * Create 5 by 7 grids inside calendar
     * @param calendar
     */
    private void createGrid(GridPane calendar) {

        calendar.setPrefSize(500, 450);
        calendar.setGridLinesVisible(true);
        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(200, 90);
                calendar.add(ap, j, i);
                allCalendarDays.add(ap);
            }
        }
    }

    ///////////////////////////// Methods related to populating events on the calendar //////////////////////////////

    /**
     * Add the event's name on the calendar grid
     * @param ap AnchorPaneNode that we are adding the event to
     * @param eventText Text for the event(s)
     */
    private void addEventName(AnchorPaneNode ap, Text eventText) {
        ap.setBottomAnchor(eventText, 5.0);
        ap.setLeftAnchor(eventText, 5.0);
        ap.getChildren().add(eventText);
    }

    /**
     * Create a HashMap of LocalDate and Arraylist of ReadOnlyEvent to use for populating events on calendar
     * Each key in the HashMap can contain one or more events in the value of the HashMap, stored using an ArrayList
     * @param eventList list of ReadOnlyEvent
     * @return HashMap of LocalDate and Arraylist of ReadOnlyEvent
     */
    private HashMap<LocalDate, ArrayList<ReadOnlyEvent>> eventsHashMap(ObservableList<ReadOnlyEvent> eventList) {
        HashMap<LocalDate, ArrayList<ReadOnlyEvent>> hashEvents = new HashMap<LocalDate, ArrayList<ReadOnlyEvent>>();
        for (ReadOnlyEvent event: eventList) {
            if (hashEvents.containsKey(event.getDatetime().getLocalDateTime().toLocalDate())) {
                hashEvents.get(event.getDatetime().getLocalDateTime().toLocalDate()).add(event);
            } else {
                ArrayList<ReadOnlyEvent> newEventList = new ArrayList<ReadOnlyEvent>();
                newEventList.add(event);
                hashEvents.put(event.getDatetime().getLocalDateTime().toLocalDate(), newEventList);
            }
        }

        return hashEvents;
    }


    /**
     * Method to calculate the date of first day in a page of calendar
     * @param yearMonth the YearMonth for this calendar page
     * @return the LocalDate for this month/page of the calendar
     */
    private LocalDate dateForCalendarPage(YearMonth yearMonth) {
        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);

        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }
        return calendarDate;
    }

    /**
     * Set up the AnchorPaneNode to prepare for date and event population
     * @param node Individual AnchorPaneNode
     */
    private void setupAnchorPaneNode(AnchorPaneNode node) {
        node.setId("calendarCell");
        if (node.getChildren().size() != 0) {
            node.getChildren().remove(0);
        }
        node.getChildren().clear();
    }

    /**
     * Light up today's grid
     * @param node Individual AnchorPaneNode
     * @param calendarDate
     */
    private void setupToday(AnchorPaneNode node, LocalDate calendarDate) {
        if (calendarDate.equals(LocalDate.now())) {
            node.lightUpToday();
        } else {
            node.revertBackground();
        }
    }

    /**
     * add the date number to the grids
     */
    private void addDates(LocalDate calendarDate, AnchorPaneNode ap) {
        Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
        ap.setDate(calendarDate);
        ap.setTopAnchor(txt, 10.0);
        ap.setLeftAnchor(txt, 5.0);
        ap.getChildren().add(txt);
    }

    /**
     * Create a String that represents the events in a day to fit into a grid in the calendar
     * @param eventInADay ArrayList of events in a day
     * @return String that represents all events in a day
     */
    private String populateDayEvents(ArrayList<ReadOnlyEvent> eventInADay) {
        int numEvents = 0;
        String eventTitles = "";
        for (ReadOnlyEvent actualEvent: eventInADay) {

            //if number of events is already more than 2, populate only 2 and tell users there are more events
            if (numEvents == 2) {
                eventTitles = eventTitles + "and more...";
                break;
            }
            String eventTitle = actualEvent.getTitle().toString();
            if (eventTitle.length() > 8) {
                eventTitle = eventTitle.substring(0, 8) + "...";
            }
            eventTitles = eventTitles + eventTitle + "\n";
            numEvents++;
        }
        return eventTitles;
    }

    /**
     * Populate the days and it's corresponding event(if any) in the calendar
     * @param calendarDate the LocalDate referenced to populate this calendar
     */
    private void populateDays(LocalDate calendarDate) {
        for (AnchorPaneNode ap : allCalendarDays) {
            setupAnchorPaneNode(ap);
            setupToday(ap, calendarDate);
            addDates(calendarDate, ap);

            if (hashEvents.containsKey(calendarDate)) {
                ArrayList<ReadOnlyEvent> eventInADay = hashEvents.get(calendarDate);
                Text eventText = new Text(populateDayEvents(eventInADay));
                addEventName(ap, eventText);
            }
            calendarDate = calendarDate.plusDays(1);

        }
    }

    /////////////////////////////////////// Other methods ////////////////////////////////////////////////

    /**
     * Move the month back by one. Repopulate the calendar with the correct dates.
     */
    public void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        populateCalendar(currentYearMonth, logic.getFilteredEventList());
    }

    public void refreshCalendar(Logic logic) {
        populateCalendar(currentYearMonth, logic.getFilteredEventList());
    }

    /**
     * Move the month forward by one. Repopulate the calendar with the correct dates.
     */
    public void nextMonth() {
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

