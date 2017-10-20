package seedu.address.model.group;

import static java.util.Objects.requireNonNull;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents a Group in an address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Group implements ReadOnlyGroup {

    private ObjectProperty<GroupName> groupName;
    /**
     *  A Group will have an empty persons list by default
     */
    private final UniquePersonList groupMembers = new UniquePersonList();

    /**
     * Every field must be present and not null.
     */
    public Group(GroupName name) {
        requireNonNull(name);
        this.groupName = new SimpleObjectProperty<>(name);
    }

    /**
     * Every field must be present and not null.
     */
    public Group(String name) throws IllegalValueException {
        requireNonNull(name);
        this.groupName = new SimpleObjectProperty<>(new GroupName(name));
    }
    /**
     * Creates a copy of the given ReadOnlyGroup.
     */
    public Group(ReadOnlyGroup source) {
        this(source.getName());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Group // instanceof handles nulls
                && this.groupName.toString().equals(((Group) other).groupName.toString())); // state check
    }

    @Override
    public int hashCode() {
        return groupName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return getAsText();
    }

    public void addMember(ReadOnlyPerson person) throws DuplicatePersonException {
        this.groupMembers.add(person);
    }

    public void deleteMember(ReadOnlyPerson person) throws PersonNotFoundException {
        this.groupMembers.remove(person);
    }

    @Override
    public ObjectProperty<GroupName> nameProperty() {
        return groupName;
    }

    @Override
    public GroupName getName() {
        return groupName.get();
    }

    public void setGroupName(GroupName name) {
        this.groupName.set(requireNonNull(name));
    }

    @Override
    public ObservableList<ReadOnlyPerson> getMembers() {
        return groupMembers.asObservableList();
    }

}
