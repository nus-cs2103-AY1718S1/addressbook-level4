//@@author wishingmaid
package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.storage.PhotoStorage.WRITE_FAILURE_MESSAGE;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import seedu.address.commons.util.ExtensionCheckerUtil;

public class PhotoStorageTest {
    private String[] allowedExt =  new String[]{"jpg", "png", "JPEG"};
    @Test
    public void writeSuccess() throws IOException {
        String imageSource = "src/test/resources/images/noPhoto.png";
        PhotoStorage testStorage = new PhotoStorage(imageSource);
        String newFilePath = testStorage.setNewFilePath();
        File imageFile = new File(newFilePath);
        assertTrue(imageFile.exists());
    }
    @Test
    public void writeFailureWithNonImageType() {
        String userFilePath = "C:/Users/pigir/Desktop/addPhoto.txt";
        try {
            PhotoStorage storage = new PhotoStorage(userFilePath);
            storage.setNewFilePath();
        } catch (IOException e) {
            assertEquals(e.getMessage(), WRITE_FAILURE_MESSAGE);
        }
    }
    @Test
    public void writeFailureWithImageType() {
        String userFilePath = "C:/Users/pigir/Desktop/sfsf.gif";
        try {
            PhotoStorage storage = new PhotoStorage(userFilePath);
            storage.setNewFilePath();
        } catch (IOException e) {
            assertEquals(e.getMessage(), WRITE_FAILURE_MESSAGE);
        }
    }
    @Test
    public void getExtensionSuccess() {
        String filePathJpg = "C:/Users/pigir/Desktop/images/saltbae.jpg";
        String extJpg = ExtensionCheckerUtil.getExtension(filePathJpg);
        assertEquals(extJpg, "jpg");

        String filePathPng = "C:/Users/pigir/Desktop/images/saltbae.png";
        String extPng = ExtensionCheckerUtil.getExtension(filePathPng);
        assertEquals(extPng, "png");

        String filePathJpeg = "C:/Users/pigir/Desktop/images/saltbae.JPEG";
        String extJpeg = ExtensionCheckerUtil.getExtension(filePathJpeg);
        assertEquals(extJpeg, "JPEG");
    }
    @Test
    public void checkExtension() {
        String filePathJpg = "C:/Users/pigir/Desktop/images/saltbae.jpg";
        String extJpg = ExtensionCheckerUtil.getExtension(filePathJpg);
        assertTrue(ExtensionCheckerUtil.isOfType(extJpg, allowedExt));

        String filePathGif = "C:/Users/pigir/Desktop/images/saltbae.gif";
        String extGif = ExtensionCheckerUtil.getExtension(filePathGif);
        assertFalse(ExtensionCheckerUtil.isOfType(extGif, allowedExt));
    }
}
