package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalMeetings.AGEING;
import static seedu.address.testutil.TypicalMeetings.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.ALICE;

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
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.ReadOnlyMeeting;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getTagList());
        assertEquals(Collections.emptyList(), addressBook.getMeetingList());
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
        List<Meeting> newMeetings = Arrays.asList(new Meeting(AGEING));
        AddressBookStub newData = new AddressBookStub(newPersons, newTags, newMeetings);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    //@@author Melvin-leo
    @Test
    public void resetData_withDuplicateMeetings_throwsAssertionError() {
        // Repeat AGEING twice
        List<Person> newPersons = Arrays.asList(new Person(ALICE));
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        List<Meeting> newMeetings = Arrays.asList(new Meeting(AGEING), new Meeting(AGEING));
        AddressBookStub newData = new AddressBookStub(newPersons, newTags, newMeetings);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }
    //@@author

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTagList().remove(0);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons and tags lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<ReadOnlyPerson> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        private final ObservableList<ReadOnlyMeeting> meetings = FXCollections.observableArrayList();

        AddressBookStub(Collection<? extends ReadOnlyPerson> persons, Collection<? extends Tag> tags,
                        Collection<? extends ReadOnlyMeeting> meetings) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
            this.meetings.setAll(meetings);
        }

        @Override
        public ObservableList<ReadOnlyPerson> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }

        @Override
        public ObservableList<ReadOnlyMeeting> getMeetingList() {
            return meetings;
        }
    }

}
