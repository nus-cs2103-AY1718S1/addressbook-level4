package seedu.address.commons.core;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author limcel
public class MessagesTest {

    @Test
    public void testMessageClass() {
        Messages messageClass = new Messages();

        assertTrue("Unknown command".equals(messageClass.MESSAGE_UNKNOWN_COMMAND));
        assertTrue("Invalid command format! \n%1$s".equals(messageClass.MESSAGE_INVALID_COMMAND_FORMAT));
        assertTrue("The person index provided is invalid"
                .equals(messageClass.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX));
        assertTrue("The schedule index provided is invalid"
                .equals(messageClass.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX));
        assertTrue("%1$d persons listed!".equals(messageClass.MESSAGE_PERSONS_LISTED_OVERVIEW));
        assertTrue("Add or edit command do not accept/change remarks."
                .equals(messageClass.MESSAGE_ADDEDITCOMMANDREMARK_INVALID));
    }
}
