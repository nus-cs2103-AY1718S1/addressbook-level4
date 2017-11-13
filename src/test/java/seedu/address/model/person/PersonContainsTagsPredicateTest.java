//@@author PokkaKiyo
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsTagsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonContainsTagsPredicate firstPredicate = new PersonContainsTagsPredicate(firstPredicateKeywordList);
        PersonContainsTagsPredicate secondPredicate = new PersonContainsTagsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsTagsPredicate firstPredicateCopy = new PersonContainsTagsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personHasTagsMatchingKeywordsToInclude_returnsTrue() {
        // One keyword
        PersonContainsTagsPredicate predicate =
                new PersonContainsTagsPredicate(Collections.singletonList("colleagues"));
        assertTrue(predicate.test(new PersonBuilder().withTags("colleagues").build()));

        // Multiple keywords
        predicate = new PersonContainsTagsPredicate(Arrays.asList("colleagues", "schoolmates"));
        assertTrue(predicate.test(new PersonBuilder().withTags("colleagues", "friends", "schoolmates").build()));

        // Only one matching keyword
        predicate = new PersonContainsTagsPredicate(Arrays.asList("colleagues", "schoolmates"));
        assertTrue(predicate.test(new PersonBuilder().withTags("colleagues").build()));

        // Mixed-case keywords
        predicate = new PersonContainsTagsPredicate(Arrays.asList("cOLLeaguEs", "FrIeNdS"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "colleagues").build()));

    }

    @Test
    public void test_personDoesNotHaveTagsContainingKeywordsToInclude_returnsFalse() {
        // Non-matching keyword
        PersonContainsTagsPredicate predicate = new PersonContainsTagsPredicate(Arrays.asList("roommate"));
        assertFalse(predicate.test(new PersonBuilder().withTags("schoolmate", "classmate").build()));

        // Keywords match name, phone, email, address, birthday, facebook address, but does not match any tag name
        predicate = new PersonContainsTagsPredicate(Arrays.asList("12345", "alice@email.com", "Main",
                "Street", "Alice", "14051998", "https://www.facebook.com/default_address_for_testing/"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street")
                .withName("Alice").withFacebookAddress("https://www.facebook.com/default_address_for_testing/")
                .withBirthday("14051998").build()));
    }

    @Test
    public void test_personHasNoTags() {
        //Person with no tags for inclusion
        PersonContainsTagsPredicate predicate = new PersonContainsTagsPredicate(Arrays.asList("roommates"));
        assertFalse(predicate.test(new PersonBuilder().withTags().build()));

        // Person with no tags for exclusion
        predicate = new PersonContainsTagsPredicate(Arrays.asList("-roommates"));
        assertTrue(predicate.test(new PersonBuilder().withTags().build()));

        //Person with no tags for both exclusion and inclusion
        predicate = new PersonContainsTagsPredicate(Arrays.asList("-roommates", "friends"));
        assertFalse(predicate.test(new PersonBuilder().withTags().build()));
    }

    @Test
    public void test_personHasTagsForExclusion() {
        // Has one tag matching tags to exclude
        PersonContainsTagsPredicate predicate = new PersonContainsTagsPredicate(Arrays.asList("-roommates"));
        assertFalse(predicate.test(new PersonBuilder().withTags("roommates").build()));

        // Has at least one tag matching tags to exclude
        predicate = new PersonContainsTagsPredicate(Arrays.asList("-roommates"));
        assertFalse(predicate.test(new PersonBuilder().withTags("roommates", "schoolmates").build()));

        // More keywords to exclude than tags of person
        predicate = new PersonContainsTagsPredicate(Arrays.asList("-roommates", "-schoolmates"));
        assertFalse(predicate.test(new PersonBuilder().withTags("roommates").build()));

        // More tags on person than keywords to exclude
        predicate = new PersonContainsTagsPredicate(Arrays.asList("-roommates", "-schoolmates"));
        assertFalse(predicate.test(new PersonBuilder().withTags("roommates", "schoolmates", "classmates").build()));

    }

    @Test
    public void test_personDoesNotHaveTagsForExclusion() {
        // Does not have matching tags to exclude
        PersonContainsTagsPredicate predicate = new PersonContainsTagsPredicate(Arrays.asList("-roommates"));
        assertTrue(predicate.test(new PersonBuilder().withTags("classmates").build()));

        // Keywords to exclude match name, phone, email, address, birthday, and facebook address,
        // but does not match any tag name
        predicate = new PersonContainsTagsPredicate(Arrays.asList("-12345", "-alice@email.com", "-Main",
                "-Street", "-Alice", "-14051998", "-https://www.facebook.com/default_address_for_testing/"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street")
                .withName("Alice").withFacebookAddress("https://www.facebook.com/default_address_for_testing/")
                .withBirthday("14051998").build()));
    }

    @Test
    public void test_personHasTagsForInclusionAndTagsForExclusion() {
        // Person has tags to include but also to exclude
        PersonContainsTagsPredicate predicate =
                new PersonContainsTagsPredicate(Arrays.asList("friends", "-colleagues"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends", "colleagues").build()));

        // Person has tags to include but also to exclude, in mixed order
        predicate = new PersonContainsTagsPredicate(Arrays.asList("-colleagues", "friends"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends", "colleagues").build()));

        // Person has tags to include but also to exclude, in mixed case
        predicate = new PersonContainsTagsPredicate(Arrays.asList("fRiENds", "-colLEaguEs"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends", "colleagues").build()));
    }
}
