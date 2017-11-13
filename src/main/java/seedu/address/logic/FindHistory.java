package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.LinkedList;
import java.util.List;

import seedu.address.model.person.ReadOnlyPerson;

//@@author karrui
/**
 * Stores the persons returned by FindCommand.
 */
public class FindHistory {
    private LinkedList<ReadOnlyPerson> userFindHistory;

    public FindHistory() {
        userFindHistory = new LinkedList<>();
    }

    /**
     * Adds {@code person} to the list of persons user found with FindCommand.
     */
    public void add(ReadOnlyPerson person) {
        requireNonNull(person);
        userFindHistory.add(person);
    }

    /**
     * Changes {@code person} to {@code newPerson}
     */
    public void set(ReadOnlyPerson person, ReadOnlyPerson newPerson) {
        if (userFindHistory.contains(person)) {
            userFindHistory.set(userFindHistory.indexOf(person), newPerson);
        }
    }

    /**
     * Removes {@code person} from userFindHistory
     */
    public void deletePerson(ReadOnlyPerson person) {
        userFindHistory.remove(person);
    }

    public void resetData(LinkedList<ReadOnlyPerson> newData) {
        this.userFindHistory = newData;
    }

    /**
     * Returns a defensive copy of {@code userInputHistory}.
     */
    public List<ReadOnlyPerson> getHistory() {
        return new LinkedList<>(userFindHistory);
    }

}
