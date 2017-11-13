package seedu.address.model.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISPLAY_DATE_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NATURAL_DATE_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NATURAL_DATE_EVENT2;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

//@@author junyango
public class DateTimeTest {
    @BeforeClass
    public static void setUp() {
        PropertyManager.initializePropertyManager();
    }

    @Test
    public void isValidTime() {
        // invalid time
        assertFalse(DateTime.isValidTime("")); // empty string
        assertFalse(DateTime.isValidTime("some random staff")); // unrelated string

        // valid time
        assertTrue(DateTime.isValidTime("25122015 08:30"));
        assertTrue(DateTime.isValidTime("14122016 13:30"));
        assertTrue(DateTime.isValidTime("09121924 23:30"));
    }

    //@@author yunpengn
    @Test
    public void create_viaString_checkCorrectness() throws Exception {
        DateTime dateTime = new DateTime(VALID_DATE_EVENT1);
        assertEquals(VALID_DISPLAY_DATE_EVENT1, dateTime.getValue());
    }

    @Test
    public void create_viaDateObject_checkCorrectness() throws Exception {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMMyyyy HH:mm");
        Date date = dateFormatter.parse(VALID_DATE_EVENT1);

        // Create a Datetime property via alternative constructor.
        DateTime dateTime = new DateTime(date);
        assertEquals(VALID_DISPLAY_DATE_EVENT1, dateTime.getValue());
    }

    @Test
    public void create_viaNaturalLanguage_checkCorrectness() throws Exception {
        assertEquals(new DateTime(VALID_DATE_EVENT1), new DateTime(VALID_NATURAL_DATE_EVENT1));
        assertEquals(new DateTime(VALID_DATE_EVENT2), new DateTime(VALID_NATURAL_DATE_EVENT2));
    }
}
