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

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("FaMiLy"));
        assertTrue(predicate.test(new PersonBuilder().withTags("family").build()));

        // Individual keywords follow "AND" logic
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Colleague", "Family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Colleague", "Family", "Female").build()));
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Colleague", "Male"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Colleague", "Family", "Female").build()));
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Colleague", "Family", "Male"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Colleague", "Family").build()));
    }

    @Test
    public void testTagIsNotValidReturnsFalse() {
        TagContainsKeywordsPredicate predicate;
        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Family"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Colleague").build()));

        // Keywords match phone, email and address, but does not match tag
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street", ""));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withTags("Family").build()));

        // Only one matching tag -> False
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Colleague", "Family"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Colleague").build()));
        assertFalse(predicate.test(new PersonBuilder().withTags("Family").build()));
    }

    @Test
    public void testValidArgsWithAndOr() {
        //And included
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Arrays.asList("Colleague", "and", "Family"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Colleague").build()));
        assertFalse(predicate.test(new PersonBuilder().withTags("Family").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("Colleague", "Family").build()));

        //Or included
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Colleague", "or", "Family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Colleague").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("Family").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("Colleague", "Family").build()));

        //Complex combination
        // [a,[b,c],d,[e,f,g],h]
        // a or (b and c) or d or (e and f and g) or h
        // Equivalence Partition:
        // 1. Contains None
        // 2. Contains At least one
        List<String> myStringArray = Arrays.asList("a", "or", "b", "and", "c", "or", "d",
                "or", "e", "and", "f", "and", "g", "or", "h");
        predicate = new TagContainsKeywordsPredicate(myStringArray);
        assertFalse(predicate.test(new PersonBuilder().withTags("i").build()));
        assertTrue (predicate.test(new PersonBuilder().withTags("e","f","g").build()));

        //Additional Cases for Boundary cases
        assertFalse(predicate.test(new PersonBuilder().withTags("e","f","b").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("a","b","c","d","e","f","g","h","i").build()));

        //Case sensitive
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("COLLEAGUE", "and", "family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("colleague", "FAMILY").build()));
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("COLLEAGUE", "aNd", "family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("colleague", "FAMILY").build()));
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("COLLEAGUE", "AnD", "family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("colleague", "FAMILY").build()));
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("colleague", "OR", "FAMILY"));
        assertTrue(predicate.test(new PersonBuilder().withTags("COLLEAGUE", "family").build()));

    }

}
