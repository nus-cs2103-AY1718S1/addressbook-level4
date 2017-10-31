//@@author sebtsh
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CompanyTest {

    @Test
    public void isValidCompany() {
        // invalid companies
        assertFalse(Company.isValidCompany("")); // empty string
        assertFalse(Company.isValidCompany(" ")); // spaces only

        // valid companies
        assertTrue(Company.isValidCompany("Microsoft"));
        assertTrue(Company.isValidCompany("-")); // one character
        // long company
        assertTrue(Company.isValidCompany("Mongolian Tribal Software Engineering Line Dance Corporation Limited"));
    }
}
