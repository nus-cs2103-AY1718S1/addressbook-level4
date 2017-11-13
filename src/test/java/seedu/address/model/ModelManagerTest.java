package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalEvents.FIRST;
import static seedu.address.testutil.TypicalEvents.SECOND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.EventListBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
        modelManager.getFilteredEventList().remove(0);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        // @@author HuWanqing
        EventList eventList = new EventListBuilder().withEvent(FIRST).withEvent(SECOND).build();
        AddressBook differentAddressBook = new AddressBook();
        EventList differentEventList = new EventList();
        // @@author HuWanqing
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(addressBook, eventList, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, eventList, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, eventList, userPrefs)));

        // @@author HuWanqing
        // different EventList -> returns false
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentEventList, userPrefs)));

        // @@author
        // different filtered person list -> returns false
        String[] personKeywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(personKeywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, eventList, userPrefs)));

        // @@author HuWanqing
        // different filtered event list -> returns false
        String[] eventKeywords = FIRST.getEventName().fullEventName.split("\\s+");
        modelManager.updateFilteredEventList(new EventNameContainsKeywordsPredicate(Arrays.asList(eventKeywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, eventList, userPrefs)));

        // @@author
        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(addressBook, eventList, differentUserPrefs)));
    }
}
