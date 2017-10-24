package seedu.room.model;

import static org.junit.Assert.assertEquals;
import static seedu.room.testutil.TypicalPersons.ALICE;
import static seedu.room.testutil.TypicalPersons.getTypicalRoomBook;

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
import seedu.room.model.person.Person;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.tag.Tag;

public class RoomBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final RoomBook roomBook = new RoomBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), roomBook.getPersonList());
        assertEquals(Collections.emptyList(), roomBook.getTagList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        roomBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyRoomBook_replacesData() {
        RoomBook newData = getTypicalRoomBook();
        roomBook.resetData(newData);
        assertEquals(newData, roomBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        // Repeat ALICE twice
        List<Person> newPersons = Arrays.asList(new Person(ALICE), new Person(ALICE));
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        RoomBookStub newData = new RoomBookStub(newPersons, newTags);

        thrown.expect(AssertionError.class);
        roomBook.resetData(newData);
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        roomBook.getPersonList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        roomBook.getTagList().remove(0);
    }

    /**
     * A stub ReadOnlyRoomBook whose persons and tags lists can violate interface constraints.
     */
    private static class RoomBookStub implements ReadOnlyRoomBook {
        private final ObservableList<ReadOnlyPerson> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        RoomBookStub(Collection<? extends ReadOnlyPerson> persons, Collection<? extends Tag> tags) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<ReadOnlyPerson> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
