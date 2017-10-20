package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLLEGE_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Test;

import seedu.address.model.tag.TagMatchingKeywordPredicate;
import seedu.address.testutil.PersonBuilder;

public class TagMatchingKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";
        String thirdPredicateKeyword = "First";

        TagMatchingKeywordPredicate firstPredicate = new TagMatchingKeywordPredicate(firstPredicateKeyword);
        TagMatchingKeywordPredicate secondPredicate = new TagMatchingKeywordPredicate(secondPredicateKeyword);
        TagMatchingKeywordPredicate thirdPredicate = new TagMatchingKeywordPredicate(thirdPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagMatchingKeywordPredicate firstPredicateCopy = new TagMatchingKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // same value ignoring case -> returns true
        assertTrue(firstPredicate.equals(thirdPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testTagsContainsKeywordReturnsTrue() {
        TagMatchingKeywordPredicate predicate1 = new TagMatchingKeywordPredicate(VALID_TAG_HUSBAND);
        TagMatchingKeywordPredicate predicate2 = new TagMatchingKeywordPredicate(VALID_TAG_COLLEGE_FRIEND);

        // One keyword
        assertTrue(predicate1.test(new PersonBuilder().withTags("husband").build()));

        //Mixed-case keyword
        assertTrue(predicate1.test(new PersonBuilder().withTags("HUSBAND").build()));

        // Multiple words keyword
        assertTrue(predicate2.test(new PersonBuilder().withTags("college friend").build()));

        //Mixed-case multiple words keyword
        assertTrue(predicate2.test(new PersonBuilder().withTags("College Friend").build()));
    }

    @Test
    public void testTagsDoesNotContainKeywordReturnsFalse() {
        // Zero keyword
        TagMatchingKeywordPredicate predicate = new TagMatchingKeywordPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withTags("friend").build()));

        // Non-matching keyword
        predicate = new TagMatchingKeywordPredicate(VALID_TAG_HUSBAND);
        assertFalse(predicate.test(new PersonBuilder().withTags("Alice Bob").build()));

        // Keyword match name, but does not match tag
        predicate = new TagMatchingKeywordPredicate("Alice");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

}
