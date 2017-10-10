package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class PropertyTest {

    @Test
    public void createProperty_preLoadedProperty_successfulCreation() throws Exception {
        String value = "12345678";
        Property newProperty = new Property("p", value);

        assertEquals("p", newProperty.getShortName());
        assertEquals("phone", newProperty.getFullName());
        assertEquals(value, newProperty.getValue());
    }

    @Test
    public void createProperty_preLoadedProperty_invalidValue() {
        Property newProperty = null;
        String value = "12";
        String expectedMessage = "Phone numbers can only contain numbers, and should be at least 3 digits long";

        try {
            newProperty = new Property("p", value);
        } catch (IllegalValueException ive) {
            assertEquals(expectedMessage, ive.getMessage());
        }

        assertNull(newProperty);
    }

    @Test
    public void equalProperty_sameKeyAndValue_successfulCompare() throws Exception {
        Property propertyA = new Property("a", "This is my address");
        Property propertyB = new Property("a", "This is my address");

        assertEquals(propertyA, propertyB);
    }
}
