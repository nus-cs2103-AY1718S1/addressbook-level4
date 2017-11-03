package seedu.address.ui;

import com.calendarfx.view.CalendarView;
import org.junit.Before;
import org.junit.Test;
import seedu.address.testutil.TypicalPersons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

//@@author Eric
public class CalendarWindowTest {

    private CalendarWindow calendarWindow;
    @Before
    public void setUp() {
        com.sun.javafx.application.PlatformImpl.startup(()-> {});
        calendarWindow = new CalendarWindow(TypicalPersons.getTypicalAddressBook().getPersonList());

    }

    @Test
    public void display() {
        assertNotNull(calendarWindow.getRoot());
    }

    @Test
    public void setNextViewTest() {

        //Default view should be week view
        CalendarView calendarView = calendarWindow.getRoot();
        assertEquals(calendarView.getSelectedPage(), calendarView.getWeekPage());

        //Switch to month view
        calendarWindow.showNextPage();
        assertEquals(calendarView.getSelectedPage(), calendarView.getMonthPage());

        //Switch to year view
        calendarWindow.showNextPage();
        assertEquals(calendarView.getSelectedPage(), calendarView.getYearPage());

        //Switch to day view
        calendarWindow.showNextPage();
        assertEquals(calendarView.getSelectedPage(), calendarView.getDayPage());

        //Switch to week view
        calendarWindow.showNextPage();
        assertEquals(calendarView.getSelectedPage(), calendarView.getWeekPage());


    }


}
