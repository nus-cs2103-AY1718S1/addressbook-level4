package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
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
    private ObjectProperty<UniquePersonList> groupMembers =
            new SimpleObjectProperty<>(new UniquePersonList());

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
    public Group(GroupName name, Set<Person> groupMembers) {
        requireAllNonNull(name, groupMembers);
        this.groupName = new SimpleObjectProperty<>(name);
        this.groupMembers = new SimpleObjectProperty<>(new UniquePersonList(groupMembers));
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
        this(source.getName(), source.getMembers());
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
        this.groupMembers.get().add(person);
    }

    public void deleteMember(ReadOnlyPerson person) throws PersonNotFoundException {
        this.groupMembers.get().remove(person);
    }

    @Override
    public ObjectProperty<GroupName> nameProperty() {
        return groupName;
    }

    @Override
    public GroupName getName() {
        return groupName.get();
    }

    @Override
    public ObjectProperty<UniquePersonList> groupMembersProperty() {
        return groupMembers;
    }

    public void setGroupName(GroupName name) {
        this.groupName.set(requireNonNull(name));
    }

    @Override
    public Set<Person> getMembers() {
        return groupMembers.get().toSet();
    }



}
