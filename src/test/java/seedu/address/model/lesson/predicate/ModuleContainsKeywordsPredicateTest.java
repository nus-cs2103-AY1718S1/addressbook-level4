package seedu.address.model.lesson.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.module.predicates.ModuleContainsKeywordsPredicate;
import seedu.address.testutil.LessonBuilder;

public class ModuleContainsKeywordsPredicateTest {

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


        ModuleContainsKeywordsPredicate predicateOne = new ModuleContainsKeywordsPredicate(keywordOne);
        ModuleContainsKeywordsPredicate predicateTwo = new ModuleContainsKeywordsPredicate(keywordTwo);
        ModuleContainsKeywordsPredicate predicateNull = new ModuleContainsKeywordsPredicate(keywordNull);

        // same object -> returns true
        assertTrue(predicateOne.equals(predicateOne));

        // same values -> returns true
        ModuleContainsKeywordsPredicate predicateTestOne = new ModuleContainsKeywordsPredicate(keywordOne);
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
    public void test_moduleCodeContainsKeywords_returnsTrue() {
        // One keyword
        ModuleContainsKeywordsPredicate predicate = new ModuleContainsKeywordsPredicate(Collections.singletonList(
                "MA1101R"));
        assertTrue(predicate.test(new LessonBuilder().withCode("MA1101R").build()));

        // Only one matching keyword
        predicate = new ModuleContainsKeywordsPredicate(Arrays.asList("MA1101", "MA1101R"));
        assertTrue(predicate.test(new LessonBuilder().withCode("MA1101R").build()));

        // Mixed-case keywords
        predicate = new ModuleContainsKeywordsPredicate(Arrays.asList("ma1101r", "Ma1101R"));
        assertTrue(predicate.test(new LessonBuilder().withCode("MA1101R").build()));

        // partial keywords that is a substring of the moduleCode
        predicate = new ModuleContainsKeywordsPredicate(Arrays.asList("MA1101"));
        assertTrue(predicate.test(new LessonBuilder().withCode("MA1101Z").build()));

    }

    @Test
    public void test_moduleCodeDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ModuleContainsKeywordsPredicate predicate = new ModuleContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new LessonBuilder().withCode("MA1101R").build()));

        // Non-matching keyword
        predicate = new ModuleContainsKeywordsPredicate(Arrays.asList("MA1101"));
        assertFalse(predicate.test(new LessonBuilder().withCode("CS2010").build()));

    }
}

