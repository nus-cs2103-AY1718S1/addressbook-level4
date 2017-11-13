package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;

import java.util.Collections;
import java.util.Objects;

import org.junit.BeforeClass;
import org.junit.Test;

import seedu.address.model.property.Address;
import seedu.address.model.property.Email;
import seedu.address.model.property.Name;
import seedu.address.model.property.Phone;
import seedu.address.model.property.PropertyManager;

//@@author yunpengn
public class PersonTest {
    private static Name name;
    private static Phone phone;
    private static Email email;
    private static Address address;

    @BeforeClass
    public static void setUp() throws Exception {
        PropertyManager.initializePropertyManager();

        name = new Name(VALID_NAME_AMY);
        phone = new Phone(VALID_PHONE_AMY);
        email = new Email(VALID_EMAIL_AMY);
        address = new Address(VALID_ADDRESS_AMY);
    }

    @Test
    public void createPerson_preDefinedFieldsPresent_checkCorrectness() throws Exception {
        Person person = new Person(name, phone, email, address, Collections.emptySet());
        assertNotNull(person);

        assertEquals(name, person.getName());
        assertEquals(phone, person.getPhone());
        assertEquals(email, person.getEmail());
        assertEquals(address, person.getAddress());
        assertEquals(0, person.getTags().size());
        assertEquals(4, person.getProperties().size());
        assertEquals(4, person.getSortedProperties().size());
    }

    @Test
    public void equal_twoSameStatePerson_checkCorrectness() throws Exception {
        Person person = new Person(name, phone, email, address, Collections.emptySet());
        Person another = new Person(name, phone, email, address, Collections.emptySet());
        assertEquals(person, another);

        Person copied = new Person(person);
        assertEquals(person, copied);
    }

    //@@author junyango
    @Test
    public void hashCode_checkCorrectness() {
        Person person = new Person(name, phone, email, address, Collections.emptySet());
        assertNotNull(person);

        assertEquals(Objects.hash(person.nameProperty(), person.phoneProperty(), person.emailProperty(),
                person.addressProperty(), person.tagProperty()), person.hashCode());
    }
}
