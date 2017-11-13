package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;
//@@author k-l-a
public class TagListContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagListContainsKeywordsPredicate firstPredicate =
                new TagListContainsKeywordsPredicate(firstPredicateKeywordList);
        TagListContainsKeywordsPredicate  secondPredicate =
                new TagListContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagListContainsKeywordsPredicate firstPredicateCopy =
                new TagListContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagListContainsKeywords_returnsTrue() {
        // One keyword
        TagListContainsKeywordsPredicate predicate =
                new TagListContainsKeywordsPredicate(Collections.singletonList("Friend"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friend", "Family").build()));

        // Multiple keywords
        predicate = new TagListContainsKeywordsPredicate(Arrays.asList("Friend", "Family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friend", "Family").build()));

        // Only one matching keyword
        predicate = new TagListContainsKeywordsPredicate(Arrays.asList("Family", "Wife"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Family", "Uncle").build()));

        // Mixed-case keywords
        predicate = new TagListContainsKeywordsPredicate(Arrays.asList("faMIly", "FAmiLy"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Family").build()));

        // Partial Match (one of the words starts with one of the keywords)
        predicate = new TagListContainsKeywordsPredicate(Arrays.asList("Frien", "Fam"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friend", "Family").build()));
    }

    @Test
    public void test_tagListDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagListContainsKeywordsPredicate predicate = new TagListContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("Friends").build()));

        // Non-matching keyword
        predicate = new TagListContainsKeywordsPredicate(Arrays.asList("Family"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Friends", "Teammates").build()));

        // Keywords match name, phone, email and address, but does not match tags
        predicate = new TagListContainsKeywordsPredicate(Arrays.asList("Ben", "123", "al@mail.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Ben").withPhone("123")
                .withEmail("al@mail.com").withAddress("Main Street").withTags("Friends").build()));
    }
}
