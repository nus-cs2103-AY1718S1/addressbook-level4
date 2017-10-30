//@@author duyson98

package seedu.address.model.tag;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordPredicateTest {

    @Test
    public void equals() throws Exception {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate(new Tag("friends"));

        // same values -> returns true
        assertTrue(predicate.equals(new TagContainsKeywordPredicate(new Tag("friends"))));

        // same object -> returns true
        assertTrue(predicate.equals(predicate));

        // different types -> returns false
        assertFalse(predicate.equals(1));

        // null -> returns false
        assertFalse(predicate.equals(null));

        // different person -> returns false
        assertFalse(predicate.equals(new TagContainsKeywordPredicate(new Tag("family"))));
    }

    @Test
    public void test_tagFound_returnsTrue() throws Exception {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate(new Tag("friends"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTags("friends", "tester").build()));
    }

    @Test
    public void test_tagNotFound_returnsFalse() throws Exception {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate(new Tag("friends"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withTags("family", "tester").build()));
    }

}
