package seedu.address.model.group;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A read-only immutable interface for a Group in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyGroup {

    ObjectProperty<GroupName> nameProperty();
    GroupName getName();
    ObservableList<ReadOnlyPerson> getMembers();

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
