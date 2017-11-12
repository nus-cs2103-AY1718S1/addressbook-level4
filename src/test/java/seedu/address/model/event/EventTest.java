package seedu.address.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_EVENT1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import seedu.address.model.property.Address;
import seedu.address.model.property.DateTime;
import seedu.address.model.property.Name;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyManager;

//@@author junyango
public class EventTest {
    private static Name name;
    private static DateTime dateTime;
    private static Address address;
    private static Set<Property> properties;

    @BeforeClass
    public static void setUp() throws Exception {
        PropertyManager.initializePropertyManager();

        name = new Name(VALID_NAME_EVENT1);
        dateTime = new DateTime(VALID_DATE_EVENT1);
        address = new Address(VALID_ADDRESS_AMY);

        properties = new HashSet<>();
        properties.add(name);
        properties.add(dateTime);
        properties.add(address);
    }

    @Test
    public void createEvent_preDefinedFieldsPresent_checkCorrectness() throws Exception {
        Event event = new Event(name, dateTime, address, Collections.emptyList());
        assertNotNull(event);

        assertEquals(name, event.getName());
        assertEquals(dateTime, event.getTime());
        assertEquals(address, event.getAddress());
        assertEquals(0, event.getReminders().size());
        assertEquals(3, event.getProperties().size());
    }

    //@@author yunpengn
    @Test
    public void createEvent_viaSetProperties_checkCorrectness() throws Exception {
        Event event = new Event(properties, new ArrayList<>());

        assertEquals(name, event.getName());
        assertEquals(dateTime, event.getTime());
        assertEquals(address, event.getAddress());
        assertEquals(0, event.getReminders().size());
        assertEquals(3, event.getProperties().size());
    }
    //@@author

    //@@author junyango
    @Test
    public void equal_twoSameStateEvent_checkCorrectness() throws Exception {
        Event event = new Event(name, dateTime, address, new ArrayList<>());
        Event another = new Event(name, dateTime, address, new ArrayList<>());
        assertEquals(event, another);

        Event copied = new Event(event);
        assertEquals(event, copied);
    }

    //@@author yunpengn
    @Test
    public void toString_checkCorrectness() throws Exception {
        Event event = new Event(name, dateTime, address, new ArrayList<>());
        String expected =
                " Event: Mel Birthday |  Date/Time: 25 Dec, 2017 08:30 |  Address: Block 312, Amy Street 1";

        assertEquals(expected, event.toString());
        assertEquals(expected, event.getAsText());
    }
}
