package seedu.address.model.person.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.predicates.UniqueAddressPredicate;

public class UniqueAddressPredicateTest {

    private Model model;

    @Test
    public void equals() {

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        UniqueAddressPredicate predicate = new UniqueAddressPredicate(model.getUniqueAdPersonSet());

        // same object -> returns true
        assertTrue(predicate.equals(predicate));

        // same values -> returns true
        UniqueAddressPredicate firstPredicateCopy = new UniqueAddressPredicate(model.getUniqueAdPersonSet());
        assertTrue(predicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(predicate.equals(1));

        // null -> returns false
        assertFalse(predicate.equals(null));

    }

    @Test
    public void test_isTheAddressUnique() {

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        UniqueAddressPredicate predicate = new UniqueAddressPredicate(model.getUniqueAdPersonSet());
        assertTrue(predicate.test(ALICE));

        // Now it is not unique
        assertTrue(predicate.test(BENSON));
    }
}
