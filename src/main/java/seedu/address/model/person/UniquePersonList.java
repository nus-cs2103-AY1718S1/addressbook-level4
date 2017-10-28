package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniquePersonList implements Iterable<Person> {

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyPerson> mappedList = EasyBind.map(internalList, (person) -> person);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyPerson toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(ReadOnlyPerson toAdd) throws DuplicatePersonException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(new Person(toAdd));
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if the replacement is equivalent to another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    public void setPerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.equals(editedPerson) && internalList.contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, new Person(editedPerson));
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyPerson toRemove) throws PersonNotFoundException {
        requireNonNull(toRemove);
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
        return personFoundAndDeleted;
    }

    public void setPersons(UniquePersonList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setPersons(List<? extends ReadOnlyPerson> persons) throws DuplicatePersonException {
        final UniquePersonList replacement = new UniquePersonList();
        for (final ReadOnlyPerson person : persons) {
            replacement.add(new Person(person));
        }
        setPersons(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyPerson> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    public void sort(String field) {
        switch (field) {
            case "name":
                Collections.sort(internalList, new Comparator<Person>() {
                    public int compare(Person one, Person other) {
                        String oneName = one.getName().toString().toLowerCase();
                        String otherName = other.getName().toString().toLowerCase();
                        return oneName.compareTo(otherName);
                    }
                });
                break;
            case "phone":
                Collections.sort(internalList, new Comparator<Person>() {
                    public int compare(Person one, Person other) {
                        return one.getPhone().toString().compareTo(other.getPhone().toString());
                    }
                });
                break;
            case "email":
                Collections.sort(internalList, new Comparator<Person>() {
                    public int compare(Person one, Person other) {
                        String oneEmail = one.getEmail().toString().toLowerCase();
                        String otherEmail = other.getEmail().toString().toLowerCase();
                        String noEmail = "no email";
                        if (oneEmail.equals(noEmail)) {
                            if (otherEmail.equals(noEmail)) {
                                return 0;
                            } else {
                                return 1;
                            }
                        } else if (otherEmail.equals(noEmail)) {
                            return -1;
                        } else {
                            return oneEmail.compareTo(otherEmail);
                        }
                    }
                });
                break;
            case "address":
                Collections.sort(internalList, new Comparator<Person>() {
                    public int compare(Person one, Person other) {
                        String oneAddress = one.getAddress().toString().toLowerCase();
                        String otherAddress = other.getAddress().toString().toLowerCase();
                        String noAddress = "no address";
                        if (oneAddress.equals(noAddress)) {
                            if (otherAddress.equals(noAddress)) {
                                return 0;
                            } else {
                                return 1;
                            }
                        } else if (otherAddress.equals(noAddress)) {
                            return -1;
                        } else {
                            return oneAddress.compareTo(otherAddress);
                        }
                    }
                });
                break;
            case "tag":
                Collections.sort(internalList, new Comparator<Person>() {
                    public int compare(Person one, Person other) {
                        Set<Tag> oneTags = one.getTags();
                        ArrayList<String> oneTagsString = new ArrayList<String>();
                        for (Tag tag : oneTags) {
                            oneTagsString.add(tag.toString().toLowerCase());
                        }
                        Collections.sort(oneTagsString);
                        Set<Tag> otherTags = other.getTags();
                        ArrayList<String> otherTagsString = new ArrayList<String>();
                        for (Tag tag : otherTags) {
                            otherTagsString.add(tag.toString().toLowerCase());
                        }
                        Collections.sort(otherTagsString);
                        for (int i = 0; i < Math.min(oneTagsString.size(), otherTagsString.size()); i++) {
                            if (!(oneTagsString.get(i).equals(otherTagsString.get(i)))) {
                                return oneTagsString.get(i).compareTo(otherTagsString.get(i));
                            }
                        }
                        return 0;
                    }
                });
                break;
            default:
                // case "meeting"
                Collections.sort(internalList, new Comparator<Person>() {
                    public int compare(Person one, Person other) {
                        Set<Meeting> oneMeetings = one.getMeetings();
                        ArrayList<String> oneMeetingsString = new ArrayList<String>();
                        for (Meeting meeting : oneMeetings) {
                            oneMeetingsString.add(meeting.toString().toLowerCase());
                        }
                        Collections.sort(oneMeetingsString);
                        Set<Meeting> otherMeetings = other.getMeetings();
                        ArrayList<String> otherMeetingsString = new ArrayList<String>();
                        for (Meeting meeting : otherMeetings) {
                            otherMeetingsString.add(meeting.toString().toLowerCase());
                        }
                        Collections.sort(otherMeetingsString);
                        for (int i = 0; i < Math.min(oneMeetingsString.size(), otherMeetingsString.size()); i++) {
                            if (!(oneMeetingsString.get(i).equals(otherMeetingsString.get(i)))) {
                                return oneMeetingsString.get(i).compareTo(otherMeetingsString.get(i));
                            }
                        }
                        return 0;
                    }
                });
        }
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && this.internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
