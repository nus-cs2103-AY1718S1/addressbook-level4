package seedu.address.model.meeting;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;

//@@author LimYangSheng
/**
 * Tests {@code ReadOnlyPerson} in list matches that in meeting list.
 */
public class MeetingContainPersonPredicate implements Predicate<Meeting> {
    private final List<ReadOnlyPerson> personList;

    public MeetingContainPersonPredicate(List<ReadOnlyPerson> personList) {
        this.personList = personList;
    }

    @Override
    public boolean test(Meeting meeting) {
        for (ReadOnlyPerson person : personList) {
            if (meeting.getPerson().equals(person)) {
                return true;
            }
        }

        return false;
    }
}
//@@author
