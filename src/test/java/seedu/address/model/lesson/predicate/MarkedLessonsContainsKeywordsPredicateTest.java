package seedu.address.model.lesson.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.module.predicates.MarkedLessonContainsKeywordsPredicate;
import seedu.address.testutil.LessonBuilder;

//@@author angtianlannus
public class MarkedLessonsContainsKeywordsPredicateTest {

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

        MarkedLessonContainsKeywordsPredicate predicateOne = new MarkedLessonContainsKeywordsPredicate(keywordOne);
        MarkedLessonContainsKeywordsPredicate predicateTwo = new MarkedLessonContainsKeywordsPredicate(keywordTwo);
        MarkedLessonContainsKeywordsPredicate predicateNull = new MarkedLessonContainsKeywordsPredicate(keywordNull);

        // same object -> returns true
        assertTrue(predicateOne.equals(predicateOne));

        // same values -> returns true
        MarkedLessonContainsKeywordsPredicate predicateTestOne = new MarkedLessonContainsKeywordsPredicate(keywordOne);
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
    public void test_markedLessonsAllAttributeContainsKeywords_returnsTrue() {
        // One keyword to find location
        MarkedLessonContainsKeywordsPredicate predicate =
                new MarkedLessonContainsKeywordsPredicate(Collections.singletonList("LT27"));
        assertTrue(predicate.test(new LessonBuilder().withLocation("LT27").withMarked().build()));

        // One keyword to find group
        predicate = new MarkedLessonContainsKeywordsPredicate(Collections.singletonList("12"));
        assertTrue(predicate.test(new LessonBuilder().withGroup("12").withMarked().build()));

        // One keyword to find class type
        predicate = new MarkedLessonContainsKeywordsPredicate(Collections.singletonList("LEC"));
        assertTrue(predicate.test(new LessonBuilder().withClassType("LEC").withMarked().build()));

        // One keyword to find time slot
        predicate = new MarkedLessonContainsKeywordsPredicate(Collections.singletonList("MON[1200-1300]"));
        assertTrue(predicate.test(new LessonBuilder().withTimeSlot("MON[1200-1300]").withMarked().build()));

        // Only one matching keyword to find location
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("LT26", "LT27"));
        assertTrue(predicate.test(new LessonBuilder().withLocation("LT27").withMarked().build()));

        // Only one matching keyword to find group
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("1", "2"));
        assertTrue(predicate.test(new LessonBuilder().withGroup("1").withMarked().build()));

        // Only one matching keyword to find class type
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("TUT", "LEC"));
        assertTrue(predicate.test(new LessonBuilder().withClassType("TUT").withMarked().build()));

        // Only one matching keyword to find time slot
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("MON[1200-1300]", "TUE[0900-1000]"));
        assertTrue(predicate.test(new LessonBuilder().withTimeSlot("MON[1200-1300]").withMarked().build()));

        // Mixed-case keywords to find location
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("Lt12"));
        assertTrue(predicate.test(new LessonBuilder().withLocation("LT12").withMarked().build()));

        // Mixed-case keywords to find class type
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("LeC"));
        assertTrue(predicate.test(new LessonBuilder().withClassType("LEC").withMarked().build()));

        // Mixed-case keywords to find time slot
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("mOn[1200-1300]"));
        assertTrue(predicate.test(new LessonBuilder().withTimeSlot("MON[1200-1300]").withMarked().build()));

        // partial keywords that is a substring of the location that user intend to find
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("LT"));
        assertTrue(predicate.test(new LessonBuilder().withLocation("LT12").withMarked().build()));

        // partial keywords that is a substring of the time slot that user intend to find
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("MON"));
        assertTrue(predicate.test(new LessonBuilder().withTimeSlot("MON[1200-1300]").withMarked().build()));

        // partial keywords that is a substring of the class type that user intend to find
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("T"));
        assertTrue(predicate.test(new LessonBuilder().withClassType("TUT").withMarked().build()));

    }

    @Test
    public void test_markedLessonDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MarkedLessonContainsKeywordsPredicate predicate =
                new MarkedLessonContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test((new LessonBuilder().withMarked().build())));

        // Non-matching keyword for all attributes
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("COM1"));
        assertFalse(predicate.test(new LessonBuilder().withMarked().build()));

    }

    @Test
    public void test_unmarkedLesson_returnsFalse() {
        MarkedLessonContainsKeywordsPredicate predicate =
                new MarkedLessonContainsKeywordsPredicate(Arrays.asList("LEC"));
        assertFalse(predicate.test(new LessonBuilder().withUnmarked().build()));
    }
}
