package seedu.address.logic.commands.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static seedu.address.logic.commands.person.AddAvatarCommand.MESSAGE_ADD_AVATAR_SUCCESS;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.stub.ModelStub;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

//@@author yunpengn
public class AddAvatarCommandTest {
    private static final String VALID_PATH = FileUtil.getPath("./src/test/resources/SampleAvatar.jpg");
    private static Avatar validAvatar;
    private static ModelStub model;

    @BeforeClass
    public static void setUp() throws Exception {
        validAvatar = new Avatar(VALID_PATH);
        model = new AddAvatarModelStub();
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

    @Test
    public void execute_changeAvatar_checkCorrectness() throws Exception {
        Command command = new AddAvatarCommand(Index.fromOneBased(1), validAvatar);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult result = command.execute();

        ReadOnlyPerson person = model.getFilteredPersonList().get(0);
        assertEquals(String.format(MESSAGE_ADD_AVATAR_SUCCESS, person), result.feedbackToUser);
        assertEquals(validAvatar, person.getAvatar());
    }

    @Test
    public void execute_invalidIndex_expectException() {
        Command command = new AddAvatarCommand(Index.fromOneBased(5), validAvatar);
        command.setData(model, new CommandHistory(), new UndoRedoStack());

        CommandResult result = null;
        try {
            result = command.execute();
        } catch (CommandException ce) {
            assertEquals(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, ce.getMessage());
            assertNull(result);
        }

    }

    private static class AddAvatarModelStub extends ModelStub {
        private List<ReadOnlyPerson> list = new ArrayList<>();

        public AddAvatarModelStub() {
            list.add(new PersonBuilder().build());
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return FXCollections.observableList(list);
        }

        @Override
        public void setPersonAvatar(ReadOnlyPerson target, Avatar avatar) {
            target.setAvatar(avatar);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
