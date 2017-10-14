package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

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
    @Test
    public void sortPersonByName_success() throws Exception {
        Person person1 = new PersonBuilder().withName("BENSON").build();
        Person person2 = new PersonBuilder().withName("ALICE").build();
        Person person3 = new PersonBuilder().withName("GEORGE").build();
        Person person4 = new PersonBuilder().withName("FIONA").build();
        Person person5 = new PersonBuilder().withName("ELLE").build();

        ArrayList<ReadOnlyPerson> listToSort = new ArrayList<>();

        listToSort.add(person1);
        listToSort.add(person2);
        listToSort.add(person3);
        listToSort.add(person4);
        listToSort.add(person5);

        AddressBook initialAddressBook = new AddressBook();
        initialAddressBook.setPersons(listToSort);

        ModelManager expectedModel = new ModelManager(initialAddressBook, new UserPrefs());
        listToSort.clear();
        expectedModel.sortByPersonName(listToSort);
        initialAddressBook.setPersons(listToSort);

        ArrayList<ReadOnlyPerson> sortedList = new ArrayList<>();

        sortedList.add(person2);
        sortedList.add(person1);
        sortedList.add(person5);
        sortedList.add(person4);
        sortedList.add(person3);

        AddressBook sortedAddressBook = new AddressBook();
        sortedAddressBook.setPersons(sortedList);

        ModelManager actualModel = new ModelManager(sortedAddressBook, new UserPrefs());

        assertEquals(expectedModel.getAddressBook().getPersonList(), actualModel.getAddressBook().getPersonList());
    }

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
