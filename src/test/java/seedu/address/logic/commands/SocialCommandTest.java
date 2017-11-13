//@@author tbhbhbh
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.SocialCommand.MESSAGE_IF_MISSING;
import static seedu.address.logic.commands.SocialCommand.MESSAGE_INSTAGRAM;
import static seedu.address.logic.commands.SocialCommand.MESSAGE_SOCIAL_UNSUPPORTED;
import static seedu.address.logic.commands.SocialCommand.MESSAGE_TWITTER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.testutil.EventsCollectorRule;

public class SocialCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    //Model provides required names of recipients and are not modified in any way
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_socialRequestInstagramEvent_success() throws CommandException {
        ReadOnlyPerson personToCheckSocial = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        SocialCommand socialCommand = prepareCommand(INDEX_FIRST_PERSON, "ig");

        String expectedMessagePrefix = String.format(socialCommand.getMessageSocialSuccess(),
                personToCheckSocial.getName().toString());
        String expectedMessage = expectedMessagePrefix
                + MESSAGE_INSTAGRAM
                + MESSAGE_IF_MISSING;

        CommandResult result = socialCommand.execute();
        assertEquals(expectedMessage, result.feedbackToUser);

    }

    @Test
    public void execute_socialRequestTwitterEvent_success() throws CommandException {
        ReadOnlyPerson personToCheckSocial = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        SocialCommand socialCommand = prepareCommand(INDEX_FIRST_PERSON, "tw");

        String expectedMessagePrefix = String.format(socialCommand.getMessageSocialSuccess(),
                personToCheckSocial.getName().toString());
        String expectedMessage = expectedMessagePrefix
                + MESSAGE_TWITTER
                + MESSAGE_IF_MISSING;

        CommandResult result = socialCommand.execute();
        assertEquals(expectedMessage, result.feedbackToUser);

    }

    @Test
    public void execute_validIndexWithInvalidSocialMedia_throwsCommandException() throws Exception {
        SocialCommand socialCommand = prepareCommand(INDEX_FIRST_PERSON, "fb");

        String expectedMessage = MESSAGE_SOCIAL_UNSUPPORTED + "fb";

        assertCommandFailure(socialCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        SocialCommand socialCommand = prepareCommand(outOfBoundIndex, "ig");
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX + outOfBoundIndex.getOneBased();

        assertCommandFailure(socialCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        SocialCommand socialFirstInstagramCommand = prepareCommand(INDEX_FIRST_PERSON, "ig");
        SocialCommand socialSecondInstagramCommand = prepareCommand(INDEX_SECOND_PERSON, "ig");
        SocialCommand socialFirstTwitterCommand = prepareCommand(INDEX_FIRST_PERSON, "tw");

        // same object -> returns true
        assertTrue(socialFirstInstagramCommand.equals(socialFirstInstagramCommand));

        // same vaues -> returns true
        SocialCommand socialFirstInstagramCommandCopy = prepareCommand(INDEX_FIRST_PERSON,
                "ig");
        assertTrue(socialFirstInstagramCommand.equals(socialFirstInstagramCommandCopy));

        // different types -> returns false
        assertFalse(socialFirstInstagramCommand.equals(1));

        // null -> returns false
        assertFalse(socialFirstInstagramCommand.equals(null));

        // different social media -> returns false
        assertFalse(socialFirstInstagramCommand.equals(socialFirstTwitterCommand));

        // different person -> returns false
        assertFalse(socialFirstInstagramCommand.equals(socialSecondInstagramCommand));

    }

    /**
     * Returns a {@code SocialCommand} using two parameters {@code index, string}
     */
    private SocialCommand prepareCommand(Index index, String socialMedia) {
        SocialCommand socialCommand = new SocialCommand(index, socialMedia);
        socialCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        return socialCommand;
    }
}
