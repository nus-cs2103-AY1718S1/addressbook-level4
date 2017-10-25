package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("85355255");
        List<String> secondPredicateKeywordList = Arrays.asList("85355255", "99995255");

        Predicate<ReadOnlyPerson> firstPredicate = new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        Predicate<ReadOnlyPerson> secondPredicate = new PhoneContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        Predicate<ReadOnlyPerson> firstPredicateCopy = new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One phone number of 4 digits
        Predicate<ReadOnlyPerson> predicate = new PhoneContainsKeywordsPredicate(Collections.singletonList("8535"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("85355255").build()));

        //Searching multiple phone numbers with varying digits
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("8535", "8989"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("85355255").build()));

        // Searching phone number using 4 digits
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("8535", "5255"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("85355255").build()));

    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero phone numbers
        Predicate<ReadOnlyPerson> predicate = new PhoneContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("85355255").build()));

        // phone with 3 digits
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("853"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("85355255").build()));

        // Keywords match email, name and address, but does not match phone
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("alice@gmail.com", "Alice", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("123, Main Street, #08-111, Singapore 409999").build()));
    }
}
