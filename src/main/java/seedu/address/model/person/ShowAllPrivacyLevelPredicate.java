package seedu.address.model.person;
//@@author jeffreygohkw

import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} are favoured after taking into account privacy level.
 */
public class ShowAllPrivacyLevelPredicate implements Predicate<ReadOnlyPerson> {

    public ShowAllPrivacyLevelPredicate() {
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return !person.hasPrivateField();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowAllPrivacyLevelPredicate); // instanceof handles nulls
    }

}
