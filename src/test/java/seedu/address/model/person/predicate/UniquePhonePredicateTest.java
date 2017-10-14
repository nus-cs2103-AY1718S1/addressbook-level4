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
import seedu.address.model.module.predicates.UniquePhonePredicate;

public class UniquePhonePredicateTest {

    private Model model;

    @Test
    public void equals() {

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        UniquePhonePredicate predicate = new UniquePhonePredicate(model.getUniquePhonePersonSet());

        // same object -> returns true
        assertTrue(predicate.equals(predicate));

        // same values -> returns true
        UniquePhonePredicate firstPredicateCopy = new UniquePhonePredicate(model.getUniquePhonePersonSet());
        assertTrue(predicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(predicate.equals(1));

        // null -> returns false
        assertFalse(predicate.equals(null));

    }

    @Test
    public void test_isTheAddressUnique() {

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        UniquePhonePredicate predicate = new UniquePhonePredicate(model.getUniquePhonePersonSet());
        assertTrue(predicate.test(ALICE));

        // Now it is not unique
        assertTrue(predicate.test(BENSON));
    }
}
