package seedu.address.logic.commands;

//@@author LinusMelb

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.AddAvatarCommand.MESSAGE_UPDATE_AVATAR_PIC_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEB_IMAGE_URL_A;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

public class AddAvatarCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void checkAvatarCommandSuccess() throws Exception {

        ReadOnlyPerson updatedAvatarPicPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedAvatarPerson = new Person(updatedAvatarPicPerson);
        Avatar avatarPic = new Avatar(VALID_WEB_IMAGE_URL_A);
        updatedAvatarPerson.setAvatarPic(avatarPic);
        AddAvatarCommand updateAvatarPicCommand = prepareCommand(INDEX_FIRST_PERSON, avatarPic);

        String expectedMessage = String.format(MESSAGE_UPDATE_AVATAR_PIC_SUCCESS, updatedAvatarPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedAvatarPicPerson);

        assertCommandSuccess(updateAvatarPicCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void equals() throws Exception {
        final AddAvatarCommand avatarCommand = new AddAvatarCommand(INDEX_FIRST_PERSON,
                new Avatar(VALID_WEB_IMAGE_URL_A));

        AddAvatarCommand sameValueCommand = new AddAvatarCommand(INDEX_FIRST_PERSON,
                new Avatar(VALID_WEB_IMAGE_URL_A));

        assertFalse(avatarCommand.equals(null));
        assertFalse(avatarCommand.equals(new ClearCommand()));
        assertFalse(avatarCommand.equals(new AddAvatarCommand(INDEX_SECOND_PERSON,
                new Avatar(VALID_WEB_IMAGE_URL_A))));
        assertTrue(avatarCommand.equals(new AddAvatarCommand(INDEX_FIRST_PERSON,
                new Avatar(VALID_WEB_IMAGE_URL_A))));
        assertTrue(avatarCommand.equals(sameValueCommand));
        assertTrue(avatarCommand.equals(avatarCommand));


    }

    /**
     * Returns an {@code UpdateAvatarPicCommand} with parameters {@code index} and {@code AvatarPic}
     */
    private AddAvatarCommand prepareCommand(Index index, Avatar avatar) {
        AddAvatarCommand updateAvatarPicCommand = new AddAvatarCommand(index, avatar);
        updateAvatarPicCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return updateAvatarPicCommand;
    }

}

