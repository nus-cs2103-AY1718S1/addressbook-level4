package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalEvents.EVENT1;
import static seedu.address.testutil.TypicalEvents.EVENT2;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ALICE_UPDATED;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
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
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    /*****************************************************
     * Test cases for constructors
     *****************************************************/

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getTagList());
        assertEquals(Collections.emptyList(), addressBook.getEventList());
    }

    @Test
    public void alternativeConstructor() {
        AddressBook origin = getTypicalAddressBook();
        AddressBook copy = new AddressBook(origin);
        assertEquals(origin, copy);
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        // Repeat ALICE twice
        List<Person> newPersons = Arrays.asList(new Person(ALICE), new Person(ALICE));
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        List<Event> newEvents = Arrays.asList(new Event(EVENT1), new Event(EVENT2));
        AddressBookStub newData = new AddressBookStub(newPersons, newEvents, newTags);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateEvents_throwsAssertionError() {
        List<Person> newPersons = Arrays.asList(new Person(ALICE), new Person(BENSON));
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        newTags.addAll(BENSON.getTags());
        // Repeat EVENT1 twice
        List<Event> newEvents = Arrays.asList(new Event(EVENT1), new Event(EVENT1));
        AddressBookStub newData = new AddressBookStub(newPersons, newEvents, newTags);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    /*****************************************************
     * Test cases for persons.
     *****************************************************/

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
    }

    @Test
    public void removePerson_correctCase_checkCorrectness() throws Exception {
        AddressBook addressBook = getTypicalAddressBook();
        addressBook.removePerson(ALICE);
        assertFalse(addressBook.getPersonList().contains(ALICE));
    }

    @Test
    public void removePerson_personNotFound_expectException() throws Exception {
        thrown.expect(PersonNotFoundException.class);

        AddressBook addressBook = getTypicalAddressBook();
        addressBook.removePerson(HOON);
    }

    @Test
    public void sortPersonList_causedByAddPerson_checkSorted() throws Exception {
        // Adds a new person into typicalAddressBook, who should be placed as the last one after sorting.
        AddressBook addressBook = getTypicalAddressBook();
        addressBook.addPerson(AMY);
        addressBook.sortPersonList();

        // Only Alice should be placed before Amy (order by name incrementally).
        assertEquals(1, addressBook.getPersonList().indexOf(AMY));
    }

    @Test
    public void sortPersonList_causedByUpdatePerson_checkSorted() throws Exception {
        // Updates the name of an existing person in typicalAddressBook.
        AddressBook addressBook = getTypicalAddressBook();
        addressBook.updatePerson(ALICE, ALICE_UPDATED);
        addressBook.sortPersonList();

        // Now, it should be placed as the last person in the listing since the name has been changed.
        ObservableList<ReadOnlyPerson> list = addressBook.getPersonList();
        assertEquals(list.size() - 1, list.indexOf(ALICE_UPDATED));
    }

    /*****************************************************
     * Test cases for events.
     *****************************************************/

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getEventList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTagList().remove(0);
    }

    /**
     * A stub of {@link ReadOnlyAddressBook} whose persons and tags lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<ReadOnlyPerson> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        private final ObservableList<ReadOnlyEvent> events = FXCollections.observableArrayList();

        AddressBookStub(Collection<? extends ReadOnlyPerson> persons, Collection<? extends ReadOnlyEvent> events,
                        Collection<? extends Tag> tags) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
            this.events.setAll(events);
        }

        @Override
        public ObservableList<ReadOnlyPerson> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<ReadOnlyEvent> getEventList() {
            return events;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
