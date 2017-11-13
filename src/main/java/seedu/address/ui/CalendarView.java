package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.logging.Logger;

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
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.user.exceptions.DuplicateUserException;

//@@author kaiyu92

/**
 * The CalendarView UI Component
 */
public class CalendarView {

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
    private VBox view;
    private Text calendarTitle;
    private YearMonth currentYearMonth;
    private ObservableList<ReadOnlyEvent> eventList;
    private Logic logic;

    /**
     * Create a calendar view
     *
     * @param eventList contains the events of the event book
     * @param yearMonth year month to create the calendar of
     */
    public CalendarView(Logic logic, ObservableList<ReadOnlyEvent> eventList, YearMonth yearMonth) {

        this.logic = logic;
        this.eventList = eventList;
        currentYearMonth = yearMonth;
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(600, 400);
        // Create rows and columns with anchor panes for the calendar

        // This GridPane will represent the dates
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.getStyleClass().add("anchor");
                ap.setPrefSize(200, 200);
                calendar.add(ap, j, i);
                allCalendarDays.add(ap);
            }
        }

        // Days of the week labels
        Text[] dayNames = new Text[]{new Text("SUNDAY"), new Text("MONDAY"), new Text("TUESDAY"),
            new Text("WEDNESDAY"), new Text("THURSDAY"), new Text("FRIDAY"),
            new Text("SATURDAY")};

        // This GridPane will represent the days which will place at the top of calendar GridPane
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(600);
        Integer col = 0;
        for (Text txt : dayNames) {
            txt.setFill(Color.WHITE);
            txt.setStyle("-fx-font-size: 7pt;");
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 10);
            ap.setBottomAnchor(txt, 10.0);
            ap.getChildren().add(txt);
            dayLabels.add(ap, col++, 0);
        }
        // Create calendarTitle and buttons to change current month
        calendarTitle = new Text();
        calendarTitle.setFill(Color.WHITE);
        calendarTitle.setStyle("-fx-font-size: 15pt;");

        Button previousMonth = new Button("<  PREVIOUS");
        previousMonth.setOnAction(e -> previousMonth());

        Button nextMonth = new Button("NEXT  >");
        nextMonth.setOnAction(e -> nextMonth());

        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        HBox.setMargin(calendarTitle, new Insets(0, 15, 0, 15));

        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Populate calendar with the appropriate day numbers
        populateCalendar(yearMonth, null);
        // Create the calendar view
        view = new VBox(titleBar, dayLabels, calendar);
        VBox.setMargin(titleBar, new Insets(0, 0, 15, 0));
    }

    /**
     * Set the days of the calendar to correspond to the appropriate date
     * @param targetIndex a specific event
     * @param yearMonth year and month of month to render
     */
    public void populateCalendar(YearMonth yearMonth, Index targetIndex) {
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

            String dayValue = calendarDate.getDayOfMonth() + "";
            String monthValue = calendarDate.getMonthValue() + "";
            String yearValue = calendarDate.getYear() + "";

            boolean eventExist = false;

            if (targetIndex == null) {
                eventExist = eventList.stream()
                        .anyMatch(e -> checkEventDay(e, dayValue)
                                && checkEventMonth(e, monthValue)
                                && checkEventYear(e, yearValue));
            } else {
                ReadOnlyEvent e = eventList.get(targetIndex.getZeroBased());

                if (checkEventDay(e, dayValue)
                        && checkEventMonth(e, monthValue)
                        && checkEventYear(e, yearValue)) {
                    eventExist = true;
                }
            }

            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            //@@author keloysiusmak
            txt.setFont(Font.font("Avenir"));
            txt.setFill(Color.valueOf("#777"));
            txt.setStyle("-fx-font-size: 12pt; ");
            //@@author
            ap.setDate(calendarDate);
            ap.setTopAnchor(txt, 10.0);
            ap.setLeftAnchor(txt, 10.0);


            if (eventExist) {
                ap.setOnMouseClicked(ev -> {
                    String commandText = FindEventCommand.getCommandWord()
                            + " " + PREFIX_DATETIME + getFormatDate(dayValue, monthValue, yearValue);
                    try {
                        CommandResult commandResult = logic.execute(commandText);
                        logger.info("Result: " + commandResult.feedbackToUser);

                    } catch (CommandException | ParseException e) {
                        logger.info("Invalid command: " + commandText);
                    } catch (DuplicateUserException e) {
                        logger.info("Duplicated User");
                    }
                });
                ap.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
            } else {
                ap.setStyle("-fx-background-color: rgba(255, 255, 255, 0.6);");
            }

            ap.getChildren().add(txt);
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
        populateCalendar(currentYearMonth, null);
    }

    /**
     * Move the month forward by one. Repopulate the calendar with the correct dates.
     */
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        populateCalendar(currentYearMonth, null);
    }

    /**
     *
     * @return the selected current YearMonth object
     */
    public YearMonth getCurrentYearMonth() {
        return currentYearMonth;
    }

    /**
     * change the current YearMonth object to the newly specified one
     * @param currentYearMonth
     */
    public void setCurrentYearMonth(YearMonth currentYearMonth) {
        this.currentYearMonth = currentYearMonth;
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
     * Check whether the event Day matches the input dayValue
     *
     * @param event
     * @param dayValue
     * @return
     */
    private boolean checkEventDay(ReadOnlyEvent event, String dayValue) {

        if (dayValue.length() == 1) {
            return event.getDatetime().value.substring(0, 2).equals("0" + dayValue);
        } else {
            return event.getDatetime().value.substring(0, 2).equals(dayValue);
        }
    }

    /**
     * Check whether the event Day matches the input monthValue
     *
     * @param event
     * @param monthValue
     * @return
     */
    private boolean checkEventMonth(ReadOnlyEvent event, String monthValue) {

        if (monthValue.length() == 1) {
            return event.getDatetime().value.substring(3, 5).equals("0" + monthValue);
        } else {
            return event.getDatetime().value.substring(3, 5).equals(monthValue);
        }
    }

    /**
     * Check whether the event Day matches the input yearValue
     *
     * @param event
     * @param yearValue
     * @return
     */
    private boolean checkEventYear(ReadOnlyEvent event, String yearValue) {
        return event.getDatetime().value.substring(6, 10).equals(yearValue);
    }

    /**
     * Format the Date to the desired format
     * @param day
     * @param month
     * @param year
     * @return
     */
    private String getFormatDate(String day, String month, String year) {
        if (day.length() == 1) {
            day = "0" + day;
        }

        if (month.length() == 1) {
            month = "0" + month;
        }

        return day + "-" + month + "-" + year;
    }
}
