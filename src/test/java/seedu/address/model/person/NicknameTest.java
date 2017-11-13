package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NICKNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NICKNAME_BOB;

import org.junit.Test;

//@@author chuaweiwen
public class NicknameTest {

    @Test
    public void equals() {
        Nickname standardNickname = new Nickname(VALID_NICKNAME_AMY);
        Nickname sameNickname = new Nickname(VALID_NICKNAME_AMY);
        Nickname differentNickname = new Nickname(VALID_NICKNAME_BOB);

        // same object -> returns true
        assertTrue(standardNickname.equals(standardNickname));

        // null -> returns false
        assertFalse(standardNickname == null);

        // different type -> returns false
        assertFalse(standardNickname.equals("String"));

        // different nickname -> returns false
        assertFalse(standardNickname.equals(differentNickname));

        // same nickname -> returns true
        assertTrue(standardNickname.equals(sameNickname));
    }
}
//@@author
