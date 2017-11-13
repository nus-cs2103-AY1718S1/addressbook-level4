package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author chuaweiwen
public class NameAndTagsContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateNameKeywordList = Arrays.asList("alex", "yeoh");
        List<String> firstPredicateTagsKeywordList = Arrays.asList("friends", "colleagues");

        List<String> secondPredicateNameKeywordList = Arrays.asList("bernice", "yu");
        List<String> secondPredicateTagsKeywordList = Arrays.asList("clients");

        NameAndTagsContainsKeywordsPredicate firstPredicate = new NameAndTagsContainsKeywordsPredicate(
                firstPredicateNameKeywordList, firstPredicateTagsKeywordList);
        NameAndTagsContainsKeywordsPredicate secondPredicate = new NameAndTagsContainsKeywordsPredicate(
                secondPredicateNameKeywordList, secondPredicateTagsKeywordList);
        NameAndTagsContainsKeywordsPredicate predicateWithDifferentTag = new NameAndTagsContainsKeywordsPredicate(
                firstPredicateNameKeywordList, secondPredicateTagsKeywordList);
        NameAndTagsContainsKeywordsPredicate predicateWithDifferentName = new NameAndTagsContainsKeywordsPredicate(
                secondPredicateNameKeywordList, firstPredicateTagsKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameAndTagsContainsKeywordsPredicate firstPredicateCopy = new NameAndTagsContainsKeywordsPredicate(
                firstPredicateNameKeywordList, firstPredicateTagsKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // same names but different tags -> returns false
        assertFalse(firstPredicate.equals(predicateWithDifferentTag));

        // same tags but different names -> returns false
        assertFalse(firstPredicate.equals(predicateWithDifferentName));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One name
        NameAndTagsContainsKeywordsPredicate predicate = new NameAndTagsContainsKeywordsPredicate(
                Arrays.asList("Alice"), new ArrayList<>());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // One tag
        predicate = new NameAndTagsContainsKeywordsPredicate(new ArrayList<>(), Arrays.asList("family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("family").build()));

        // Multiple names
        predicate = new NameAndTagsContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), new ArrayList<>());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple tags
        predicate = new NameAndTagsContainsKeywordsPredicate(new ArrayList<>(), Arrays.asList("family", "friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("family", "friends").build()));

        // Both names and tags
        predicate = new NameAndTagsContainsKeywordsPredicate(Arrays.asList("Alice"), Arrays.asList("family"));
        assertTrue(predicate.test(new PersonBuilder().withNameAndTags("Alice Bob", "family").build()));

        // Multiple names in different order
        predicate = new NameAndTagsContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), new ArrayList<>());
        assertTrue(predicate.test(new PersonBuilder().withName("Bob Alice").build()));

        // Multiple tags in different order
        predicate = new NameAndTagsContainsKeywordsPredicate(new ArrayList<>(), Arrays.asList("family", "friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "family").build()));

        // Mixed-case name
        predicate = new NameAndTagsContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"), new ArrayList<>());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case tag
        predicate = new NameAndTagsContainsKeywordsPredicate(new ArrayList<>(), Arrays.asList("fRiEnDs"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Repeated names
        predicate = new NameAndTagsContainsKeywordsPredicate(Arrays.asList("Alice", "Alice"), new ArrayList<>());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Repeated tags
        predicate = new NameAndTagsContainsKeywordsPredicate(new ArrayList<>(), Arrays.asList("friends", "friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching name
        NameAndTagsContainsKeywordsPredicate predicate = new NameAndTagsContainsKeywordsPredicate(
                Arrays.asList("Carol"), new ArrayList<>());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Non-matching tags
        predicate = new NameAndTagsContainsKeywordsPredicate(new ArrayList<>(), Arrays.asList("family"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends", "colleagues").build()));

        // Only one matching name
        predicate = new NameAndTagsContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"), new ArrayList<>());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Only one matching tag
        predicate = new NameAndTagsContainsKeywordsPredicate(
                new ArrayList<>(), Arrays.asList("family", "colleagues"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends", "family").build()));

        // Match name but does not match tag
        predicate = new NameAndTagsContainsKeywordsPredicate(
                Arrays.asList("Alice", "Bob"), Arrays.asList("family"));
        assertFalse(predicate.test(new PersonBuilder().withNameAndTags("Alice Bob", "friends").build()));
    }

    @Test
    public void test_missingNameAndTagKeywords_throwsError() throws AssertionError {
        NameAndTagsContainsKeywordsPredicate predicate = new NameAndTagsContainsKeywordsPredicate(
                new ArrayList<>(), new ArrayList<>());
        boolean thrown = false;
        try {
            predicate.test(null);
        } catch (AssertionError e) {
            thrown = true;
        }
        assertTrue(thrown);
    }
}
//@@author
