package seedu.room.model;

import static junit.framework.TestCase.assertEquals;
import static seedu.room.testutil.TypicalEvents.getTypicalEventBook;

import java.util.Collection;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.room.model.event.ReadOnlyEvent;

//@@author sushinoya
public class EventBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EventBook eventBook = new EventBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), eventBook.getEventList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyEventBook_replacesData() {
        EventBook newData = getTypicalEventBook();
        eventBook.resetData(newData);
        assertEquals(newData, eventBook);
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        eventBook.getEventList().remove(0);
    }

    /**
     * A stub ReadOnlyEventBook whose eventlists can violate interface constraints.
     */
    private static class EventBookStub implements ReadOnlyEventBook {
        private final ObservableList<ReadOnlyEvent> events = FXCollections.observableArrayList();

        EventBookStub(Collection<? extends ReadOnlyEvent> events) {

            this.events.setAll(events);
        }

        @Override
        public ObservableList<ReadOnlyEvent> getEventList() {
            return events;
        }
    }
}
