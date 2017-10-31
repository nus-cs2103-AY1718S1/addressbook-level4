package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import guitests.AddressBookGuiTest;
import javafx.scene.image.Image;
import seedu.address.commons.util.AppUtil;

//@@author nicholaschuayunzhi
public class ImageStorageTest extends AddressBookGuiTest {

    private static final String PNG_STUB = "png";
    private static final String JPG_STUB = "jpg";


    @Before
    public void createTestPng() {
        assert ImageStorage.saveAvatar("src/test/data/images/avatars/test.png", "test.png");
        assert ImageStorage.saveAvatar("src/test/data/images/avatars/test.jpg", "test.jpg");
    }


    @Test
    public void getAvatarTest() throws FileNotFoundException {

        Image image1 = ImageStorage.getAvatar("test.png");
        Image image2 = new Image(new FileInputStream(new File("src/test/data/images/avatars/test.png")));

        assertImageAreEqual(image1, image2);

        Image image3 = ImageStorage.getAvatar("doestNotExist.png");
        Image image4 = AppUtil.getImage("/images/avatars/default.png");

        assertImageAreEqual(image3, image4);
    }

    @Test
    public void saveAvatarTest() {
        assertFalse(ImageStorage.saveAvatar("does_not_exist_file_path", "fakeimage"));
        assertFalse(ImageStorage.saveAvatar("does_not_exist_file_path.png", "fakeimage"));
    }

    @Test
    public void isValidImagePathTest() {

        assertTrue(ImageStorage.isValidImagePath("src/test/data/images/avatars/test.png"));
        assertTrue(ImageStorage.isValidImagePath("src/test/data/images/avatars/test.jpg"));

        //not jpg or png
        assertFalse(ImageStorage.isValidImagePath("src/test/data/images/avatars/test.pg"));
        assertFalse(ImageStorage.isValidImagePath("src/test/data/images/avatars/test.ng"));
        //gets caught in not jpg or png
        assertFalse(ImageStorage.isValidImagePath("src/test/data/images/avatars/"));

        //does not exist
        assertFalse(ImageStorage.isValidImagePath("src/test/data/images/avatars/fake.png"));
        assertFalse(ImageStorage.isValidImagePath("src/test/data/images/avatars/fake.jpg"));
    }

    @Test
    public void isJpgOrPngTest() {
        assertTrue(ImageStorage.isJpgOrPng("test.png"));
        assertTrue(ImageStorage.isJpgOrPng("test.jpg"));
        assertTrue(ImageStorage.isJpgOrPng(".png"));
        assertTrue(ImageStorage.isJpgOrPng(".jpg"));


        assertFalse(ImageStorage.isJpgOrPng("test.ng"));
        assertFalse(ImageStorage.isJpgOrPng("test.jng"));
        assertFalse(ImageStorage.isJpgOrPng("testpng"));
        assertFalse(ImageStorage.isJpgOrPng("testjng"));
        assertFalse(ImageStorage.isJpgOrPng(".pngtest"));
    }


    @Test
    public void getFormat() {
        assertEquals(PNG_STUB, ImageStorage.getFormat("test.png"));
        assertEquals(JPG_STUB, ImageStorage.getFormat("test.jpg"));

        assertEquals(PNG_STUB, ImageStorage.getFormat(".png"));
        assertEquals(JPG_STUB, ImageStorage.getFormat(".jpg"));

        //assumption
        assertEquals(PNG_STUB, ImageStorage.getFormat("fake.gif"));
        assertEquals(PNG_STUB, ImageStorage.getFormat("fake"));
    }

    /**
     * asserts that both image are equal by checking at each rendered pixel
     */

    public static void assertImageAreEqual(Image image1, Image image2) {

        assertEquals(image1.getHeight(), image2.getHeight(), 0);
        assertEquals(image1.getWidth(), image2.getWidth(), 0);


        for (int i = 0; i < image1.getWidth(); i++) {
            for (int j = 0; j < image1.getHeight(); j++) {
                int image1Argb = image1.getPixelReader().getArgb(i, j);
                int image2Argb  = image2.getPixelReader().getArgb(i, j);
                if (image1Argb != image2Argb) {
                    assert false;
                }
            }
        }
    }

}
