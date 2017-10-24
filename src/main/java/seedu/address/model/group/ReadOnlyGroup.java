package seedu.address.model.group;

import java.util.Set;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * A read-only immutable interface for a Group in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyGroup {

    ObjectProperty<GroupName> nameProperty();
    GroupName getName();
    ObjectProperty<UniquePersonList> groupMembersProperty();
    Set<Person> getMembers();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyGroup other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getMembers().equals(this.getMembers()));
    }

    /**
     * Formats the Group as text, showing group name.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Group Name: ")
                .append(getName());
        return builder.toString();
    }

}
