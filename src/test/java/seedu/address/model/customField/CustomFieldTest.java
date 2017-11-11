package seedu.address.model.customField;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author LuLechuan
public class CustomFieldTest {

    @Test
    public void isValidCustomField() {
        // invalid addresses
        assertFalse(CustomField.isValidCustomField("")); // empty string
        assertFalse(CustomField.isValidCustomField(" ")); // spaces only

        // valid addresses
        assertTrue(CustomField.isValidCustomField("NickName"));
    }
}
