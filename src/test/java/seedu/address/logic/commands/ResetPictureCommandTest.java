package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getAltAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

//@@author nassy93

public class ResetPictureCommandTest {

    private Model model = new ModelManager(getAltAddressBook(), new UserPrefs());

    @Test
    public void resetPictureSuccess() throws Exception {
        Person editedPerson = new PersonBuilder().withEmail("alice@example.com").build();
        ResetPictureCommand resetPictureCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ResetPictureCommand.MESSAGE_RESETPICTURE_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(resetPictureCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void invalidPersonIndexFailure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ResetPictureCommand resetPictureCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(resetPictureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    @Test
    public void duplicatePersonFailure() {
        ResetPictureCommand resetPictureCommand = prepareCommand(INDEX_SECOND_PERSON);

        assertCommandFailure(resetPictureCommand, model, ResetPictureCommand.MESSAGE_ALREADY_DEFAULT);
    }
    /**
     * Returns an {@code ResetPictureCommand} with parameters {@code index}
     **/
    private ResetPictureCommand prepareCommand(Index index) {
        ResetPictureCommand resetPictureCommand = new ResetPictureCommand(index);
        resetPictureCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return resetPictureCommand;
    }
}
