package seedu.address.model.reminder;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MessageTest {

    @Test
    public void isValidMessage() {
        assertTrue(Message.isValidMessage("Buy present for friend."));
    }
}
