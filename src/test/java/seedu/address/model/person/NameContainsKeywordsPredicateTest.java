package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import seedu.address.testutil.ImageInit;
import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordsPredicateTest {
    @BeforeClass
    public static void setup() throws Exception {
        ImageInit.checkDirectories();
        ImageInit.initPictures();
    }

    @AfterClass
    public static void recovery() throws Exception {
        ImageInit.deleteEditedFiles();
        ImageInit.deleteImagesFiles();
    }
    @Test
    public void equals() {
        List<String> firstPredicateKeywordNameList = Collections.singletonList("first");
        List<String> firstPredicateKeywordTagList = Collections.singletonList("tag1");
        List<String> secondPredicateKeywordNameList = Arrays.asList("first", "second");
        List<String> secondPredicateKeywordTagList = Arrays.asList("tag1", "tag2");

        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(firstPredicateKeywordNameList, firstPredicateKeywordTagList);
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(secondPredicateKeywordNameList, secondPredicateKeywordTagList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy =
                new NameContainsKeywordsPredicate(firstPredicateKeywordNameList, firstPredicateKeywordTagList);
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
        // One name keyword with tag1
        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice"),
                        Collections.singletonList("tag1"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("tag1").build()));

        // Multiple name keywords with tag1
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), Collections.singletonList("tag1"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("tag1").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"), Collections.singletonList("tag1"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").withTags("tag1").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"), Collections.singletonList("tag1"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("tag1").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withTags("tag2").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Carol"), Arrays.asList("tag1"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("tag2").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("88888888", "alice@email.com", "Main", "Street"),
                Arrays.asList("tag1"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("88888888")
                .withEmailAddress("alice@email.com").withAddress("Main Street").withTags("tag2").build()));
    }
}
