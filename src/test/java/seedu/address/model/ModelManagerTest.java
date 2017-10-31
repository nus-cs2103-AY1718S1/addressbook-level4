package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.util.SampleDataUtil.getTagSet;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.Arrays;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDataContainsKeywordsPredicate;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.RolodexBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getFilteredPersonListModifyListThrowsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getLatestPersonList().remove(0);
    }

    @Test
    public void equals() {
        Rolodex rolodex = new RolodexBuilder().withPerson(ALICE).withPerson(BENSON).build();
        Rolodex differentRolodex = new Rolodex();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(rolodex, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(rolodex, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different rolodex -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentRolodex, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        Arrays.stream(keywords).map(word -> word.concat("aa"));
        modelManager.updateFilteredPersonList(new PersonDataContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(rolodex, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setRolodexName("differentName");
        assertTrue(modelManager.equals(new ModelManager(rolodex, differentUserPrefs)));
    }

    @Test
    public void removeTag() throws IllegalValueException, PersonNotFoundException {

        Set<Tag> tagSet1 = getTagSet("friends", "classmate");
        Set<Tag> tagSet2 = getTagSet("owesMoney", "classmate");

        Person person1 = new Person(ALICE);
        person1.setTags(tagSet1);

        Person person2 = new Person(BENSON);
        person2.setTags(tagSet2);

        Rolodex rolodex = new RolodexBuilder().withPerson(person1).withPerson(person2).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(rolodex, userPrefs);

        Tag tagToRemove = new Tag("classmate");
        modelManager.removeTag(tagToRemove);

        Set<Tag> tagSet1New = getTagSet("friends");
        Set<Tag> tagSet2New = getTagSet("owesMoney");

        Person person1New = new Person(ALICE);
        person1.setTags(tagSet1New);
        Person person2New = new Person(BENSON);
        person2.setTags(tagSet2New);

        // check that tagToRemove from all persons are removed
        assertTrue(person1.equals(person1New));
        assertTrue(person2.equals(person2New));

        // check that tagSets are not equal because both are null
        assertFalse(person1.getTags().equals(null));
        assertFalse(person2.getTags().equals(null));
    }

    @Test
    public void getIndex() {
        Rolodex rolodex = new RolodexBuilder().withPerson(ALICE).withPerson(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(rolodex, userPrefs);

        // Alice has first index
        Index expectedIndex = INDEX_FIRST_PERSON;
        Index actualIndex = modelManager.getIndex(ALICE);
        assertEquals(expectedIndex, actualIndex);

        // Benson has second index
        expectedIndex = INDEX_SECOND_PERSON;
        actualIndex = modelManager.getIndex(BENSON);
        assertEquals(expectedIndex, actualIndex);
    }

}
