package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code BanCommand}.
 */
public class BanCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToBan = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        BanCommand banCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(BanCommand.MESSAGE_BAN_PERSON_SUCCESS, personToBan);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addBlacklistedPerson(personToBan);

        assertCommandSuccess(banCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        BanCommand banCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(banCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToBan = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        BanCommand banCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(BanCommand.MESSAGE_BAN_PERSON_SUCCESS, personToBan);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addBlacklistedPerson(personToBan);

        assertCommandSuccess(banCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code BanCommand} with the parameter {@code index}.
     */
    private BanCommand prepareCommand(Index index) {
        BanCommand banCommand = new BanCommand(index);
        banCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return banCommand;
    }

    /**
     * Updates {@code model}'s filtered blacklist to show no one.
     */
    private void showNoBlacklistedPerson(Model model) {
        model.updateFilteredBlacklistedPersonList(p -> false);

        assert model.getFilteredBlacklistedPersonList().isEmpty();
    }

}
