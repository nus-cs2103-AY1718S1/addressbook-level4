package seedu.address.ui.event;

import java.util.Calendar;

//@@author dennaloh
/**
 * Creates the dates in the month and name of month
 */
public class MonthDateBuilder {
    private static final int MAX_NUMBER_DAYS = 42; //Maximum number of cells every month has
    private Integer[] monthYearArray;
    private String[] monthDateArray;
    private Calendar calendar;
    private Integer firstDayOfMonth; //First day in the month
    private Integer maxDayOfMonth;   //Number of days in the month
    private String nameOfMonth;

    /**
     * The constructor without parameter is used to get the current month;
     * the other one is used as to get an arbitrary month and is used for testing.
     */
    public MonthDateBuilder() {
        calendar = Calendar.getInstance();

        monthDateArray = new String[MAX_NUMBER_DAYS];
        monthYearArray = new Integer[2];
        setMonthYearArray(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)); //Set current month and year
        setNameOfMonth();
        calendar.set(Calendar.MONTH, monthYearArray[0]);   //Sets the given calendar MONTH to monthYearArray[0]
        calendar.set(Calendar.YEAR, monthYearArray[1]);    //Sets the given calendar YEAR to monthYearArray[1]

        setMonthAnchors();
        buildMonthArrays();
    }

    public MonthDateBuilder(Integer month, Integer year) {
        calendar = Calendar.getInstance();

        monthDateArray = new String[MAX_NUMBER_DAYS];
        monthYearArray = new Integer[2];
        setMonthYearArray(month, year); //Sets arbitrary month and year
        setNameOfMonth();
        calendar.set(Calendar.MONTH, monthYearArray[0]);   //Sets the given calendar MONTH to monthYearArray[0]
        calendar.set(Calendar.YEAR, monthYearArray[1]);    //Sets the given calendar YEAR to monthYearArray[1]

        setMonthAnchors();
        buildMonthArrays();
    }

    /**
     * Stores desired month and year in monthYearArray
     * @param month the value used to set the <code>MONTH</code> calendar field.
     * Month value is 0-based. e.g., 0 for January.
     * @param year the value used to set the <code>YEAR</code> calendar field.
     */
    private void setMonthYearArray(Integer month, Integer year) {
        monthYearArray[0] = month;
        monthYearArray[1] = year;
    }

    /**
     * Sets the month anchors (first day and number of days in the month)
     * by setting the calender to 1st date of the month and finding out the day of the week it falls on
     */
    private void setMonthAnchors() {
        maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        Integer dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek) {
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
        default:
            firstDayOfMonth = 0;
        }
    }

    public Integer getFirstDayOfMonth() {
        return firstDayOfMonth;
    }

    /**
     * Builds the monthly view with the required spaces and numbers
     */
    public void buildMonthArrays() {
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
        default:
            nameOfMonth = null;
        }
    }

    public String getNameOfMonth() {
        return nameOfMonth;
    }

}
