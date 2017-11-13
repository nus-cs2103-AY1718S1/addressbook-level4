package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;


/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class FindFunctionPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public FindFunctionPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    //@@author yilun-zhu
    @Override
    public boolean test(ReadOnlyPerson person) {
        boolean name = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        boolean address = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().toString(), keyword));
        boolean email = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsSubStringIgnoreCase(person.getEmail().toString(), keyword));
        boolean number = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsSubStringIgnoreCase(person.getPhone().toString(), keyword));
        boolean tag = tagSearch(person);

        if (name || address || tag || email || number) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches any of the keywords given.
     */
    public boolean tagSearch(ReadOnlyPerson person) {
        Set<Tag> tags = person.getTags();
        for (Tag s : tags) {
            for (String key : keywords) {
                if (key.equalsIgnoreCase(s.tagName)) {
                    return true;
                }
            }
        }
        return false;
    }
    //@@author


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindFunctionPredicate // instanceof handles nulls
                && this.keywords.equals(((FindFunctionPredicate) other).keywords)); // state check
    }

}

