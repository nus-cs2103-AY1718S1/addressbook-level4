package seedu.address.model.lesson.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalLessons.CS2101_L1;
import static seedu.address.testutil.TypicalLessons.MA1101R_L1;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.predicates.UniqueLocationPredicate;

public class UniqueLocationPredicateTest {

    private Model model;

    @Test
    public void equals() {

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        UniqueLocationPredicate predicate = new UniqueLocationPredicate(model.getUniqueLocationSet());

        // same object -> returns true
        assertTrue(predicate.equals(predicate));

        // same values -> returns true
        UniqueLocationPredicate firstPredicateCopy = new UniqueLocationPredicate(model.getUniqueLocationSet());
        assertTrue(predicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(predicate.equals(1));

        // null -> returns false
        assertFalse(predicate.equals(null));

    }

    @Test
    public void test_isTheAddressUnique() {

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        UniqueLocationPredicate predicate = new UniqueLocationPredicate(model.getUniqueLocationSet());
        assertTrue(predicate.test(MA1101R_L1));

        // Now it is not unique
        assertTrue(predicate.test(CS2101_L1));
    }
}
