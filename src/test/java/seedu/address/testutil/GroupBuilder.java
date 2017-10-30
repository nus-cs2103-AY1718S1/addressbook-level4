package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.ReadOnlyGroup;


/**
 * A utility class to help with building Group objects.
 */
public class GroupBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline Group";

    private Group group;

    public GroupBuilder() {
        try {
            GroupName defaultName = new GroupName(DEFAULT_NAME);
            this.group = new Group(defaultName);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default group's values are invalid.");
        }
    }

    /**
     * Initializes the GroupBuilder with the data of {@code groupToCopy}.
     */
    public GroupBuilder(ReadOnlyGroup groupToCopy) {
        this.group = new Group(groupToCopy);
    }

    /**
     * Sets the {@code Name} of the {@code Group} that we are building.
     */
    public GroupBuilder withName(String name) {
        try {
            this.group.setGroupName(new GroupName(name));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    public Group build() {
        return this.group;
    }

}
