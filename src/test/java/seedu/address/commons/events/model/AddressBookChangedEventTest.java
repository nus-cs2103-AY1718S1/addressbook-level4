package seedu.address.commons.events.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.AddressBook;

public class AddressBookChangedEventTest {
    @Test
    public void createEvent_success() throws Exception {
        BaseEvent event = new AddressBookChangedEvent(new AddressBook());
        assertEquals("number of persons 0, number of tags 0", event.toString());
    }
}
