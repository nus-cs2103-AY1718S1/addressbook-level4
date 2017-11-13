//@@author tshradheya
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISPLAYPIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISPLAYPIC_BOB;

import org.junit.Test;

public class DisplayPictureTest {
    @Test
    public void equals() {
        DisplayPicture standardDisplayPicture = new DisplayPicture(VALID_DISPLAYPIC_AMY);
        DisplayPicture sameDisplayPicture = new DisplayPicture(VALID_DISPLAYPIC_AMY);
        DisplayPicture differentDisplayPicture = new DisplayPicture(VALID_DISPLAYPIC_BOB);

        // same object -> returns true
        assertTrue(standardDisplayPicture.equals(standardDisplayPicture));

        // null -> returns false
        assertFalse(standardDisplayPicture == null);

        // different type -> returns false
        assertFalse(standardDisplayPicture.equals("String"));

        // different nickname -> returns false
        assertFalse(sameDisplayPicture.equals(differentDisplayPicture));

        // same nickname -> returns true
        assertTrue(standardDisplayPicture.equals(sameDisplayPicture));
    }
}
