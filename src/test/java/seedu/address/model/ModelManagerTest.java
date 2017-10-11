package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

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

    @Test
    public void deleteTag() throws IllegalValueException, PersonNotFoundException {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        Tag tag = new Tag("friends","");

        ModelManager modelManager = new ModelManager(addressBook, userPrefs);

        //person not found, empty AddressBook
        AddressBook emptyAddressBook = new AddressBookBuilder().build();
        ModelManager emptyModelManager = new ModelManager(emptyAddressBook, userPrefs);
        AddressBook expectedAddressBook = new AddressBookBuilder().build();
        emptyModelManager.deleteTag(tag);
        assertTrue(emptyAddressBook.equals(expectedAddressBook));

        //person not found, no such tag
        Tag noSuchTag = new Tag("nosuchtag","");
        modelManager.deleteTag(noSuchTag);
        expectedAddressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        assertTrue(addressBook.equals(expectedAddressBook));

        //deletes a tag
        modelManager.deleteTag(tag);
        AddressBook originalAddressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        for (ReadOnlyPerson person : originalAddressBook.getPersonList()) {
            for (ReadOnlyPerson personCopy : modelManager.getAddressBook().getPersonList()) {
                if (person.getName().equals(personCopy.getName())) {
                    assertFalse(person.getTags().equals(personCopy.getTags()));
                }
            }
        }
    }

    @Test
    public void tagColor() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);

        //default tagcolor should be off
        for (Tag tag : addressBook.getTagList()) {
            assertTrue(tag.getTagColor().equals("grey"));
        }
    }
}
