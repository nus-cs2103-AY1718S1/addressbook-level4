package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.photo.PhotoPath;

//@@author April0616
public class PhotoPathTest {

    @Test
    public void isValidPhotoPath() {

        // empty
        assertTrue(PhotoPath.isValidPhotoPath(""));
        assertFalse(PhotoPath.isValidPhotoPath(" "));

        // missing parts: not start with 'docs/images/contactPhotos/'
        assertFalse(PhotoPath.isValidPhotoPath("photo.jpg"));
        assertFalse(PhotoPath.isValidPhotoPath("c:photo.jpg"));
        assertFalse(PhotoPath.isValidPhotoPath("d:photo.jpg"));
        assertFalse(PhotoPath.isValidPhotoPath("c:\\\\photo.jpg"));
        assertFalse(PhotoPath.isValidPhotoPath("c:\\"));
        assertFalse(PhotoPath.isValidPhotoPath("c:\\"));

        // invalid file extension
        assertFalse(PhotoPath.isValidPhotoPath("src/main/resources/images/contactPhotos/photo.txt"));
        assertFalse(PhotoPath.isValidPhotoPath("src/main/resources/images/contactPhotos/selfie.pdf"));
        assertFalse(PhotoPath.isValidPhotoPath("src/main/resources/images/contactPhotos/selfie2.doc"));

        // valid photo path
        //assertTrue(PhotoPath.isValidPhotoPath("src/main/resources/images/help_icon.png"));  //default photo path
        assertTrue(PhotoPath.isValidPhotoPath("src/main/resources/images/contactPhotos/1234.jpg"));
        assertTrue(PhotoPath.isValidPhotoPath("src/main/resources/images/contactPhotos/12345678.jpg"));
        assertTrue(PhotoPath.isValidPhotoPath("src/main/resources/images/contactPhotos/1234_5678.jpg"));  // underscore
    }

    /*
    @Ignore
    @Test
    public void equals() throws IllegalValueException {
        String parentPath = PATH_FILE_SAVED_PARENT_DIRECTORY;
        //PhotoPath validPhotoPath_1 = new PhotoPath(parentPath + "1234.jpg");
        //PhotoPath validPhotoPath_2 = new PhotoPath(parentPath + "5678.jpg");

        // same object -> returns true
        assertTrue(validPhotoPath_1.equals(validPhotoPath_1));

        // same value -> returns true
        PhotoPath copy = new PhotoPath(validPhotoPath_1.value);
        assertTrue(validPhotoPath_1.equals(copy));

        // different types -> returns false
        assertFalse(validPhotoPath_1.equals(1));

        // null -> returns false
        assertFalse(validPhotoPath_1 == null);

        // different objects -> returns false
        assertFalse(validPhotoPath_1.equals(validPhotoPath_2));
    }
    */
}
