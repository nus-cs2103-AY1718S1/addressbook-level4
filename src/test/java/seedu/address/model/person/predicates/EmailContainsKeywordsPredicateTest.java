//@@author A0143832J
package seedu.address.model.person.predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class EmailContainsKeywordsPredicateTest {
    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(
                Collections.singletonList("example@mail.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("example@mail.com").build()));

        // Multiple keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("example@mail.com", "example@yeahoo.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("example@mail.com").build()));

        // Only one matching sub-keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("example@mail.com", "example@yeahoo.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("example@mail.com.sg").build()));

        // Mixed-case keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("example@MAIl.com", "example@yeahoo.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("example@mail.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("example@mail.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("example@mail.com", "example@yahoo.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("example@facebook.com").build()));

        // Keywords match name, address and birthday, but does not match email
        predicate = new EmailContainsKeywordsPredicate(
                Arrays.asList("Ben", "Street", "Main", "01/01/1990"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Street").withBirthday("01/01/1990").build()));
    }

    @Test
    public void equals() throws Exception {
        List<String> firstPredicateKeywordList = Collections.singletonList("example@mail.com");
        List<String> secondPredicateKeywordList = Arrays.asList("example@mail.com", "example@yahoo.com");

        EmailContainsKeywordsPredicate firstPredicate = new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        EmailContainsKeywordsPredicate secondPredicate = new EmailContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsKeywordsPredicate firstPredicateCopy =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

}
//@@author
