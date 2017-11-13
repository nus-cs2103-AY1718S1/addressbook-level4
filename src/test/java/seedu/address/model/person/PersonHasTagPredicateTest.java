package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonHasTagPredicateTest {

    @Test
    public void test_personHasTag_returnsTrue() {
        //person has only one tag
        PersonHasTagPredicate predicate = new PersonHasTagPredicate(Collections.singletonList("friends"));
        assertTrue(predicate.test(new PersonBuilder().build()));

        //person has multiple tags
        predicate = new PersonHasTagPredicate(Collections.singletonList("friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "colleagues").build()));

        //only one matching tag
        predicate = new PersonHasTagPredicate(Arrays.asList("friends", "families"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "colleagues").build()));

        //different case
        predicate = new PersonHasTagPredicate(Collections.singletonList("FRIENDS"));
        assertTrue(predicate.test(new PersonBuilder().withTags("fRiEnds").build()));
    }

    @Test
    public void test_personHasTag_returnsFalse() {
        //no keyword
        PersonHasTagPredicate predicate = new PersonHasTagPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().build()));

        //keyword does not match
        predicate = new PersonHasTagPredicate(Arrays.asList("families", "classmates"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends", "colleagues").build()));

        //keyword matches name, but not tag name
        predicate = new PersonHasTagPredicate(Collections.singletonList("Alice"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void equals() throws Exception {
        List<String> firstPredicateTagList = Collections.singletonList("first");
        List<String> secondPredicateTagList = Arrays.asList("first", "second");

        PersonHasTagPredicate firstPredicate = new PersonHasTagPredicate(firstPredicateTagList);
        PersonHasTagPredicate secondPredicate = new PersonHasTagPredicate(secondPredicateTagList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonHasTagPredicate firstPredicateCopy = new PersonHasTagPredicate(firstPredicateTagList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different tags -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }
}
