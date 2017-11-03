package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.calendarfx.view.CalendarView;

import seedu.address.testutil.TypicalPersons;

//@@author Eric
public class CalendarWindowTest extends GuiUnitTest {

    private CalendarWindow calendarWindow;
    @Before
    public void setUp() {
        calendarWindow = new CalendarWindow(TypicalPersons.getTypicalAddressBook().getPersonList());
        uiPartRule.setUiPart(calendarWindow);
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
