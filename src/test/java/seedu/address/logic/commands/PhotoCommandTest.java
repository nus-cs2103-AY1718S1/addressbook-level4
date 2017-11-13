package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

//import static seedu.address.logic.commands.CommandTestUtil.VALID_PHOTONAME_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_PHOTONAME_BOB;

//@@author April0616
public class PhotoCommandTest {

    @Test
    public void isValidLocalPhotoPath() {
        // blank photo path
        assertFalse(PhotoCommand.isValidLocalPhotoPath("")); // empty string
        assertFalse(PhotoCommand.isValidLocalPhotoPath(" ")); // spaces only

        // missing parts
        assertFalse(PhotoCommand.isValidLocalPhotoPath("photo.jpg")); // missing disk part
        assertFalse(PhotoCommand.isValidLocalPhotoPath("c:photo.jpg")); // missing backslash
        assertFalse(PhotoCommand.isValidLocalPhotoPath("d:photo.jpg")); // missing backslash

        // invalid parts
        assertFalse(PhotoCommand.isValidLocalPhotoPath("c:\\\\photo.jpg")); // too many backslashes
        assertFalse(PhotoCommand.isValidLocalPhotoPath("c:\\")); // no file name
        assertFalse(PhotoCommand.isValidLocalPhotoPath("c:\\")); // no file name
        assertFalse(PhotoCommand.isValidLocalPhotoPath("d:\\my_photo.txt"));  // invalid extension
        assertFalse(PhotoCommand.isValidLocalPhotoPath("d:\\my_photo.pdf"));  // invalid extension

        // valid photo path
        assertTrue(PhotoCommand.isValidLocalPhotoPath("c:\\desktop\\baby.jpg"));
        assertTrue(PhotoCommand.isValidLocalPhotoPath("d:\\myself.jpg"));  //
        assertTrue(PhotoCommand.isValidLocalPhotoPath("d:\\my_photo.jpg"));  // underscore
    }



    /**
     * This test cannot run on Travis CI.
     */
    @Ignore
    private void equals() throws FileNotFoundException, IllegalValueException {
        //File amyFile = new File(VALID_PHOTONAME_AMY);
        File amyFile = new File("photo.jpg");
        //File bobFile = new File(VALID_PHOTONAME_BOB);
        File bobFile = new File("selfie.jpg");
        try {
            amyFile.createNewFile();
            bobFile.createNewFile();
        } catch (IOException ioe) {
            System.err.println("Cannot create temporary files!");
        }

        String amyPath = amyFile.getAbsolutePath();
        String bobPath = bobFile.getAbsolutePath();
        //TestUtil.createTempFile(VALID_PHOTOPATH_AMY);
        //TestUtil.createTempFile(VALID_PHOTOPATH_BOB);

        PhotoCommand addFirstPersonPhotoPath = new PhotoCommand(INDEX_FIRST_PERSON, amyPath);
        PhotoCommand addSecondPersonPhotoPath = new PhotoCommand(INDEX_SECOND_PERSON, bobPath);

        // same object -> returns true
        assertTrue(addFirstPersonPhotoPath.equals(addFirstPersonPhotoPath));

        // same value -> returns true
        PhotoCommand copy = new PhotoCommand(addFirstPersonPhotoPath.getIndex(),
                addFirstPersonPhotoPath.getLocalPhotoPath());
        assertTrue(addFirstPersonPhotoPath.equals(copy));

        // different types -> returns false
        assertFalse(addFirstPersonPhotoPath.equals(1));

        // null -> returns false
        assertFalse(addFirstPersonPhotoPath == null);

        // different objects -> returns false
        assertFalse(addFirstPersonPhotoPath.equals(addSecondPersonPhotoPath));

        //TestUtil.removeFileAndItsParentsTillRoot(VALID_PHOTOPATH_BOB);
        //TestUtil.removeFileAndItsParentsTillRoot(VALID_PHOTOPATH_AMY);
        amyFile.delete();
        bobFile.delete();
    }
}
