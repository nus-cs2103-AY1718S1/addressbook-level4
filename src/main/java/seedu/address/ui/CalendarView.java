package seedu.address.ui;

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
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.ReadOnlyPerson;

//@@author jacoblipech
/**
 * The UI Component of the Calendar shown.
 */
public class CalendarView {

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
    private VBox view;
    private Text calendarTitle;
    private YearMonth currentYearMonth;
    private ObservableList<ReadOnlyPerson> contactList;
    private Logic logic;

    /**
     * Create a calendar view
     * @param yearMonth year month to create the calendar from
     */
    public CalendarView(YearMonth yearMonth, ObservableList<ReadOnlyPerson> contactList, Logic logic) {

        this.currentYearMonth = yearMonth;
        this.contactList = contactList;
        this.logic = logic;
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
                //ap.getStyleClass().add("calendar-color"); does not work yet
            }
        }

        // Days of the week labels
        Text[] dayNames = new Text[]{
            new Text("Sun"), new Text("Mon"), new Text("Tue"), new Text("Wed"),
            new Text("Thu"), new Text("Fri"), new Text("Sat")
        };

        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(600);
        Integer col = 0;
        for (Text txt : dayNames) {
            txt.setFill(Color.GHOSTWHITE);
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 10);
            ap.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            dayLabels.add(ap, col++, 0);
        }

        // Create calendarTitle and buttons to change current month
        calendarTitle = new Text();
        calendarTitle.setFill(Color.GHOSTWHITE);
        Button previousMonth = new Button("<");
        previousMonth.setOnAction(e -> previousMonth());
        Button nextMonth = new Button(">");
        nextMonth.setOnAction(e -> nextMonth());
        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        HBox.setMargin(calendarTitle, new Insets(0, 15, 15, 15));
        titleBar.setSpacing(10);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Populate calendar with the appropriate day numbers
        populateCalendar(currentYearMonth);
        // Create the calendar view
        view = new VBox(titleBar, dayLabels, calendar);
        VBox.setMargin(titleBar, new Insets(8, 0, 0, 0)); //GOOD TO SET MARGINS, CAN CHANGE THE VALUE
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
            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            txt.setFill(Color.GHOSTWHITE);
            ap.setDate(calendarDate);

            ap.setStyle("calendar-color");
            ap.setTopAnchor(txt, 5.0);
            ap.setLeftAnchor(txt, 5.0);

            //populating birthday of contacts to the calendar
            String dateValue = getFormatDate(calendarDate.getDayOfMonth() + "", calendarDate.getMonthValue() + "");
            String birthdayValue = Birthday.DEFAULT_BIRTHDAY;
            Boolean birthdayExist = false;
            for (ReadOnlyPerson person : contactList) {
                if (!person.getBirthday().equals(Birthday.DEFAULT_BIRTHDAY)) {
                    birthdayValue = person.getBirthday().toString();
                    birthdayExist = true;
                }
                if (birthdayExist) {
                    if (dateValue.equals(birthdayValue.substring(0, 5))) {
                        System.out.println("date value: " + dateValue);
                        ap.getChildren();
                        ap.setStyle("-fx-background-color: #CD5C5C");
                        ap.setAccessibleText(person.getName().toString());
                    }

                    ap.setOnMouseClicked(event -> {
                        String findCommandText = FindCommand.COMMAND_WORDVAR_1 + " " + ap.getAccessibleText();
                        try {
                            CommandResult commandResult = logic.execute(findCommandText);
                            logger.info("Result: " + commandResult.feedbackToUser);
                        } catch (CommandException | ParseException e) {
                            logger.info("Invalid command: " + findCommandText);
                        }
                    });
                }
            }

            ap.getChildren().add(txt);
            calendarDate = calendarDate.plusDays(1);
        }
        // Change the title of the calendar
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }

    /**
     * Update CalendarView when a new filter list is created.
     */
    public void populateUpdatedCalendar (ObservableList<ReadOnlyPerson> contactList) {
        this.contactList = contactList;
        populateCalendar(currentYearMonth);
    }

    /**
     * Move the month back by one. Repopulate the calendar with the correct dates.
     */
    public void previousMonth() {
        populateCalendar(currentYearMonth.minusMonths(1));
    }

    /**
     * Move the month forward by one. Repopulate the calendar with the correct dates.
     */
    public void nextMonth() {
        populateCalendar(currentYearMonth.plusMonths(1));
    }

    public VBox getView() {
        return view;
    }

    /**
     * Returns the correct format of the day in String format
     */
    private String getFormatDate(String day, String month) {

        if (day.length() == 1) {
            day = "0" + day;
        }
        if (month.length() == 1) {
            month = "0" + month;
        }
        return day + "/" + month;
    }
}
