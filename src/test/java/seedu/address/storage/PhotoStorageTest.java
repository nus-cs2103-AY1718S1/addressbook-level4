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
        String filePathJPG = "C:/Users/pigir/Desktop/images/saltbae.jpg";
        String extJPG = ExtensionCheckerUtil.getExtension(filePathJPG);
        assertEquals(extJPG,"jpg");

        String filePathPNG = "C:/Users/pigir/Desktop/images/saltbae.png";
        String extPNG = ExtensionCheckerUtil.getExtension(filePathPNG);
        assertEquals(extPNG,"png");

        String filePathJPEG = "C:/Users/pigir/Desktop/images/saltbae.JPEG";
        String extJPEG = ExtensionCheckerUtil.getExtension(filePathJPEG);
        assertEquals(extJPEG,"JPEG");
    }
    @Test
    public void checkExtension() {
        String filePathJPG = "C:/Users/pigir/Desktop/images/saltbae.jpg";
        String extJPG = ExtensionCheckerUtil.getExtension(filePathJPG);
        assertTrue(ExtensionCheckerUtil.isOfType(extJPG, allowedExt));

        String filePathGIF = "C:/Users/pigir/Desktop/images/saltbae.gif";
        String extGIF = ExtensionCheckerUtil.getExtension(filePathGIF);
        assertFalse(ExtensionCheckerUtil.isOfType(extGIF, allowedExt));
    }
}
