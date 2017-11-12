package seedu.address.commons.core;

import static org.junit.Assert.assertEquals;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.ListObserver;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

//@@author lawwman
/**
 * Tests the ListObserver class.
 * It is expected that the TypicalAddressBook in the {@code TypicalPersons} class has a person residing in every list.
 */
public class ListObserverTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Before
    public void setUp() {
        ListObserver.init(model);
    }

    @Test
    public void checkCurrentFilteredList() {
        model.setCurrentListName("list");
        assertEquals(ListObserver.getCurrentFilteredList(), model.getFilteredPersonList());
        model.setCurrentListName("blacklist");
        assertEquals(ListObserver.getCurrentFilteredList(), model.getFilteredBlacklistedPersonList());
        model.setCurrentListName("whitelist");
        assertEquals(ListObserver.getCurrentFilteredList(), model.getFilteredWhitelistedPersonList());
        model.setCurrentListName("overduelist");
        assertEquals(ListObserver.getCurrentFilteredList(), model.getFilteredOverduePersonList());
    }

    @Test
    public void checkCurrentListName() {
        model.setCurrentListName("list");
        assertEquals(ListObserver.getCurrentListName(), "MASTERLIST:\n");
        model.setCurrentListName("blacklist");
        assertEquals(ListObserver.getCurrentListName(), "BLACKLIST:\n");
        model.setCurrentListName("whitelist");
        assertEquals(ListObserver.getCurrentListName(), "WHITELIST:\n");
        model.setCurrentListName("overduelist");
        assertEquals(ListObserver.getCurrentListName(), "OVERDUELIST:\n");
    }

    @Test
    public void updateCurrentFilteredListTest() {
        Person personToFind = (Person) model.getFilteredPersonList().get(0);
        String nameToFind = personToFind.getName().fullName;
        String[] keywords = nameToFind.split("\\s+");
        NameContainsKeywordsPredicate findPredicate = new NameContainsKeywordsPredicate(Arrays.asList(keywords));
        assertEquals(ListObserver.updateCurrentFilteredList(findPredicate), 1);

        ListObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS); // reset predicate

        model.setCurrentListName("blacklist");
        // typical addressbook has blacklisted person
        personToFind = (Person) model.getFilteredBlacklistedPersonList().get(0);
        nameToFind = personToFind.getName().fullName;
        keywords = nameToFind.split("\\s+");
        findPredicate = new NameContainsKeywordsPredicate(Arrays.asList(keywords));
        assertEquals(ListObserver.updateCurrentFilteredList(findPredicate), 1);

        ListObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS); // reset predicate

        model.setCurrentListName("whitelist");
        // typical addressbook has whitelisted person
        personToFind = (Person) model.getFilteredWhitelistedPersonList().get(0);
        nameToFind = personToFind.getName().fullName;
        keywords = nameToFind.split("\\s+");
        findPredicate = new NameContainsKeywordsPredicate(Arrays.asList(keywords));
        assertEquals(ListObserver.updateCurrentFilteredList(findPredicate), 1);

        ListObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS); // reset predicate

        model.setCurrentListName("overduelist");
        // typical addressbook has overdue debt person
        personToFind = (Person) model.getFilteredOverduePersonList().get(0);
        nameToFind = personToFind.getName().fullName;
        keywords = nameToFind.split("\\s+");
        findPredicate = new NameContainsKeywordsPredicate(Arrays.asList(keywords));
        assertEquals(ListObserver.updateCurrentFilteredList(findPredicate), 1);
    }

    /**
     * Assuming that the typicalAddressBook in the {@code model} has at least 1 person in each list
     */
    @Test
    public void checkIndexOfCurrentPersonInList() {
        model.setCurrentListName("list");
        Person personToCheck = (Person) model.getFilteredPersonList().get(0);
        Index indexOfPerson = ListObserver.getIndexOfPersonInCurrentList(personToCheck);
        assertEquals(0, indexOfPerson.getZeroBased());

        model.setCurrentListName("blacklist");
        personToCheck = (Person) model.getFilteredBlacklistedPersonList().get(0);
        indexOfPerson = ListObserver.getIndexOfPersonInCurrentList(personToCheck);
        assertEquals(0, indexOfPerson.getZeroBased());

        model.setCurrentListName("whitelist");
        personToCheck = (Person) model.getFilteredWhitelistedPersonList().get(0);
        indexOfPerson = ListObserver.getIndexOfPersonInCurrentList(personToCheck);
        assertEquals(0, indexOfPerson.getZeroBased());

        model.setCurrentListName("overduelist");
        personToCheck = (Person) model.getFilteredOverduePersonList().get(0);
        indexOfPerson = ListObserver.getIndexOfPersonInCurrentList(personToCheck);
        assertEquals(0, indexOfPerson.getZeroBased());
    }
}
