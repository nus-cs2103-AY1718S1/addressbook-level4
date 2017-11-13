package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<ReadOnlyPerson> PREDICATE_MATCHING_NO_PERSONS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<ReadOnlyPerson> pToDisplay) {
        Optional<Predicate<ReadOnlyPerson>> predicate =
                pToDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredPersonList(predicate.orElse(PREDICATE_MATCHING_NO_PERSONS));
        model.updateFilteredGroupList(new Predicate<Group>() {
            @Override
            public boolean test(Group people) {
                return true;
            }
        });
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, ReadOnlyPerson... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code ReadOnlyPerson} equals to {@code other}.
     */
    private static Predicate<ReadOnlyPerson> getPredicateMatching(ReadOnlyPerson other) {
        return person -> person.equals(other);
    }
}
