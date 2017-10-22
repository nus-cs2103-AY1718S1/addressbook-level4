package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class AddressContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        AddressContainsKeywordsPredicate firstPredicate = new AddressContainsKeywordsPredicate(
                firstPredicateKeywordList);
        AddressContainsKeywordsPredicate secondPredicate = new AddressContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsKeywordsPredicate firstPredicateCopy = new AddressContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        // One keyword
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(
                Collections.singletonList("Serangoon"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Serangoon Gardens").build()));

        // Multiple keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Serangoon", "Bishan"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Serangoon Bishan").build()));

        // Only one matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Serangoon", "Tampines"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Jurong Tampines").build()));

        // Mixed-case keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("seRaNGooN", "BisHAN"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Serangoon Bishan").build()));

        // Partial keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Seran", "Bish"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Serangoon Bishan").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("Serangoon").build()));

        // Non-matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Tampines"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Serangoon Bishan").build()));

        // Keywords match phone, email and name, but does not match address
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Alice"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
