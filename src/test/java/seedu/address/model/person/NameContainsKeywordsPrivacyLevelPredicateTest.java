package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordsPrivacyLevelPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPrivacyLevelPredicate firstPredicate =
                new NameContainsKeywordsPrivacyLevelPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPrivacyLevelPredicate secondPredicate =
                new NameContainsKeywordsPrivacyLevelPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPrivacyLevelPredicate firstPredicateCopy =
                new NameContainsKeywordsPrivacyLevelPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() throws IllegalValueException {
        Person testee = new PersonBuilder().withName("Alice Bob").build();

        // One keyword
        NameContainsKeywordsPrivacyLevelPredicate predicate =
                new NameContainsKeywordsPrivacyLevelPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(testee));

        testee.getPhone().setPrivate(true);
        assertFalse(predicate.test(testee));

        testee = new PersonBuilder().withName("Alice Bob").build();
        // Multiple keywords
        predicate = new NameContainsKeywordsPrivacyLevelPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        testee.getEmail().setPrivate(true);
        assertFalse(predicate.test(testee));

        testee = new PersonBuilder().withName("Alice Carol").build();
        // Only one matching keyword
        predicate = new NameContainsKeywordsPrivacyLevelPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        testee.getAddress().setPrivate(true);
        assertFalse(predicate.test(testee));

        testee = new PersonBuilder().withName("Alice Bob").build();
        // Mixed-case keywords
        predicate = new NameContainsKeywordsPrivacyLevelPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        testee.getName().setPrivate(true);
        assertFalse(predicate.test(testee));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        Person testee = new PersonBuilder().withName("Alice").build();

        // Zero keywords
        NameContainsKeywordsPrivacyLevelPredicate predicate =
                new NameContainsKeywordsPrivacyLevelPredicate(Collections.emptyList());
        assertFalse(predicate.test(testee));

        testee.getName().setPrivate(true);
        assertFalse(predicate.test(testee));

        testee = new PersonBuilder().withName("Alice Bob").build();
        // Non-matching keyword
        predicate = new NameContainsKeywordsPrivacyLevelPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(testee));

        testee.getRemark().setPrivate(true);
        assertFalse(predicate.test(testee));

        testee = new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build();

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsKeywordsPrivacyLevelPredicate(Arrays.asList(
                "12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(testee));

        testee.getEmail().setPrivate(true);
        assertFalse(predicate.test(testee));
    }
}
