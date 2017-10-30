package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_FAMILY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UngroupCommand.
 */
public class UngroupCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_ungroup_success() throws Exception {
        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person afterEditPerson = new PersonBuilder(personInFilteredList).build();
        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withGroups("Some group").build();
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        model.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        UngroupCommand ungroupCommand = prepareCommand(INDEX_FIRST_PERSON, new Group("Some group"));
        String expectedMessage =
                String.format(UngroupCommand.MESSAGE_UNGROUP_SUCCESS, afterEditPerson.getName(), "Some group");

        assertCommandSuccess(ungroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person afterEditPerson = new PersonBuilder(personInFilteredList).build();
        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withGroups("Some group").build();
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        model.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        UngroupCommand ungroupCommand = prepareCommand(INDEX_FIRST_PERSON, new Group("Some group"));
        String expectedMessage =
                String.format(UngroupCommand.MESSAGE_UNGROUP_SUCCESS, afterEditPerson.getName(), "Some group");

        assertCommandSuccess(ungroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UngroupCommand remarkCommand = prepareCommand(outOfBoundIndex, new Group(VALID_GROUP_NAME_FAMILY));

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UngroupCommand ungroupCommand = prepareCommand(outOfBoundIndex, new Group(VALID_GROUP_NAME_FAMILY));

        assertCommandFailure(ungroupCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        final UngroupCommand standardCommand =
                new UngroupCommand(INDEX_FIRST_PERSON, new Group(VALID_GROUP_NAME_FAMILY));

        // same values -> returns true
        UngroupCommand commandWithSameValues =
                new UngroupCommand(INDEX_FIRST_PERSON, new Group(VALID_GROUP_NAME_FAMILY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        Assert.assertFalse(standardCommand.equals(null));

        // different types -> returns false
        Assert.assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        Assert.assertFalse(
                standardCommand.equals(new UngroupCommand(INDEX_SECOND_PERSON, new Group(VALID_GROUP_NAME_FAMILY))));

        // different descriptor -> returns false
        Assert.assertFalse(
                standardCommand.equals(new UngroupCommand(INDEX_FIRST_PERSON, new Group(VALID_GROUP_NAME_CS2103))));
    }

    /**
     * Returns an {@code UngroupCommand} with parameters {@code index} and {@code group}
     */
    private UngroupCommand prepareCommand(Index index, Group group) {
        UngroupCommand ungroupCommand = new UngroupCommand(index, group);
        ungroupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return ungroupCommand;
    }
}
