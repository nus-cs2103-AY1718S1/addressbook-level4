package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalLessons.MA1101R_L1;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;

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

import seedu.address.model.lecturer.Lecturer;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.ReadOnlyLesson;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();


    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getLessonList());
        assertEquals(Collections.emptyList(), addressBook.getLecturerList());
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
        List<Lesson> newLessons = Arrays.asList(new Lesson(MA1101R_L1), new Lesson(MA1101R_L1));
        List<Lecturer> newLecturers = new ArrayList<>(MA1101R_L1.getLecturers());
        AddressBookStub newData = new AddressBookStub(newLessons, newLecturers);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void getLessonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getLessonList().remove(0);
    }

    @Test
    public void getLecturerList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getLecturerList().remove(0);
    }

    /**
     * A stub ReadOnlyAddressBook whose lessons and lecturers lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<ReadOnlyLesson> lessons = FXCollections.observableArrayList();
        private final ObservableList<Lecturer> lecturers = FXCollections.observableArrayList();

        AddressBookStub(Collection<? extends ReadOnlyLesson> lessons, Collection<? extends Lecturer> lecturers) {
            this.lessons.setAll(lessons);
            this.lecturers.setAll(lecturers);
        }

        @Override
        public ObservableList<ReadOnlyLesson> getLessonList() {
            return lessons;
        }

        @Override
        public ObservableList<Lecturer> getLecturerList() {
            return lecturers;
        }
    }

}
