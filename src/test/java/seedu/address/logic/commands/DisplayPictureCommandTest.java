//@@author tshradheya
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DISPLAY_PICTURE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DISPLAY_PICTURE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISPLAYPIC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalReminders.getUniqueTypicalReminders;

import java.io.File;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.DisplayPicture;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DisplayPictureCommandTest {

    public static final String INDEX_FIRST_PERSON_EMAIL = "alice@example.com";

    public static final  String DISPLAY_PICTURE_ALICE_PATH =
            new File("./src/test/resources/pictures/" + VALID_DISPLAYPIC_ALICE)
                    .getAbsolutePath();

    private static final String INVALID_PATH = " . /?no/2 t hing";
    private Model model = new ModelManager(getTypicalAddressBook(), getUniqueTypicalReminders(), new UserPrefs());

    @Test
    public void execute_setDisplayPicture_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withDisplayPicture(Integer.toString(INDEX_FIRST_PERSON_EMAIL.hashCode())).build();

        DisplayPictureCommand displayPictureCommand = prepareCommand(INDEX_FIRST_PERSON,
                DISPLAY_PICTURE_ALICE_PATH);

        String expectedMessage = String.format(DisplayPictureCommand.MESSAGE_ADD_DISPLAYPICTURE_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                getUniqueTypicalReminders(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(displayPictureCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_setDisplayPicture_successWithNoPath() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withDisplayPicture("").build();

        DisplayPictureCommand displayPictureCommand = prepareCommand(INDEX_FIRST_PERSON,
                "");

        String expectedMessage = String.format(DisplayPictureCommand.MESSAGE_DELETE_DISPLAYPICTURE_SUCCESS,
                editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                getUniqueTypicalReminders(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(displayPictureCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DisplayPictureCommand displayPictureCommand = prepareCommand(outOfBoundIndex, DISPLAY_PICTURE_ALICE_PATH);

        assertCommandFailure(displayPictureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still within bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DisplayPictureCommand displayPictureCommand = prepareCommand(outOfBoundIndex, DISPLAY_PICTURE_ALICE_PATH);

        assertCommandFailure(displayPictureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        final DisplayPictureCommand standardCommand =
                new DisplayPictureCommand(INDEX_FIRST_PERSON, DISPLAY_PICTURE_AMY);
        final DisplayPictureCommand commandWithSameValues =
                new DisplayPictureCommand(INDEX_FIRST_PERSON, DISPLAY_PICTURE_AMY);
        final DisplayPictureCommand commandWithDifferentIndex =
                new DisplayPictureCommand(INDEX_SECOND_PERSON, DISPLAY_PICTURE_AMY);
        final DisplayPictureCommand commandWithDifferentDisplayPicture =
                new DisplayPictureCommand(INDEX_FIRST_PERSON, DISPLAY_PICTURE_BOB);
        final DisplayPictureCommand commandWithDifferentValues =
                new DisplayPictureCommand(INDEX_SECOND_PERSON, DISPLAY_PICTURE_BOB);

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
        assertFalse(standardCommand.equals(commandWithDifferentDisplayPicture));

        // same type but different index and nickname -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentValues));
    }

    /**
     * Returns an {@code DisplayPictureCommand} with parameters {@code index} and {@code displaypic}
     */
    private DisplayPictureCommand prepareCommand(Index index, String displaypic) {
        DisplayPictureCommand displayPictureCommand = new DisplayPictureCommand(index, new DisplayPicture(displaypic));
        displayPictureCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return displayPictureCommand;
    }
}
