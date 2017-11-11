package seedu.room.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.room.testutil.TypicalPersons.ALICE;
import static seedu.room.testutil.TypicalPersons.TEMPORARY_DOE;
import static seedu.room.testutil.TypicalPersons.TEMPORARY_JOE;
import static seedu.room.testutil.TypicalPersons.getTypicalResidentBook;

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
import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.model.person.Person;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.exceptions.PersonNotFoundException;
import seedu.room.model.tag.Tag;
import seedu.room.testutil.ResidentBookBuilder;

public class ResidentBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ResidentBook residentBook = new ResidentBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), residentBook.getPersonList());
        assertEquals(Collections.emptyList(), residentBook.getTagList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        residentBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyResidentBook_replacesData() {
        ResidentBook newData = getTypicalResidentBook();
        residentBook.resetData(newData);
        assertEquals(newData, residentBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        // Repeat ALICE twice
        List<Person> newPersons = Arrays.asList(new Person(ALICE), new Person(ALICE));
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        ResidentBookStub newData = new ResidentBookStub(newPersons, newTags);

        thrown.expect(AssertionError.class);
        residentBook.resetData(newData);
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        residentBook.getPersonList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        residentBook.getTagList().remove(0);
    }

    //@@author Haozhe321
    @Test
    public void deleteTemporaryTest() {
        ResidentBook residentBookOne = new ResidentBookBuilder().withPerson(TEMPORARY_JOE).build();

        residentBookOne.deleteTemporary();
        //added temporary has argument of 0, so it stays permanently -> returns false
        assertFalse(residentBookOne.getPersonList().size() == 0);

        ResidentBook residentBookTwo = new ResidentBookBuilder().withPerson(TEMPORARY_JOE).
                withPerson(TEMPORARY_DOE).build();

        residentBookTwo.deleteTemporary();
        //added new temporary contact has expiry day of 1000 years, so it won't be deleted
        assertTrue(residentBookTwo.getPersonList().size() == 2);

    }
    //@@author

    /**
     * A stub ReadOnlyResidentBook whose persons and tags lists can violate interface constraints.
     */
    private static class ResidentBookStub implements ReadOnlyResidentBook {
        private final ObservableList<ReadOnlyPerson> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        ResidentBookStub(Collection<? extends ReadOnlyPerson> persons, Collection<? extends Tag> tags) {
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
