package seedu.address.model.person;

import org.junit.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.PersonBuilder;

import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

import static org.junit.Assert.*;

//@@author rushan-khor
public class HasPotentialDuplicatesPredicateTest {

    @Test
    public void equals() {
        HashSet<Name> firstPredicateKeywordSet = new HashSet<>();
        HashSet<Name> secondPredicateKeywordSet = new HashSet<>();

        try {
            firstPredicateKeywordSet.add(new Name("CARL"));
            secondPredicateKeywordSet.add(new Name("ALICE"));
            firstPredicateKeywordSet.add(new Name("BOB"));
            secondPredicateKeywordSet.add(new Name("BOB"));
        } catch (IllegalValueException e) {
            System.out.println(e.getMessage());
        }

        HasPotentialDuplicatesPredicate firstPredicate =
                new HasPotentialDuplicatesPredicate(firstPredicateKeywordSet);
        HasPotentialDuplicatesPredicate secondPredicate =
                new HasPotentialDuplicatesPredicate(secondPredicateKeywordSet);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        HasPotentialDuplicatesPredicate firstPredicateCopy =
                new HasPotentialDuplicatesPredicate(firstPredicateKeywordSet);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertNotNull(firstPredicate);

        // different blood type -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }
}
