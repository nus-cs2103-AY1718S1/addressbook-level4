package seedu.address.model.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UniquePropertyMapTest {
    private static Set<Property> mySet;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUp() throws Exception {
        PropertyManager.initializePropertyManager();
        mySet = new HashSet<>();
        mySet.add(new Property("a", "some address"));
        mySet.add(new Property("p", "12345678"));
    }

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
        UniquePropertyMap propertyMap = createSampleMap();

        assertEquals(2, propertyMap.toSet().size());
    }

    @Test
    public void addProperty_checkCorrectness() throws Exception {
        Property newProperty = new Property("e", "google@microsoft.com");
        UniquePropertyMap propertyMap = createSampleMap();
        propertyMap.add(newProperty);

        assertEquals(3, propertyMap.toSet().size());
    }

    @Test
    public void containsProperty_checkCorrectness() throws Exception {
        Property newProperty = new Property("e", "google@microsoft.com");
        UniquePropertyMap propertyMap = createSampleMap();
        propertyMap.add(newProperty);

        assertTrue(propertyMap.containsProperty("e"));
        assertTrue(propertyMap.containsProperty(newProperty));
    }

    @Test
    public void toSet_checkCorrectness() throws Exception {
        UniquePropertyMap propertyMap = createSampleMap();
        assertEquals(mySet, propertyMap.toSet());
    }

    @Test
    public void setProperties_validButEmptyInput_successfullySet() throws Exception {
        Set<Property> myNewSet = new HashSet<>();
        UniquePropertyMap propertyMap = createSampleMap();
        propertyMap.setProperties(myNewSet);

        assertEquals(0, propertyMap.toSet().size());
    }

    @Test
    public void mergeFrom_samePropertyMap_notChanged() throws Exception {
        Set<Property> myNewSet = new HashSet<>();
        UniquePropertyMap propertyMap1 = createSampleMap();
        UniquePropertyMap propertyMap2 = createSampleMap();
        propertyMap1.mergeFrom(propertyMap2);

        assertEquals(2, propertyMap1.toSet().size());
    }

    @Test
    public void equal_samePropertyMap_returnTrue() throws Exception {
        UniquePropertyMap propertyMap1 = createSampleMap();
        UniquePropertyMap propertyMap2 = createSampleMap();

        assertEquals(propertyMap1, propertyMap2);
        assertTrue(propertyMap1.equalsOrderInsensitive(propertyMap2));
    }

    /**
     * Util for creating a sample {@link UniquePropertyMap}.
     */
    private UniquePropertyMap createSampleMap() throws Exception {
        return new UniquePropertyMap(mySet);
    }
}
