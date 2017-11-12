package systemtests;

import static seedu.address.logic.commands.person.AddAvatarCommand.MESSAGE_ADD_AVATAR_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.person.AddAvatarCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.ReadOnlyPerson;

//@@author yunpengn
public class AddAvatarCommandSystemTest extends AddressBookSystemTest {
    private static final String VALID_PATH = FileUtil.getPath("./src/test/resources/SampleAvatar.jpg");

    @Test
    public void addAvatar() throws Exception {
        Index index = INDEX_FIRST_PERSON;
        String command = AddAvatarCommand.COMMAND_WORD + " " + index.getOneBased() + " " + VALID_PATH;
        assertCommandSuccess(command, index, VALID_PATH);
    }

    private void assertCommandSuccess(String command, Index toAdd, String path) throws Exception {
        Model model = getModel();
        ReadOnlyPerson personToAdd = model.getFilteredPersonList().get(toAdd.getZeroBased());
        Avatar avatar = new Avatar(path);
        String expectedResultMessage = String.format(MESSAGE_ADD_AVATAR_SUCCESS, personToAdd);
        model.setPersonAvatar(personToAdd, avatar);

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, model);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }
}
