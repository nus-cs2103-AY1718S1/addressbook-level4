package seedu.address.ui;

import static junit.framework.TestCase.assertEquals;

import org.junit.Before;
import org.junit.Test;

import seedu.address.ui.event.MonthDateBuilder;

//@@author dennaloh
public class EventCalendarTest {
    private String[] monthDateArray;
    private MonthDateBuilder monthDateBuilder;

    @Before
    public void setUp() {
        monthDateBuilder = new MonthDateBuilder();
        monthDateBuilder.setMonthYearArray(10, 2017);
        monthDateArray = monthDateBuilder.getMonthDateArray();
    }

    @Test
    public void test() throws Exception {
        assertEquals(monthDateBuilder.getNameOfMonth(), "November");
        assertEquals(monthDateArray[2], " ");
        assertEquals(monthDateArray[15], 13);
        assertEquals(monthDateArray[34], " ");
    }

}
