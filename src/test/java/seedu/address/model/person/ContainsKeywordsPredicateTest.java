package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class ContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ContainsKeywordsPredicate firstPredicate =
                new ContainsKeywordsPredicate(firstPredicateKeywordList, "name");
        ContainsKeywordsPredicate secondPredicate =
                new ContainsKeywordsPredicate(secondPredicateKeywordList, "name");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContainsKeywordsPredicate firstPredicateCopy =
                new ContainsKeywordsPredicate(firstPredicateKeywordList, "name");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    //@@author Gideonfu
    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(Collections.singletonList("Alice"), "name");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new ContainsKeywordsPredicate(Collections.singletonList("Serangoon"), "address");
        assertTrue(predicate.test(new PersonBuilder().withAddress("Serangoon Street 30").build()));

        predicate = new ContainsKeywordsPredicate(Collections.singletonList("example"), "email");
        assertTrue(predicate.test(new PersonBuilder().withEmail("example@123.com").build()));

        predicate = new ContainsKeywordsPredicate(Collections.singletonList("12345678"), "phone");
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        predicate = new ContainsKeywordsPredicate(Collections.singletonList("10"), "birthday");
        assertTrue(predicate.test(new PersonBuilder().withBirthday("01-10-1995").build()));

        predicate = new ContainsKeywordsPredicate(Collections.singletonList("friend"), "tag");
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "colleagues").build()));

        predicate = new ContainsKeywordsPredicate(Collections.singletonList("Lipton"), "org");
        assertTrue(predicate.test(new PersonBuilder().withOrganisation("Lipton").build()));

        // Multiple keywords
        predicate = new ContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), "name");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new ContainsKeywordsPredicate(Arrays.asList("Serangoon", "Street"), "address");
        assertTrue(predicate.test(new PersonBuilder().withAddress("Serangoon Street 30").build()));

        predicate = new ContainsKeywordsPredicate(Arrays.asList("friend", "colleagues"), "tag");
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "colleagues").build()));

        predicate = new ContainsKeywordsPredicate(Arrays.asList("Apple", "Inc"), "org");
        assertTrue(predicate.test(new PersonBuilder().withOrganisation("Apple Inc").build()));

        // Only one matching keyword
        predicate = new ContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"), "name");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        predicate = new ContainsKeywordsPredicate(Arrays.asList("Friend", "Colleague"), "tag");
        assertTrue(predicate.test(new PersonBuilder().withTags("Friend", "Classmate").build()));

        predicate = new ContainsKeywordsPredicate(Arrays.asList("example", "Alex"), "email");
        assertTrue(predicate.test(new PersonBuilder().withEmail("example@123.com").build()));

        // Mixed-case keywords
        predicate = new ContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"), "name");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new ContainsKeywordsPredicate(Collections.singletonList("SerAngOOn"), "address");
        assertTrue(predicate.test(new PersonBuilder().withAddress("Serangoon Street 30").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(Collections.emptyList(), "");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new ContainsKeywordsPredicate(Collections.singletonList("Carol"), "name");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new ContainsKeywordsPredicate(Collections.singletonList("Serangoon"), "address");
        assertFalse(predicate.test(new PersonBuilder().withAddress("Bishan Street 21").build()));

        predicate = new ContainsKeywordsPredicate(Collections.singletonList("02"), "birthday");
        assertFalse(predicate.test(new PersonBuilder().withBirthday("11-01-1995").build()));

        predicate = new ContainsKeywordsPredicate(Collections.singletonList("Alex"), "email");
        assertFalse(predicate.test(new PersonBuilder().withEmail("example@123.com").build()));

        predicate = new ContainsKeywordsPredicate(Collections.singletonList("98765432"), "phone");
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        predicate = new ContainsKeywordsPredicate(Collections.singletonList("classmate"), "tag");
        assertFalse(predicate.test(new PersonBuilder().withTags("friends", "colleagues").build()));

        predicate = new ContainsKeywordsPredicate(Collections.singletonList("Apple"), "org");
        assertFalse(predicate.test(new PersonBuilder().withOrganisation("Lipton").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new ContainsKeywordsPredicate(
                Arrays.asList("12345", "alice@email.com", "Main", "Street"), "name");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

        // Keywords match name, phone, email and address, but does not match organisation
        predicate = new ContainsKeywordsPredicate(
                Arrays.asList("Alice", "12345", "alice@email.com", "Main", "Street"), "org");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withOrganisation("Apple").build()));

        // Keywords match name, phone, email and organisation, but does not match address
        predicate = new ContainsKeywordsPredicate(
                Arrays.asList("Alice", "12345", "alice@email.com", "Apple"), "org");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withOrganisation("Apple").build()));

        // Keywords match name, phone, address and organisation, but does not match email
        predicate = new ContainsKeywordsPredicate(
                Arrays.asList("Alice", "12345", "Main", "Street", "Apple"), "email");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("example@email.com").withAddress("Main Street").withOrganisation("Apple").build()));

        // Keywords match name, phone, email, address and organisation, but does not match tag
        predicate = new ContainsKeywordsPredicate(
                Arrays.asList("Alice", "12345", "alice@email.com", "Main", "Street", "Apple"), "tag");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withOrganisation("Apple")
                .withTags("Friends").build()));

        // Only one matching keyword for address
        predicate = new ContainsKeywordsPredicate(Arrays.asList("Bishan", "Street", "20"), "address");
        assertFalse(predicate.test(new PersonBuilder().withAddress("Main Street").build()));

        // Only one matching keyword for organisation
        predicate = new ContainsKeywordsPredicate(Arrays.asList("Apple", "Pte", "Ltd"), "org");
        assertFalse(predicate.test(new PersonBuilder().withOrganisation("Apple Inc").build()));
    }

    @Test
    public void test_invalidMainKeyword_returnsFalse() {
        // Empty main keyword
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(Collections.singletonList("Alice"), "");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Invalid main keyword
        predicate = new ContainsKeywordsPredicate(Collections.singletonList("Alice"), "abc");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }
}
