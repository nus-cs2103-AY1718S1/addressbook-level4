package seedu.address.ui;

import static junit.framework.TestCase.assertEquals;

import org.junit.Test;

import seedu.address.ui.event.MonthDateBuilder;

//@@author dennaloh
public class EventCalendarTest {

    @Test
    public void testNov() throws Exception {
        MonthDateBuilder monthDateBuilderNov = new MonthDateBuilder(10, 2017);
        String[] monthDateArrayNov = monthDateBuilderNov.getMonthDateArray();
        assertEquals(monthDateBuilderNov.getNameOfMonth(), "November");
        assertEquals(monthDateBuilderNov.getFirstDayOfMonth().toString(), "3");
        assertEquals(monthDateArrayNov[2], " ");
        assertEquals(monthDateArrayNov[3], "1");
        assertEquals(monthDateArrayNov[20], "18");
        assertEquals(monthDateArrayNov[34], " ");
    }

    @Test
    public void testFeb() throws Exception {
        MonthDateBuilder monthDateBuilderFeb = new MonthDateBuilder(1, 2017);
        String[] monthDateArrayFeb = monthDateBuilderFeb.getMonthDateArray();
        assertEquals(monthDateBuilderFeb.getNameOfMonth(), "February");
        assertEquals(monthDateBuilderFeb.getFirstDayOfMonth().toString(), "3");
        assertEquals(monthDateArrayFeb[2], " ");
        assertEquals(monthDateArrayFeb[3], "1");
        assertEquals(monthDateArrayFeb[30], "28");
        assertEquals(monthDateArrayFeb[34], " ");
    }

}
