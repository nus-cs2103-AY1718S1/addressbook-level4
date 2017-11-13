package seedu.address.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.TypicalEvents.EVENT1;
import static seedu.address.testutil.TypicalEvents.EVENT2;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;

//@@author junyango
public class UniqueEventListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueEventList.asObservableList().remove(0);
    }

    //@@author yunpengn
    @Test
    public void add_allPresent_checkCorrectness() throws Exception {
        UniqueEventList list = new UniqueEventList();
        assertEquals(0, list.asObservableList().size());

        list.add(EVENT1);
        assertEquals(1, list.asObservableList().size());

        list.add(EVENT2);
        assertEquals(2, list.asObservableList().size());
    }

    @Test
    public void add_haveDuplicate_expectException() throws Exception {
        UniqueEventList list = new UniqueEventList();
        assertEquals(0, list.asObservableList().size());

        list.add(EVENT1);
        assertEquals(1, list.asObservableList().size());

        thrown.expect(DuplicateEventException.class);
        list.add(EVENT1);
        assertEquals(1, list.asObservableList().size());
    }

    @Test
    public void addEvents_success_checkCorrectness() throws Exception {
        UniqueEventList list1 = new UniqueEventList();
        list1.add(EVENT1);
        list1.add(EVENT2);

        UniqueEventList list2 = new UniqueEventList();
        list2.addEvents(Arrays.asList(EVENT1, EVENT2));

        assertEquals(list1, list2);
    }

    @Test
    public void sort_basedOnTime_checkCorrectness() throws Exception {
        UniqueEventList list = new UniqueEventList();
        list.add(EVENT1);
        list.add(EVENT2);
        assertEquals(2, list.asObservableList().size());

        list.sortEvents();
        assertEquals(EVENT1, list.asObservableList().get(0));
        assertEquals(EVENT2, list.asObservableList().get(1));
    }

    @Test
    public void setEvent_changeSingleEvent_checkCorrectness() throws Exception {
        UniqueEventList list = new UniqueEventList();
        list.add(EVENT1);
        assertEquals(1, list.asObservableList().size());

        list.setEvent(EVENT1, EVENT2);
        assertEquals(EVENT2, list.asObservableList().get(0));
    }

    @Test
    public void setEvent_newEventAlreadyExist_expectException() throws Exception {
        UniqueEventList list = new UniqueEventList();
        list.add(EVENT1);
        list.add(EVENT2);
        assertEquals(2, list.asObservableList().size());

        thrown.expect(DuplicateEventException.class);
        list.setEvent(EVENT1, EVENT2);
        assertEquals(2, list.asObservableList().size());
        assertEquals(EVENT1, list.asObservableList().get(0));
    }

    @Test
    public void setEvent_eventNotFound_expectException() throws Exception {
        UniqueEventList list = new UniqueEventList();
        list.add(EVENT1);
        assertEquals(1, list.asObservableList().size());

        thrown.expect(EventNotFoundException.class);
        list.setEvent(EVENT2, EVENT1);
        assertEquals(1, list.asObservableList().size());
        assertEquals(EVENT1, list.asObservableList().get(0));
    }

    @Test
    public void remove_success_checkCorrectness() throws Exception {
        UniqueEventList list = new UniqueEventList();
        list.add(EVENT1);
        list.add(EVENT2);
        assertEquals(2, list.asObservableList().size());

        list.remove(EVENT1);
        assertEquals(1, list.asObservableList().size());
        assertEquals(EVENT2, list.asObservableList().get(0));
    }

    @Test
    public void equal_checkCorrectness() {
        UniqueEventList list1 = new UniqueEventList();
        UniqueEventList list2 = new UniqueEventList();

        assertEquals(list1, list1);
        assertEquals(list1, list2);
        assertNotEquals(list1, null);
        assertNotEquals(list1, 1);
    }
}
