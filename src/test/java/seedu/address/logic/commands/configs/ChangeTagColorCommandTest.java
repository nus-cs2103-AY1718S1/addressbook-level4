package seedu.address.logic.commands.configs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.stub.ModelStub;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

//@@author yunpengn
public class ChangeTagColorCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createCommand_success() throws Exception {
        ConfigCommand command = new ChangeTagColorCommand(VALID_TAG_FRIEND + VALID_TAG_COLOR,
                VALID_TAG_FRIEND, VALID_TAG_COLOR);

        assertNotNull(command);
    }

    @Test
    public void createCommand_illegalTagName_throwException() throws Exception {
        thrown.expect(ParseException.class);

        ConfigCommand command = new ChangeTagColorCommand(INVALID_TAG + VALID_TAG_COLOR,
                INVALID_TAG, VALID_TAG_COLOR);

        assertNotNull(command);
    }

    @Test
    public void execute_noSuchTag_throwException() throws Exception {
        thrown.expect(CommandException.class);

        ConfigCommand command = new ChangeTagColorCommand(VALID_TAG_FRIEND + VALID_TAG_COLOR,
                VALID_TAG_FRIEND, VALID_TAG_COLOR);
        command.setData(new NoSuchTagModelStub(), new CommandHistory(), new UndoRedoStack());
        command.execute();
    }

    @Test
    public void execute_hasTag_success() throws Exception {
        ConfigCommand command = new ChangeTagColorCommand(VALID_TAG_FRIEND + VALID_TAG_COLOR,
                VALID_TAG_FRIEND, VALID_TAG_COLOR);
        command.setData(new HasTagModelStub(), new CommandHistory(), new UndoRedoStack());
        CommandResult result = command.execute();

        assertEquals(String.format(ChangeTagColorCommand.MESSAGE_SUCCESS, "[friend]", VALID_TAG_COLOR),
                result.feedbackToUser);
    }

    @Test
    public void equal_twoSameCommands_returnTrue() throws Exception {
        ConfigCommand command1 = new ChangeTagColorCommand(VALID_TAG_FRIEND + VALID_TAG_COLOR,
                VALID_TAG_FRIEND, VALID_TAG_COLOR);
        ConfigCommand command2 = new ChangeTagColorCommand(VALID_TAG_FRIEND + VALID_TAG_COLOR,
                VALID_TAG_FRIEND, VALID_TAG_COLOR);

        assertEquals(command1, command2);
    }

    private class NoSuchTagModelStub extends ModelStub {
        @Override
        public boolean hasTag(Tag tag) {
            return false;
        }
    }

    private class HasTagModelStub extends ModelStub {
        @Override
        public boolean hasTag(Tag tag) {
            return true;
        }

        @Override
        public void setTagColor(Tag tag, String color) {

        }
    }
}
