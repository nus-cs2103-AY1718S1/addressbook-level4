package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.UUID;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author nicholaschuayunzhi
public class AvatarTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void validFilePath_for_readAndCreateAvatar() {

        try {
            Avatar png = Avatar.readAndCreateAvatar("src/test/data/images/avatars/test.png");
            Avatar jpg = Avatar.readAndCreateAvatar("src/test/data/images/avatars/test.jpg");
            Avatar nullPath = Avatar.readAndCreateAvatar(null);

            assertEquals("src/test/data/images/avatars/test.png", png.getOriginalFilePath());
            assertEquals("src/test/data/images/avatars/test.jpg", jpg.getOriginalFilePath());
            assertNotEquals(UUID.randomUUID().toString() + ".png", png.value);
            assertNotEquals(UUID.randomUUID().toString() + ".jpg", jpg.value);
            assertEquals(new Avatar(png.value), png);
            assertEquals(new Avatar(jpg.value), jpg);
            assertEquals(new Avatar(""), nullPath);


        } catch (IllegalValueException e) {
            assert false;
        }
    }

    @Test
    public void toStringTest() {
        Avatar stub = new Avatar("stub");
        assertEquals("stub", stub.value);

        Avatar stub2 = new Avatar("stub2");
        assertEquals("stub2", stub2.value);
    }

    @Test
    public void hashCodeTest() {
        Avatar stub = new Avatar("stub");
        assertEquals("stub".hashCode(), stub.hashCode());

        Avatar stub2 = new Avatar("stub2");
        assertEquals("stub2".hashCode(), stub2.hashCode());
    }

    @Test
    public void invalidPngFilePath_for_readAndCreateAvatar()  throws IllegalValueException {
        exception.expect(IllegalValueException.class);
        Avatar.readAndCreateAvatar("src/test/data/images/avatars/fake.png");
    }

    @Test
    public void invalidJpgFilePath_for_readAndCreateAvatar()  throws IllegalValueException {
        exception.expect(IllegalValueException.class);
        Avatar.readAndCreateAvatar("src/test/data/images/avatars/fake.jpg");
    }

    @Test
    public void notImage_for_readAndCreateAvatar()  throws IllegalValueException {
        exception.expect(IllegalValueException.class);
        Avatar.readAndCreateAvatar("src/test/data/images/avatars/fake");
    }

}
