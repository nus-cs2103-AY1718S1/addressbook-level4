package seedu.address.model.person;

//@@author jeffreygohkw
import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} are favoured and has no private fields
 */
public class NameContainsFavouritePrivacyLevelPredicate implements Predicate<ReadOnlyPerson> {

    public NameContainsFavouritePrivacyLevelPredicate() {
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return (person.getFavourite() && !person.hasPrivateField());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsFavouritePrivacyLevelPredicate); // instanceof handles nulls
    }
}
