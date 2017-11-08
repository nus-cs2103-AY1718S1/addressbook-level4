package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;
import seedu.address.ui.GuiUnitTest;

//@@author KhorSL
public class PersonContainsKeywordsPredicateTest extends GuiUnitTest {

    @Test
    public void equals() {
        HashMap<String, List<String>> firstPredicateKeywordHashMap = new HashMap<>();
        firstPredicateKeywordHashMap.put("T", Collections.singletonList("first"));
        HashMap<String, List<String>> secondPredicateKeywordHashMap = new HashMap<>();
        secondPredicateKeywordHashMap.put("T", Arrays.asList("first", "second"));

        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(firstPredicateKeywordHashMap);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(secondPredicateKeywordHashMap);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy =
                new PersonContainsKeywordsPredicate(firstPredicateKeywordHashMap);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personContainsKeywords_returnsTrue() {
        // One keyword
        HashMap<String, List<String>> expectedHashMap = new HashMap<>();
        expectedHashMap.put(PREFIX_NAME.toString(), Collections.singletonList("Alice"));
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        expectedHashMap.put(PREFIX_NAME.toString(), Arrays.asList("Alice", "Bob"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        expectedHashMap.put(PREFIX_NAME.toString(), Arrays.asList("Bob", "Carol"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        expectedHashMap.put(PREFIX_NAME.toString(), Arrays.asList("aLIce", "bOB"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Single prefix
        expectedHashMap.put(PREFIX_NAME.toString(), Collections.singletonList("Alice"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        expectedHashMap.put(PREFIX_TAG.toString(), Collections.singletonList("friends"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        expectedHashMap.put(PREFIX_PHONE.toString(), Collections.singletonList("12345"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345").build()));

        expectedHashMap.put(PREFIX_EMAIL.toString(), Collections.singletonList("@gmail.com"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withEmail("test@gmail.com").build()));

        expectedHashMap.put(PREFIX_ADDRESS.toString(), Collections.singletonList("Wall"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withAddress("Wall Street").build()));

        expectedHashMap.put(PREFIX_COMMENT.toString(), Collections.singletonList("funny"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withComment("funny").build()));

        // Multiple prefixes
        expectedHashMap = new HashMap<>();
        expectedHashMap.put(PREFIX_PHONE.toString(), Collections.singletonList("12345"));
        expectedHashMap.put(PREFIX_EMAIL.toString(), Collections.singletonList("alice@gmail.com"));
        expectedHashMap.put(PREFIX_ADDRESS.toString(), Arrays.asList("Main", "Street"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        HashMap<String, List<String>> expectedHashMap = new HashMap<>();
        expectedHashMap.put(PREFIX_NAME.toString(), Collections.emptyList());
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        expectedHashMap.put(PREFIX_NAME.toString(), Collections.singletonList("Carol"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }
}
