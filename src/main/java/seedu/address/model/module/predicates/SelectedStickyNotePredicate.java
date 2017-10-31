package seedu.address.model.module.predicates;

import java.util.function.Predicate;

import seedu.address.model.module.Code;
import seedu.address.model.module.Remark;

//@@author junming403
/**
 * Tests that a {@code Remark}'s {@code moduleCode} matches the given module code.
 */
public class SelectedStickyNotePredicate implements Predicate<Remark> {
    private final Code codeTotest;

    public SelectedStickyNotePredicate(Code code) {
        this.codeTotest = code;
    }

    @Override
    public boolean test(Remark remark) {
        if (remark.moduleCode.equals(codeTotest)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FixedCodePredicate // instanceof handles nulls
                && this.codeTotest.equals(((SelectedStickyNotePredicate) other).codeTotest)); // state check
    }

}
