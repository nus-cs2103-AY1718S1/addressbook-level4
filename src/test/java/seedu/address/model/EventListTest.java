package seedu.address.model;
// @@author HuWanqing

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalEvents.FIRST;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;






public class EventListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EventList eventList = new EventList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), eventList.getEventList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyEventList_replacesData() {
        EventList newData = getTypicalEventList();
        eventList.resetData(newData);
        assertEquals(newData, eventList);
    }

    @Test
    public void resetData_withDuplicateEvents_throwsAssertionError() {
        // Repeat FIRST twice
        List<Event> newEvents = Arrays.asList(new Event(FIRST), new Event(FIRST));
        EventListStub newData = new EventListTest.EventListStub(newEvents);

        thrown.expect(AssertionError.class);
        eventList.resetData(newData);
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        eventList.getEventList().remove(0);
    }

    /**
     * A stub ReadOnlyEventList whose event lists can violate interface constraints.
     */
    private static class EventListStub implements ReadOnlyEventList {
        private final ObservableList<ReadOnlyEvent> events = FXCollections.observableArrayList();

        EventListStub(Collection<? extends ReadOnlyEvent> events) {
            this.events.setAll(events);
        }

        @Override
        public ObservableList<ReadOnlyEvent> getEventList() {
            return events;
        }

    }
}

