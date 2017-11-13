package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.FindFieldsBuilder;
import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        PersonContainsKeywordsPredicate.FindFields firstFields = new FindFieldsBuilder().withName("first").build();
        PersonContainsKeywordsPredicate.FindFields secondFields = new FindFieldsBuilder().withName("first")
                .withName("second").build();

        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(firstFields);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(secondFields);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy =
                new PersonContainsKeywordsPredicate(firstFields);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate.FindFields fields = new FindFieldsBuilder().withName("Alice").build();
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(fields);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Multiple keywords
        PersonContainsKeywordsPredicate.FindFields multipleFields = new FindFieldsBuilder().withName("Alice")
                .withPhone("91112333").build();
        predicate = new PersonContainsKeywordsPredicate(multipleFields);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        PersonContainsKeywordsPredicate.FindFields oneMatchingField = new FindFieldsBuilder().withName("Bob")
                .withName("Carol").build();
        predicate = new PersonContainsKeywordsPredicate(oneMatchingField);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        PersonContainsKeywordsPredicate.FindFields mixedCaseFields = new FindFieldsBuilder().withName("aLIce")
                .withName("bOB").build();
        predicate = new PersonContainsKeywordsPredicate(mixedCaseFields);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        //@@author sofarsophie
        // Keywords match phone, email and address, but does not match name
        PersonContainsKeywordsPredicate.FindFields differentMatchFields = new FindFieldsBuilder().withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main").withAddress("Street").build();
        predicate =
                new PersonContainsKeywordsPredicate(differentMatchFields);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
        //@@author
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonContainsKeywordsPredicate.FindFields noKeywordsFields = new FindFieldsBuilder().build();
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(noKeywordsFields);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        PersonContainsKeywordsPredicate.FindFields nonMatchingFields = new FindFieldsBuilder().withName("Carol")
                .build();
        predicate = new PersonContainsKeywordsPredicate(nonMatchingFields);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }
}
