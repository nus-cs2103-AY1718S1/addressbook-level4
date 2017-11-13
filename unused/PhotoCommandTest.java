package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHOTONAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHOTONAME_BOB;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author April0616-unused

public class PhotoCommandTest {
    @Test
    public void equals() throws FileNotFoundException, IllegalValueException {
        File amyFile = new File(VALID_PHOTONAME_AMY);
        File bobFile = new File(VALID_PHOTONAME_BOB);

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
