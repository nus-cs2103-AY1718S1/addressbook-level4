package seedu.address.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EmailTaskTest {
    private EmailTask task = new EmailTask();

    @Test
    public void getsetTask() {
        //Empty Task --> Returns True
        assertEquals("", task.getTask());

        //send --> Returns True
        task.setTask("send");
        assertEquals("send", task.getTask());
    }

    @Test
    public void isValid() {
        //Empty Task --> Returns False
        task.setTask("");
        assertFalse(task.isValid());

        //send Task --> Returns True
        task.setTask("send");
        assertTrue(task.isValid());

        //send clear --> Returns True
        task.setTask("clear");
        assertTrue(task.isValid());

        //unknown Task --> Returns True
        task.setTask("1234");
        assertFalse(task.isValid());
    }

    @Test
    public void equals() {
        EmailTask standardEmailTask = new EmailTask();
        task = new EmailTask();

        //same values --> returns true
        assertTrue(standardEmailTask.equals(task));

        //same object --> returns true

        assertTrue(standardEmailTask.equals(standardEmailTask));

        //null --> returns false
        assertFalse(standardEmailTask.equals(null));

        //different type --> returns false
        assertFalse(standardEmailTask.equals(5));

        //different values --> returns false
        task.setTask("send");
        assertFalse(standardEmailTask.equals(task));
    }

}
