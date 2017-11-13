package seedu.address.model.property;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testForPerson_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = generatePredicate("Alice");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = generatePredicate("Alice", "Bob");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = generatePredicate("Bob", "Carol");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = generatePredicate("aLIce", "bOB");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void testForPerson_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = generatePredicate();
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = generatePredicate("Carol");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = generatePredicate("12345", "alice@email.com", "Main", "Street");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    //@@author yunpengn
    @Test
    public void testForEvent_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = generatePredicate("CS2103T");
        assertTrue(predicate.test(new EventBuilder().withName("CS2103T Tutorial").build()));

        // Multiple keywords
        predicate = generatePredicate("CS2103T", "Tutorial");
        assertTrue(predicate.test(new EventBuilder().withName("CS2103T Tutorial").build()));

        // Only one matching keyword
        predicate = generatePredicate("CS2103T", "Examination");
        assertTrue(predicate.test(new EventBuilder().withName("CS2103T Tutorial").build()));

        // Mixed-case keywords
        predicate = generatePredicate("cs2103T", "tutorial");
        assertTrue(predicate.test(new EventBuilder().withName("CS2103T Tutorial").build()));
    }

    private NameContainsKeywordsPredicate generatePredicate(String... names) {
        return new NameContainsKeywordsPredicate(Arrays.asList(names));
    }
}
