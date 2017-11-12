package seedu.address.ui.event;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.ui.UiPart;

import java.util.Calendar;

//@@author dennaloh
public class EventCalendar extends UiPart<Region> {
    private static final String FXML = "event/EventCalendar.fxml";

    @FXML
    private Text date1, date2, date3, date4, date5, date6, date7, date8, date9, date10, date11, date12, date13, date14,
            date15, date16, date17, date18, date19, date20, date21, date22, date23, date24, date25, date26, date27, date28,
            date29, date30, date31, date32, date33, date34, date35, date36, date37, date38, date39, date40, date41, date42;
    @FXML
    private Label monthName;


    private Text[] dateArray;

    private MonthDateBuilder monthDateBuilder;


    public EventCalendar() {
        super(FXML);
        monthDateBuilder = new MonthDateBuilder();
        inits(); // to put all text views into array to facilitate looping through
        setDates(monthDateBuilder.getMonthDateArray());
        monthName.setText(monthDateBuilder.getNameOfMonth());
    }

    public void setDates(String[] _monthDateArray) {
        dateArray[0].setText(_monthDateArray[0]);
        dateArray[1].setText(_monthDateArray[1]);
        dateArray[2].setText(_monthDateArray[2]);
        dateArray[3].setText(_monthDateArray[3]);
        dateArray[4].setText(_monthDateArray[4]);
        dateArray[5].setText(_monthDateArray[5]);
        dateArray[6].setText(_monthDateArray[6]);
        dateArray[7].setText(_monthDateArray[7]);
        dateArray[8].setText(_monthDateArray[8]);
        dateArray[9].setText(_monthDateArray[9]);
        dateArray[10].setText(_monthDateArray[10]);
        dateArray[11].setText(_monthDateArray[11]);
        dateArray[12].setText(_monthDateArray[12]);
        dateArray[13].setText(_monthDateArray[13]);
        dateArray[14].setText(_monthDateArray[14]);
        dateArray[15].setText(_monthDateArray[15]);
        dateArray[16].setText(_monthDateArray[16]);
        dateArray[17].setText(_monthDateArray[17]);
        dateArray[18].setText(_monthDateArray[18]);
        dateArray[19].setText(_monthDateArray[19]);
        dateArray[20].setText(_monthDateArray[20]);
        dateArray[21].setText(_monthDateArray[21]);
        dateArray[22].setText(_monthDateArray[22]);
        dateArray[23].setText(_monthDateArray[23]);
        dateArray[24].setText(_monthDateArray[24]);
        dateArray[25].setText(_monthDateArray[25]);
        dateArray[26].setText(_monthDateArray[26]);
        dateArray[27].setText(_monthDateArray[27]);
        dateArray[28].setText(_monthDateArray[28]);
        dateArray[29].setText(_monthDateArray[29]);
        dateArray[30].setText(_monthDateArray[30]);
        dateArray[31].setText(_monthDateArray[31]);
        dateArray[32].setText(_monthDateArray[32]);
        dateArray[33].setText(_monthDateArray[33]);
        dateArray[34].setText(_monthDateArray[34]);
        dateArray[35].setText(_monthDateArray[35]);
        dateArray[36].setText(_monthDateArray[36]);
        dateArray[37].setText(_monthDateArray[37]);
        dateArray[38].setText(_monthDateArray[38]);
        dateArray[39].setText(_monthDateArray[39]);
        dateArray[40].setText(_monthDateArray[40]);
        dateArray[41].setText(_monthDateArray[41]);

    }

    public void inits() {
        dateArray = new Text[42];
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


class MonthDateBuilder {
    private Integer[] monthYearArray;
    private String[] monthDateArray;
    private Calendar calendar;
    private Integer firstDayOfMonth, maxDayOfMonth;
    private String nameOfMonth;

    public MonthDateBuilder() {
        calendar = Calendar.getInstance();
        firstDayOfMonth = 1; // random inits, can delete
        maxDayOfMonth = 5; //random inits, can delete for testing purposes


        monthDateArray = new String[42];// FOR STORING THE VALUES FOR THE DATE YOU SEE
        monthYearArray = new Integer[2];// FOR USE IN CALENDAR IF YOU WANT TO IN THE FUTURE USE CUSTOM MONTHS MONTH/YEAR
        monthYearArray[0] = calendar.get(Calendar.MONTH); //RETURNS YOU CURRENT MONTH
        monthYearArray[1] = calendar.get(Calendar.YEAR); //RETURNS YOU CURRENT YEAR
        setNameOfMonth();


        calendar.set(Calendar.MONTH, monthYearArray[0]); //SETS YOUR CALENDAR OBJECT AS WHATEVER MONTH YOU WANT
        calendar.set(Calendar.YEAR, monthYearArray[1]); //SETS YOUR CALENDAR OBJECT YEAR VALUES AS WTV YOU WANT


        setMonthAchors(); // THIS IS TO SET THE MONTH ANCHORS, BEING THE FIRST DAY NUMBER AND THE MAx days
        setMonthArrays(); //THIS IS TO BUILD THE ARRAY OF 42, FOR BUILDING THE VIEW OF THE MONTH
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
        while (a < 42) {
            monthDateArray[a] = " ";
            a++;
        }


    }


    public String[] getMonthDateArray() {
        return monthDateArray;
    }

    public void setNameOfMonth(){
        switch(monthYearArray[0]){
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

    public String getNameOfMonth(){return nameOfMonth;}


}
