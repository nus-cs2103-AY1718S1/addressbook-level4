//@@author majunting
package seedu.address.model.person.predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class FavoritePersonsPredicateTest {
    private boolean Favorite = true;
    private boolean Unfavorite = false;
    @Test
    public void test_equal() {
        String firstPredicateKeyword = "favorite";
        String secondPredicateKeyword = "unfavorite";

        FavoritePersonsPredicate firstPredicate = new FavoritePersonsPredicate(firstPredicateKeyword);
        FavoritePersonsPredicate secondPredicate = new FavoritePersonsPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FavoritePersonsPredicate firstPredicateCopy = new FavoritePersonsPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_ableToFindPersons_returnsTrue() {
        // favorite_returnsTrue
        FavoritePersonsPredicate predicate = new FavoritePersonsPredicate("favorite");
        assertTrue(predicate.test(new PersonBuilder().withFavorite(Favorite).build()));

        //unfavorite_returnsTrue
        predicate = new FavoritePersonsPredicate("unfavorite");
        assertTrue(predicate.test(new PersonBuilder().withFavorite(Unfavorite).build()));
    }

    @Test
    public void test_unableToFindPerson_returnsFalse() {
        // favorite_returnsFalse
        FavoritePersonsPredicate predicate = new FavoritePersonsPredicate("unfavorite");
        assertFalse(predicate.test(new PersonBuilder().withFavorite(Favorite).build()));

        // unfavorite_returnFalse
        predicate = new FavoritePersonsPredicate("favorite");
        assertFalse(predicate.test(new PersonBuilder().withFavorite(Unfavorite).build()));
    }
}
