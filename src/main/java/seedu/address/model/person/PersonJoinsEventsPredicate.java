package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.event.ReadOnlyEvent;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Participation} matches the event given.
 */
public class PersonJoinsEventsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public PersonJoinsEventsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        Boolean isSelected = false;
        if (!person.getParticipation().isEmpty()) {
            for (ReadOnlyEvent event: person.getParticipation()) {
                if (!isSelected) {
                    isSelected = keywords.stream().allMatch(keyword ->
                            StringUtil.containsWordIgnoreCase(event.getEventName().fullEventName, keyword));
                }
            }
        }
        return isSelected;
    }

}
