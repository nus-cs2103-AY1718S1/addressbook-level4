package seedu.address.model.group;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.core.index.Index;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.List;

public class Group implements ReadOnlyGroup {

    private ObjectProperty<GroupName> groupName;
    private ObjectProperty<List<ReadOnlyPerson>> groupMembers;

    public Group(GroupName name, List<ReadOnlyPerson> members) {
        this.groupName = new SimpleObjectProperty<>(name);
        this.groupMembers = new SimpleObjectProperty<>(members);
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
}
