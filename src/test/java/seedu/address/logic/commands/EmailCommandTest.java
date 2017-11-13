//@@author conantteo
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.EmailRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.testutil.EventsCollectorRule;

public class EmailCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    //Model simply provides the required names of recipients and are not modified in any way.
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Index[] indexArray;

    @Test
    public void execute_emailRequestEvent_success() throws CommandException {
        EmailCommand emailCommand = prepareCommand(INDEX_FIRST_PERSON);
        CommandResult result = emailCommand.execute();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof EmailRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);

    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws CommandException {
        ReadOnlyPerson personToEmail = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EmailCommand emailCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(EmailCommand.MESSAGE_EMAIL_PERSON_SUCCESS,
                personToEmail.getName().toString());
        CommandResult result = emailCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EmailCommand emailCommand = prepareCommand(outOfBoundIndex);
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                + outOfBoundIndex.getOneBased();

        assertCommandFailure(emailCommand, model, expectedMessage);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws CommandException {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToEmail = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EmailCommand emailCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(EmailCommand.MESSAGE_EMAIL_PERSON_SUCCESS,
                personToEmail.getName().toString());
        CommandResult result = emailCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EmailCommand emailCommand = prepareCommand(outOfBoundIndex);
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                + outOfBoundIndex.getOneBased();

        assertCommandFailure(emailCommand, model, expectedMessage);
    }

    @Test
    public void execute_multipleValidIndex_success() throws CommandException {
        ReadOnlyPerson firstPersonToEmail = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson secondPersonToEmail = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EmailCommand emailCommand = prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        String expectedMessage = String.format(EmailCommand.MESSAGE_EMAIL_PERSON_SUCCESS,
                firstPersonToEmail.getName().toString() + ", " + secondPersonToEmail.getName().toString());
        CommandResult result = emailCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void execute_validIndexWithoutEmail_throwsCommandException() {
        EmailCommand emailCommand = prepareCommand(INDEX_THIRD_PERSON);
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_TO_EMAIL
                + INDEX_THIRD_PERSON.getOneBased();

        assertCommandFailure(emailCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        EmailCommand emailFirstCommand = prepareCommand(INDEX_FIRST_PERSON);
        EmailCommand emailSecondCommand = prepareCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(emailFirstCommand.equals(emailFirstCommand));

        // same values -> returns true
        EmailCommand emailFirstCommandCopy = prepareCommand(INDEX_FIRST_PERSON);
        assertTrue(emailFirstCommand.equals(emailFirstCommandCopy));

        // different types -> returns false
        assertFalse(emailFirstCommand.equals(1));

        // null -> returns false
        assertFalse(emailFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(emailFirstCommand.equals(emailSecondCommand));
    }

    /**
     * Returns a {@code EmailCommand} using one index parameter {@code index}
     */
    private EmailCommand prepareCommand(Index index) {
        indexArray = new Index[1];
        indexArray[0] = index;

        EmailCommand emailCommand = new EmailCommand(indexArray);
        emailCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        return emailCommand;
    }
    /**
     * Returns a {@code EmailCommand} using two index parameter {@code index1, index2}
     */
    private EmailCommand prepareCommand(Index index1, Index index2) {
        indexArray = new Index[2];
        indexArray[0] = index1;
        indexArray[1] = index2;

        EmailCommand emailCommand = new EmailCommand(indexArray);
        emailCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        return emailCommand;
    }
}
