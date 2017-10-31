package seedu.address.model.module.predicates;

import java.util.function.Predicate;

import seedu.address.model.module.Code;
import seedu.address.model.module.ReadOnlyLesson;

//@@author junming403
/**
 * Tests that a {@code ReadOnlyLesson}'s {@code location} matches the given module code.
 */
public class FixedCodePredicate implements Predicate<ReadOnlyLesson> {
    private final Code codeTotest;

    public FixedCodePredicate(Code code) {
        this.codeTotest = code;
    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {
        if (lesson.getCode().equals(codeTotest)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FixedCodePredicate // instanceof handles nulls
                && this.codeTotest.equals(((FixedCodePredicate) other).codeTotest)); // state check
    }

}
