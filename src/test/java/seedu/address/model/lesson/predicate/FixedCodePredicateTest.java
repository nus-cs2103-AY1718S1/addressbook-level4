package seedu.address.model.lesson.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_MA1101R;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Code;
import seedu.address.model.module.predicates.FixedCodePredicate;
import seedu.address.testutil.LessonBuilder;

public class FixedCodePredicateTest {
    @Test
    public void equals() {

        Code firstCode = null;
        Code secondCode = null;

        try {
            firstCode = new Code(VALID_CODE_MA1101R);
            secondCode = new Code(VALID_CODE_CS2101);
        } catch (IllegalValueException e) {
            assert false : "The code shouldn't invalid";
        }

        FixedCodePredicate firstPredicate = new FixedCodePredicate(firstCode);
        FixedCodePredicate secondPredicate = new FixedCodePredicate(secondCode);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FixedCodePredicate firstPredicateCopy = new FixedCodePredicate(firstCode);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different address -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_isTheCodeGiven_returnsTrue() {

        Code code = null;

        try {
            code = new Code("MA1101R");
        } catch (IllegalValueException e) {
            assert false : "The address shouldn't invalid";
        }

        FixedCodePredicate predicate = new FixedCodePredicate(code);
        assertTrue(predicate.test(new LessonBuilder().build()));

    }

    @Test
    public void test_isTheCodeCGiven_returnsFalse() {

        try {
            Code code = new Code("GEQ1000");
            FixedCodePredicate predicate = new FixedCodePredicate(code);
            assertFalse(predicate.test(new LessonBuilder().build()));
        } catch (IllegalValueException e) {
            assert false : "The code shouldn't invalid";
        }
    }
}
