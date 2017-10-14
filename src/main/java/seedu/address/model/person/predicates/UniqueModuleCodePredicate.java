package seedu.address.model.person.predicates;

import java.util.HashSet;
import java.util.function.Predicate;

import seedu.address.model.module.Code;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.person.Email;
import seedu.address.model.person.ReadOnlyPerson;


/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Code} is unique in the given list.
 */
public class UniqueModuleCodePredicate implements Predicate<ReadOnlyLesson> {
    private final HashSet<Code> uniqueCodeSet;

    public UniqueModuleCodePredicate(HashSet<Code> codeSet) {
        this.uniqueCodeSet = codeSet;
    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {
        if (uniqueCodeSet.contains(lesson.getCode())) {
            uniqueCodeSet.remove(lesson.getCode());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueModuleCodePredicate // instanceof handles nulls
                && this.uniqueCodeSet.equals(((UniqueModuleCodePredicate) other).uniqueCodeSet)); // state check
    }

}
