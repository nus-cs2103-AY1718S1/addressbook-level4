package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.module.BookedSlot;
import seedu.address.model.module.Code;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.Location;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.exceptions.DuplicateBookedSlotException;
import seedu.address.model.module.exceptions.DuplicateLessonException;
import seedu.address.model.module.exceptions.LessonNotFoundException;
import seedu.address.testutil.LessonBuilder;

public class AddCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullLesson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_lessonAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingLessonAdded modelStub = new ModelStubAcceptingLessonAdded();
        Lesson validLesson = new LessonBuilder().build();

        CommandResult commandResult = getAddCommandForLesson(validLesson, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validLesson), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validLesson), modelStub.lessonsAdded);
    }

    @Test
    public void execute_duplicateTimeSlot_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateTimeSlotException();
        Lesson validLesson = new LessonBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_BOOKEDSLOT);

        getAddCommandForLesson(validLesson, modelStub).execute();

    }

    @Test
    public void execute_duplicateLesson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateLessonException();
        Lesson validLesson = new LessonBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_LESSON);

        getAddCommandForLesson(validLesson, modelStub).execute();
    }

    @Test
    public void equals() {
        Lesson algebra = new LessonBuilder().withCode("MA1101R").build();
        Lesson software = new LessonBuilder().withCode("CS2103T").build();
        AddCommand addAlgebraCommand = new AddCommand(algebra);
        AddCommand addSoftwareCommand = new AddCommand(software);

        // same object -> returns true
        assertTrue(addAlgebraCommand.equals(addAlgebraCommand));

        // same values -> returns true
        AddCommand addAlgebraCommandCopy = new AddCommand(algebra);
        assertTrue(addAlgebraCommand.equals(addAlgebraCommandCopy));

        // different types -> returns false
        assertFalse(addAlgebraCommand.equals(1));

        // null -> returns false
        assertFalse(addAlgebraCommand.equals(null));

        // different lessons-> returns false
        assertFalse(addAlgebraCommand.equals(addSoftwareCommand));
    }

    /**
     * Generates a new AddCommand with the details of the given lesson.
     */
    private AddCommand getAddCommandForLesson(Lesson lesson, Model model) {
        AddCommand command = new AddCommand(lesson);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public HashSet<Location> getUniqueLocationSet() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public HashSet<Code> getUniqueCodeSet() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateBookedSlotSet() {
            fail("This method should not be called.");
        }

        @Override
        public void deleteLesson(ReadOnlyLesson target) throws LessonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteLessonSet(List<ReadOnlyLesson> lessonList) throws LessonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addLesson(ReadOnlyLesson lesson) throws DuplicateLessonException {
            fail("This method should not be called.");
        }

        @Override
        public void bookmarkLesson(ReadOnlyLesson lesson) throws DuplicateLessonException {
            fail("This method should not be called.");
        }

        @Override
        public void unBookmarkLesson(ReadOnlyLesson lesson) {
            fail("This method should not be called.");
        }

        @Override
        public void bookingSlot(BookedSlot booking) throws DuplicateBookedSlotException {
        }

        @Override
        public void unbookBookedSlot(BookedSlot booking) {
            fail("This method should not be called.");
        }

        @Override
        public void updateBookedSlot(BookedSlot target, BookedSlot newBookingSlot) throws DuplicateBookedSlotException {
            fail("This method should not be called.");
        }

        @Override
        public void unbookAllSlot() {
            fail("This method should not be called.");
        }

        @Override
        public void updateLesson(ReadOnlyLesson target, ReadOnlyLesson editedLesson)
                throws DuplicateLessonException, LessonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyLesson> getFilteredLessonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredLessonList(Predicate<ReadOnlyLesson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void handleListingUnit() {
            fail("This method should not be called.");
        }

        @Override
        public void sortLessons() {
            fail("This method should not be called.");
        }

        @Override
        public void setCurrentViewingLesson(ReadOnlyLesson lesson) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyLesson getCurrentViewingLesson() {
            return null;
        }

        @Override
        public void setViewingPanelAttribute(String attribute) {
            fail("This method should not be called.");
        }

        @Override
        public String getCurrentViewingAttribute() {
            return null;
        }
    }

    /**
     * A Model stub that always throw a DuplicateLessonException when trying to add a lesson.
     */
    private class ModelStubThrowingDuplicateLessonException extends ModelStub {
        @Override
        public void addLesson(ReadOnlyLesson lesson) throws DuplicateLessonException {
            throw new DuplicateLessonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the lesson being added.
     */
    private class ModelStubAcceptingLessonAdded extends ModelStub {
        final ArrayList<Lesson> lessonsAdded = new ArrayList<>();

        @Override
        public void addLesson(ReadOnlyLesson lesson) throws DuplicateLessonException {
            lessonsAdded.add(new Lesson(lesson));
        }

        @Override
        public void handleListingUnit() {

        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always thrown duplicate time slot exception.
     */
    private class ModelStubThrowingDuplicateTimeSlotException extends ModelStub {
        final ArrayList<Lesson> lessonsAdded = new ArrayList<>();

        @Override
        public void bookingSlot(BookedSlot booking) throws DuplicateBookedSlotException {
            throw new DuplicateBookedSlotException();
        }

        @Override
        public void handleListingUnit() {

        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
