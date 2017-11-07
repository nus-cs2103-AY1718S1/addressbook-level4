package seedu.address.logic.commands.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.AVATAR_VALID_URL;

import org.junit.BeforeClass;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.model.person.Avatar;

public class AddAvatarCommandTest {
    private static Avatar validAvatar;

    @BeforeClass
    public static void setUp() throws Exception {
        validAvatar = new Avatar(AVATAR_VALID_URL);
    }

    @Test
    public void equal_twoCommands_checkCorrectness() {
        Command command1 = new AddAvatarCommand(Index.fromOneBased(1), validAvatar);
        Command command2 = new AddAvatarCommand(Index.fromOneBased(1), validAvatar);
        assertEquals(command1, command2);

        command1 = new AddAvatarCommand(Index.fromZeroBased(1), validAvatar);
        command2 = new AddAvatarCommand(Index.fromOneBased(1), validAvatar);
        assertNotEquals(command1, command2);
    }
}
