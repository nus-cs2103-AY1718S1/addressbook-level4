package seedu.address.model.customField;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author LuLechuan
public class CustomFieldTest {

    @Test
    public void isValidCustomField() {
        // valid custom field
        assertTrue(CustomField.isValidCustomField("NickName"));
    }

    @Test
    public void isInvalidCustomField() {
        // invalid custom fields
        assertFalse(CustomField.isValidCustomField("")); // empty string
        assertFalse(CustomField.isValidCustomField(" ")); // spaces only
    }
}
