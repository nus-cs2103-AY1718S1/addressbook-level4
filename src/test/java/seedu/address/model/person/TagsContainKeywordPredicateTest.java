package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author thehelpfulbees

public class TagsContainKeywordPredicateTest {

    @Test
    public void test_tagsContainKeyword_returnsTrue() {
        // One tag
        TagsContainKeywordPredicate predicate = new TagsContainKeywordPredicate("first");
        assertTrue(predicate.test(new PersonBuilder().withTags("first").build()));

        // Multiple tags
        predicate = new TagsContainKeywordPredicate("first");
        assertTrue(predicate.test(new PersonBuilder().withTags("first", "second").build()));

    }

    @Test
    public void test_tagsDoNotContainKeyword_returnsFalse() {
        // One tag
        TagsContainKeywordPredicate predicate = new TagsContainKeywordPredicate("wrong");
        assertFalse(predicate.test(new PersonBuilder().withTags("first").build()));

        // Multiple tags
        predicate = new TagsContainKeywordPredicate("wrong");
        assertFalse(predicate.test(new PersonBuilder().withTags("first", "second").build()));

        // Keyword is inside one of the tags but doesn't match
        predicate = new TagsContainKeywordPredicate("wrong");
        assertFalse(predicate.test(new PersonBuilder().withTags("thisiswrong").build()));

        // Mixed-case tags
        predicate = new TagsContainKeywordPredicate("wrong");
        assertFalse(predicate.test(new PersonBuilder().withTags("Wrong").build()));
    }

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";


        TagsContainKeywordPredicate firstPredicate = new TagsContainKeywordPredicate(firstPredicateKeyword);
        TagsContainKeywordPredicate secondPredicate = new TagsContainKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagsContainKeywordPredicate firstPredicateCopy = new TagsContainKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

}
