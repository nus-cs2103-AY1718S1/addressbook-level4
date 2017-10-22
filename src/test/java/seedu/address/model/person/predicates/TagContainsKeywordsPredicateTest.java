//@@author A0143832J
package seedu.address.model.person.predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordsPredicateTest {
    private static Set<Tag> set1 = new UniqueTagList().toSet();
    private static Set<Tag> set2 = new UniqueTagList().toSet();

    @Before
    public void setUp() throws Exception {
        set1.add(new Tag("friends"));
        set1.add(new Tag("family"));
        set2.add(new Tag("friends"));

    }
    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(set2);
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(set1);
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Only one matching sub-keyword
        predicate = new TagContainsKeywordsPredicate(set1);
        assertTrue(predicate.test(new PersonBuilder().withTags("friendship").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(new UniqueTagList().toSet());
        assertFalse(predicate.test(new PersonBuilder().withTags("cs2103").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(set1);
        assertFalse(predicate.test(new PersonBuilder().withTags("cs2103").build()));

        predicate = new TagContainsKeywordsPredicate(set1);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Street").withBirthday("01/01/1990")
                .withTags("cs2103").build()));
    }

    @Test
    public void equals() throws Exception {
        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(set1);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(set2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(set1);
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
