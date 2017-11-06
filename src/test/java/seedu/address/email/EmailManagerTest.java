package seedu.address.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.email.exceptions.LoginFailedException;

public class EmailManagerTest {
    private Email emailManager = new EmailManager();
    private String correctEmail = "cs2103testacc@gmail.com";
    private String correctPass = "testpass";
    private String wrongEmail = "phungtuanhoang1996@gmail."
            +
            "com";
    private String wrongPass = "thisisnotmypassword";

    @Test
    public void testValidLogin() throws LoginFailedException {
        emailManager.login(correctEmail, correctPass);

        assertEquals(correctEmail, emailManager.getEmail());
        assertEquals(emailManager.isLoggedIn(), true);
    }

    @Test(expected = LoginFailedException.class)
    public void testInvalidLogin() throws LoginFailedException {
        emailManager.login(wrongEmail, wrongPass);

        assertEquals(null, emailManager.getEmail());
        assertFalse(emailManager.isLoggedIn());
    }
}
