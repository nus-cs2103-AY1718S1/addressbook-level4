package seedu.address.model.person;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ProfilePictureTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void missingFileLocation() throws Exception {
        thrown.expect(NullPointerException.class);
        new ProfilePicture(null);
    }

}
