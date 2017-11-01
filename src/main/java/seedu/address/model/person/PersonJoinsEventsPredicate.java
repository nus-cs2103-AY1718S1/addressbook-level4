package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.model.event.ReadOnlyEvent;

// @@author HouDenghao
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Participation} matches the event given.
 */
public class PersonJoinsEventsPredicate implements Predicate<ReadOnlyPerson> {
    private final String keywords;

    public PersonJoinsEventsPredicate(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        Boolean isSelected = false;
        if (!person.getParticipation().isEmpty()) {
            for (ReadOnlyEvent event: person.getParticipation()) {
                if (!isSelected) {
                    isSelected = keywords.equals(event.getEventName().fullEventName);
                }
            }
        }
        return isSelected;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonJoinsEventsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonJoinsEventsPredicate) other).keywords)); // state check
    }
}
