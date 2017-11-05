package seedu.address.model.lesson.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_MA1101R;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Code;
import seedu.address.model.module.Remark;
import seedu.address.model.module.predicates.SelectedStickyNotePredicate;

//@@author junming403
public class SelectedStickyNotePredicateTest {

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

        SelectedStickyNotePredicate firstPredicate = new SelectedStickyNotePredicate(firstCode);
        SelectedStickyNotePredicate secondPredicate = new SelectedStickyNotePredicate(secondCode);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SelectedStickyNotePredicate firstPredicateCopy = new SelectedStickyNotePredicate(firstCode);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different address -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_isTheCodeGiven_returnsTrue() throws IllegalValueException {

        Code code = null;

        try {
            code = new Code("MA1101R");
        } catch (IllegalValueException e) {
            assert false : "The address shouldn't invalid";
        }

        SelectedStickyNotePredicate predicate = new SelectedStickyNotePredicate(code);
        assertTrue(predicate.test(new Remark("Remark content", code)));

    }

    @Test
    public void test_isTheCodeCGiven_returnsFalse() {

        try {
            Code code = new Code("GEQ1000");
            Code secondCode = new Code("GEQ1001");
            SelectedStickyNotePredicate predicate = new SelectedStickyNotePredicate(code);
            assertFalse(predicate.test(new Remark("Remark content", secondCode)));
        } catch (IllegalValueException e) {
            assert false : "The code shouldn't invalid";
        }
    }

}
