package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author lincredibleJC
public class TagsContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagsContainsKeywordsPredicate firstPredicate = new TagsContainsKeywordsPredicate(firstPredicateKeywordList);
        TagsContainsKeywordsPredicate secondPredicate = new TagsContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagsContainsKeywordsPredicate firstPredicateCopy = new TagsContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different tag -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagsContainsKeywords_returnsTrue() {
        // One keyword
        TagsContainsKeywordsPredicate predicate = new TagsContainsKeywordsPredicate(Collections.singletonList("tag1"));
        assertTrue(predicate.test(new PersonBuilder().withName("personName").withTags("tag1").build()));

        // Multiple keywords
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("tag1", "tag2"));
        assertTrue(predicate.test(new PersonBuilder().withName("personName").withTags("tag1", "tag2").build()));

        // Mixed-case keywords
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("tAg1", "TaG2"));
        assertTrue(predicate.test(new PersonBuilder().withName("personName").withTags("tag1", "tag2").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        TagsContainsKeywordsPredicate predicate = new TagsContainsKeywordsPredicate(Arrays.asList("tag10"));
        assertFalse(predicate.test(new PersonBuilder().withName("personName").withTags("tag1").build()));

        // Only one matching keyword
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("tag1", "tag2"));
        assertFalse(predicate.test(new PersonBuilder().withName("personName").withTags("tag1").build()));

        // Keywords match phone, email and address, but does not match tags
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("12345", "person@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("personName")
                .withPhone("student/97272031 parent/97979797")
                .withEmail("alice@email.com").withAddress("Main Street").withTags("tag1", "tag2", "tag3").build()));
    }
}
