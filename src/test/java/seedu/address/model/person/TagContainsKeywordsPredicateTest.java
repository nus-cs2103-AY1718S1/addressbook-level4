package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author Jeremy
public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Patient");
        List<String> secondPredicateKeywordList = Arrays.asList("Patient", "Colleague");

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertNotNull(firstPredicate);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testTagIsPresentReturnsTrue() {
        // One Tag
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.singletonList("Patient"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Patient").build()));

        // Multiple tags
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Colleague", "Family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Colleague", "Family").build()));

        // Only one matching tag
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Colleague", "Family"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Colleague").build()));
        assertFalse(predicate.test(new PersonBuilder().withTags("Family").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("FaMiLy"));
        assertTrue(predicate.test(new PersonBuilder().withTags("family").build()));
    }

    @Test
    public void testTagIsNotValidReturnsFalse() {
        TagContainsKeywordsPredicate predicate ;

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Family"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Colleague").build()));

        // Keywords match phone, email and address, but does not match tag
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street", ""));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withTags("Family").build()));
    }
}
