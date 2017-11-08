package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

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
        //default page should be daily
        assertEquals(calendarWindow.getRoot().getSelectedPage(), calendarWindow.getRoot().getDayPage());
    }
}
