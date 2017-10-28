package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SchEmailTest {

    @Test
    public void isValidSchEmail() {
        // blank school email
        assertFalse(SchEmail.isValidSchEmail("")); // empty string
        assertFalse(SchEmail.isValidSchEmail(" ")); // spaces only

        // missing parts
        assertFalse(SchEmail.isValidSchEmail("@example.com")); // missing local part
        assertFalse(SchEmail.isValidSchEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(SchEmail.isValidSchEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(SchEmail.isValidSchEmail("-@example.com")); // invalid local part
        assertFalse(SchEmail.isValidSchEmail("peterjack@-")); // invalid domain name
        assertFalse(SchEmail.isValidSchEmail("peter jack@example.com")); // spaces in local part
        assertFalse(SchEmail.isValidSchEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(SchEmail.isValidSchEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(SchEmail.isValidSchEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(SchEmail.isValidSchEmail("peterjack@example@com")); // '@' symbol in domain name

        // valid school email
        assertTrue(SchEmail.isValidSchEmail("PeterJack_1190@example.com"));
        assertTrue(SchEmail.isValidSchEmail("a@b"));  // minimal
        assertTrue(SchEmail.isValidSchEmail("test@localhost"));   // alphabets only
        assertTrue(SchEmail.isValidSchEmail("123@145"));  // numeric local part and domain name
        assertTrue(SchEmail.isValidSchEmail("a1@example1.com"));  // mixture of alphanumeric and dot characters
        assertTrue(SchEmail.isValidSchEmail("_user_@_e_x_a_m_p_l_e_.com_"));    // underscores
        assertTrue(SchEmail.isValidSchEmail("peter_jack@very_very_very_long_example.com"));   // long domain name
        assertTrue(SchEmail.isValidSchEmail("if.you.dream.it_you.can.do.it@example.com"));    // long local part

        // school email not filled in
        assertTrue(SchEmail.isValidSchEmail(SchEmail.SCH_EMAIL_TEMPORARY));
    }
}
