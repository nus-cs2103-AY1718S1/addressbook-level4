package seedu.address.model.person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

//@@author NabeelZaheer
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag, Name, Email, Phone, Address} matches any of the keywords given.
 */
public class FieldContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<List<String>> keywords;

    public FieldContainsKeywordsPredicate(List<List<String>> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        Set<Tag> tag = new HashSet<>(person.getTags());
        List<String> tagName = new ArrayList<>();
        Iterator<Tag> it = tag.iterator();
        while (it.hasNext()) {
            tagName.add(it.next().tagName);
        }
        String mergedNames = tagName.stream().collect(Collectors.joining(" "));
        return keywords.get(0).stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase
                        (person.getName().fullName, keyword))
                || keywords.get(1).stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase
                                (person.getPhone().value, keyword))
                || keywords.get(2).stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase
                                (person.getEmail().value, keyword))
                || keywords.get(3).stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase
                                (person.getAddress().value, keyword))
                || keywords.get(4).stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase
                                (mergedNames, keyword))
                || keywords.get(0).stream()
                        .anyMatch(keyword -> ((person.getName().fullName).toLowerCase()).contains(keyword))
                || keywords.get(1).stream()
                        .anyMatch(keyword -> ((person.getPhone().value).toLowerCase()).contains(keyword))
                || keywords.get(2).stream()
                        .anyMatch(keyword -> ((person.getEmail().value).toLowerCase()).contains(keyword))
                || keywords.get(3).stream()
                        .anyMatch(keyword -> ((person.getAddress().value).toLowerCase()).contains(keyword))
                || keywords.get(4).stream()
                        .anyMatch(keyword -> (mergedNames.toLowerCase()).contains(keyword));
    }
    //@@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FieldContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((FieldContainsKeywordsPredicate) other).keywords)); // state check
    }


}
