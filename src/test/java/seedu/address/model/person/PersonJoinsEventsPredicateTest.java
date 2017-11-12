package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.joinEvents;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

// @@author HouDenghao
public class PersonJoinsEventsPredicateTest {
    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        joinEvents(model);
    }

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        PersonJoinsEventsPredicate firstPredicate = new PersonJoinsEventsPredicate(firstPredicateKeyword);
        PersonJoinsEventsPredicate secondPredicate = new PersonJoinsEventsPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonJoinsEventsPredicate firstPredicateCopy = new PersonJoinsEventsPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testPersonJoinsEventsReturnsTrue() {
        PersonJoinsEventsPredicate predicate =
                new PersonJoinsEventsPredicate("First meeting");
        ReadOnlyPerson target = model.getFilteredPersonList().get(0);
        assertTrue(predicate.test(target));
    }

    @Test
    public void testPersonDoesNotJoinEventsReturnsFalse() {
        PersonJoinsEventsPredicate predicate =
                new PersonJoinsEventsPredicate("First Meeting");
        ReadOnlyPerson target = model.getFilteredPersonList().get(5);
        assertFalse(predicate.test(target));
    }
}
