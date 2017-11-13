package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.NICKNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NICKNAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NICKNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalReminders.getUniqueTypicalReminders;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Nickname;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

//@@author chuaweiwen
public class NicknameCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getUniqueTypicalReminders(), new UserPrefs());

    @Test
    public void execute_setNickname_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withNickname("Some nickname").build();

        NicknameCommand nicknameCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getNickname().value);

        String expectedMessage = String.format(NicknameCommand.MESSAGE_SET_NICKNAME_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getUniqueReminderList(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(nicknameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeNickname_success() throws Exception {
        // intialize model with a person with a nickname
        Person intializedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).withNickname(VALID_NICKNAME_AMY).build();
        model.updatePerson(model.getFilteredPersonList().get(0), intializedPerson);

        // building a person without a nickname
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withNickname("").build();

        NicknameCommand nicknameCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getNickname().value);

        String expectedMessage = String.format(NicknameCommand.MESSAGE_REMOVE_NICKNAME_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getUniqueReminderList(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(nicknameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_displayMessageUnchangedNickname_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withNickname("").build();

        NicknameCommand nicknameCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getNickname().value);

        String expectedMessage = String.format(NicknameCommand.MESSAGE_UNCHANGED_NICKNAME, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getUniqueReminderList(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(nicknameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withNickname("Some nickname").build();
        NicknameCommand nicknameCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getNickname().value);

        String expectedMessage = String.format(NicknameCommand.MESSAGE_SET_NICKNAME_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getUniqueReminderList(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(nicknameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        NicknameCommand nicknameCommand = prepareCommand(outOfBoundIndex, VALID_NICKNAME_AMY);

        assertCommandFailure(nicknameCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still within bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        NicknameCommand nicknameCommand = prepareCommand(outOfBoundIndex, VALID_NICKNAME_AMY);

        assertCommandFailure(nicknameCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final NicknameCommand standardCommand = new NicknameCommand(INDEX_FIRST_PERSON, NICKNAME_AMY);
        final NicknameCommand commandWithSameValues = new NicknameCommand(INDEX_FIRST_PERSON, NICKNAME_AMY);
        final NicknameCommand commandWithDifferentIndex = new NicknameCommand(INDEX_SECOND_PERSON, NICKNAME_AMY);
        final NicknameCommand commandWithDifferentNickname = new NicknameCommand(INDEX_FIRST_PERSON, NICKNAME_BOB);
        final NicknameCommand commandWithDifferentValues = new NicknameCommand(INDEX_SECOND_PERSON, NICKNAME_BOB);

        // same object -> Returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different object, same type with same values -> return true
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same type but different index -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

        // same type but different nickname -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentNickname));

        // same type but different index and nickname -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentValues));
    }

    /**
     * Returns an {@code NicknameCommand} with parameters {@code index} and {@code remark}
     */
    private NicknameCommand prepareCommand(Index index, String nickname) {
        NicknameCommand nicknameCommand = new NicknameCommand(index, new Nickname(nickname));
        nicknameCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return nicknameCommand;
    }
}
//@@author
