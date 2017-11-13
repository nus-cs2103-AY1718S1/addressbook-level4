//@@author tbhbhbh
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.TagEditCommand.MESSAGE_EDIT_TAG_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class TagEditCommandTest {

    public static final String DUMMY_TAG = "dummy";
    public static final String DUMMY_TAG_TWO = "dummytwo";
    public static final String TAG_THAT_FAILS_ALPHANUMERIC = "fa-il";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_editValidTagUnfilteredList_success() throws Exception {
        Set<Tag> tagSet = new HashSet<>(model.getFilteredPersonList()
                .get(INDEX_SECOND_PERSON.getZeroBased()).getTags());
        String oldTagName = tagSet.toArray()[0].toString().substring(1, 10);
        Tag oldTag = new Tag(oldTagName);
        String newTagName = DUMMY_TAG;
        Tag newTag = new Tag(newTagName);

        TagEditCommand tagEditCommand = prepareCommand(oldTagName, newTagName);

        String expectedMessage = String.format(MESSAGE_EDIT_TAG_SUCCESS, oldTag.toString(), newTag.toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.editTag(oldTag, newTag);

        assertCommandSuccess(tagEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editInvalidTagUnfilteredList_throwsCommandException() throws Exception {
        String oldTagName = DUMMY_TAG;
        Tag oldTag = new Tag(oldTagName);
        String newTagName = DUMMY_TAG_TWO;
        Tag newTag = new Tag(newTagName);

        TagEditCommand tagEditCommand = prepareCommand(oldTagName, newTagName);

        String expectedMessage = Messages.MESSAGE_UNKNOWN_TAG_NAME;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.editTag(oldTag, newTag);

        assertCommandFailure(tagEditCommand, model, expectedMessage);
    }

    @Test
    public void execute_editIntoNonAlphanumericTag_failure() throws Exception {
        String oldTagName = DUMMY_TAG;
        String newTagName = TAG_THAT_FAILS_ALPHANUMERIC;

        String expectedMessage = Tag.MESSAGE_TAG_CONSTRAINTS;
        TagEditCommand tagEditCommand = prepareCommand(oldTagName, newTagName);

        assertCommandFailure(tagEditCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        Person aliceFriend = new PersonBuilder().withName("Alice").withTags("friends").build();
        Person bobColleague = new PersonBuilder().withName("Bob").withTags("colleagues").build();
        TagEditCommand editFriendsCommand = new TagEditCommand("friends", DUMMY_TAG);
        TagEditCommand editColleaguesCommand = new TagEditCommand("colleagues", DUMMY_TAG);

        // same object -> returns true
        assertTrue(editFriendsCommand.equals(editFriendsCommand));

        // same values -> returns true
        TagEditCommand editFriendsCommandCopy = new TagEditCommand("friends", DUMMY_TAG);
        assertTrue(editFriendsCommand.equals(editFriendsCommandCopy));

        // different types -> returns false
        assertFalse(editColleaguesCommand.equals(1));

        // null -> returns false
        assertFalse(editColleaguesCommand.equals(null));

        // different person -> returns false
        assertFalse(editColleaguesCommand.equals(editFriendsCommand));
    }

    /**
     * Returns a {@code TagEditCommand} with the parameters {@code oldTagName, newTagName}.
     */
    private TagEditCommand prepareCommand(String oldTagName, String newTagName) {
        TagEditCommand tagEditCommand = new TagEditCommand(oldTagName, newTagName);
        tagEditCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return tagEditCommand;
    }
}
