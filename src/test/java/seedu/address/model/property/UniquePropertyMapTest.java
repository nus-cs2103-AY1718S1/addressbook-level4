package seedu.address.model.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.property.exceptions.DuplicatePropertyException;
import seedu.address.model.property.exceptions.PropertyNotFoundException;

//@@author yunpengn
public class UniquePropertyMapTest {
    private static Set<Property> mySet;
    private static Property newProperty;
    private static Property existingProperty;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUp() throws Exception {
        PropertyManager.initializePropertyManager();
        mySet = new HashSet<>();
        mySet.add(new Property("a", "some address"));
        mySet.add(new Property("p", "12345678"));

        newProperty = new Property("e", "google@microsoft.com");
        existingProperty = new Property("a", "another address");
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

        assertEquals(mySet.size(), propertyMap.size());
    }

    @Test
    public void addProperty_checkCorrectness() throws Exception {
        UniquePropertyMap propertyMap = createSampleMap();
        int countBefore = propertyMap.size();
        propertyMap.add(newProperty);
        int countAfter = propertyMap.size();

        assertEquals(1, countAfter - countBefore);
    }

    @Test
    public void addProperty_existingProperty_throwException() throws Exception {
        thrown.expect(DuplicatePropertyException.class);

        UniquePropertyMap propertyMap = createSampleMap();
        propertyMap.add(existingProperty);
        assertEquals(mySet.size(), propertyMap.size());
    }

    @Test
    public void updateProperty_checkCorrectness() throws Exception {
        UniquePropertyMap propertyMap = createSampleMap();
        int countBefore = propertyMap.size();
        propertyMap.update(existingProperty);
        int countAfter = propertyMap.size();

        assertEquals(countBefore, countAfter);
    }

    @Test
    public void updateProperty_notFoundProperty_throwException() throws Exception {
        thrown.expect(PropertyNotFoundException.class);

        UniquePropertyMap propertyMap = createSampleMap();
        propertyMap.update(newProperty);
    }

    @Test
    public void addOrUpdateProperty_checkCorrectness() throws Exception {
        UniquePropertyMap propertyMap = createSampleMap();
        int count1 = propertyMap.size();
        propertyMap.addOrUpdate(existingProperty);
        int count2 = propertyMap.size();
        propertyMap.addOrUpdate(newProperty);
        int count3 = propertyMap.size();

        assertEquals(0, count2 - count1);
        assertEquals(1, count3 - count2);
    }

    @Test
    public void containsProperty_checkCorrectness() throws Exception {
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
    public void toSortedList_checkCorrectness() throws Exception {
        UniquePropertyMap propertyMap = createSampleMap();
        List list = propertyMap.toSortedList();
        assertEquals(new Property("a", "some address"), list.get(0));
        assertEquals(new Property("p", "12345678"), list.get(1));
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
        UniquePropertyMap propertyMap1 = createSampleMap();
        UniquePropertyMap propertyMap2 = createSampleMap();
        propertyMap1.mergeFrom(propertyMap2);

        assertEquals(mySet.size(), propertyMap1.toSet().size());
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
