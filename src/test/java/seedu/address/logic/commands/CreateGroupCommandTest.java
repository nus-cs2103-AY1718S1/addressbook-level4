package seedu.address.logic.commands;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.GroupName;

//@@author eldonng
public class CreateGroupCommandTest {

    @Test
    public void equals() throws IllegalValueException {

        GroupName groupName1 = new GroupName("Group 1");
        GroupName groupName2 = new GroupName("Group 2");
        List<Index> groupList = new ArrayList<>();
        CreateGroupCommand addGroup1Command = new CreateGroupCommand(groupName1, groupList);
        CreateGroupCommand addGroup2Command = new CreateGroupCommand(groupName2, groupList);

        // same object -> returns true
        assertTrue(addGroup1Command.equals(addGroup1Command));

        // same values -> returns true
        CreateGroupCommand addGroup1CommandCopy = new CreateGroupCommand(groupName1, groupList);
        assertTrue(addGroup1Command.equals(addGroup1CommandCopy));

        // different types -> returns false
        assertFalse(addGroup1Command.equals(1));

        // null -> returns false
        assertFalse(addGroup1Command.equals(null));

        // different person -> returns false
        assertFalse(addGroup1Command.equals(addGroup2Command));
    }
}
