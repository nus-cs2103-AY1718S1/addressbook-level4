package seedu.address.model.person;

import org.junit.Test;
import seedu.address.testutil.PersonBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class AddressContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("123, Jurong West Ave 6, #08-111");
        List<String> secondPredicateKeywordList = Arrays.asList("123, Jurong West Ave 6, #08-111", "10, Geylang Street 20, #01-111");

        AddressContainsKeywordsPredicate firstPredicate = new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        AddressContainsKeywordsPredicate secondPredicate = new AddressContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsKeywordsPredicate firstPredicateCopy = new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        // match any address field
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(Collections.singletonList("Jurong"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111").build()));

        // match any address field
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("#08-111"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123, JurongWest Ave 6, #08-111").build()));

        // Mixed-case keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("JuRONG"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111").build()));

    }


    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero address parmeter
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111").build()));

        // Non-matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList(" #08-110"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111").build()));

        // Keywords match phone, name and email, but does not match address
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("12345", "Alice", "alice@gmail.com"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
