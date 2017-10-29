package seedu.address.model.group;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.core.index.Index;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.List;
import java.util.Objects;

public class Group implements ReadOnlyGroup {

    private ObjectProperty<GroupName> groupName;
    private ObjectProperty<List<ReadOnlyPerson>> groupMembers;

    public Group(GroupName name, List<ReadOnlyPerson> members) {
        this.groupName = new SimpleObjectProperty<>(name);
        this.groupMembers = new SimpleObjectProperty<>(members);
    }

    public Group(ReadOnlyGroup group) {
        this(group.getGroupName(), group.getGroupMembers());
    }

    @Override
    public ObjectProperty<GroupName> nameProperty() {
        return groupName;
    }

    @Override
    public GroupName getGroupName() {
        return groupName.get();
    }

    @Override
    public ObjectProperty<List<ReadOnlyPerson>> membersProperty() {
        return groupMembers;
    }

    @Override
    public List<ReadOnlyPerson> getGroupMembers() {
        return groupMembers.get();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyGroup // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyGroup) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(groupName, groupMembers);
    }
}
