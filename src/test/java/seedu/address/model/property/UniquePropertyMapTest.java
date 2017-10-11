package seedu.address.model.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.HashSet;
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
    public void createPropertyMap_validButEmptyInput_successfullyCreated() throws Exception {
        Set<Property> mySet = new HashSet<>();
        UniquePropertyMap propertyMap = new UniquePropertyMap(mySet);

        assertEquals(0, propertyMap.toSet().size());
    }

    @Test
    public void createPropertyMap_validNonEmptyInput_successfullyCreated() throws Exception {
        Set<Property> mySet = new HashSet<>();
        mySet.add(new Property("a", "some address"));
        mySet.add(new Property("p", "12345678"));
        UniquePropertyMap propertyMap = new UniquePropertyMap(mySet);

        assertEquals(2, propertyMap.toSet().size());
    }
}
