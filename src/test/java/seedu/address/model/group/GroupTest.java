package seedu.address.model.group;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.UniquePersonList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GroupTest {

    String groupName = "Bamboo";
    String differentGroupName = "AnotherGroup";
    Group group = new Group("Bamboo");

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
