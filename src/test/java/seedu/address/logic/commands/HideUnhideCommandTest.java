package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.logic.commands.CommandTestUtil.showHiddenPersonList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.person.HideCommand;
import seedu.address.logic.commands.person.UnhideCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameIsPrivatePredicate;
import seedu.address.model.person.ReadOnlyPerson;

public class HideUnhideCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private NameIsPrivatePredicate predicate = new NameIsPrivatePredicate(true);

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToHide = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        HideCommand hideCommand = prepareHideCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(HideCommand.MESSAGE_HIDE_PERSON_SUCCESS, personToHide);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.hidePerson(personToHide);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(hideCommand, model, expectedMessage, expectedModel);

        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ONLY_HIDDEN);
        ReadOnlyPerson personToUnhide = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnhideCommand unhideCommand = prepareUnhideCommand(INDEX_FIRST_PERSON);

        expectedMessage = String.format(UnhideCommand.MESSAGE_UNHIDE_PERSON_SUCCESS, personToUnhide);

        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.unhidePerson(personToUnhide);
        expectedModel.updateFilteredPersonList(predicate);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ONLY_HIDDEN);

        assertCommandSuccess(unhideCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredListHide_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        HideCommand hideCommand = prepareHideCommand(outOfBoundIndex);

        assertCommandFailure(hideCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexUnfilteredListUnpin_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnhideCommand unhideCommand = prepareUnhideCommand(outOfBoundIndex);

        assertCommandFailure(unhideCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personAlreadyHidden_throwsCommandException() throws Exception {
        ReadOnlyPerson personToPin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.hidePerson(personToPin);
        HideCommand hideCommand = prepareHideCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(hideCommand, model, Messages.MESSAGE_PERSON_ALREADY_HIDDEN);
    }

    @Test
    public void execute_personAlreadyUnhidden_throwsCommandException() throws Exception {
        ReadOnlyPerson personToUnhide = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.unhidePerson(personToUnhide);
        UnhideCommand unhideCommand = prepareUnhideCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(unhideCommand, model, Messages.MESSAGE_PERSON_ALREADY_UNHIDDEN);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToHide = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        HideCommand hideCommand = prepareHideCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(HideCommand.MESSAGE_HIDE_PERSON_SUCCESS, personToHide);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showFirstPersonOnly(expectedModel);
        expectedModel.hidePerson(personToHide);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(hideCommand, model, expectedMessage, expectedModel);

        showHiddenPersonList(model);

        ReadOnlyPerson personToUnhide = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnhideCommand unhideCommand = prepareUnhideCommand(INDEX_FIRST_PERSON);

        expectedMessage = String.format(UnhideCommand.MESSAGE_UNHIDE_PERSON_SUCCESS, personToUnhide);

        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showHiddenPersonList(expectedModel);
        expectedModel.unhidePerson(personToHide);
        expectedModel.updateFilteredPersonList(predicate);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ONLY_HIDDEN);


        assertCommandSuccess(unhideCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        HideCommand hideCommand = prepareHideCommand(outOfBoundIndex);

        assertCommandFailure(hideCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        HideCommand hideFirstCommand = new HideCommand(INDEX_FIRST_PERSON);
        HideCommand hideSecondCommand = new HideCommand(INDEX_SECOND_PERSON);
        UnhideCommand unhideFirstCommand = new UnhideCommand(INDEX_FIRST_PERSON);
        UnhideCommand unhideSecondCommand = new UnhideCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(hideFirstCommand.equals(hideFirstCommand));
        assertTrue(unhideFirstCommand.equals(unhideFirstCommand));

        // same values -> returns true
        HideCommand hideFirstCommandCopy = new HideCommand(INDEX_FIRST_PERSON);
        assertTrue(hideFirstCommand.equals(hideFirstCommandCopy));
        UnhideCommand unhideFirstCommandCopy = new UnhideCommand(INDEX_FIRST_PERSON);
        assertTrue(unhideFirstCommand.equals(unhideFirstCommandCopy));

        // different types -> returns false
        assertFalse(hideFirstCommand.equals(1));
        assertFalse(unhideFirstCommand.equals(1));

        // null -> returns false
        assertFalse(hideFirstCommand.equals(null));
        assertFalse(unhideFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(hideFirstCommand.equals(hideSecondCommand));
        assertFalse(unhideFirstCommand.equals(unhideSecondCommand));
    }

    /**
     * Returns a {@code HideCommand} with the parameter {@code index}.
     */
    private HideCommand prepareHideCommand(Index index) {
        HideCommand hideCommand = new HideCommand(index);
        hideCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return hideCommand;
    }

    /**
     * Returns a {@code UnhideCommand} with the parameter {@code index}.
     */
    private UnhideCommand prepareUnhideCommand(Index index) {
        UnhideCommand unhideCommand = new UnhideCommand(index);
        unhideCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unhideCommand;
    }
}
