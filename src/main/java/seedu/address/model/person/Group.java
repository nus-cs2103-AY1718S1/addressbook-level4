package seedu.address.model.person;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Class for group feature
 * A group is a field in the person object used to filter UI display
 * A Person can only be part of one group
 */
public class Group {

    private String groupName;
    private String groupType;

    public Group (String groupName, String groupType) {
        this.groupName = groupName;
        this.groupType = groupType;
    }

    public String getGroupName () {
        return groupName;
    }

    public String getGroupType () {
        return groupType;
    }

    public void setGroupName (String groupName) {
        this.groupName = groupName;
    }

    public void setGroupType (String groupType) {
        this.groupType = groupType;
    }
}
