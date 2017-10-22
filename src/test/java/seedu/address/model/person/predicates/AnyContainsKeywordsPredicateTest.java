//@@author A0143832J
package seedu.address.model.person.predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.testutil.PersonBuilder;

public class AnyContainsKeywordsPredicateTest {
    private static Set<Tag> set1 = new UniqueTagList().toSet();
    private static Set<Tag> set2 = new UniqueTagList().toSet();
    private static AnyContainsKeywordsPredicate predicate1 = new AnyContainsKeywordsPredicate(
            Arrays.asList("Alice", "street"));
    private static AnyContainsKeywordsPredicate predicate2 = new AnyContainsKeywordsPredicate(
            Collections.singletonList("gmail.com"));


    @Before
    public void setUp() throws Exception {
        set1.add(new Tag("friends"));
        set1.add(new Tag("family"));
        set2.add(new Tag("friends"));

    }

    @Test
    public void test_anyContainsKeywords_returnsTrue() {
        // One keyword
        assertTrue(predicate2.test(new PersonBuilder().withEmail("huehue@gmail.com").build()));

        // Multiple keywords
        assertTrue(predicate1.test(new PersonBuilder().withName("Alice").build()));

        // Only one matching sub-keyword
        assertTrue(predicate1.test(new PersonBuilder().withName("Alicey").build()));
    }

    @Test
    public void test_anyDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AnyContainsKeywordsPredicate predicate0 = new AnyContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate0.test(new PersonBuilder().withTags("cs2103").build()));

        // Non-matching keyword
        assertFalse(predicate2.test(new PersonBuilder().withEmail("haha@yahoo.com").build()));

        assertFalse(predicate1.test(new PersonBuilder().withName("Alic").withPhone("12345")
                .withEmail("alic@email.com").withAddress("Stree").withBirthday("01/01/1990")
                .withTags("cs2103").build()));
    }

    @Test
    public void equals() throws Exception {

        // same object -> returns true
        assertTrue(predicate1.equals(predicate1));

        // same values -> returns true
        AnyContainsKeywordsPredicate firstPredicateCopy = new AnyContainsKeywordsPredicate(
                Arrays.asList("Alice", "street"));
        assertTrue(predicate1.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(predicate1.equals(1));

        // null -> returns false
        assertFalse(predicate1.equals(null));

        // different person -> returns false
        assertFalse(predicate1.equals(predicate2));
    }

}
//@@author
