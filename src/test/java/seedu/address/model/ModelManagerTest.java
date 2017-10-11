package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;
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

    /**
     * Tests if sortPersonByName can return a list of sorted names from an input
     * of names with random orders.
     * @throws Exception
     */

    @Test
    public void sortPersonByName_validSort_success() throws Exception {
        Person inputPerson1 = new PersonBuilder().withName("YING ZHENG").build();
        Person inputPerson2 = new PersonBuilder().withName("JACOB").build();
        Person inputPerson3 = new PersonBuilder().withName("VIVEK").build();
        Person inputPerson4 = new PersonBuilder().withName("JIA SHU").build();

        ArrayList<ReadOnlyPerson> inputPersonList = new ArrayList<>();

        inputPersonList.add(inputPerson1);
        inputPersonList.add(inputPerson2);
        inputPersonList.add(inputPerson3);
        inputPersonList.add(inputPerson4);

        AddressBook inputAddressBook = new AddressBook();
        inputAddressBook.setPersons(inputPersonList);

        ModelManager expectedModel = new ModelManager(inputAddressBook, new UserPrefs());
        inputPersonList.clear();
        expectedModel.sortPersonByName(inputPersonList);
        inputAddressBook.setPersons(inputPersonList);

        ArrayList<ReadOnlyPerson> sortedInputPersonList = new ArrayList<>();

        sortedInputPersonList.add(inputPerson2);
        sortedInputPersonList.add(inputPerson4);
        sortedInputPersonList.add(inputPerson3);
        sortedInputPersonList.add(inputPerson1);

        AddressBook sortedAddressBook = new AddressBook();
        sortedAddressBook.setPersons(sortedInputPersonList);

        ModelManager actualModel = new ModelManager(sortedAddressBook, new UserPrefs());
        assertEquals(expectedModel.getAddressBook().getPersonList(), actualModel.getAddressBook().getPersonList());
    }
  
    /*
     * Tests if the actual output of removeTag is equals to the expected
     * output when given valid target indexes and a valid tag to remove.
     */
    @Test
    public void removeTag_validIndexesAndTag_success() throws Exception {
        Person oldPerson1 = new PersonBuilder().withName("BOB").withTags("owesMoney", "friends").build();
        Person oldPerson2 = new PersonBuilder().withTags("classmate").build();
        List<ReadOnlyPerson> oldPersonList = new ArrayList<ReadOnlyPerson>();
        oldPersonList.add(oldPerson1);
        oldPersonList.add(oldPerson2);
        AddressBook oldAddressBook = new AddressBook();
        oldAddressBook.setPersons(oldPersonList);


        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        Tag toRemove = new Tag("owesMoney");

        ModelManager expectedModel = new ModelManager(oldAddressBook, new UserPrefs());
        expectedModel.removeTag(indexes, toRemove);

        Person newPerson1 = new PersonBuilder().withName("BOB").withTags("friends").build();
        Person newPerson2 = new PersonBuilder().withTags("classmate").build();
        List<ReadOnlyPerson> newPersonList = new ArrayList<ReadOnlyPerson>();
        newPersonList.add(newPerson1);
        newPersonList.add(newPerson2);
        AddressBook newAddressBook = new AddressBook();
        newAddressBook.setPersons(newPersonList);
        ModelManager actualModel = new ModelManager(newAddressBook, new UserPrefs());

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
