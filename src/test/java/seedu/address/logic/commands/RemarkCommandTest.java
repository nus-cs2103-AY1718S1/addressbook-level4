package seedu.address.logic.commands;
//@@author yangminxingnus
import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;

/**
  * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_addRemark_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("Remark").build();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertEquals(editedPerson, expectedModel.getFilteredPersonList().get(0));
    }
    @Test
    public void execute_invalidIndexUnfilteredList_failure() throws IllegalValueException {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundsIndex, "CS2103T/SEC/1");
        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    @Test
    public void executeCommandSuccess() throws IllegalValueException {
        ReadOnlyPerson personToAdd = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        personToAdd.remarkProperty().set(new Remark("CS2103T/SEC/1"));
        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, personToAdd);
        RemarkCommand remarkCommand = prepareCommand(INDEX_SECOND_PERSON, "CS2103T/SEC/1");
        assertCommandSuccessRemark(remarkCommand, model, expectedMessage);
    }

    /**
     * Returns a {@code RemarkCommand} with the parameter {@code index}.
     */
    private RemarkCommand prepareCommand(Index index, String remarkSent) throws IllegalValueException {

        Integer newIndex = index.getOneBased();
        Remark remark = new Remark(remarkSent);
        RemarkCommand Command = new RemarkCommand(newIndex, remark);
        Command.setData(model, new CommandHistory(), new UndoRedoStack());
        return Command;
    }
    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccessRemark(Command command, Model actualModel, String expectedMessage) {
        try {

            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);;
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }


}
//@@author
