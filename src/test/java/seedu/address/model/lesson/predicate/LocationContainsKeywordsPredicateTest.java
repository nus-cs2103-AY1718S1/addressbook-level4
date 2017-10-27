package seedu.address.model.lesson.predicate;

import org.junit.Test;
import seedu.address.model.module.predicates.LocationContainsKeywordsPredicate;
import seedu.address.testutil.LessonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LocationContainsKeywordsPredicateTest {

    @Test
    public void equals() {

        List<String> keywordOne = new ArrayList<String>() {
        };
        List<String> keywordTwo = new ArrayList<String>() {
        };
        List<String> keywordNull = new ArrayList<String>() {
        };

        keywordOne.add("111");
        keywordOne.add("222");
        keywordOne.add("333");

        keywordTwo.add("aaa");
        keywordTwo.add("bbb");
        keywordTwo.add("bbb");


        LocationContainsKeywordsPredicate predicateOne = new LocationContainsKeywordsPredicate(keywordOne);
        LocationContainsKeywordsPredicate predicateTwo = new LocationContainsKeywordsPredicate(keywordTwo);
        LocationContainsKeywordsPredicate predicateNull = new LocationContainsKeywordsPredicate(keywordNull);

        // same object -> returns true
        assertTrue(predicateOne.equals(predicateOne));

        // same values -> returns true
        LocationContainsKeywordsPredicate predicateTestOne = new LocationContainsKeywordsPredicate(keywordOne);
        assertTrue(predicateOne.equals(predicateTestOne));

        // different types -> returns false
        assertFalse(predicateOne.equals(1));

        // null -> returns false
        assertFalse(predicateOne.equals(null));

        // different keywords -> returns false
        assertFalse(predicateOne.equals(predicateTwo));

        // one predicate with keywords compare with another predicate without keywords -> returns false
        assertFalse(predicateOne.equals(predicateNull));
    }

    @Test
    public void test_locationContainsKeywords_returnsTrue() {
        // One keyword
        LocationContainsKeywordsPredicate predicate = new LocationContainsKeywordsPredicate(Collections.singletonList(
                "LT27"));
        assertTrue(predicate.test(new LessonBuilder().withLocation("LT27").build()));

        // Only one matching keyword
        predicate = new LocationContainsKeywordsPredicate(Arrays.asList("LT26", "LT27"));
        assertTrue(predicate.test(new LessonBuilder().withLocation("LT27").build()));

        // Mixed-case keywords
        predicate = new LocationContainsKeywordsPredicate(Arrays.asList("lT27", "Lt28"));
        assertTrue(predicate.test(new LessonBuilder().withLocation("LT27").build()));

        // partial keywords that is a substring of the moduleCode
        predicate = new LocationContainsKeywordsPredicate(Arrays.asList("28"));
        assertTrue(predicate.test(new LessonBuilder().withLocation("LT28").build()));

    }

    @Test
    public void test_locationDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        LocationContainsKeywordsPredicate predicate = new LocationContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new LessonBuilder().withLocation("LT27").build()));

        // Non-matching keyword
        predicate = new LocationContainsKeywordsPredicate(Arrays.asList("COM1"));
        assertFalse(predicate.test(new LessonBuilder().withLocation("LT22").build()));

    }

}
