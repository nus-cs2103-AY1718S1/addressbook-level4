package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonContainsKeywordsPredicate firstPredicate = new PersonContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD, firstPredicateKeywordList, false);
        PersonContainsKeywordsPredicate secondPredicate = new PersonContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD, secondPredicateKeywordList, false);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy = new PersonContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD, firstPredicateKeywordList, false);
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
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD, Collections.singletonList("Alice"), false);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        PersonContainsKeywordsPredicate predicate2 = new PersonContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD_ADDRESS, Collections.singletonList("Jurong"), false);
        assertTrue(predicate2.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111").build()));

        PersonContainsKeywordsPredicate predicate3 = new PersonContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD_EMAIL, Collections.singletonList("alice@example.com"), false);
        assertTrue(predicate3.test(new PersonBuilder().withEmail("alice@example.com").build()));

        PersonContainsKeywordsPredicate predicate4 = new PersonContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD_PHONE, Collections.singletonList("85355255"), false);
        assertTrue(predicate4.test(new PersonBuilder().withPhone("85355255").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordsPredicate(FindCommand.COMMAND_WORD, Arrays.asList("Alice", "Bob"), false);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new PersonContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD_ADDRESS, Arrays.asList("Jurong", "West"), false);
        assertTrue(predicate.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111").build()));

        //@TODO: More test case to be added..
        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(FindCommand.COMMAND_WORD, Arrays.asList("Bob", "Carol"), false);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PersonContainsKeywordsPredicate(FindCommand.COMMAND_WORD, Arrays.asList("aLIce", "bOB"), false);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD, Collections.emptyList(), false);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new PersonContainsKeywordsPredicate(FindCommand.COMMAND_WORD, Arrays.asList("Carol"), false);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new PersonContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD, Arrays.asList("12345", "alice@email.com", "Main", "Street"), false);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
