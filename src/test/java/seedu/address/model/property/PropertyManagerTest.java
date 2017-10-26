package seedu.address.model.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.property.PropertyManager.DEFAULT_MESSAGE;
import static seedu.address.model.property.PropertyManager.DEFAULT_REGEX;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.regex.PatternSyntaxException;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.property.exceptions.DuplicatePropertyException;

public class PropertyManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        PropertyManager.initializePropertyManager();
    }

    @Test
    public void preLoadedProperty_checkInitializationSuccessful() {
        assertEquals(5, PropertyManager.getAllShortNames().size());

        assertEquals("Name", PropertyManager.getPropertyFullName("n"));
        assertEquals("Email", PropertyManager.getPropertyFullName("e"));
        assertEquals("Phone", PropertyManager.getPropertyFullName("p"));
        assertEquals("Address", PropertyManager.getPropertyFullName("a"));
        assertEquals("DateTime", PropertyManager.getPropertyFullName("dt"));

        assertEquals(String.format(DEFAULT_MESSAGE, "Address"), PropertyManager.getPropertyConstraintMessage("a"));
        assertEquals(DEFAULT_REGEX, PropertyManager.getPropertyValidationRegex("a"));

        assertTrue(PropertyManager.containsShortName("n"));
        assertTrue(PropertyManager.containsShortName("e"));
        assertTrue(PropertyManager.containsShortName("p"));
        assertTrue(PropertyManager.containsShortName("a"));
        assertTrue(PropertyManager.containsShortName("dt"));
    }

    @Test
    public void preLoadedProperty_checkCount() throws Exception {
        int numPreLoadedProperties = testPrivateFieldsCount("propertyFullNames");

        assertEquals(numPreLoadedProperties, testPrivateFieldsCount("propertyConstraintMessages"));
        assertEquals(numPreLoadedProperties, testPrivateFieldsCount("propertyValidationRegex"));
    }

    @Test
    public void addProperty_successfullyAdd() throws Exception {
        String shortName = "b";
        String fullName = "birthday";
        String message = "Birthday must be a valid date format in dd/mm/yyyy, dd-mm-yyyy or dd.mm.yyyy";
        String regex = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)"
                + "(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\"
                + "3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|"
                + "(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)"
                + "(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

        PropertyManager.addNewProperty(shortName, fullName, message, regex);

        assertEquals(fullName, PropertyManager.getPropertyFullName(shortName));
        assertEquals(message, PropertyManager.getPropertyConstraintMessage(shortName));
        assertEquals(regex, PropertyManager.getPropertyValidationRegex(shortName));
    }

    @Test
    public void addProperty_invalidRegex_error() throws Exception {
        String shortName = "d";
        String fullName = "description";
        String message = "Description can be any string, but cannot be blank";
        String regex = "*asf";

        thrown.expect(PatternSyntaxException.class);

        PropertyManager.addNewProperty(shortName, fullName, message, regex);
    }

    @Test
    public void addProperty_duplicateShortName_error() throws Exception {
        // Duplicate because the shortName is the same as the pre-loaded property "address".
        String shortName = "a";
        String fullName = "mailing address";
        String message = "Description can be any string, but cannot be blank";
        String regex = "[^\\s].*";

        thrown.expect(DuplicatePropertyException.class);

        PropertyManager.addNewProperty(shortName, fullName, message, regex);
    }

    @Test
    public void clearAllProperties_success_checkCorrectness() {
        PropertyManager.clearAllProperties();
        assertEquals(0, PropertyManager.getAllShortNames().size());
        PropertyManager.initializePropertyManager();
    }

    /**
     * Uses reflection to get the size of private static {@link HashMap}s.
     *
     * @param fieldName is the name of the private field in String format.
     * @return the number of items in the {@link HashMap}
     */
    @SuppressWarnings(value = "unchecked")
    private int testPrivateFieldsCount(String fieldName) {
        HashMap<String, String> variable = null;
        try {
            Field field = PropertyManager.class.getDeclaredField(fieldName);
            field.setAccessible(true);

            // We use an SuppressWarning annotation to avoid warning here, because this is
            // in the test environment anyway.
            variable = (HashMap<String, String>) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            assert(false);
        }

        return variable.size();
    }
}
