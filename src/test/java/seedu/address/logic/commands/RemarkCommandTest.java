package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;

public class RemarkCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));

        // same value -> returns true
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // different types -> returns false
        assertFalse(standardCommand.equals(1));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB))));
    }

    @Test
    public void get_commandWord() {
        RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));
        assertEquals(standardCommand.getCommandWord(), RemarkCommand.COMMAND_WORD);
    }

    @Test
    public void generate_successMessage() {
        RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));
        RemarkCommand deleteCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(""));
        try {
            Method m = standardCommand.getClass().getDeclaredMethod("generateSuccessMessage", ReadOnlyPerson.class);
            m.setAccessible(true);

            //has remark
            String addSuccessMessage = (String) m.invoke(standardCommand, AMY);
            Field field = RemarkCommand.class.getDeclaredField("MESSAGE_ADD_REMARK_SUCCESS");
            field.setAccessible(true);
            String messageAddRemarkSuccess = (String) field.get(standardCommand);
            assertEquals(String.format(messageAddRemarkSuccess, AMY), addSuccessMessage);

            //no remark
            String deleteSuccessMessage = (String) m.invoke(deleteCommand, AMY);
            field = RemarkCommand.class.getDeclaredField("MESSAGE_DELETE_REMARK_SUCCESS");
            field.setAccessible(true);
            String messageDeleteRemarkSuccess = (String) field.get(deleteCommand);
            assertEquals(String.format(messageDeleteRemarkSuccess, AMY), deleteSuccessMessage);


        } catch (Exception e) {
            assert false;
        }
    }
}
