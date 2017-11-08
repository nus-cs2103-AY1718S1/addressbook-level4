package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.email.Email;

//@@author jin-ting

/**
 * Contains integration tests (interaction with the Model) for {@code EmailCommand}.
 */
public class EmailCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Set<Index> indices = new HashSet<>();
        indices.add(INDEX_FIRST_PERSON);
        EmailCommand emailAliceCommand = new EmailCommand(indices);
        Set<Index> indicesBob = new HashSet<>();
        indicesBob.add(INDEX_SECOND_PERSON);
        EmailCommand emailBobCommand = new EmailCommand(indicesBob);

        // same object -> returns true
        assertTrue(emailAliceCommand.equals(emailAliceCommand));

        // same values -> returns true
        EmailCommand emailAliceCommandCopy = new EmailCommand(indices);
        assertTrue(emailAliceCommand.equals(emailAliceCommandCopy));


        // different types -> returns false
        assertFalse(emailAliceCommand.equals(1));

        // null -> returns false
        assertFalse(emailAliceCommand.equals(null));

    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToEmail = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EmailCommand emailCommand = prepareCommand(INDEX_FIRST_PERSON);
        Set<String> recipientSet = new HashSet<>();
        for (Email email : personToEmail.getEmails()) {
            recipientSet.add(email.toString());
        }

        String emailList = String.join(",", recipientSet);
        String expectedMessage = String.format(EmailCommand.MESSAGE_DISPLAY_EMAIL_SUCCESS, emailList);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EmailCommand emailCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(emailCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EmailCommand emailCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(emailCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    /**
     * Returns a {@code EmailCommand} with the parameter {@code index}.
     */
    private EmailCommand prepareCommand(Index index) {
        Set<Index> indices = new HashSet<>();
        indices.add(index);

        EmailCommand emailCommand = new EmailCommand(indices);
        emailCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return emailCommand;
    }


}
