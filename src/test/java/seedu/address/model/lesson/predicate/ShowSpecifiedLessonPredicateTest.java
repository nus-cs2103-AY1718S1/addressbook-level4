package seedu.address.model.lesson.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalLessons.CS2101_L1;
import static seedu.address.testutil.TypicalLessons.MA1101R_L1;

import org.junit.Test;

import seedu.address.model.module.predicates.ShowSpecifiedLessonPredicate;

public class ShowSpecifiedLessonPredicateTest {

    @Test
    public void equals() {

        ShowSpecifiedLessonPredicate firstPredicate = new ShowSpecifiedLessonPredicate(MA1101R_L1.hashCode());
        ShowSpecifiedLessonPredicate secondPredicate = new ShowSpecifiedLessonPredicate(CS2101_L1.hashCode());

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ShowSpecifiedLessonPredicate firstPredicateCopy = new ShowSpecifiedLessonPredicate(MA1101R_L1.hashCode());
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_isTheLessonGiven_returnsTrue() {


        ShowSpecifiedLessonPredicate predicate = new ShowSpecifiedLessonPredicate(MA1101R_L1.hashCode());
        assertTrue(predicate.test(MA1101R_L1));

    }

    @Test
    public void test_isThePersonGiven_returnsFalse() {

        ShowSpecifiedLessonPredicate predicate = new ShowSpecifiedLessonPredicate(MA1101R_L1.hashCode());
        assertFalse(predicate.test(CS2101_L1));
    }
}
