package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.RemoveTagCommand.MESSAGE_DELETE_TAG_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

//@@author sidhmads
/**
 * Contains integration tests (interaction with the Model) for {@code RemoveTagCommand}.
 */
public class RemoveTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() throws IllegalValueException {
        Set<Tag> tagFriend = new HashSet<>();
        tagFriend.add(new Tag(VALID_TAG_FRIEND));
        RemoveTagCommand findFirstCommand = new RemoveTagCommand(new ArrayList<Index>(), tagFriend);
        Set<Tag> tagHusband = new HashSet<>();
        tagHusband.add(new Tag(VALID_TAG_HUSBAND));
        RemoveTagCommand findSecondCommand = new RemoveTagCommand(new ArrayList<Index>(), tagHusband);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        RemoveTagCommand findFirstCommandCopy = new RemoveTagCommand(new ArrayList<Index>(), tagFriend);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords() throws Exception {
        String expectedMessage = String.format("The Tag is invalid!");
        RemoveTagCommand command = prepareCommand(new ArrayList<Index>(), new HashSet<Tag>());
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() throws Exception {
        ObservableList<ReadOnlyPerson> personToRemoveTag = FXCollections.observableArrayList();
        personToRemoveTag.add(getTypicalAddressBook().getPersonList().get(0));
        personToRemoveTag.add(getTypicalAddressBook().getPersonList().get(1));
        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(ParserUtil.parseIndex("1"));
        indexes.add(ParserUtil.parseIndex("2"));
        Set<Tag> tagFriend = new HashSet<>();
        tagFriend.add(new Tag("friends"));
        String expectedMessage = String.format(MESSAGE_DELETE_TAG_SUCCESS,
                tagFriend.toString()) + String.format("from %s", personToRemoveTag.toString());
        RemoveTagCommand command = prepareCommand(indexes, tagFriend);
        Model mod = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        mod.removeTag(personToRemoveTag, tagFriend);
        assertCommandSuccess(command, model, expectedMessage, mod);
    }

    /**
     * Parses {@code userInput} into a {@code RemoveTagCommand}.
     */
    private RemoveTagCommand prepareCommand(ArrayList<Index> indexes, Set<Tag> tags) {
        RemoveTagCommand command =
                new RemoveTagCommand(indexes, tags);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
