package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsTagsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonContainsTagsPredicate firstPredicate = new PersonContainsTagsPredicate(firstPredicateKeywordList);
        PersonContainsTagsPredicate secondPredicate = new PersonContainsTagsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsTagsPredicate firstPredicateCopy = new PersonContainsTagsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personHasTagsMatchingKeywords_returnsTrue() {
        // One keyword
        PersonContainsTagsPredicate predicate =
                new PersonContainsTagsPredicate(Collections.singletonList("colleagues"));
        assertTrue(predicate.test(new PersonBuilder().withTags("colleagues").build()));

        // Multiple keywords
        predicate = new PersonContainsTagsPredicate(Arrays.asList("colleagues", "schoolmates"));
        assertTrue(predicate.test(new PersonBuilder().withTags("colleagues", "friends", "schoolmates").build()));

        // Only one matching keyword
        predicate = new PersonContainsTagsPredicate(Arrays.asList("colleagues", "schoolmates"));
        assertTrue(predicate.test(new PersonBuilder().withTags("colleagues").build()));

        // Mixed-case keywords
        predicate = new PersonContainsTagsPredicate(Arrays.asList("cOLLeaguEs", "FrIeNdS"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "colleagues").build()));

    }

    @Test
    public void test_personDoesNotHaveTagsContainingKeywords_returnsFalse() {
        // Zero keywords
        PersonContainsTagsPredicate predicate = new PersonContainsTagsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("whateverTag").build()));

        // Non-matching keyword
        predicate = new PersonContainsTagsPredicate(Arrays.asList("roommate"));
        assertFalse(predicate.test(new PersonBuilder().withTags("schoolmate", "classmate").build()));

        // Keywords match phone, email and address, but does not match any tag name
        predicate = new PersonContainsTagsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
