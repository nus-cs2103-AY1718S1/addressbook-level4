package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPlaceOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PLACE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PLACE;
import static seedu.address.testutil.TypicalPlaces.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.place.ReadOnlyPlace;
import seedu.address.model.tag.Tag;
//@@author Chng-Zhi-Xuan
public class BookmarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Tag bookmarkTag;
    private final String bookmarkTagString = "Bookmarked";

    public BookmarkCommandTest() {
        createTag();
    }

    /**
     *  Creates Tag during class construction
     */
    private void createTag() {
        try {
            bookmarkTag = new Tag(bookmarkTagString);
        } catch (IllegalValueException ive) {
            assert false : "Tag cannot be invalid";
        }
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {

        ReadOnlyPlace placeToBookmark = model.getFilteredPlaceList().get(INDEX_FIRST_PLACE.getZeroBased());
        BookmarkCommand bookmarkCommand = prepareCommand(INDEX_FIRST_PLACE);

        String expectedMessage = String.format(bookmarkCommand.MESSAGE_BOOKMARK_SUCCESS, placeToBookmark);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTag(placeToBookmark, bookmarkTag);

        assertCommandSuccess(bookmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPlaceList().size() + 1);
        BookmarkCommand bookmarkCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(bookmarkCommand, model, Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPlaceOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PLACE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPlaceList().size());

        BookmarkCommand bookmarkCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(bookmarkCommand, model, Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        BookmarkCommand bookmarkFirstCommand = new BookmarkCommand(INDEX_FIRST_PLACE);
        BookmarkCommand bookmarkSecondCommand = new BookmarkCommand(INDEX_SECOND_PLACE);

        // same object -> returns true
        assertTrue(bookmarkFirstCommand.equals(bookmarkFirstCommand));

        // same values -> returns true
        BookmarkCommand bookmarkFirstCommandCopy = new BookmarkCommand(INDEX_FIRST_PLACE);
        assertTrue(bookmarkFirstCommand.equals(bookmarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(bookmarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(bookmarkFirstCommand.equals(null));

        // different place -> returns false
        assertFalse(bookmarkFirstCommand.equals(bookmarkSecondCommand));
    }

    private BookmarkCommand prepareCommand(Index index) {
        BookmarkCommand bookmarkCommand = new BookmarkCommand(index);
        bookmarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return bookmarkCommand;
    }

    private void showNoPlace(Model model) {
        model.updateFilteredPlaceList(p -> false);

        assert model.getFilteredPlaceList().isEmpty();
    }
}
