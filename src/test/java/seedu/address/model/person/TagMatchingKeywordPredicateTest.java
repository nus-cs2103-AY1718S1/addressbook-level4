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
        boolean looseFind = true;
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";
        String thirdPredicateKeyword = "First";

        TagMatchingKeywordPredicate firstPredicate =
                new TagMatchingKeywordPredicate(firstPredicateKeyword, looseFind);
        TagMatchingKeywordPredicate secondPredicate =
                new TagMatchingKeywordPredicate(secondPredicateKeyword, looseFind);
        TagMatchingKeywordPredicate thirdPredicate =
                new TagMatchingKeywordPredicate(thirdPredicateKeyword, looseFind);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagMatchingKeywordPredicate firstPredicateCopy =
                new TagMatchingKeywordPredicate(firstPredicateKeyword, looseFind);
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
        boolean looseFind = true;
        TagMatchingKeywordPredicate predicate1 = new TagMatchingKeywordPredicate(VALID_TAG_HUSBAND, looseFind);
        TagMatchingKeywordPredicate predicate2 = new TagMatchingKeywordPredicate(VALID_TAG_COLLEGE_FRIEND, looseFind);

        // One keyword
        assertTrue(predicate1.test(new PersonBuilder().withTags("husband").build()));

        // One keyword
        assertTrue(predicate1.test(new PersonBuilder().withTags("husbands").build()));

        //Mixed-case keyword
        assertTrue(predicate1.test(new PersonBuilder().withTags("HUSBAND").build()));

        // Multiple words keyword
        assertTrue(predicate2.test(new PersonBuilder().withTags("college friend").build()));

        //Mixed-case multiple words keyword
        assertTrue(predicate2.test(new PersonBuilder().withTags("College Friend").build()));

        // Multiple words keyword
        assertTrue(predicate2.test(new PersonBuilder().withTags("college friend 1").build()));
    }

    @Test
    public void testTagsDoesNotMatchExactlyReturnsFalse() {
        boolean looseFind = false;
        TagMatchingKeywordPredicate predicate1 = new TagMatchingKeywordPredicate(VALID_TAG_HUSBAND, looseFind);
        TagMatchingKeywordPredicate predicate2 = new TagMatchingKeywordPredicate(VALID_TAG_COLLEGE_FRIEND, looseFind);

        // One keyword
        assertFalse(predicate1.test(new PersonBuilder().withTags("husbands").build()));

        //Mixed-case keyword
        assertFalse(predicate1.test(new PersonBuilder().withTags("HUSBAND").build()));

        //Mixed-case multiple words keyword
        assertFalse(predicate2.test(new PersonBuilder().withTags("College Friend").build()));

        // Multiple words keyword
        assertFalse(predicate2.test(new PersonBuilder().withTags("college friend 1").build()));
    }

    @Test
    public void testTagsDoesNotContainKeywordReturnsFalse() {
        boolean looseFind = true;
        // Zero keyword
        TagMatchingKeywordPredicate predicate = new TagMatchingKeywordPredicate("", looseFind);
        assertFalse(predicate.test(new PersonBuilder().withTags("friend").build()));

        // Non-matching keyword
        predicate = new TagMatchingKeywordPredicate(VALID_TAG_HUSBAND, looseFind);
        assertFalse(predicate.test(new PersonBuilder().withTags("Alice Bob").build()));

        // Keyword match name, but does not match tag
        predicate = new TagMatchingKeywordPredicate("Alice", looseFind);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void testKeywordReturnsTrue() {
        boolean looseFind = true;
        TagMatchingKeywordPredicate predicate = new TagMatchingKeywordPredicate("", looseFind);
        assertTrue("".equals(predicate.getKeyword()));
    }

}
