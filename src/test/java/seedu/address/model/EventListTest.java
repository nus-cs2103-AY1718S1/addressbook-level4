package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalEvents.ANNIVERSARY;
import static seedu.address.testutil.TypicalEvents.BIRTHDAY;
import static seedu.address.testutil.TypicalEvents.DEADLINE;
import static seedu.address.testutil.TypicalEvents.EXAM;
import static seedu.address.testutil.TypicalEvents.MOURN;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.model.event.EventList;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.timeslot.Date;

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

    //@@author reginleiff
    @Test
    public void getSubList_success() {
        EventList eventList = new EventList();
        EventList expectedSublist = new EventList();
        try {
            Date testDate = new Date("10/12/2017");

            eventList.add(BIRTHDAY);
            eventList.add(ANNIVERSARY);
            eventList.add(EXAM);
            eventList.add(MOURN);
            eventList.add(DEADLINE);

            expectedSublist.add(MOURN);
            expectedSublist.add(DEADLINE);

            ObservableList<ReadOnlyEvent> actualSublist = eventList.getObservableSubList(testDate);
            assertEquals(actualSublist, expectedSublist.asObservableList());
        } catch (Exception e) {
            return;
        }
    }
    //@@author
}
