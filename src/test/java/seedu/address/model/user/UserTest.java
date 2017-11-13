package seedu.address.model.user;

//@@author quanle1994

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test User
 */
public class UserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_compareUser() {
        User user = new User();
        user.setUserId("abc");
        user.setPassword("abc");
        assertTrue(user.sameAs(new User("abc", "abc", "abc")));
        assertTrue(user.equals(new User("abc", "", "")));
    }
}
