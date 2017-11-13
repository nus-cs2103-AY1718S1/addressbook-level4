package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.Arrays;
import java.util.Calendar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.exceptions.ScheduleNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.TypicalPersons;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    //@@author limcel
    @Test
    public void getScheduleList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getScheduleList().remove(0);
    }

    @Test
    public void removeAndAddScheduleUnitTests() throws ScheduleNotFoundException {
        Calendar date = Calendar.getInstance();
        String personToAdd = TypicalPersons.ALICE.getName().toString();
        Schedule newSchedule = new Schedule(personToAdd, date);
        ModelManager modelManager = new ModelManager();

        modelManager.addSchedule(newSchedule);
        assertTrue(modelManager.getScheduleList().get(0).equals(newSchedule));

        modelManager.removeSchedule(newSchedule);
        assertTrue(modelManager.getScheduleList().size() == 0);
    }

    @Test
    public void testDeleteTag() throws PersonNotFoundException, IllegalValueException, TagNotFoundException {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager =  new ModelManager(addressBook, userPrefs);
        modelManager.deleteTag(new Tag("friends"));
        modelManager.deleteTag(new Tag("owesMoney"));

        // deletion of "friend","owesMoney" tag --> returns false
        AddressBook oldAddressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        assertFalse(addressBook.getPersonList().equals(oldAddressBook));
    }
    //@@author
    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
