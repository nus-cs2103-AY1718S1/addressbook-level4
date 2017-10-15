package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class ContainsTagsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ContainsTagsPredicate firstPredicate = new ContainsTagsPredicate(firstPredicateKeywordList);
        ContainsTagsPredicate secondPredicate = new ContainsTagsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContainsTagsPredicate firstPredicateCopy = new ContainsTagsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different tags -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_containsTags_returnsTrue() {
        // One keyword
        ContainsTagsPredicate predicate = new ContainsTagsPredicate(Collections.singletonList("family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("family").build()));

        // Multiple keywords
        predicate = new ContainsTagsPredicate(Arrays.asList("family", "friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Mixed-case keywords
        predicate = new ContainsTagsPredicate(Arrays.asList("FaMiLy", "FriEndS"));
        assertTrue(predicate.test(new PersonBuilder().withTags("fAmilY", "friEnDS").build()));
    }

    @Test
    public void test_doesNotContainTags_returnsFalse() {
        // Zero keywords
        ContainsTagsPredicate predicate = new ContainsTagsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Non-matching keyword
        predicate = new ContainsTagsPredicate(Arrays.asList("family"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Keywords match name, phone, email and address, but does not match tags
        predicate = new ContainsTagsPredicate(Arrays.asList("Alice", "12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withTags("family").build()));
    }
}
