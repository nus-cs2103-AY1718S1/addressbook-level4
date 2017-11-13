package seedu.address.model.tag;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;

//@@author ReneeSeet
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tags} matches any of the Tag keywords given.
 * if Tag Keywords is "all", returns true for everyone
 */
public class TagMatchingPredicate implements Predicate<ReadOnlyPerson> {

    private final List<String> keytags;

    public TagMatchingPredicate(List<String> keytags) {
        this.keytags = keytags;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        if (keytags.size() == 1 && keytags.contains("all")) {
            return true;
        }
        Set<Tag> tags = person.getTags();
        for (Tag s : tags) {
            for (String key : keytags) {
                if (key.equals(s.tagName)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagMatchingPredicate // instanceof handles nulls
                && this.keytags.equals(((TagMatchingPredicate) other).keytags)); // state check
    }

}
