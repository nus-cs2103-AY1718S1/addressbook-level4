package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.EventList;

//@@author a0107442n

public class EventListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        EventList eventList = new EventList();
        thrown.expect(UnsupportedOperationException.class);
        eventList.asObservableList().remove(0);
    }
}
