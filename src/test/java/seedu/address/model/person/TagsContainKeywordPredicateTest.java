package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author nicholaschuayunzhi
public class TagsContainKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        TagsContainKeywordPredicate firstPredicate = new  TagsContainKeywordPredicate(firstPredicateKeyword);
        TagsContainKeywordPredicate secondPredicate = new  TagsContainKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagsContainKeywordPredicate firstPredicateCopy = new  TagsContainKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_containsKeywords_returnsTrue() {
        // One keyword
        TagsContainKeywordPredicate predicate = new  TagsContainKeywordPredicate("friends");
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        //multiple tags
        predicate = new  TagsContainKeywordPredicate("friends");
        assertTrue(predicate.test(new PersonBuilder().withTags("owesMoney", "friends").build()));

        //multiple similar tags, one match only
        predicate = new  TagsContainKeywordPredicate("friends");
        assertTrue(predicate.test(
                new PersonBuilder().withTags("bestFriends", "friends", "friendsAndPals").build()));

        // sub-string
        predicate = new TagsContainKeywordPredicate("fri");
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Mixed-case
        predicate = new TagsContainKeywordPredicate("FriEndS");
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));
    }

    @Test
    public void test_doesNotContainKeywords_returnsFalse() {

        TagsContainKeywordPredicate predicate = new TagsContainKeywordPredicate("enemies");
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));

        predicate = new TagsContainKeywordPredicate("friendsForLife");
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));

        //multiple similar tags, no match only
        predicate = new  TagsContainKeywordPredicate("bestFriends");
        assertFalse(predicate.test(
                new PersonBuilder().withTags("friendsForLife", "friends", "friendsAndPals").build()));
    }
}
