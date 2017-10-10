package seedu.address.model.person;

import static org.junit.Assert.assertEquals;

import java.util.regex.PatternSyntaxException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.exceptions.DuplicatePropertyException;

public class PropertyManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
}
