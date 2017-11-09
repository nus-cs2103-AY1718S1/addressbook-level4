package seedu.room.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.room.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.room.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.room.testutil.TypicalPersons.ALICE;
import static seedu.room.testutil.TypicalPersons.BENSON;
import static seedu.room.testutil.TypicalPersons.TEMPORARY_JOE;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.model.person.NameContainsKeywordsPredicate;
import seedu.room.model.person.Person;
import seedu.room.model.person.exceptions.PersonNotFoundException;
import seedu.room.testutil.ResidentBookBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }


    //@@author shitian007
    @Test
    public void updatePersonPictureTest() throws IllegalValueException, PersonNotFoundException {
        ResidentBook residentBook = new ResidentBookBuilder().withPerson(TEMPORARY_JOE).build();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(residentBook, userPrefs);

        //modelManager has nobody in it -> returns false
        assertFalse(modelManager.equals(null));

        Person editedPerson = (Person) modelManager.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        editedPerson.getPicture().setPictureUrl("TestUrl");

        modelManager.updateFilteredPersonListPicture(PREDICATE_SHOW_ALL_PERSONS, editedPerson);
        assertTrue(modelManager.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).equals(editedPerson));

    }
    //@@author

    @Test
    public void equals() {
        ResidentBook residentBook = new ResidentBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        ResidentBook differentResidentBook = new ResidentBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(residentBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(residentBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different residentBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentResidentBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(residentBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setResidentBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(residentBook, differentUserPrefs)));
    }
}
