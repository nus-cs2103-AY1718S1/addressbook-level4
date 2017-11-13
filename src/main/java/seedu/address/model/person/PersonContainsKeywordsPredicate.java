package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson} matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public PersonContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return
                //@@author Valerieyue
                keywords.stream().filter(input -> input.substring(0, 2).equals("m/"))
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getBirthday().value,
                                keyword.substring(2)))
                // @@author pwenzhe
                || keywords.stream()
                        .filter(num -> num.matches("\\d+"))
                        .anyMatch(number -> person.getPhone().toString().contains(number))
                || keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword))
                || keywords.stream()
                        .anyMatch(email -> person.getEmail().toString().contains(email))
                || keywords.stream()
                        .anyMatch(address -> StringUtil
                                .containsWordIgnoreCase(person.getAddress().toString(), address))
                || keywords.stream()
                        .filter(postal -> postal.matches("\\d+"))
                        .anyMatch(postalCode -> person.getPostalCode().toString().contains(postalCode))
                || keywords.stream()
                        .anyMatch(tag -> StringUtil.containsWordIgnoreCase(person.getTags().stream()
                                .map(i -> i.toString()).collect(Collectors.joining(" "))
                                .replaceAll("\\[|\\]|\\,", " "), tag))
                || keywords.stream()
                        .filter(fav -> fav.toLowerCase().matches("favourite|fav|favs|favorite|favourites|favorites"))
                        .anyMatch(favourite -> person.getFavourite().toString().matches("yes"));
        // @@author
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsKeywordsPredicate) other).keywords)); // state check
    }

}
