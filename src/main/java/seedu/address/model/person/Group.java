package seedu.address.model.person;

import com.sun.xml.internal.xsom.impl.WildcardImpl;

/**
 * Class for group feature
 * A group is a field in the person object used to filter UI display
 * A Person can only be part of one group
 */
public class Group {

    public final String groupName;

    public Group (Group group) {
        this.groupName = group.getGroupName();
    }

    public Group (String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName () {
        return groupName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Group
                && this.groupName.equals(((Group) other).groupName));
    }
}
