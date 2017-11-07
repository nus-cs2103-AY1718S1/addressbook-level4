# Chng-Zhi-Xuan
###### \java\seedu\address\logic\commands\BookmarkCommandTest.java
``` java
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
```
###### \java\seedu\address\logic\commands\ClearBookmarkCommandTest.java
``` java
public class ClearBookmarkCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, ClearBookmarkCommand.MESSAGE_CLEAR_BOOKMARK_SUCCESS, model);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, ClearBookmarkCommand.MESSAGE_CLEAR_BOOKMARK_SUCCESS, model);
    }

    @Test
    public void equals() {
        ClearBookmarkCommand clearBookmarkFirstCommand = new ClearBookmarkCommand();

        // same object -> returns true
        assertTrue(clearBookmarkFirstCommand.equals(clearBookmarkFirstCommand));

        // same values -> returns true
        ClearBookmarkCommand clearBookmarkFirstCommandCopy = new ClearBookmarkCommand();
        assertTrue(clearBookmarkFirstCommand.equals(clearBookmarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(clearBookmarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(clearBookmarkFirstCommand.equals(null));
    }

    private ClearBookmarkCommand prepareCommand(Model model) {
        ClearBookmarkCommand command = new ClearBookmarkCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseIndexPosition_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndexFromPosition(" a 2 ", 0);
    }
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseIndexPosition_outOfRangeInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndexFromPosition(" 0 1 2 3 4", 5);
    }
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseIndexPosition_validInput_success() throws Exception {

        assertEquals(INDEX_FIRST_PLACE, ParserUtil.parseIndexFromPosition("1 2", 0));

        assertEquals(INDEX_FIRST_PLACE, ParserUtil.parseIndexFromPosition("   1 2   ",
                                                                                                0));
    }
```
