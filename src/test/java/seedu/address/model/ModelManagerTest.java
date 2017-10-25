package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getSortedTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getUnsortedTypicalAddressBook;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.UserPerson;
import seedu.address.testutil.AddressBookBuilder;

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
    public void sortFilteredPersonListByName_sortListSuccess() {
        String filterType = "name";
        UserPrefs userPrefs = new UserPrefs();
        AddressBook unSortedTypicalPersons = getUnsortedTypicalAddressBook();
        AddressBook sortedTypicalPersons = getSortedTypicalAddressBook();
        ModelManager modelManager1 = new ModelManager(unSortedTypicalPersons, userPrefs, new UserPerson());
        ModelManager modelManager2 = new ModelManager(sortedTypicalPersons, userPrefs, new UserPerson());
        modelManager1.sortFilteredPersonList(filterType);
        assertTrue(modelManager1.equals(modelManager2));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(addressBook, userPrefs, new UserPerson());
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs, new UserPerson());
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs, new UserPerson())));

        // different userPerson -> returns false
        assertFalse(modelManager.equals(new ModelManager(new AddressBook(), userPrefs, new UserPerson(ALICE))));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs, new UserPerson())));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs, new UserPerson())));
    }
}
