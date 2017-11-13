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
public class RemoveFavouriteCommandTest {
    private Model model = new ModelManager(getAltAddressBook(), new UserPrefs());

    @Test
    public void removeFavouriteSuccess() throws Exception {
        Person editedPerson = new PersonBuilder().withName("Benson Meier")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withEmail("johnd@example.com").withPhone("98765432")
                .withTags("owesMoney", "friends").build();
        RemoveFavouriteCommand removeFavouriteCommand = prepareCommand(INDEX_SECOND_PERSON);

        String expectedMessage = String.format(RemoveFavouriteCommand.MESSAGE_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(1), editedPerson);

        assertCommandSuccess(removeFavouriteCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void invalidPersonIndexFailure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemoveFavouriteCommand removeFavouriteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(removeFavouriteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    @Test
    public void duplicatePersonFailure() {
        RemoveFavouriteCommand removeFavouriteCommand = prepareCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(removeFavouriteCommand, model, RemoveFavouriteCommand.MESSAGE_ALREADY_NORMAL);
    }
    /**
     * Returns an {@code ResetPictureCommand} with parameters {@code index}
     **/
    private RemoveFavouriteCommand prepareCommand(Index index) {
        RemoveFavouriteCommand removeFavouriteCommand = new RemoveFavouriteCommand(index);
        removeFavouriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeFavouriteCommand;
    }
}
