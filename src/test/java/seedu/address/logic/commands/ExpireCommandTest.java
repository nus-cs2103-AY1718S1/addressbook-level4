package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.ExpireCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ExpiryDate;

public class ExpireCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() throws Exception {
        final String dateString = "2011-01-01";
        assertCommandFailure(prepareCommand(INDEX_FIRST_PERSON, dateString), model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), dateString));
    }

    @Test
    public void equals() throws IllegalValueException {
        final ExpireCommand standardCommand = new ExpireCommand(INDEX_FIRST_PERSON, new ExpiryDate(VALID_EXPIRE_AMY));

        // same value -> returns true
        ExpireCommand commandWithSameValues = new ExpireCommand(INDEX_FIRST_PERSON, new ExpiryDate(VALID_EXPIRE_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ExpireCommand(INDEX_SECOND_PERSON, new ExpiryDate(VALID_EXPIRE_AMY))));

        // different dateString -> returns false
        assertFalse(standardCommand.equals(new ExpireCommand(INDEX_FIRST_PERSON, new ExpiryDate(VALID_EXPIRE_BOB))));
    }

    /**
     * Returns an {@code ExpireCommand} with parameters {@code index} and {@code dateString}
     */
    private ExpireCommand prepareCommand(Index index, String dateString) throws IllegalValueException {
        ExpireCommand expireCommand = new ExpireCommand(index, new ExpiryDate(dateString));
        expireCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return expireCommand;
    }

}
