package seedu.address.model.person;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PropertyManagerTest {
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
}
