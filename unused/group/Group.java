//@@author hxy0229
package seedu.address.model.group;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/** checkstyle comment */
public class Group {

    public static final String MESSAGE_GROUP_CONSTRAINTS = "Groups names should be alphanumeric";
    public static final String GROUP_VALIDATION_REGEX = "\\p{Alnum}+";

    private ObjectProperty<Name> name;

    private ObjectProperty<UniquePersonList> persons;

    public Group(Name name, Set<ReadOnlyPerson> persons) throws IllegalValueException {
        requireAllNonNull(name, persons);
        this.name = new SimpleObjectProperty<>(name);
        this.persons = new SimpleObjectProperty<>(new UniquePersonList());
        if (!this.getName().fullName.matches(GROUP_VALIDATION_REGEX)) {
            throw new IllegalValueException(MESSAGE_GROUP_CONSTRAINTS);
        }
    }

    public Group(Name name) throws IllegalValueException {
        requireAllNonNull(name);
        this.name = new SimpleObjectProperty<>(name);
        this.persons = new SimpleObjectProperty<>(new UniquePersonList());
        if (!this.getName().fullName.matches(GROUP_VALIDATION_REGEX)) {
            throw new IllegalValueException(MESSAGE_GROUP_CONSTRAINTS);
        }
    }

    public Group(Group group) throws IllegalValueException {
        this(group.getName(), group.getPersonList());
        if (!this.getName().fullName.matches(GROUP_VALIDATION_REGEX)) {
            throw new IllegalValueException(MESSAGE_GROUP_CONSTRAINTS);
        }
    }

    public ObjectProperty<Name> nameProperty() {
        return this.name;
    }

    public ObjectProperty<UniquePersonList> personProperty() {
        return this.persons;
    }

    public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        persons.get().add(person);
    }

    public Name getName() {
        return name.get();
    }

    public Set<ReadOnlyPerson> getPersonList() {
        return Collections.unmodifiableSet(persons.get().toSet());
    }

    public void setPersons(Set<ReadOnlyPerson> replacementPerson) {
        persons.set(new UniquePersonList(replacementPerson));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Group // instanceof handles nulls
                && this.name.get().fullName.equals(((Group) other).name.get().fullName)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, persons);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Persons: ");
        getPersonList().forEach(builder::append);
        return  builder.toString();
    }
}
//@@author
