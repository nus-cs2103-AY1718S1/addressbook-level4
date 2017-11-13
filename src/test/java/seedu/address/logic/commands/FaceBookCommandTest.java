package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.FaceBookEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.FacebookUsername;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.testutil.EventsCollectorRule;
//@@author RonakLakhotia
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code MapCommand}.
 */

public class FaceBookCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {

        ReadOnlyPerson personToShow = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        FaceBookCommand faceBookCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(FaceBookCommand.MESSAGE_FACEBOOK_SHOWN_SUCCESS, personToShow);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.faceBook(personToShow);

        assertCommandSuccess(faceBookCommand, model, expectedMessage, expectedModel);

        FaceBookEvent lastFacebookEvent = (FaceBookEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased()), lastFacebookEvent.getPerson());
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {


        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        FaceBookCommand faceBookCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(faceBookCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        FaceBookCommand faceBookCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(faceBookCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

    }
    @Test
    public void execute_no_usernameFacebookCommmand() throws IllegalValueException {

        ReadOnlyPerson personToSearch = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        personToSearch.usernameProperty().setValue(new FacebookUsername(""));
        FaceBookCommand faceBookCommand = prepareCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(faceBookCommand, model, Messages.MESSAGE_NO_USERNAME);
    }

    @Test
    public void equals() {


        FaceBookCommand faceBookCommandFirst = new FaceBookCommand(INDEX_FIRST_PERSON);
        FaceBookCommand faceBookCommandSecond = new FaceBookCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(faceBookCommandFirst.equals(faceBookCommandFirst));

        // same values -> returns true
        FaceBookCommand facebookFirstCommandCopy = new FaceBookCommand(INDEX_FIRST_PERSON);
        assertTrue(facebookFirstCommandCopy.equals(facebookFirstCommandCopy));

        // different types -> returns false
        assertFalse(faceBookCommandFirst.equals(1));

        // null -> returns false
        assertFalse(faceBookCommandFirst.equals(null));

        // different person -> returns false
        assertFalse(faceBookCommandFirst.equals(faceBookCommandSecond));

    }

    @Test
    public void check_facebookEventCollected() throws CommandException {
        FaceBookCommand faceBookCommand = prepareCommand(INDEX_FIRST_PERSON);
        faceBookCommand.execute();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof FaceBookEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    /**
     * Returns a {@code FacebookCommand} with the parameter {@code index}.
     */
    private FaceBookCommand prepareCommand(Index index) {
        FaceBookCommand faceBookCommand = new FaceBookCommand(index);
        faceBookCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return faceBookCommand;
    }
}
