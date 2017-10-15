package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class InterceptionPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");
        List<String> thirdPredicateKeywordList = Arrays.asList("first", "second", "third");
        List<String> fourthPredicateKeywordList = Arrays.asList("first", "second", "third", "fourth");

        NameContainsKeywordsPredicate firstComponentPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondComponentPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);
        NameContainsKeywordsPredicate thirdComponentPredicate = new NameContainsKeywordsPredicate(thirdPredicateKeywordList);
        NameContainsKeywordsPredicate fourthComponentPredicate = new NameContainsKeywordsPredicate(fourthPredicateKeywordList);

        InterceptionPredicate firstPredicate = new InterceptionPredicate(firstComponentPredicate, secondComponentPredicate);
        InterceptionPredicate secondPredicate = new InterceptionPredicate(thirdComponentPredicate, fourthComponentPredicate);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        InterceptionPredicate firstPredicateCopy = new InterceptionPredicate(firstComponentPredicate, secondComponentPredicate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // same values in different order -> true
        firstPredicateCopy = new InterceptionPredicate(secondComponentPredicate, firstComponentPredicate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different values -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_interception_returnsTrue() {

        // both predicates fulfilled
        NameContainsKeywordsPredicate predicateComponent1 = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        ContainsTagsPredicate predicateComponent2 = new ContainsTagsPredicate(Collections.singletonList("family"));
        InterceptionPredicate predicate = new InterceptionPredicate(predicateComponent1, predicateComponent2);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("family").build()));

    }

    @Test
    public void test_interception_returnsFalse() {

        NameContainsKeywordsPredicate predicateComponent1 = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        ContainsTagsPredicate predicateComponent2 = new ContainsTagsPredicate(Collections.singletonList("family"));

        // predicate1 is not fulfilled
        InterceptionPredicate predicate = new InterceptionPredicate(predicateComponent1, predicateComponent2);
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").withTags("family").build()));

        // predicate2 is not fulfilled
        predicate = new InterceptionPredicate(predicateComponent1, predicateComponent2);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withTags("friends").build()));

        // neither predicates are not fulfilled
        predicate = new InterceptionPredicate(predicateComponent1, predicateComponent2);
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").withTags("friends").build()));
    }
}
