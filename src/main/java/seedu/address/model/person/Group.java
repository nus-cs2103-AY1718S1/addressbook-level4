package seedu.address.model.person;

/**
 * Class for group feature
 * A group is a field in the person object used to filter UI display
 * A Person can only be part of one group
 */
public class Group {

    public final String groupName;

    //Must be a single word without spaces
    public static final String GROUP_VALIDATION_REGEX = "[\\p{Alpha}]*";

    public Group (Group group) {
        this.groupName = group.getGroupName();
    }

    public Group (String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName () {
        return groupName;
    }

    /**
     * Determines if groupName is valid
     */
    public static boolean isValidGroup (String groupName) {
        return groupName.matches(GROUP_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Group
                && this.groupName.equals(((Group) other).groupName));
    }
}
