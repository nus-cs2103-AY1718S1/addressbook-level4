package seedu.room.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.room.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.room.testutil.TypicalPersons.ALICE;
import static seedu.room.testutil.TypicalPersons.BENSON;
import static seedu.room.testutil.TypicalPersons.TEMPORARY_JOE;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.model.person.NameContainsKeywordsPredicate;
import seedu.room.model.person.exceptions.PersonNotFoundException;
import seedu.room.testutil.RoomBookBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    @Test
    public void deleteTemporaryTest() throws IllegalValueException, PersonNotFoundException {
        RoomBook roomBook = new RoomBookBuilder().withPerson(TEMPORARY_JOE).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(roomBook, userPrefs);

        //modelManager has one temporary person inside -> returns false
        assertFalse(modelManager.equals(null));

        modelManager.deleteTemporary(roomBook);
        //added temporary has argument of 0, so it stays permanently -> returns false
        assertFalse(modelManager.getRoomBook().getPersonList().size() == 0);

    }

    @Test
    public void equals() {
        RoomBook roomBook = new RoomBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        RoomBook differentRoomBook = new RoomBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(roomBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(roomBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different roomBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentRoomBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(roomBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setRoomBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(roomBook, differentUserPrefs)));
    }
}
