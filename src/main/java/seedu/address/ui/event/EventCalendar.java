package seedu.address.ui.event;

import java.util.Calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.ui.UiPart;

//@@author dennaloh
/**
 * The panel on the right side of {@link EventListPanel}. Used to show a calendar.
 */
public class EventCalendar extends UiPart<Region> {
    private static final String FXML = "event/EventCalendar.fxml";
    private static final int MAX_NUMBER_DAYS = 42;

    @FXML
    private Text date1;
    @FXML
    private Text date2;
    @FXML
    private Text date3;
    @FXML
    private Text date4;
    @FXML
    private Text date5;
    @FXML
    private Text date6;
    @FXML
    private Text date7;
    @FXML
    private Text date8;
    @FXML
    private Text date9;
    @FXML
    private Text date10;
    @FXML
    private Text date11;
    @FXML
    private Text date12;
    @FXML
    private Text date13;
    @FXML
    private Text date14;
    @FXML
    private Text date15;
    @FXML
    private Text date16;
    @FXML
    private Text date17;
    @FXML
    private Text date18;
    @FXML
    private Text date19;
    @FXML
    private Text date20;
    @FXML
    private Text date21;
    @FXML
    private Text date22;
    @FXML
    private Text date23;
    @FXML
    private Text date24;
    @FXML
    private Text date25;
    @FXML
    private Text date26;
    @FXML
    private Text date27;
    @FXML
    private Text date28;
    @FXML
    private Text date29;
    @FXML
    private Text date30;
    @FXML
    private Text date31;
    @FXML
    private Text date32;
    @FXML
    private Text date33;
    @FXML
    private Text date34;
    @FXML
    private Text date35;
    @FXML
    private Text date36;
    @FXML
    private Text date37;
    @FXML
    private Text date38;
    @FXML
    private Text date39;
    @FXML
    private Text date40;
    @FXML
    private Text date41;
    @FXML
    private Text date42;

    @FXML
    private Label monthName;

    private Text[] dateArray;

    private MonthDateBuilder monthDateBuilder;


    public EventCalendar() {
        super(FXML);
        monthDateBuilder = new MonthDateBuilder();
        inits();
        setDates(monthDateBuilder.getMonthDateArray());
        monthName.setText(monthDateBuilder.getNameOfMonth());
    }

    public void setDates(String[] monthDateArray) {
        for (int i = 0; i < MAX_NUMBER_DAYS; i++) {
            dateArray[i].setText(monthDateArray[i]);
        }
    }

    /**
     * To put all text views into array to facilitate looping through
     */
    public void inits() {
        dateArray = new Text[MAX_NUMBER_DAYS];
        dateArray[0] = date1;
        dateArray[1] = date2;
        dateArray[2] = date3;
        dateArray[3] = date4;
        dateArray[4] = date5;
        dateArray[5] = date6;
        dateArray[6] = date7;
        dateArray[7] = date8;
        dateArray[8] = date9;
        dateArray[9] = date10;
        dateArray[10] = date11;
        dateArray[11] = date12;
        dateArray[12] = date13;
        dateArray[13] = date14;
        dateArray[14] = date15;
        dateArray[15] = date16;
        dateArray[16] = date17;
        dateArray[17] = date18;
        dateArray[18] = date19;
        dateArray[19] = date20;
        dateArray[20] = date21;
        dateArray[21] = date22;
        dateArray[22] = date23;
        dateArray[23] = date24;
        dateArray[24] = date25;
        dateArray[25] = date26;
        dateArray[26] = date27;
        dateArray[27] = date28;
        dateArray[28] = date29;
        dateArray[29] = date30;
        dateArray[30] = date31;
        dateArray[31] = date32;
        dateArray[32] = date33;
        dateArray[33] = date34;
        dateArray[34] = date35;
        dateArray[35] = date36;
        dateArray[36] = date37;
        dateArray[37] = date38;
        dateArray[38] = date39;
        dateArray[39] = date40;
        dateArray[40] = date41;
        dateArray[41] = date42;
    }
}

/**
 * Create dates in month TODO: change this
 */
class MonthDateBuilder {
    private Integer[] monthYearArray;
    private String[] monthDateArray;
    private Calendar calendar;
    private Integer firstDayOfMonth;
    private Integer maxDayOfMonth;
    private String nameOfMonth;
    private int MAX_NUMBER_DAYS = 42;

    public MonthDateBuilder() {
        calendar = Calendar.getInstance();
        firstDayOfMonth = 1; // random inits, can delete
        maxDayOfMonth = 5; //random inits, can delete for testing purposes

        monthDateArray = new String[MAX_NUMBER_DAYS]; // FOR STORING THE VALUES FOR THE DATE YOU SEE
        monthYearArray = new Integer[2]; // FOR USE IN CALENDAR IF YOU WANT TO IN THE FUTURE USE CUSTOM MONTHS MONTH/YR
        monthYearArray[0] = calendar.get(Calendar.MONTH); //RETURNS YOU CURRENT MONTH
        monthYearArray[1] = calendar.get(Calendar.YEAR); //RETURNS YOU CURRENT YEAR
        setNameOfMonth();

        calendar.set(Calendar.MONTH, monthYearArray[0]); //SETS YOUR CALENDAR OBJECT AS WHATEVER MONTH YOU WANT
        calendar.set(Calendar.YEAR, monthYearArray[1]); //SETS YOUR CALENDAR OBJECT YEAR VALUES AS WTV YOU WANT

        setMonthAchors(); // THIS IS TO SET THE MONTH ANCHORS, BEING THE FIRST DAY NUMBER AND THE MAx days
        setMonthArrays(); //THIS IS TO BUILD THE ARRAY OF MAX_NUMBER_DAYS, FOR BUILDING THE VIEW OF THE MONTH
    }

    private void setMonthAchors() {
        Integer dayOfWeek;
        maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); //getting max day of month
        calendar.set(Calendar.DAY_OF_MONTH, 1); //setting calendar to first day of month, numerical 1 to find weekday
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek) {
        default:
            firstDayOfMonth = 0;
            break;
        case Calendar.SUNDAY:
            firstDayOfMonth = 0;
            break;
        case Calendar.MONDAY:
            firstDayOfMonth = 1;
            break;
        case Calendar.TUESDAY:
            firstDayOfMonth = 2;
            break;
        case Calendar.WEDNESDAY:
            firstDayOfMonth = 3;
            break;
        case Calendar.THURSDAY:
            firstDayOfMonth = 4;
            break;
        case Calendar.FRIDAY:
            firstDayOfMonth = 5;
            break;
        case Calendar.SATURDAY:
            firstDayOfMonth = 6;
            break;
        }
    }

    public void setMonthArrays() {
        Integer i = 0;
        Integer j = firstDayOfMonth;
        Integer a = firstDayOfMonth + maxDayOfMonth;
        while (i < firstDayOfMonth) {
            monthDateArray[i] = " ";
            i++;
        }
        while (j < maxDayOfMonth + firstDayOfMonth) {
            monthDateArray[j] = String.valueOf(j - firstDayOfMonth + 1);
            j++;
        }
        while (a < MAX_NUMBER_DAYS) {
            monthDateArray[a] = " ";
            a++;
        }
    }

    public String[] getMonthDateArray() {
        return monthDateArray;
    }

    public void setNameOfMonth() {
        switch(monthYearArray[0]) {
        default:
            nameOfMonth = "January";
        case Calendar.JANUARY: nameOfMonth = "January";
                break;
        case Calendar.FEBRUARY: nameOfMonth = "February";
                break;
        case Calendar.MARCH: nameOfMonth = "March";
                break;
        case Calendar.APRIL: nameOfMonth = "April";
                break;
        case Calendar.MAY: nameOfMonth = "May";
                break;
        case Calendar.JUNE: nameOfMonth = "June";
                break;
        case Calendar.JULY: nameOfMonth = "July";
                break;
        case Calendar.AUGUST: nameOfMonth = "August";
                break;
        case Calendar.SEPTEMBER: nameOfMonth = "September";
                break;
        case Calendar.OCTOBER: nameOfMonth = "October";
                break;
        case Calendar.NOVEMBER: nameOfMonth = "November";
                break;
        case Calendar.DECEMBER: nameOfMonth = "December";
                break;
        }
    }

    public String getNameOfMonth() {
        return nameOfMonth;
    }

}
