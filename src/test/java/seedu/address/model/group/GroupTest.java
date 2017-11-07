package seedu.address.model.group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.UniquePersonList;

public class GroupTest {

    private static final String groupName = "Bamboo";
    private static final String differentGroupName = "AnotherGroup";
    private static Group group;

    static {
        try {
            group = new Group(groupName);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    public GroupTest() throws IllegalValueException {
    }


    @Test
    public void constructor() throws IllegalValueException {
        assertEquals(group.getName(), new GroupName("Bamboo"));
        assertEquals(group.getMembers(), new SimpleObjectProperty<>(new UniquePersonList()).get().toSet());
    }

    @Test
    public void equals() throws IllegalValueException {
        //same group -> returns True
        assertTrue(group.equals(group));

        //null pointer -> returns False
        assertFalse(group.equals(null));

        //different group
        assertFalse(group.equals(new Group(differentGroupName)));
    }
}
