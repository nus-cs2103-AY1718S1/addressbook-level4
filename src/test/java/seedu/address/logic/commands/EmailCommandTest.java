package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RecycleBin;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class EmailCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new RecycleBin(), new UserPrefs());
    }

    //@@author stan789-reused
    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex);
    }

    //@@author stan789-reused
    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    //@@author stan789-reused
    @Test
    public void execute_validIndexFilteredList_success() {
        showFirstPersonOnly(model, false);

        assertExecutionSuccess(INDEX_FIRST_PERSON);
    }

    //@@author stan789-reused
    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstPersonOnly(model, false);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    //@@author stan789
    @Test
    public void emptyEmail_failure() {
        Person person = new PersonBuilder().withName("ALICE").withEmail(null).build();
        try {
            model.addPerson(person);
        } catch (DuplicatePersonException e) {
            throw new IllegalArgumentException("Execution of command should not fail.", e);
        }
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        assertExecutionFailure(lastPersonIndex, EmailCommand.MESSAGE_EMPTY_EMAIL);
    }

    //@@author stan789
    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        EmailCommandStub emailCommand = prepareCommand(index);

        try {
            CommandResult commandResult = emailCommand.execute();
            assertEquals(EmailCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

    }

    //@@author stan789
    /**
     * Executes a {@code EmailCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        EmailCommandStub emailCommand = prepareCommand(index);

        try {
            emailCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
        }
    }

    //@@author stan789-reused
    /**
     * Returns a {@code EmailCommand} with parameters {@code index}.
     */
    private EmailCommandStub prepareCommand(Index index) {
        EmailCommandStub emailCommand = new EmailCommandStub(index);
        emailCommand.setData(model, new CommandHistory(), new UndoRedoStack(), false);
        return emailCommand;
    }

}

class EmailCommandStub extends EmailCommand {

    EmailCommandStub(Index index) {
        super(index);
    }

    @Override
    protected void desktopEmail(String email) throws IOException, URISyntaxException {
    }
}
