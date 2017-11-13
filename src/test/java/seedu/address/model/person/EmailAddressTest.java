package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class EmailAddressTest {

    @Test
    public void isValidEmail() {
        // blank email
        assertFalse(EmailAddress.isValidEmailAddress("")); // empty string
        assertFalse(EmailAddress.isValidEmailAddress(" ")); // spaces only

        // missing parts
        assertFalse(EmailAddress.isValidEmailAddress("@example.com")); // missing local part
        assertFalse(EmailAddress.isValidEmailAddress("peterjackexample.com")); // missing '@' symbol
        assertFalse(EmailAddress.isValidEmailAddress("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(EmailAddress.isValidEmailAddress("-@example.com")); // invalid local part
        assertFalse(EmailAddress.isValidEmailAddress("peterjack@-")); // invalid domain name
        assertFalse(EmailAddress.isValidEmailAddress("peter jack@example.com")); // spaces in local part
        assertFalse(EmailAddress.isValidEmailAddress("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(EmailAddress.isValidEmailAddress("peterjack@@example.com")); // double '@' symbol
        assertFalse(EmailAddress.isValidEmailAddress("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(EmailAddress.isValidEmailAddress("peterjack@example@com")); // '@' symbol in domain name

        // valid email
        assertTrue(EmailAddress.isValidEmailAddress("PeterJack_1190@example.com"));
        assertTrue(EmailAddress.isValidEmailAddress("a@b"));  // minimal
        assertTrue(EmailAddress.isValidEmailAddress("test@localhost"));   // alphabets only
        assertTrue(EmailAddress.isValidEmailAddress("123@145"));  // numeric local part and domain name
        assertTrue(EmailAddress.isValidEmailAddress(
                "a1@example1.com"));  // mixture of alphanumeric and dot characters
        assertTrue(EmailAddress.isValidEmailAddress("_user_@_e_x_a_m_p_l_e_.com_"));    // underscores
        assertTrue(EmailAddress.isValidEmailAddress(
                "peter_jack@very_very_very_long_example.com"));   // long domain name
        assertTrue(EmailAddress.isValidEmailAddress("if.you.dream.it_you.can.do.it@example.com"));    // long local part
    }

    @Test
    public void emailHashCode() {
        try {
            EmailAddress emailAddress1 = new EmailAddress("PeterJack_1190@example.com");
            EmailAddress emailAddress2 = new EmailAddress("PeterJack_2222@example.com");

            //hashcode matches for same email --> return true
            assertTrue(emailAddress1.hashCode() == emailAddress1.hashCode());

            //hashcode don't match for different email --> return false
            assertFalse(emailAddress1.hashCode() == emailAddress2.hashCode());

        } catch (IllegalValueException e) {
            assert false : "shouldn't hit this case at all";
        }
    }
}
