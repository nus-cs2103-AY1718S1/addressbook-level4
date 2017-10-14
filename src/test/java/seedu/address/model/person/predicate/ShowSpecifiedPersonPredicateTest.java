package seedu.address.model.person.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.Test;

import seedu.address.model.module.predicates.ShowSpecifiedPersonPredicate;

public class ShowSpecifiedPersonPredicateTest {

    @Test
    public void equals() {

        ShowSpecifiedPersonPredicate firstPredicate = new ShowSpecifiedPersonPredicate(ALICE);
        ShowSpecifiedPersonPredicate secondPredicate = new ShowSpecifiedPersonPredicate(BENSON);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ShowSpecifiedPersonPredicate firstPredicateCopy = new ShowSpecifiedPersonPredicate(ALICE);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_isThePersonGiven_returnsTrue() {


        ShowSpecifiedPersonPredicate predicate = new ShowSpecifiedPersonPredicate(ALICE);
        assertTrue(predicate.test(ALICE));

    }

    @Test
    public void test_isThePersonGiven_returnsFalse() {

        ShowSpecifiedPersonPredicate predicate = new ShowSpecifiedPersonPredicate(ALICE);
        assertFalse(predicate.test(BENSON));
    }
}
