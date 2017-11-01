package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

public class ListObserverTest {

    Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    ListObserver listObserver = new ListObserver(model);

    @Test
    public void checkCurrentFilteredList() {
        model.setCurrentListName("list");
        assertEquals(listObserver.getCurrentFilteredList(), model.getFilteredPersonList());
        model.setCurrentListName("blacklist");
        assertEquals(listObserver.getCurrentFilteredList(), model.getFilteredBlacklistedPersonList());
        model.setCurrentListName("whitelist");
        assertEquals(listObserver.getCurrentFilteredList(), model.getFilteredWhitelistedPersonList());
        model.setCurrentListName("overduelist");
        assertEquals(listObserver.getCurrentFilteredList(), model.getFilteredOverduePersonList());
    }

    @Test
    public void checkCurrentListName() {
        model.setCurrentListName("list");
        assertEquals(listObserver.getCurrentListName(), "MASTERLIST:\n");
        model.setCurrentListName("blacklist");
        assertEquals(listObserver.getCurrentListName(), "BLACKLIST:\n");
        model.setCurrentListName("whitelist");
        assertEquals(listObserver.getCurrentListName(), "WHITELIST:\n");
        model.setCurrentListName("overduelist");
        assertEquals(listObserver.getCurrentListName(), "OVERDUELIST:\n");
    }

    @Test
    public void updateCurrentFilteredListTest() {
        Person personToFind = (Person) model.getFilteredPersonList().get(0);
        String nameToFind = personToFind.getName().fullName;
        String[] keywords = nameToFind.split("\\s+");
        NameContainsKeywordsPredicate findPredicate = new NameContainsKeywordsPredicate(Arrays.asList(keywords));
        assertEquals(listObserver.updateCurrentFilteredList(findPredicate), 1);

        listObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS); // reset predicate

        model.setCurrentListName("blacklist");
        // typical addressbook has blacklisted person
        personToFind = (Person) model.getFilteredBlacklistedPersonList().get(0);
        nameToFind = personToFind.getName().fullName;
        keywords = nameToFind.split("\\s+");
        findPredicate = new NameContainsKeywordsPredicate(Arrays.asList(keywords));
        assertEquals(listObserver.updateCurrentFilteredList(findPredicate), 1);

        listObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS); // reset predicate

        model.setCurrentListName("whitelist");
        // typical addressbook has blacklisted person
        personToFind = (Person) model.getFilteredBlacklistedPersonList().get(0);
        nameToFind = personToFind.getName().fullName;
        keywords = nameToFind.split("\\s+");
        findPredicate = new NameContainsKeywordsPredicate(Arrays.asList(keywords));
        assertEquals(listObserver.updateCurrentFilteredList(findPredicate), 1);

        listObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS); // reset predicate

        model.setCurrentListName("overduelist");
        // typical addressbook has blacklisted person
        personToFind = (Person) model.getFilteredBlacklistedPersonList().get(0);
        nameToFind = personToFind.getName().fullName;
        keywords = nameToFind.split("\\s+");
        findPredicate = new NameContainsKeywordsPredicate(Arrays.asList(keywords));
        assertEquals(listObserver.updateCurrentFilteredList(findPredicate), 1);
    }
}
