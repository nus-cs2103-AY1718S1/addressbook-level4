package seedu.address.model;

//@@author ReneeSeet

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.tag.TagMatchingPredicate;
import seedu.address.testutil.PersonBuilder;

public class TagMatchingPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagMatchingPredicate firstPredicate = new TagMatchingPredicate(firstPredicateKeywordList);
        TagMatchingPredicate secondPredicate = new TagMatchingPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagMatchingPredicate firstPredicateCopy = new TagMatchingPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagMatchingPredicate predicate = new TagMatchingPredicate(Collections.singletonList("friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Multiple keywords
        predicate = new  TagMatchingPredicate(Arrays.asList("sec2", "sec1"));
        assertTrue(predicate.test(new PersonBuilder().withTags("sec2", "sec1").build()));

        // Only one matching keyword
        predicate = new  TagMatchingPredicate(Arrays.asList("sec1"));
        assertTrue(predicate.test(new PersonBuilder().withTags("sec2", "sec1").build()));

        //if keyword is 'all'
        predicate = new  TagMatchingPredicate(Arrays.asList("all"));
        assertTrue(predicate.test(new PersonBuilder().withTags("sec2", "sec1").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("sec3").build()));

    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagMatchingPredicate predicate = new  TagMatchingPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("sec1").build()));

        // Non-matching keyword
        predicate = new  TagMatchingPredicate(Arrays.asList("sec1"));
        assertFalse(predicate.test(new PersonBuilder().withTags("sec3").build()));

        // Mixed-case keywords
        predicate = new  TagMatchingPredicate(Arrays.asList("SeC1", "Sec2"));
        assertFalse(predicate.test(new PersonBuilder().withTags("sec2", "sec1").build()));

        predicate = new  TagMatchingPredicate(Arrays.asList("ALL"));
        assertFalse(predicate.test(new PersonBuilder().withTags("sec2", "sec1").build()));

        // Keywords match phone, email and address
        predicate = new  TagMatchingPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
        //Keywords match phone
        predicate = new  TagMatchingPredicate(Arrays.asList("12345"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345").build()));

        //Keywords match address
        predicate = new  TagMatchingPredicate(Arrays.asList("Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Main Street").build()));

        //Keywords match email
        predicate = new  TagMatchingPredicate(Arrays.asList("alice", "email.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));
    }
}
