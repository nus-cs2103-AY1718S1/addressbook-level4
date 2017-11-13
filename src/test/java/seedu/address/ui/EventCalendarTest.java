package seedu.address.ui;

import static junit.framework.TestCase.assertEquals;

import org.junit.Test;

import seedu.address.ui.event.MonthDateBuilder;

//@@author dennaloh
public class EventCalendarTest {

    @Test
    public void testJan() throws Exception {
        MonthDateBuilder monthDateBuilderJan = new MonthDateBuilder(0, 2018);
        String[] monthDateArrayJan = monthDateBuilderJan.getMonthDateArray();
        assertEquals(monthDateBuilderJan.getNameOfMonth(), "January");
        assertEquals(monthDateBuilderJan.getFirstDayOfMonth().toString(), "1");
        assertEquals(monthDateArrayJan[0], " ");
        assertEquals(monthDateArrayJan[1], "1");
        assertEquals(monthDateArrayJan[31], "31");
        assertEquals(monthDateArrayJan[35], " ");
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

    @Test
    public void testApr() throws Exception {
        MonthDateBuilder monthDateBuilderApr = new MonthDateBuilder(3, 2018);
        String[] monthDateArrayApr = monthDateBuilderApr.getMonthDateArray();
        assertEquals(monthDateBuilderApr.getNameOfMonth(), "April");
        assertEquals(monthDateBuilderApr.getFirstDayOfMonth().toString(), "0");
        assertEquals(monthDateArrayApr[0], "1");
        assertEquals(monthDateArrayApr[29], "30");
        assertEquals(monthDateArrayApr[34], " ");
    }

    @Test
    public void testJune() throws Exception {
        MonthDateBuilder monthDateBuilderJune = new MonthDateBuilder(5, 2018);
        String[] monthDateArrayJune = monthDateBuilderJune.getMonthDateArray();
        assertEquals(monthDateBuilderJune.getNameOfMonth(), "June");
        assertEquals(monthDateBuilderJune.getFirstDayOfMonth().toString(), "5");
        assertEquals(monthDateArrayJune[4], " ");
        assertEquals(monthDateArrayJune[5], "1");
        assertEquals(monthDateArrayJune[34], "30");
        assertEquals(monthDateArrayJune[35], " ");
    }

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

}
