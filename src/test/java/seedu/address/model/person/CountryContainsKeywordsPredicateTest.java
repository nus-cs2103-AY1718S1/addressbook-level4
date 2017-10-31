package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class CountryContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Singapore");
        List<String> secondPredicateKeywordList = Arrays.asList("Malaysia", "Singapore");

        Predicate<ReadOnlyPerson> firstPredicate = new CountryContainsKeywordsPredicate(firstPredicateKeywordList);
        Predicate<ReadOnlyPerson> secondPredicate = new CountryContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        Predicate<ReadOnlyPerson> firstPredicateCopy = new CountryContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_countryContainsKeywords_returnsTrue() {
        // One keyword

        Predicate<ReadOnlyPerson> predicate = new CountryContainsKeywordsPredicate(Collections.singletonList("Singapore"));
        assertTrue(predicate.test(new PersonBuilder().withCountry("65").build()));

        // Only one matching keyword
        predicate = new CountryContainsKeywordsPredicate(Arrays.asList("Singapore", "China"));
        assertTrue(predicate.test(new PersonBuilder().withCountry("65").build()));

        // Mixed-case keywords
        predicate = new CountryContainsKeywordsPredicate(Arrays.asList("sinGapORE", "cHINa"));
        assertTrue(predicate.test(new PersonBuilder().withCountry("65").build()));
    }

    @Test
    public void test_countryDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CountryContainsKeywordsPredicate predicate = new CountryContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withCountry("65").build()));

        // Non-matching keyword
        predicate = new CountryContainsKeywordsPredicate(Arrays.asList("China"));
        assertFalse(predicate.test(new PersonBuilder().withCountry("65").build()));

        // Keywords match phone, email and address, but does not match country
        predicate = new CountryContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withCountry("65").withPhone("12345")
                .withEmail("alice@email.com").withAddress("123, Main Street, #08-111, Singapore 409999").build()));
    }
}
