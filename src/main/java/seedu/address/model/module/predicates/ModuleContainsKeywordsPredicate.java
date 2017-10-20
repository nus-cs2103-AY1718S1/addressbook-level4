package seedu.address.model.module.predicates;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.ReadOnlyLesson;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Phone Number} matches any of the keywords given.
 */
public class ModuleContainsKeywordsPredicate implements Predicate<ReadOnlyLesson> {
    private final List<String> keywords;

    public ModuleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {

        for(int i=0;i<keywords.size();i++){
            if(lesson.getCode().fullCodeName.toLowerCase().contains(keywords.get(i).toLowerCase())){
                keywords.set(i,"xxxxxx");
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((ModuleContainsKeywordsPredicate) other).keywords)); // state check
    }

}
