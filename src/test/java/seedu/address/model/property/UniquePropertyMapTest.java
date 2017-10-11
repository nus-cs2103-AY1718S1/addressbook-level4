package seedu.address.model.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UniquePropertyMapTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createPropertyMap_nullInput_throwNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);

        UniquePropertyMap propertyMap = null;
        Set<Property> myMap = null;
        propertyMap = new UniquePropertyMap(myMap);
        assertNull(propertyMap);
    }

    @Test
    public void createPropertyMap_validButEmptyInput_successfullyCreated() {
        HashMap<String, Property> myMap = new HashMap<>();
        UniquePropertyMap propertyMap = new UniquePropertyMap(myMap);

        assertEquals(0, propertyMap.toSet().size());
    }

    @Test
    public void createPropertyMap_validNonEmptyInput_successfullyCreated() throws Exception {
        HashMap<String, Property> myMap = new HashMap<>();
        myMap.put("a", new Property("a", "some address"));
        myMap.put("p", new Property("p", "12345678"));
        UniquePropertyMap propertyMap = new UniquePropertyMap(myMap);

        assertEquals(2, propertyMap.toSet().size());
    }
}
