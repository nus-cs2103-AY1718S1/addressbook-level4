# junming403
###### /java/seedu/address/logic/commands/DeleteCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        ReadOnlyLesson lessonToDelete = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_LESSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_LESSON_SUCCESS, lessonToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteLesson(lessonToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexMarkedList_success() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        ReadOnlyLesson lessonToDelete = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        model.bookmarkLesson(lessonToDelete);
        model.updateFilteredLessonList(new MarkedListPredicate());

        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_LESSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_LESSON_SUCCESS, lessonToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredLessonList(new MarkedListPredicate());
        expectedModel.deleteLesson(lessonToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexModuleList_throwsCommandException() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        model.updateFilteredLessonList(new UniqueModuleCodePredicate(model.getUniqueCodeSet()));
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexMarkedList_throwsCommandException() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        model.updateFilteredLessonList(new MarkedListPredicate());
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexLocationList_throwsCommandException() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);
        model.updateFilteredLessonList(new UniqueLocationPredicate(model.getUniqueLocationSet()));
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        showFirstLessonOnly(model);

        ReadOnlyLesson lessonToDelete = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_LESSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_LESSON_SUCCESS, lessonToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteLesson(lessonToDelete);
        showNoLesson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        showFirstLessonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_LESSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getLessonList().size());

        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_LESSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_LESSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_LESSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different delete command -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void execute_validIndexDeleteByModule_success() throws Exception {

        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        ReadOnlyLesson lessonToDelete = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_LESSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_LESSON_WITH_MODULE_SUCCESS,
                lessonToDelete.getCode());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        List<ReadOnlyLesson> lastShownList = expectedModel.getFilteredLessonList();
        for (int i = 0; i < lastShownList.size(); i++) {
            ReadOnlyLesson lesson = lastShownList.get(i);
            if (lesson.getCode().equals(lessonToDelete.getCode())) {
                expectedModel.deleteLesson(lesson);
                i--;
            }
        }

        expectedModel.updateFilteredLessonList(new UniqueModuleCodePredicate(expectedModel.getUniqueCodeSet()));

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexDeleteByLocation_success() throws Exception {

        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);
        ReadOnlyLesson lessonToDelete = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_LESSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_LESSON_WITH_LOCATION_SUCCESS,
                lessonToDelete.getLocation());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);

        List<ReadOnlyLesson> lastShownList = expectedModel.getFilteredLessonList();

        for (int i = 0; i < lastShownList.size(); i++) {
            ReadOnlyLesson lesson = lastShownList.get(i);
            if (lesson.getLocation().equals(lessonToDelete.getLocation())) {
                expectedModel.deleteLesson(lesson);
                i--;
            }
        }

        expectedModel.updateFilteredLessonList(new UniqueLocationPredicate(expectedModel.getUniqueLocationSet()));
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareCommand(Index index) {
        DeleteCommand deleteCommand = new DeleteCommand(index);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoLesson(Model model) {
        model.updateFilteredLessonList(p -> false);

        assert model.getFilteredLessonList().isEmpty();
    }
}
```
###### /java/seedu/address/logic/commands/DeleteRemarkCommandTest.java
``` java
public class DeleteRemarkCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private RemarkCommand remarkCommand;

    @Before
    public void setUp() throws CommandException {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        remarkCommand = new RemarkCommand(INDEX_FIRST_LESSON, "This is a sample remark");
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        remarkCommand.executeUndoableCommand();
    }

    @Test
    public void execute_validIndex_success() throws Exception {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS,
                "This is a sample remark");

        RemarkCommand deleteRemarkCommand = prepareCommand(INDEX_FIRST_LESSON);

        assertCommandSuccess(deleteRemarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        Index outOfBoundIndex = Index.fromZeroBased(model.getFilteredLessonList().size());
        RemarkCommand deleteRemarkCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteRemarkCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    /**
     * Returns a {@code RemarkCommand} with the parameter {@code index}.
     */
    private RemarkCommand prepareCommand(Index index) {
        RemarkCommand deleteRemarkCommand = new RemarkCommand(index);
        deleteRemarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteRemarkCommand;
    }
}
```
###### /java/seedu/address/logic/commands/EditCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Before
    public void reset_model() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {

        model.updateFilteredLessonList(
                new FixedCodePredicate(
                        model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode()));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredLessonList(
                new FixedCodePredicate(
                        expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode()));


        Lesson editedLesson = new LessonBuilder().build();
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(editedLesson).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_LESSON, descriptor);
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);

        expectedModel.updateLesson(
                model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()), editedLesson);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LESSON_SUCCESS, editedLesson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastLesson = Index.fromOneBased(model.getFilteredLessonList().size());
        model.updateFilteredLessonList(
                new FixedCodePredicate(model.getFilteredLessonList().get(indexLastLesson.getZeroBased()).getCode()));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.updateFilteredLessonList(
                new FixedCodePredicate(
                        expectedModel.getFilteredLessonList().get(indexLastLesson.getZeroBased()).getCode()));

        indexLastLesson = Index.fromOneBased(model.getFilteredLessonList().size());

        ReadOnlyLesson lastLesson = model.getFilteredLessonList().get(indexLastLesson.getZeroBased());

        LessonBuilder lessonInList = new LessonBuilder(lastLesson);
        Lesson editedLesson = lessonInList.withCode(VALID_CODE_CS2101).withClassType(VALID_CLASSTYPE_CS2101)
                .withGroup(VALID_GROUP_CS2101).build();

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withCode(VALID_CODE_CS2101)
                .withClassType(VALID_CLASSTYPE_CS2101).withGroup(VALID_GROUP_CS2101).build();
        EditCommand editCommand = prepareCommand(indexLastLesson, descriptor);
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LESSON_SUCCESS, editedLesson);

        expectedModel.updateLesson(lastLesson, editedLesson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {

        model.updateFilteredLessonList(
                new FixedCodePredicate(
                        model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode()));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.updateFilteredLessonList(
                new FixedCodePredicate(
                        expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode()));


        EditCommand editCommand = prepareCommand(INDEX_FIRST_LESSON, new EditLessonDescriptor());
        ReadOnlyLesson editedLesson = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LESSON_SUCCESS, editedLesson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstLessonOnly(model);

        ReadOnlyLesson lessonInFilteredList = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        Lesson editedLesson = new LessonBuilder(lessonInFilteredList).withCode(VALID_CODE_CS2101).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_LESSON,
                new EditLessonDescriptorBuilder().withCode(VALID_CODE_CS2101).build());
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LESSON_SUCCESS, editedLesson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        showFirstLessonOnly(expectedModel);

        expectedModel.updateLesson(model.getFilteredLessonList().get(0), editedLesson);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editSpecifiedModule_success() throws Exception {
        Lesson editedLesson = new LessonBuilder().build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_LESSON, editedLesson.getCode().fullCodeName);
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedLesson.getCode());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);

        for (ReadOnlyLesson p : expectedModel.getFilteredLessonList()) {
            if (p.getCode().equals(model.getFilteredLessonList().get(0).getCode())) {
                ReadOnlyLesson editTo = new Lesson(p.getClassType(), p.getLocation(), p.getGroup(),
                        p.getTimeSlot(), editedLesson.getCode(), p.getLecturers());
                expectedModel.updateLesson(p, editTo);
            }
        }

        expectedModel.updateFilteredLessonList(new UniqueModuleCodePredicate(expectedModel.getUniqueCodeSet()));
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editSpecifiedLocation_success() throws Exception {
        Lesson editedLesson = new LessonBuilder().build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_LESSON, editedLesson.getLocation().value);
        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LOCATION_SUCCESS, editedLesson.getLocation());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        for (ReadOnlyLesson p : expectedModel.getFilteredLessonList()) {
            if (p.getLocation().equals(model.getFilteredLessonList().get(0).getLocation())) {
                ReadOnlyLesson editTo = new Lesson(p.getClassType(), editedLesson.getLocation(), p.getGroup(),
                        p.getTimeSlot(), p.getCode(), p.getLecturers());
                expectedModel.updateLesson(p, editTo);
            }
        }
        expectedModel.updateFilteredLessonList(new UniqueLocationPredicate(expectedModel.getUniqueLocationSet()));
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editLessonInMarkedList_success() throws Exception {
        model.bookmarkLesson(model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()));
        model.updateFilteredLessonList(new MarkedListPredicate());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredLessonList(new MarkedListPredicate());


        Lesson editedLesson = new LessonBuilder().build();
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(editedLesson).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_LESSON, descriptor);
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);

        expectedModel.updateLesson(
                model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()), editedLesson);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LESSON_SUCCESS, editedLesson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_duplicateLessonUnfilteredList_failure() {

        model.updateFilteredLessonList(
                new FixedCodePredicate(model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode()));

        Lesson firstLesson = new Lesson(model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()));
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(firstLesson).build();
        EditCommand editCommand = prepareCommand(INDEX_SECOND_LESSON, descriptor);
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_BOOKEDSLOT);
    }

    @Test
    public void execute_duplicateLessonFilteredList_failure() {
        showFirstLessonOnly(model);

        // edit lesson in filtered list into a duplicate in address book
        ReadOnlyLesson lessonInList = model.getAddressBook().getLessonList().get(INDEX_SECOND_LESSON.getZeroBased());
        EditCommand editCommand = prepareCommand(INDEX_FIRST_LESSON,
                new EditLessonDescriptorBuilder(lessonInList).build());
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_LESSON);
    }

    @Test
    public void execute_invalidLessonIndexUnfilteredList_failure() {

        model.updateFilteredLessonList(
                new FixedCodePredicate(model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode()));
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withCode(VALID_CODE_CS2101).build();
        EditCommand editCommand = prepareCommand(outOfBoundIndex, descriptor);
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidLessonIndexFilteredList_failure() {
        showFirstLessonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_LESSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getLessonList().size());

        EditCommand editCommand = prepareCommand(outOfBoundIndex,
                new EditLessonDescriptorBuilder().withCode(VALID_CODE_CS2101).build());
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_LESSON, DESC_MA1101R);

        // same values -> returns true
        EditLessonDescriptor copyDescriptor = new EditLessonDescriptor(DESC_MA1101R);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_LESSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_LESSON, DESC_MA1101R)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_LESSON, DESC_CS2101)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditCommand prepareCommand(Index index, EditLessonDescriptor descriptor) {
        EditCommand editCommand = new EditCommand(index, descriptor);
        editCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editCommand;
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code attributeValue}
     */
    private EditCommand prepareCommand(Index index, String attributeValue) {
        EditCommand editCommand = new EditCommand(index, attributeValue);
        editCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editCommand;
    }
}
```
###### /java/seedu/address/logic/commands/ListCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCommand listModuleCommand;
    private ListCommand listLocationCommand;
    private ListCommand listMarkedCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listModuleCommand = new ListCommand("module");
        listLocationCommand = new ListCommand("location");
        listMarkedCommand = new ListCommand("marked");

        listModuleCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        listLocationCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        listMarkedCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_list_marked() throws DuplicateLessonException {

        expectedModel.updateFilteredLessonList(new MarkedListPredicate());
        assertCommandSuccess(listMarkedCommand, model, String.format(ListCommand.MESSAGE_SUCCESS,
                ListCommand.MARKED_LIST_KEYWORD), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsAllModules() {
        expectedModel.updateFilteredLessonList(new UniqueModuleCodePredicate(expectedModel.getUniqueCodeSet()));
        assertCommandSuccess(listModuleCommand, model, String.format(ListCommand.MESSAGE_SUCCESS,
                ListCommand.MODULE_KEYWORD), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsAllLocations() {
        expectedModel.updateFilteredLessonList(new UniqueLocationPredicate(expectedModel.getUniqueLocationSet()));
        assertCommandSuccess(listLocationCommand, model, String.format(ListCommand.MESSAGE_SUCCESS,
                ListCommand.LOCATION_KEYWORD), expectedModel);
    }


}
```
###### /java/seedu/address/logic/commands/MarkCommandTest.java
``` java
public class MarkCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws DuplicateLessonException {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        ReadOnlyLesson lessonToMark = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        MarkCommand markCommand = prepareCommand(INDEX_FIRST_LESSON);

        String expectedMessage = String.format(MarkCommand.MESSAGE_BOOKMARK_LESSON_SUCCESS, lessonToMark);

        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.bookmarkLesson(expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()));

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws DuplicateLessonException {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        model.updateFilteredLessonList(new FixedCodePredicate(
                model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode()));
        ReadOnlyLesson lessonToMark = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        MarkCommand markCommand = prepareCommand(INDEX_FIRST_LESSON);

        String expectedMessage = String.format(MarkCommand.MESSAGE_BOOKMARK_LESSON_SUCCESS, lessonToMark);

        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.updateFilteredLessonList(new FixedCodePredicate(
                expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode()));
        expectedModel.bookmarkLesson(expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()));

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() throws DuplicateLessonException {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        model.updateFilteredLessonList(new FixedCodePredicate(
                model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode()));
        Index invalidIndex = Index.fromZeroBased(model.getFilteredLessonList().size() + 1);
        MarkCommand markCommand = prepareCommand(invalidIndex);

        assertCommandFailure(markCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidListingTypeModule_failure() throws DuplicateLessonException {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        model.updateFilteredLessonList(new UniqueModuleCodePredicate(model.getUniqueCodeSet()));
        MarkCommand markCommand = prepareCommand(INDEX_FIRST_LESSON);

        assertCommandFailure(markCommand, model, MarkCommand.MESSAGE_WRONG_LISTING_UNIT_FAILURE);
    }

    @Test
    public void execute_invalidListingTypeLocation_failure() throws DuplicateLessonException {
        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);
        model.updateFilteredLessonList(new UniqueLocationPredicate(model.getUniqueLocationSet()));
        MarkCommand markCommand = prepareCommand(INDEX_FIRST_LESSON);

        assertCommandFailure(markCommand, model, MarkCommand.MESSAGE_WRONG_LISTING_UNIT_FAILURE);
    }



    private MarkCommand prepareCommand(Index index) {
        MarkCommand markCommand = new MarkCommand(index);
        markCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return markCommand;
    }
}
```
###### /java/seedu/address/logic/commands/RemarkCommandTest.java
``` java
public class RemarkCommandTest {

    private static final String SAMPLE_REMARK = "This is a sample remark";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private RemarkCommand remarkCommand;


    @Test
    public void execute_validIndex_success() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Code code = model.getFilteredLessonList().get(INDEX_SECOND_LESSON.getZeroBased()).getCode();
        Remark remarkToAdd = new Remark(SAMPLE_REMARK, code);
        RemarkCommand remarkCommand = prepareCommand(INDEX_SECOND_LESSON, SAMPLE_REMARK);
        expectedModel.addRemark(remarkToAdd);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_REMARK_MODULE_SUCCESS, code);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndex_throwsCommandException() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        Index outOfBoundIndex = Index.fromZeroBased(model.getFilteredLessonList().size());
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, SAMPLE_REMARK);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_longRemark_throwsCommandException() throws Exception {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_LESSON, getLongString());

        assertCommandFailure(remarkCommand, model, Remark.MESSAGE_REMARK_CONSTRAINTS);
    }

    @Test
    public void execute_validIndex_deleteSuccess() throws Exception {
        remarkCommand = new RemarkCommand(INDEX_FIRST_LESSON, "This is a sample remark");
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        remarkCommand.executeUndoableCommand();

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS,
                "This is a sample remark");

        RemarkCommand deleteRemarkCommand = prepareCommand(INDEX_FIRST_LESSON);

        assertCommandSuccess(deleteRemarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeDelete_invalidIndex_throwsCommandException() throws Exception {
        remarkCommand = new RemarkCommand(INDEX_FIRST_LESSON, "This is a sample remark");
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        remarkCommand.executeUndoableCommand();

        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        Index outOfBoundIndex = Index.fromZeroBased(model.getFilteredLessonList().size());
        RemarkCommand deleteRemarkCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteRemarkCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    /**
     * Returns a {@code RemarkCommand} with the parameter {@code index}.
     */
    private RemarkCommand prepareCommand(Index index) {
        RemarkCommand deleteRemarkCommand = new RemarkCommand(index);
        deleteRemarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteRemarkCommand;
    }


    /**
     * Returns a {@code RemarkCommand} with the parameter {@code index}.
     */
    private RemarkCommand prepareCommand(Index index, String remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, remark);
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }

    /**
     * Get a String that is longger than 150 characters.
     */
    private String getLongString() {
        String ouput = "";
        for (int i = 0; i < 1000; i++) {
            ouput += "abc";
        }
        return ouput;
    }

}
```
###### /java/seedu/address/logic/commands/UnmarkCommandTest.java
``` java
public class UnmarkCommandTest {

    private Model model;

    @Test
    public void execute_validIndexUnfilteredList_success() throws DuplicateLessonException {
        model = prepareModel();
        ListingUnit.setCurrentListingUnit(LESSON);
        ReadOnlyLesson lessonToUnmark = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        UnmarkCommand unmarkCommand = prepareCommand(INDEX_FIRST_LESSON);

        String expectedMessage = String.format(UnmarkCommand.MESSAGE_UNBOOKMARK_LESSON_SUCCESS, lessonToUnmark);

        Model expectedModel = prepareModel();
        expectedModel.unBookmarkLesson(expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()));

        assertCommandSuccess(unmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws DuplicateLessonException {
        model = prepareModel();
        model.updateFilteredLessonList(
                new FixedCodePredicate(model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode()));
        ListingUnit.setCurrentListingUnit(LESSON);
        ReadOnlyLesson lessonToUnmark = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        UnmarkCommand unmarkCommand = prepareCommand(INDEX_FIRST_LESSON);

        String expectedMessage = String.format(UnmarkCommand.MESSAGE_UNBOOKMARK_LESSON_SUCCESS, lessonToUnmark);

        Model expectedModel = prepareModel();
        expectedModel.updateFilteredLessonList(
                new FixedCodePredicate(
                        expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode()));
        expectedModel.unBookmarkLesson(expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()));

        assertCommandSuccess(unmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() throws DuplicateLessonException {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        model = prepareModel();
        model.updateFilteredLessonList(new FixedCodePredicate(
                model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode()));
        Index invalidIndex = Index.fromZeroBased(model.getFilteredLessonList().size() + 1);
        UnmarkCommand unmarkCommand = prepareCommand(invalidIndex);

        assertCommandFailure(unmarkCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidListingTypeModule_failure() throws DuplicateLessonException {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        model = prepareModel();
        model.updateFilteredLessonList(new UniqueModuleCodePredicate(model.getUniqueCodeSet()));
        UnmarkCommand unmarkCommand = prepareCommand(INDEX_FIRST_LESSON);

        assertCommandFailure(unmarkCommand, model, UnmarkCommand.MESSAGE_WRONG_LISTING_UNIT_FAILURE);
    }

    @Test
    public void execute_invalidListingTypeLocation_failure() throws DuplicateLessonException {
        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);
        model = prepareModel();
        model.updateFilteredLessonList(new UniqueLocationPredicate(model.getUniqueLocationSet()));
        UnmarkCommand unmarkCommand = prepareCommand(INDEX_FIRST_LESSON);

        assertCommandFailure(unmarkCommand, model, UnmarkCommand.MESSAGE_WRONG_LISTING_UNIT_FAILURE);
    }

    private UnmarkCommand prepareCommand(Index index) {
        UnmarkCommand unmarkCommand = new UnmarkCommand(index);
        unmarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unmarkCommand;
    }

    /**
     * Prepare a model of all lessons inside being marked.
     */
    private Model prepareModel() throws DuplicateLessonException {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ObservableList<ReadOnlyLesson> lessonList = model.getFilteredLessonList();
        for (ReadOnlyLesson lesson : lessonList) {
            model.bookmarkLesson(lesson);
        }
        return model;
    }
}
```
###### /java/seedu/address/logic/commands/ViewCommandTest.java
``` java
public class ViewCommandTest {

    private Model model;
    private ViewCommand viewCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        viewCommand = new ViewCommand(INDEX_FIRST_LESSON);
        viewCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_viewSpecifiedLesson() {

        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);

        assertCommandSuccess(viewCommand, String.format(viewCommand.MESSAGE_VIEW_LESSON_SUCCESS,
                model.getFilteredLessonList().get(0)), Arrays.asList(MA1101R_L1));
    }

    @Test
    public void execute_viewAllLessons_withSpecifiedModule() {

        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);

        assertCommandSuccess(viewCommand, String.format(viewCommand.MESSAGE_VIEW_MODULE_SUCCESS,
                model.getFilteredLessonList().get(0).getCode()), Arrays.asList(MA1101R_L1,
                MA1101R_L2, MA1101R_T1, MA1101R_T2));
    }

    @Test
    public void execute_viewAllLesson_withSpecifiedLocation() {

        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);

        assertCommandSuccess(viewCommand, String.format(viewCommand.MESSAGE_VIEW_LOCATION_SUCCESS,
                model.getFilteredLessonList().get(0).getLocation()), Arrays.asList(MA1101R_L1, MA1101R_L2));
    }


    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyLesson>} is equal to {@code expectedList}<br>
     * - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(ViewCommand command, String expectedMessage, List<ReadOnlyLesson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = null;
        try {
            commandResult = command.execute();
        } catch (CommandException e) {
            assert false : "The exception is unexpected";
        }

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredLessonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

}
```
###### /java/seedu/address/logic/parser/EditCommandParserTest.java
``` java
public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_LECTURER;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_CODE_MA1101R, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        // negative index
        assertParseFailure(parser, "-5" + CODE_DESC_MA1101R, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + CODE_DESC_MA1101R, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        assertParseFailure(parser, "1" + INVALID_CODE_DESC,
                Code.MESSAGE_CODE_CONSTRAINTS); // invalid module code
        assertParseFailure(parser, "1" + INVALID_CLASSTYPE_DESC,
                ClassType.MESSAGE_CLASSTYPE_CONSTRAINTS); // invalid class type
        assertParseFailure(parser, "1" + INVALID_VENUE_DESC,
                Location.MESSAGE_LOCATION_CONSTRAINTS); // invalid venue
        assertParseFailure(parser, "1" + INVALID_GROUP_DESC,
                Group.MESSAGE_GROUP_CONSTRAINTS); // invalid group
        assertParseFailure(parser, "1" + INVALID_TIMESLOT_DESC,
                TimeSlot.MESSAGE_TIMESLOT_CONSTRAINTS); // invalid time slot

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_CLASSTYPE_DESC + VENUE_DESC_MA1101R,
                ClassType.MESSAGE_CLASSTYPE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + CLASSTYPE_DESC_MA1101R + INVALID_CLASSTYPE_DESC,
                ClassType.MESSAGE_CLASSTYPE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + LECTURER_DESC_MA1101R + LECTURER_DESC_CS2101 + TAG_EMPTY,
                Lecturer.MESSAGE_LECTURER_CONSTRAINTS);
        assertParseFailure(parser, "1" + LECTURER_DESC_MA1101R + TAG_EMPTY + LECTURER_DESC_CS2101,
                Lecturer.MESSAGE_LECTURER_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + LECTURER_DESC_MA1101R + LECTURER_DESC_CS2101,
                Lecturer.MESSAGE_LECTURER_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_CODE_DESC + INVALID_CLASSTYPE_DESC + INVALID_VENUE_DESC
                        + INVALID_GROUP_DESC + INVALID_TIMESLOT_DESC,
                ClassType.MESSAGE_CLASSTYPE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_LESSON;
        String userInput = targetIndex.getOneBased() + CLASSTYPE_DESC_CS2101 + LECTURER_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + CODE_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + LECTURER_DESC_CS2101;

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withCode(VALID_CODE_MA1101R)
                .withClassType(VALID_CLASSTYPE_CS2101).withLocation(VALID_VENUE_MA1101R)
                .withGroup(VALID_GROUP_MA1101R).withTimeSlot(VALID_TIMESLOT_MA1101R)
                .withLecturers(VALID_LECTURER_MA1101R, VALID_LECTURER_CS2101).build();
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_LESSON;
        String userInput = targetIndex.getOneBased() + CLASSTYPE_DESC_CS2101 + VENUE_DESC_MA1101R;

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withClassType(VALID_CLASSTYPE_CS2101)
                .withLocation(VALID_VENUE_MA1101R).build();
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        // code
        Index targetIndex = INDEX_SECOND_LESSON;
        String userInput = targetIndex.getOneBased() + CODE_DESC_MA1101R;
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withCode(VALID_CODE_MA1101R).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // class type
        userInput = targetIndex.getOneBased() + CLASSTYPE_DESC_MA1101R;
        descriptor = new EditLessonDescriptorBuilder().withClassType(VALID_CLASSTYPE_MA1101R).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // venue
        userInput = targetIndex.getOneBased() + VENUE_DESC_MA1101R;
        descriptor = new EditLessonDescriptorBuilder().withLocation(VALID_VENUE_MA1101R).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // group
        userInput = targetIndex.getOneBased() + GROUP_DESC_MA1101R;
        descriptor = new EditLessonDescriptorBuilder().withGroup(VALID_GROUP_MA1101R).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // time slot
        userInput = targetIndex.getOneBased() + TIMESLOT_DESC_MA1101R;
        descriptor = new EditLessonDescriptorBuilder().withTimeSlot(VALID_TIMESLOT_MA1101R).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + LECTURER_DESC_MA1101R;
        descriptor = new EditLessonDescriptorBuilder().withLecturers(VALID_LECTURER_MA1101R).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_LESSON;
        String userInput = targetIndex.getOneBased()  + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R
                + GROUP_DESC_MA1101R  + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R
                + GROUP_DESC_MA1101R
                + CLASSTYPE_DESC_CS2101 + VENUE_DESC_CS2101 + GROUP_DESC_CS2101 + LECTURER_DESC_MA1101R
                + LECTURER_DESC_CS2101;

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withClassType(VALID_CLASSTYPE_CS2101)
                .withLocation(VALID_VENUE_CS2101).withGroup(VALID_GROUP_CS2101)
                .withLecturers(VALID_LECTURER_CS2101, VALID_LECTURER_MA1101R).build();
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        // no other valid values specified
        Index targetIndex = INDEX_THIRD_LESSON;
        String userInput = targetIndex.getOneBased() + INVALID_CLASSTYPE_DESC + CLASSTYPE_DESC_CS2101;
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
                .withClassType(VALID_CLASSTYPE_CS2101).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + VENUE_DESC_MA1101R + INVALID_CLASSTYPE_DESC
                + GROUP_DESC_CS2101
                + CLASSTYPE_DESC_CS2101;
        descriptor = new EditLessonDescriptorBuilder().withClassType(VALID_CLASSTYPE_CS2101)
                .withGroup(VALID_GROUP_CS2101)
                .withLocation(VALID_VENUE_MA1101R).build();
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetLecturers_failure() {
        Index targetIndex = INDEX_THIRD_LESSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withLecturers().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);

        assertParseFailure(parser, userInput, Lecturer.MESSAGE_LECTURER_CONSTRAINTS);
    }

    @Test
    public void parse_editModule() {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        Index targetIndex = INDEX_FIRST_LESSON;
        String userInput = targetIndex.getOneBased()  + " " + VALID_CODE_MA1101R;

        EditCommand expectedCommand = new EditCommand(targetIndex, VALID_CODE_MA1101R);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_editLocation() {
        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);
        Index targetIndex = INDEX_FIRST_LESSON;
        String userInput = targetIndex.getOneBased()  + " " + VALID_VENUE_MA1101R;

        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);
        EditCommand expectedCommand = new EditCommand(targetIndex, VALID_VENUE_MA1101R);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### /java/seedu/address/logic/parser/ListCommandParserTest.java
``` java
public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_listLocation_returnsListCommand() {
        assertParseSuccess(parser, " location", new ListCommand(ListCommand.LOCATION_KEYWORD));
    }

    @Test
    public void parse_listModule_returnsListCommand() {
        assertParseSuccess(parser, " module", new ListCommand(ListCommand.MODULE_KEYWORD));
    }

    @Test
    public void parse_listMarked_returnsListCommand() {
        assertParseSuccess(parser, " marked", new ListCommand(ListCommand.MARKED_LIST_KEYWORD));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "list asda", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/MarkCommandParserTest.java
``` java
public class MarkCommandParserTest {
    private MarkCommandParser parser = new MarkCommandParser();

    @Test
    public void parse_validArgs_returnsViewCommand() {
        assertParseSuccess(parser, "1", new MarkCommand(INDEX_FIRST_LESSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/RemarkCommandParserTest.java
``` java
public class RemarkCommandParserTest {
    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_validArgs_returnsRemarkCommand() {
        assertParseSuccess(parser, "1 Sample Remark",
                new RemarkCommand(INDEX_FIRST_LESSON, "Sample Remark"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a dqswd",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_deleteRemark_returnsDeleteRemarkCommand() {
        assertParseSuccess(parser, "-d 2", new RemarkCommand(INDEX_SECOND_LESSON));
    }

}
```
###### /java/seedu/address/logic/parser/UnmarkCommandParserTest.java
``` java
public class UnmarkCommandParserTest {
    private UnmarkCommandParser parser = new UnmarkCommandParser();

    @Test
    public void parse_validArgs_returnsViewCommand() {
        assertParseSuccess(parser, "1", new UnmarkCommand(INDEX_FIRST_LESSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/ViewCommandParserTest.java
``` java
public class ViewCommandParserTest {

    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_validArgs_returnsViewCommand() {
        assertParseSuccess(parser, "1", new ViewCommand(INDEX_FIRST_LESSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/model/lesson/predicate/FixedCodePredicateTest.java
``` java
public class FixedCodePredicateTest {
    @Test
    public void equals() {

        Code firstCode = null;
        Code secondCode = null;

        try {
            firstCode = new Code(VALID_CODE_MA1101R);
            secondCode = new Code(VALID_CODE_CS2101);
        } catch (IllegalValueException e) {
            assert false : "The code shouldn't invalid";
        }

        FixedCodePredicate firstPredicate = new FixedCodePredicate(firstCode);
        FixedCodePredicate secondPredicate = new FixedCodePredicate(secondCode);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FixedCodePredicate firstPredicateCopy = new FixedCodePredicate(firstCode);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different address -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_isTheCodeGiven_returnsTrue() {

        Code code = null;

        try {
            code = new Code("MA1101R");
        } catch (IllegalValueException e) {
            assert false : "The address shouldn't invalid";
        }

        FixedCodePredicate predicate = new FixedCodePredicate(code);
        assertTrue(predicate.test(new LessonBuilder().build()));

    }

    @Test
    public void test_isTheCodeCGiven_returnsFalse() {

        try {
            Code code = new Code("GEQ1000");
            FixedCodePredicate predicate = new FixedCodePredicate(code);
            assertFalse(predicate.test(new LessonBuilder().build()));
        } catch (IllegalValueException e) {
            assert false : "The code shouldn't invalid";
        }
    }
}
```
###### /java/seedu/address/model/lesson/predicate/FixedLocationPredicateTest.java
``` java
public class FixedLocationPredicateTest {

    @Test
    public void equals() {

        Location firstLocation = null;
        Location secondLocation = null;

        try {
            firstLocation = new Location(VALID_VENUE_MA1101R);
            secondLocation = new Location(VALID_VENUE_CS2101);
        } catch (IllegalValueException e) {
            assert false : "The location shouldn't invalid";
        }

        FixedLocationPredicate firstPredicate = new FixedLocationPredicate(firstLocation);
        FixedLocationPredicate secondPredicate = new FixedLocationPredicate(secondLocation);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FixedLocationPredicate firstPredicateCopy = new FixedLocationPredicate(firstLocation);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different address -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_isTheAddressGiven_returnsTrue() {

        Location location = null;

        try {
            location = new Location("LT27");
        } catch (IllegalValueException e) {
            assert false : "The location shouldn't invalid";
        }

        FixedLocationPredicate predicate = new FixedLocationPredicate(location);
        assertTrue(predicate.test(new LessonBuilder().build()));

    }

    @Test
    public void test_isTheAddressGiven_returnsFalse() {

        try {
            Location location = new Location("LT1");
            FixedLocationPredicate predicate = new FixedLocationPredicate(location);
            assertFalse(predicate.test(new LessonBuilder().build()));
        } catch (IllegalValueException e) {
            assert false : "The location shouldn't invalid";
        }
    }
}
```
###### /java/seedu/address/model/lesson/predicate/SelectedStickyNotePredicateTest.java
``` java
public class SelectedStickyNotePredicateTest {

    @Test
    public void equals() {

        Code firstCode = null;
        Code secondCode = null;

        try {
            firstCode = new Code(VALID_CODE_MA1101R);
            secondCode = new Code(VALID_CODE_CS2101);
        } catch (IllegalValueException e) {
            assert false : "The code shouldn't invalid";
        }

        SelectedStickyNotePredicate firstPredicate = new SelectedStickyNotePredicate(firstCode);
        SelectedStickyNotePredicate secondPredicate = new SelectedStickyNotePredicate(secondCode);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SelectedStickyNotePredicate firstPredicateCopy = new SelectedStickyNotePredicate(firstCode);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different address -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_isTheCodeGiven_returnsTrue() throws IllegalValueException {

        Code code = null;

        try {
            code = new Code("MA1101R");
        } catch (IllegalValueException e) {
            assert false : "The address shouldn't invalid";
        }

        SelectedStickyNotePredicate predicate = new SelectedStickyNotePredicate(code);
        assertTrue(predicate.test(new Remark("Remark content", code)));

    }

    @Test
    public void test_isTheCodeCGiven_returnsFalse() {

        try {
            Code code = new Code("GEQ1000");
            Code secondCode = new Code("GEQ1001");
            SelectedStickyNotePredicate predicate = new SelectedStickyNotePredicate(code);
            assertFalse(predicate.test(new Remark("Remark content", secondCode)));
        } catch (IllegalValueException e) {
            assert false : "The code shouldn't invalid";
        }
    }

}
```
###### /java/seedu/address/model/lesson/predicate/ShowSpecifiedLessonPredicateTest.java
``` java
public class ShowSpecifiedLessonPredicateTest {

    @Test
    public void equals() {

        ShowSpecifiedLessonPredicate firstPredicate = new ShowSpecifiedLessonPredicate(MA1101R_L1.hashCode());
        ShowSpecifiedLessonPredicate secondPredicate = new ShowSpecifiedLessonPredicate(CS2101_L1.hashCode());

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ShowSpecifiedLessonPredicate firstPredicateCopy = new ShowSpecifiedLessonPredicate(MA1101R_L1.hashCode());
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_isTheLessonGiven_returnsTrue() {


        ShowSpecifiedLessonPredicate predicate = new ShowSpecifiedLessonPredicate(MA1101R_L1.hashCode());
        assertTrue(predicate.test(MA1101R_L1));

    }

    @Test
    public void test_isThePersonGiven_returnsFalse() {

        ShowSpecifiedLessonPredicate predicate = new ShowSpecifiedLessonPredicate(MA1101R_L1.hashCode());
        assertFalse(predicate.test(CS2101_L1));
    }
}
```
###### /java/seedu/address/model/lesson/predicate/UniqueLocationPredicateTest.java
``` java
public class UniqueLocationPredicateTest {

    private Model model;

    @Test
    public void equals() {

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        UniqueLocationPredicate predicate = new UniqueLocationPredicate(model.getUniqueLocationSet());

        // same object -> returns true
        assertTrue(predicate.equals(predicate));

        // same values -> returns true
        UniqueLocationPredicate firstPredicateCopy = new UniqueLocationPredicate(model.getUniqueLocationSet());
        assertTrue(predicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(predicate.equals(1));

        // null -> returns false
        assertFalse(predicate.equals(null));

    }

    @Test
    public void test_isTheAddressUnique() {

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        UniqueLocationPredicate predicate = new UniqueLocationPredicate(model.getUniqueLocationSet());
        assertTrue(predicate.test(MA1101R_L1));

        // Now it is not unique
        assertTrue(predicate.test(CS2101_L1));
    }
}
```
###### /java/seedu/address/model/lesson/predicate/UniqueModuleCodePredicateTest.java
``` java
public class UniqueModuleCodePredicateTest {

    private Model model;

    @Test
    public void equals() {

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        UniqueModuleCodePredicate predicate = new UniqueModuleCodePredicate(model.getUniqueCodeSet());

        // same object -> returns true
        assertTrue(predicate.equals(predicate));

        // same values -> returns true
        UniqueModuleCodePredicate firstPredicateCopy = new UniqueModuleCodePredicate(model.getUniqueCodeSet());
        assertTrue(predicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(predicate.equals(1));

        // null -> returns false
        assertFalse(predicate.equals(null));

    }

    @Test
    public void test_isTheAddressUnique() {

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        UniqueModuleCodePredicate predicate = new UniqueModuleCodePredicate(model.getUniqueCodeSet());
        assertTrue(predicate.test(MA1101R_L1));

        // Now it is not unique
        assertTrue(predicate.test(CS2101_L1));
    }
}
```
###### /java/seedu/address/model/lesson/RemarkTest.java
``` java
public class RemarkTest {

    @Test
    public void isValidRemark() {
        // invalid remark
        assertFalse(Remark.isValidRemark("")); // empty string
        assertFalse(Remark.isValidRemark(getLongString()));

        // valid phone numbers
        assertTrue(Remark.isValidRemark("it is a valid remark")); // Must follow this format
    }

    /**
     * Get a String that is longger than 150 characters.
     */
    private String getLongString() {
        String ouput = "";
        for (int i = 0; i < 1000; i++) {
            ouput += "abc";
        }
        return ouput;
    }

}
```
###### /java/seedu/address/model/ListingUnitTest.java
``` java
public class ListingUnitTest {

    @Test
    public void test_setListingUnit() {

        // set current listing unit to be lesson
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        assertTrue(ListingUnit.getCurrentListingUnit().equals(ListingUnit.LESSON));

        // set current listing unit to be location
        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);
        assertTrue(ListingUnit.getCurrentListingUnit().equals(ListingUnit.LOCATION));

        // set current listing unit to be module
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        assertTrue(ListingUnit.getCurrentListingUnit().equals(ListingUnit.MODULE));

        // set the current predicate to be marked list predicate.
        ListingUnit.setCurrentPredicate(new MarkedListPredicate());
        assertTrue(ListingUnit.getCurrentPredicate().equals(ListingUnit.getCurrentPredicate()));


    }
}
```
###### /java/seedu/address/model/UniqueRemarkListTest.java
``` java
public class UniqueRemarkListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueRemarkList uniqueLessonList = new UniqueRemarkList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueLessonList.asObservableList().remove(0);
    }
}
```
