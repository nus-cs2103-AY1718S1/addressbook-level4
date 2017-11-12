package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;

//@@author yunpengn
public class AvatarTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void create_validFile_checkCorrectness() throws Exception {
        String path = FileUtil.getPath("./src/test/resources/SampleAvatar.jpg");
        File file = new File(path);

        Avatar avatar = new Avatar(path);
        assertEquals(path, avatar.getPath());
        assertEquals(file.toURI().toString(), avatar.getUri());
    }

    @Test
    public void create_invalidName_expectException() throws Exception {
        thrown.expect(IllegalValueException.class);
        Avatar avatar = new Avatar("invalid*.png");
        assertNull(avatar);
    }

    @Test
    public void create_invalidNameSeparator_expectException() throws Exception {
        thrown.expect(IllegalValueException.class);
        Avatar avatar = new Avatar("folder\\folder/invalid*.png");
        assertNull(avatar);
    }

    @Test
    public void create_fileNotExist_expectException() throws Exception {
        String path = FileUtil.getPath("./src/test/resources/SampleAvatar2.jpg");
        thrown.expect(IllegalValueException.class);
        Avatar avatar = new Avatar(path);
        assertNull(avatar);
    }

    @Test
    public void create_fileNotImage_expectException() throws Exception {
        String path = FileUtil.getPath("./src/test/resources/SampleNotImage.txt");
        thrown.expect(IllegalValueException.class);
        Avatar avatar = new Avatar(path);
        assertNull(avatar);
    }

    @Test
    public void equals_twoSameAvatar_checkCorrectness() throws Exception {
        String path = FileUtil.getPath("./src/test/resources/SampleAvatar.jpg");
        Avatar avatar1 = new Avatar(path);
        Avatar avatar2 = new Avatar(path);
        assertEquals(avatar1, avatar2);
    }

    @Test
    public void hashCode_checkCorrectness() throws Exception {
        String path = FileUtil.getPath("./src/test/resources/SampleAvatar.jpg");
        File file = new File(path);

        Avatar avatar = new Avatar(path);
        assertEquals(file.toURI().toString().hashCode(), avatar.hashCode());
    }

    @Test
    public void toString_checkCorrectness() throws Exception {
        String path = FileUtil.getPath("./src/test/resources/SampleAvatar.jpg");
        File file = new File(path);

        Avatar avatar = new Avatar(path);
        assertEquals("Avatar from " + file.toURI().toString(), avatar.toString());
    }
}
