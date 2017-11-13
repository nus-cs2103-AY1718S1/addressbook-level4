package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalEvents.DEEPAVALI;
import static seedu.address.testutil.TypicalEvents.SPECTRA;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Config;
import seedu.address.model.event.TitleContainsKeywordsPredicate;
import seedu.address.model.person.ContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.EventBookBuilder;

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
    public void getFilteredEventList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredEventList().remove(0);
    }

    //@@author kaiyu92
    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();

        EventBook eventBook = new EventBookBuilder().withEvent(SPECTRA).withEvent(DEEPAVALI).build();
        EventBook differentEventBook = new EventBook();

        UserPrefs userPrefs = new UserPrefs();

        Account account = new Account();

        Config config = new Config();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(addressBook, eventBook, userPrefs, account, config);
        ModelManager modelManagerCopy = new ModelManager(addressBook, eventBook, userPrefs, account, config);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, eventBook, userPrefs,
                account, config)));

        // different Persons filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new ContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, eventBook, userPrefs, account, config)));

        // different events filteredList -> returns false
        String keywordForEvents = SPECTRA.getTitle().value;
        modelManager.updateFilteredEventList(new TitleContainsKeywordsPredicate(Arrays.asList(keywordForEvents)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, eventBook, userPrefs, account, config)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        modelManager.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookName("differentName");
        differentUserPrefs.setEventBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(addressBook, eventBook, differentUserPrefs, account, config)));
    }
}
