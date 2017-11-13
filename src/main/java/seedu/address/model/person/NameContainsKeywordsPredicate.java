package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

//@@author awarenessxz
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> namekeywords;
    private final List<String> tagkeywords;

    public NameContainsKeywordsPredicate(List<String> namekeywords, List<String> tagkeywords) {
        this.namekeywords = namekeywords;
        this.tagkeywords = tagkeywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {

        if (!namekeywords.isEmpty() && !tagkeywords.isEmpty()) {
            return namekeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsNonFullWordIgnoreCase(person.getName().fullName, keyword)
                            && person.containsTags(tagkeywords));
        } else if (!namekeywords.isEmpty()) {
            return namekeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsNonFullWordIgnoreCase(person.getName().fullName, keyword));
        } else if (!tagkeywords.isEmpty()) {
            return person.containsTags(tagkeywords);
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.namekeywords.equals(((NameContainsKeywordsPredicate) other).namekeywords)
                && this.tagkeywords.equals(((NameContainsKeywordsPredicate) other).tagkeywords)); // state check
    }

}
