package seedu.address.model.module.predicates;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.ReadOnlyLesson;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Phone Number} matches any of the keywords given.
 */
public class LocationContainsKeywordsPredicate implements Predicate<ReadOnlyLesson> {
    private final List<String> keywords;

    public LocationContainsKeywordsPredicate(List<String> keywords) {

        this.keywords = keywords;

    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {

        for(int i=0;i<keywords.size();i++){
            if(lesson.getLocation().value.toLowerCase().contains(keywords.get(i).toLowerCase())){
                keywords.set(i,"xxxxxx");
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocationContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((LocationContainsKeywordsPredicate) other).keywords)); // state check
    }

}
