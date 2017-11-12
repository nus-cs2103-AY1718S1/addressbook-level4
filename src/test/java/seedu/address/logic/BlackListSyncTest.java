package seedu.address.logic;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BLACKLISTED_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.ListObserver;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BanCommand;
import seedu.address.logic.commands.BlacklistCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.UnbanCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

//@@author lawwman
public class BlackListSyncTest extends CommandTest {

    private static final String expectedMessage = ListObserver.BLACKLIST_NAME_DISPLAY_FORMAT
            + BlacklistCommand.MESSAGE_SUCCESS;

    @Test
    public void execute_deleteCommandOnMasterlistDeletesPersonFromBlacklist_success() throws Exception {

        ReadOnlyPerson personToBeDeleted = model.getFilteredBlacklistedPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased());
        Index index = Index.fromZeroBased(model.getFilteredPersonList().indexOf(personToBeDeleted));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // Ensure person is deleted from masterlist
        expectedModel.deletePerson(personToBeDeleted);
        expectedModel.updateFilteredBlacklistedPersonList(PREDICATE_SHOW_ALL_BLACKLISTED_PERSONS);
        expectedModel.setCurrentListName("blacklist");

        // Preparation done on actual model
        DeleteCommand deleteCommand = prepareDeleteCommand(index);
        deleteCommand.execute();

        // Operation to be done on actual model
        BlacklistCommand blacklistCommand = prepareBlacklistCommand();
        model.setCurrentListName("blacklist");

        assertCommandSuccess(blacklistCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_banCommandOnMasterListAddsPersonToBlacklist_success() throws Exception {
        ReadOnlyPerson personToBan = model.getFilteredWhitelistedPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Index index = Index.fromZeroBased(model.getFilteredPersonList().indexOf(personToBan));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // To make sure person does not exist in whitelist anymore
        personToBan = expectedModel.removeWhitelistedPerson(personToBan);
        expectedModel.addBlacklistedPerson(personToBan);
        expectedModel.setCurrentListName("blacklist");

        // Preparation done on actual model
        BanCommand banCommand = prepareBanCommand(index);
        banCommand.execute();

        // Operation to be done on actual model
        BlacklistCommand blacklistCommand = prepareBlacklistCommand();
        model.setCurrentListName("blacklist");

        assertCommandSuccess(blacklistCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unbanCommandOnMasterListRemovesPersonFromBlacklist_success() throws Exception {
        ReadOnlyPerson personToUnban = model.getFilteredBlacklistedPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Index index = Index.fromZeroBased(model.getFilteredPersonList().indexOf(personToUnban));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeBlacklistedPerson(personToUnban);
        expectedModel.setCurrentListName("blacklist");

        // Preparation done on actual model
        UnbanCommand unbanCommand = prepareUnbanCommand(index);
        unbanCommand.execute();

        // Operation to be done on actual model
        BlacklistCommand blacklistCommand = prepareBlacklistCommand();
        model.setCurrentListName("blacklist");

        assertCommandSuccess(blacklistCommand, model, expectedMessage, expectedModel);
    }

    /**
     * @return a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareDeleteCommand(Index index) throws CommandException {
        DeleteCommand deleteCommand = new DeleteCommand(index);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

    /**
     * @return a {@code EditCommand} with the parameter {@code index} & {@code EditPersonDescriptor}.
     */
    private EditCommand prepareEditCommand(Index index, EditCommand.EditPersonDescriptor descriptor) throws
            CommandException {
        EditCommand editCommand = new EditCommand(index, descriptor);
        editCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editCommand;
    }

    /**
     *  @return a {@code BlacklistCommand}
     */
    private BlacklistCommand prepareBlacklistCommand() {
        BlacklistCommand blacklistCommand = new BlacklistCommand();
        blacklistCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return blacklistCommand;
    }

    /**
     * @return a {@code BanCommand} with the parameter {@code index}.
     */
    private BanCommand prepareBanCommand(Index index) throws CommandException {
        BanCommand banCommand = new BanCommand(index);
        banCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return banCommand;
    }

    /**
     * @return a {@code UnbanCommand} with the parameter {@code index}.
     */
    private UnbanCommand prepareUnbanCommand(Index index) throws CommandException {
        UnbanCommand unbanCommand = new UnbanCommand(index);
        unbanCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unbanCommand;
    }
}
