package seedu.address.model.person;

/**
 * Class for group feature
 * A group is a field in the person object used to filter UI display
 * A Person can only be part of one group
 */
public class Group {

    private String groupName;
    private String groupComment;

    public Group (String groupName, String groupComment) {
        this.groupName = groupName;
        this.groupComment = groupComment;
    }

    public Group (String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName () {
        return groupName;
    }

    public String getgroupComment () {
        return groupComment;
    }

    public void setGroupName (String groupName) {
        this.groupName = groupName;
    }

    public void setgroupComment (String groupComment) {
        this.groupComment = groupComment;
    }
}
