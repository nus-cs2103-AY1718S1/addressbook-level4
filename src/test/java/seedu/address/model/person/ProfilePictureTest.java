package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//@@author pohjie
public class ProfilePictureTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private final String defaultPicPath = "/images/defaultProfilePic.png";

    @Test
    public void defaultConstructorIsWorking() {
        ProfilePicture profilePicture = new ProfilePicture();
        assertEquals(profilePicture.toString(), defaultPicPath);
    }

    @Test
    public void constructorWithParameterIsWorking() {
        try {
            ProfilePicture profilePicture = new ProfilePicture("/images/defaultProfilePic.png");
            assertEquals(profilePicture.toString(), defaultPicPath);
        } catch (NullPointerException e) {
            System.out.println("Null not accepted!");
        }
    }

    @Test
    public void constructorWithInvalidInput() throws Exception {
        thrown.expect(NullPointerException.class);
        new ProfilePicture(null);
    }

    @Test
    public void testEquals() {
        ProfilePicture profilePicture0 = new ProfilePicture();
        ProfilePicture profilePicture1 = new ProfilePicture();
        assertTrue(profilePicture0.equals(profilePicture1));

        ProfilePicture profilePicture2 = new ProfilePicture("/images/calendar.png");
        assertFalse(profilePicture0.equals(profilePicture2));
    }
}
