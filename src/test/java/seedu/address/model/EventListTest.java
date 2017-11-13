package seedu.address.model;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESLOT_SOCCER;
import static seedu.address.testutil.TypicalEvents.ANNIVERSARY;
import static seedu.address.testutil.TypicalEvents.BIRTHDAY;
import static seedu.address.testutil.TypicalEvents.DEADLINE;
import static seedu.address.testutil.TypicalEvents.EXAM;
import static seedu.address.testutil.TypicalEvents.MOURN;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventList;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.EventTimeClashException;
import seedu.address.model.event.timeslot.Date;
import seedu.address.testutil.EventBuilder;

//@@author shuang-yang

public class EventListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        EventList eventList = new EventList();
        thrown.expect(UnsupportedOperationException.class);
        eventList.asObservableList().remove(0);
    }

    @Test
    public void addWithoutTimeClash_success() {
        EventList eventList = new EventList();
        ReadOnlyEvent toAdd = new EventBuilder().build();
        try {
            eventList.add(toAdd);
        } catch (Exception e) {

        }
        ReadOnlyEvent added = eventList.asObservableList().get(0);

        assertEquals(toAdd, added);
    }

    @Test
    public void addWithTimeClash_failure() {
        EventList eventList = new EventList();
        ReadOnlyEvent toAdd1 = new EventBuilder().withTimeslot(VALID_TIMESLOT_SOCCER).build();
        ReadOnlyEvent toAdd2 = new EventBuilder().withTimeslot(VALID_TIMESLOT_SOCCER).build();
        ReadOnlyEvent toAdd2 = new EventBuilder().withTimeslot(VALID_TIMESLOT_SOCCER).build();

        try {
            eventList.add(toAdd1);
            eventList.add(toAdd2);
            fail("The expected exception was not thrown.")
        } catch (Exception e) {
            thrown.expect(EventTimeClashException.class);
        }

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
