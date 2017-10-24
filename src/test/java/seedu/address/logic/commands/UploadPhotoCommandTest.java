package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Photo;
import seedu.address.testutil.PersonBuilder;

public class UploadPhotoCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_personAcceptedByModel_uploadPhotoSuccessful() throws Exception {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).withPhoto("..\\addressbook4\\docs\\images\\wolf.jpg").build();

        Photo photo = new Photo("..\\addressbook4\\docs\\images\\wolf.jpg");
        UploadPhotoCommand uploadPhotoCommand = prepareCommand(INDEX_FIRST_PERSON, photo);

        String expectedMessage = String.format(UploadPhotoCommand.MESSAGE_UPDATE_PERSON_PHOTO_SUCCESS, updatedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(uploadPhotoCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code UploadPhotoCommand} with the parameters {@code index + photo path}.
     */
    private UploadPhotoCommand prepareCommand(Index index, Photo photo) {
        UploadPhotoCommand command = new UploadPhotoCommand(index, photo);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
