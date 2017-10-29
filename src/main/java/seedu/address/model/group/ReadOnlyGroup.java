package seedu.address.model.group;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A read-only immutable interface for a Group in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyGroup {
    ObjectProperty<GroupName> nameProperty();
    GroupName getGroupName();
    ObjectProperty<List<ReadOnlyPerson>> membersProperty();
    List<ReadOnlyPerson> getGroupMembers();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyGroup other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getGroupName().equals(this.getGroupName())); // state checks here onwards

    }
}
