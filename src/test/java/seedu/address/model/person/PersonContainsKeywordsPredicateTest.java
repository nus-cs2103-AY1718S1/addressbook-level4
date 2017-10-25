package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.Test;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "criminal";

    @Test
    public void equals() throws Exception{
        Set<Tag> singleTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1)));
        Set<Tag> multipleTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        PersonContainsKeywordsPredicate firstPredicate = new PersonContainsKeywordsPredicate(new ArrayList<>(singleTagSet));
        PersonContainsKeywordsPredicate secondPredicate = new PersonContainsKeywordsPredicate(new ArrayList<>(multipleTagSet));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy = new PersonContainsKeywordsPredicate(new ArrayList<>(singleTagSet));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personContainsTags_returnsTrue() throws Exception{
        Set<Tag> emptyTagSet = new HashSet<Tag>(Arrays.asList());
        Set<Tag> singleTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1)));
        Set<Tag> multipleTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        // One keyword
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(new ArrayList<>(singleTagSet));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordsPredicate(new ArrayList<>(multipleTagSet));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "criminal").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(new ArrayList<>(multipleTagSet));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "classmate").build()));
    }

    @Test
    public void test_personDoesNotContainTags_returnsFalse() throws Exception{
        Set<Tag> singleTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1)));
        Set<Tag> multipleTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        // Non-matching keyword
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(new ArrayList<>(singleTagSet));
        assertFalse(predicate.test(new PersonBuilder().withTags("criminal").build()));
        assertFalse(predicate.test(new PersonBuilder().withTags("criminal", "classmate").build()));

        predicate = new PersonContainsKeywordsPredicate(new ArrayList<>(multipleTagSet));
        assertFalse(predicate.test(new PersonBuilder().withTags("classmate").build()));
        assertFalse(predicate.test(new PersonBuilder().withTags("classmate", "family").build()));

    }

}
