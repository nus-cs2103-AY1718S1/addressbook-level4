package seedu.address.model.schedule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author YuchenHe98
public class TimeTest {

    @Test
    public void isValidTime() {

        // blank input
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only

        // wrong input
        assertFalse(Time.isValidTime("0a30"));
        assertFalse(Time.isValidTime("600"));

        //too early or too late
        assertFalse(Time.isValidTime("0530"));
        assertFalse(Time.isValidTime("2400"));

        // valid days
        assertTrue(Time.isValidTime("0930"));
        assertTrue(Time.isValidTime("1700"));
        assertTrue(Time.isValidTime("0600"));
        assertTrue(Time.isValidTime("2330"));
    }
}

