package seedu.address.ui;

import static junit.framework.TestCase.assertEquals;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import seedu.address.ui.event.EventCalendar;
import seedu.address.ui.event.MonthDateBuilder;

//@@author dennaloh
public class EventCalendarTest extends GuiUnitTest {

    private EventCalendar eventCalendar;
    private MonthDateBuilder monthDateBuilder;
    private Calendar calendar = Calendar.getInstance();

    @Before
    public void setUp() {
        monthDateBuilder = new MonthDateBuilder();
    }

    @Test
    public int getNameOfMonthToInt(String nameOfMonth) {
        switch(nameOfMonth) {
        case "January": return 0;
        case "February": return 1;
        case "March": return 2;
        case "April": return 3;
        case "May": return 4;
        case "June": return 5;
        case "July": return 6;
        case "August": return 7;
        case "September": return 8;
        case "October": return 9;
        case "November": return 10;
        case "December": return 11;
        default: return 0;
        }
    }

    @Test
    public void display() {
        assertEquals(getNameOfMonthToInt(monthDateBuilder.getNameOfMonth()), calendar.MONTH);
    }

}
