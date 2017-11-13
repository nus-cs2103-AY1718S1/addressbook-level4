# junming403
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
public class AddCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullLesson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_lessonAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingLessonAdded modelStub = new ModelStubAcceptingLessonAdded();
        Lesson validLesson = new LessonBuilder().build();

        CommandResult commandResult = getAddCommandForLesson(validLesson, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validLesson), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validLesson), modelStub.lessonsAdded);
    }

    @Test
    public void execute_duplicateTimeSlot_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateTimeSlotException();
        Lesson validLesson = new LessonBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_BOOKEDSLOT);

        getAddCommandForLesson(validLesson, modelStub).execute();

    }

    @Test
    public void execute_duplicateLesson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateLessonException();
        Lesson validLesson = new LessonBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_LESSON);

        getAddCommandForLesson(validLesson, modelStub).execute();
    }

    @Test
    public void equals() {
        Lesson algebra = new LessonBuilder().withCode("MA1101R").build();
        Lesson software = new LessonBuilder().withCode("CS2103T").build();
        AddCommand addAlgebraCommand = new AddCommand(algebra);
        AddCommand addSoftwareCommand = new AddCommand(software);

        // same object -> returns true
        assertTrue(addAlgebraCommand.equals(addAlgebraCommand));

        // same values -> returns true
        AddCommand addAlgebraCommandCopy = new AddCommand(algebra);
        assertTrue(addAlgebraCommand.equals(addAlgebraCommandCopy));

        // different types -> returns false
        assertFalse(addAlgebraCommand.equals(1));

        // null -> returns false
        assertFalse(addAlgebraCommand.equals(null));

        // different lessons-> returns false
        assertFalse(addAlgebraCommand.equals(addSoftwareCommand));
    }

    /**
     * Generates a new AddCommand with the details of the given lesson.
     */
    private AddCommand getAddCommandForLesson(Lesson lesson, Model model) {
        AddCommand command = new AddCommand(lesson);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java

    /**
     * A Model stub that always thrown duplicate time slot exception.
     */
    private class ModelStubThrowingDuplicateTimeSlotException extends ModelStub {
        final ArrayList<Lesson> lessonsAdded = new ArrayList<>();

        @Override
        public void bookingSlot(BookedSlot booking) throws DuplicateBookedSlotException {
            throw new DuplicateBookedSlotException();
        }

        @Override
        public void handleListingUnit() {

        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
```
###### \java\seedu\address\logic\commands\DeleteCommandTest.java
``` java
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
```
###### \java\seedu\address\logic\commands\DeleteCommandTest.java
``` java
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
```
###### \java\seedu\address\logic\commands\DeleteRemarkCommandTest.java
``` java
public class DeleteRemarkCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Before
    public void setUp() throws CommandException {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_LESSON, "This is a sample remark");
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
###### \java\seedu\address\logic\commands\EditCommandTest.java
``` java
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
        model.updateFilteredLessonList(new MarkedListPredicate());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredLessonList(new MarkedListPredicate());


        Lesson editedLesson = new LessonBuilder().build();
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(editedLesson).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_LESSON, descriptor);
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);

        editedLesson.setAsMarked();
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
    public void execute_duplicateTimeSlot_failure() {

        model.updateFilteredLessonList(
                new FixedCodePredicate(model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode()));

        Lesson firstLesson = new Lesson(model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()));
        Lesson secondLesson = new Lesson(model.getFilteredLessonList().get(INDEX_SECOND_LESSON.getZeroBased()));

        EditLessonDescriptor descriptor =
                new EditLessonDescriptorBuilder(secondLesson).withTimeSlot(firstLesson.getTimeSlot().toString())
                        .withLocation(firstLesson.getLocation().toString()).build();

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

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_BOOKEDSLOT);
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
```
###### \java\seedu\address\logic\commands\EditCommandTest.java
``` java
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
```
###### \java\seedu\address\logic\commands\ListCommandTest.java
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
###### \java\seedu\address\logic\commands\MarkCommandTest.java
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
        lessonToMark.setAsUnmarked();
        MarkCommand markCommand = prepareCommand(INDEX_FIRST_LESSON);

        String expectedMessage = String.format(MarkCommand.MESSAGE_BOOKMARK_LESSON_SUCCESS, lessonToMark);

        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws DuplicateLessonException {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        model.updateFilteredLessonList(new FixedCodePredicate(
                model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode()));
        ReadOnlyLesson lessonToMark = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        lessonToMark.setAsUnmarked();
        MarkCommand markCommand = prepareCommand(INDEX_FIRST_LESSON);

        String expectedMessage = String.format(MarkCommand.MESSAGE_BOOKMARK_LESSON_SUCCESS, lessonToMark);

        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.updateFilteredLessonList(new FixedCodePredicate(
                expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode()));

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
###### \java\seedu\address\logic\commands\RemarkCommandTest.java
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
###### \java\seedu\address\logic\commands\UnmarkCommandTest.java
``` java
public class UnmarkCommandTest {

    private Model model;

    @Test
    public void execute_validIndexUnfilteredList_success() throws DuplicateLessonException, NotRemarkedLessonException {
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
    public void execute_validIndexFilteredList_success() throws DuplicateLessonException, NotRemarkedLessonException {
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
            if (!lesson.isMarked()) {
                model.bookmarkLesson(lesson);
            }
        }
        return model;
    }
}
```
###### \java\seedu\address\logic\commands\ViewCommandTest.java
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
    public void execute_viewLesson_failure() {

        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);

        assertCommandFailure(viewCommand, model, ViewCommand.MESSAGE_VIEW_LESSON_FAILURE);

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
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_LECTURER;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE_LESSON);

    private EditCommandParser parser = new EditCommandParser();

    @Before
    public void setUp() {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
    }

    @After
    public void clearUp() {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
    }


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
###### \java\seedu\address\logic\parser\ListCommandParserTest.java
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
###### \java\seedu\address\logic\parser\MarkCommandParserTest.java
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
###### \java\seedu\address\logic\parser\RemarkCommandParserTest.java
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
###### \java\seedu\address\logic\parser\UnmarkCommandParserTest.java
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
###### \java\seedu\address\logic\parser\ViewCommandParserTest.java
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
###### \java\seedu\address\model\lesson\predicate\FixedCodePredicateTest.java
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
###### \java\seedu\address\model\lesson\predicate\FixedLocationPredicateTest.java
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
###### \java\seedu\address\model\lesson\predicate\SelectedStickyNotePredicateTest.java
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
###### \java\seedu\address\model\lesson\predicate\ShowSpecifiedLessonPredicateTest.java
``` java
public class ShowSpecifiedLessonPredicateTest {

    @Test
    public void equals() {

        ShowSpecifiedLessonPredicate firstPredicate = new ShowSpecifiedLessonPredicate(MA1101R_L1);
        ShowSpecifiedLessonPredicate secondPredicate = new ShowSpecifiedLessonPredicate(CS2101_L1);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ShowSpecifiedLessonPredicate firstPredicateCopy = new ShowSpecifiedLessonPredicate(MA1101R_L1);
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


        ShowSpecifiedLessonPredicate predicate = new ShowSpecifiedLessonPredicate(MA1101R_L1);
        assertTrue(predicate.test(MA1101R_L1));

    }

    @Test
    public void test_isThePersonGiven_returnsFalse() {

        ShowSpecifiedLessonPredicate predicate = new ShowSpecifiedLessonPredicate(MA1101R_L1);
        assertFalse(predicate.test(CS2101_L1));
    }
}
```
###### \java\seedu\address\model\lesson\predicate\UniqueLocationPredicateTest.java
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
###### \java\seedu\address\model\lesson\predicate\UniqueModuleCodePredicateTest.java
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
###### \java\seedu\address\model\lesson\RemarkTest.java
``` java
public class RemarkTest {

    @Test
    public void isValidRemark() {
        // invalid remark
        assertFalse(Remark.isValidRemark("")); // empty string
        assertFalse(Remark.isValidRemark(getLongString()));

        // valid remark
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
###### \java\seedu\address\model\ListingUnitTest.java
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
###### \java\seedu\address\model\UniqueRemarkListTest.java
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

    @Test
    public void equals() throws IllegalValueException, RemarkNotFoundException {
        UniqueRemarkList firstList = new UniqueRemarkList();
        Remark firstRemark = new Remark("Sample remark", new Code(VALID_CODE_MA1101R));
        Remark secondRemark = new Remark("another remark", new Code(VALID_CODE_MA1101R));
        Remark updatedRemark = new Remark("updated", new Code(VALID_CODE_MA1101R));

        firstList.add(firstRemark);
        firstList.add(secondRemark);
        UniqueRemarkList secondList = new UniqueRemarkList(firstList.toSet());

        assertTrue(secondList.equals(firstList));
        firstList.setRemark(firstRemark, updatedRemark);
        secondList.setRemark(firstRemark, updatedRemark);
        assertTrue(secondList.equals(firstList));
        assertTrue(secondList.equalsOrderInsensitive(firstList));
    }
}
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
public class AddCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();
        /* Case: add a lesson without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        ReadOnlyLesson toAdd = TYPICAL_MA1101R;
        String command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R  + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R  + GROUP_DESC_MA1101R  + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addLesson(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a duplicate lesson -> rejected */
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R  + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R  + GROUP_DESC_MA1101R  + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOKEDSLOT);

        /* Case: add a duplicate lesson except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalLessons#ALICE
        // This test will fail is a new tag that is not in the model is used, see the bug documented in
        // AddressBook#addLesson(ReadOnlyLesson)
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + LECTURER_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + " " + PREFIX_LECTURER.getPrefix() + "Dr Wong";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOKEDSLOT);

        /* Case: add a lesson with all fields same as another lesson in the address book except Code -> added */
        toAdd = new LessonBuilder().withCode(VALID_CODE_CS2101).withClassType(VALID_CLASSTYPE_MA1101R)
                .withLocation(VALID_VENUE_MA1101R).withGroup(VALID_GROUP_MA1101R).withTimeSlot(VALID_TIMESLOT_MA1101R)
                .withLecturers(VALID_LECTURER_MA1101R).build();
        command = AddCommand.COMMAND_WORD + CODE_DESC_CS2101 + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R
                + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOKEDSLOT);

        /* Case: add a lesson with all fields same as another lesson in the address book except ClassType -> added */
        toAdd = new LessonBuilder().withCode(VALID_CODE_MA1101R).withClassType(VALID_CLASSTYPE_CS2101)
                .withLocation(VALID_VENUE_MA1101R).withGroup(VALID_GROUP_MA1101R).withTimeSlot(VALID_TIMESLOT_MA1101R)
                .withLecturers(VALID_LECTURER_MA1101R).build();
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_CS2101 + VENUE_DESC_MA1101R
                + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOKEDSLOT);

        /* Case: add a lesson with all fields same as another lesson in the address book except Locaiton -> added */
        toAdd = new LessonBuilder().withCode(VALID_CODE_MA1101R).withClassType(VALID_CLASSTYPE_MA1101R)
                .withLocation(VALID_VENUE_CS2101).withGroup(VALID_GROUP_MA1101R).withTimeSlot(VALID_TIMESLOT_MA1101R)
                .withLecturers(VALID_LECTURER_MA1101R).build();
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R + VENUE_DESC_CS2101
                + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandSuccess(command, toAdd);

        /* Case: add a lesson with all fields same as another lesson in the address book except Group -> added */
        toAdd = new LessonBuilder().withCode(VALID_CODE_MA1101R).withClassType(VALID_CLASSTYPE_MA1101R)
                .withLocation(VALID_VENUE_MA1101R).withGroup(VALID_GROUP_CS2101).withTimeSlot(VALID_TIMESLOT_MA1101R)
                .withLecturers(VALID_LECTURER_MA1101R).build();
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R
                + GROUP_DESC_CS2101 + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOKEDSLOT);

        /* Case: add a lesson with all fields same as another lesson in the address book except Time slot -> added */
        toAdd = new LessonBuilder().withCode(VALID_CODE_MA1101R).withClassType(VALID_CLASSTYPE_MA1101R)
                .withLocation(VALID_VENUE_MA1101R).withGroup(VALID_GROUP_MA1101R).withTimeSlot(VALID_TIMESLOT_CS2101)
                .withLecturers(VALID_LECTURER_MA1101R).build();
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R
                + GROUP_DESC_MA1101R + TIMESLOT_DESC_CS2101 + LECTURER_DESC_MA1101R;
        assertCommandSuccess(command, toAdd);

        /* Case: missing code -> rejected */
        command = AddCommand.COMMAND_WORD + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R
                + TIMESLOT_DESC_MA1101R;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing class type -> rejected */
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R
                + TIMESLOT_DESC_MA1101R;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing venue -> rejected */
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R + GROUP_DESC_MA1101R
                + TIMESLOT_DESC_MA1101R;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing group -> rejected */
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R
                + TIMESLOT_DESC_MA1101R;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing time slot -> rejected */
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + LessonUtil.getLessonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid code -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_CODE_DESC + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R
                + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandFailure(command, Code.MESSAGE_CODE_CONSTRAINTS);

        /* Case: invalid class type -> rejected */
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + INVALID_CLASSTYPE_DESC + VENUE_DESC_MA1101R
                + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandFailure(command, ClassType.MESSAGE_CLASSTYPE_CONSTRAINTS);

        /* Case: invalid venue -> rejected */
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R + INVALID_VENUE_DESC
                + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandFailure(command, Location.MESSAGE_LOCATION_CONSTRAINTS);

        /* Case: invalid group -> rejected */
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R
                + INVALID_GROUP_DESC + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandFailure(command, Group.MESSAGE_GROUP_CONSTRAINTS);

        /* Case: invalid time slot -> rejected */
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R
                + GROUP_DESC_MA1101R + INVALID_TIMESLOT_DESC + LECTURER_DESC_MA1101R;
        assertCommandFailure(command, TimeSlot.MESSAGE_TIMESLOT_CONSTRAINTS);

        /* Case: invalid lecturers -> rejected */
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R
                + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R + INVALID_LECTURER_DESC;
        assertCommandFailure(command, Lecturer.MESSAGE_LECTURER_CONSTRAINTS);
    }
```
###### \java\systemtests\DeleteCommandSystemTest.java
``` java
public class DeleteCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    @Test
    public void delete() throws IllegalValueException {
        /* ----------------- Performing delete operation while an module list is being shown -------------------- */

        /* list by module */
        String listModuleCommand = ListCommand.COMMAND_WORD + " module";
        executeCommand(listModuleCommand);

        /* Case: delete the first module in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();

        String command = "     " + DeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST_LESSON.getOneBased() + "       ";
        ArrayList<ReadOnlyLesson> lessonList = removeModule(expectedModel, MA1101R);
        String expectedResultMessage = String.format(MESSAGE_DELETE_LESSON_WITH_MODULE_SUCCESS, MA1101R.fullCodeName);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);


        /* Case: delete the last module in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Model modelNotDeleteYet = getModel(); //noDelete is for Undo
        ReadOnlyAddressBook addressBook = getModel().getAddressBook();
        command = DeleteCommand.COMMAND_WORD + " " + getLastModuleIndex(modelBeforeDeletingLast).getOneBased();
        lessonList = removeModule(modelBeforeDeletingLast, CS2101);
        expectedResultMessage = String.format(MESSAGE_DELETE_LESSON_WITH_MODULE_SUCCESS, CS2101.fullCodeName);
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);


        /* Case: undo deleting the last module in the list -> last lesson restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelNotDeleteYet, expectedResultMessage);


        /* Case: delete the middle module in the list -> deleted */
        addressBook = getModel().getAddressBook();
        command = DeleteCommand.COMMAND_WORD + " " + getLastModuleIndex(modelBeforeDeletingLast).getOneBased();
        lessonList = removeModule(modelNotDeleteYet, GEQ1000);
        expectedResultMessage = String.format(MESSAGE_DELETE_LESSON_WITH_MODULE_SUCCESS, GEQ1000.fullCodeName);
        assertCommandSuccess(command, modelNotDeleteYet, expectedResultMessage);


        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        executeCommand("undo");
        executeCommand("undo");

        /* list by module */
        executeCommand(listModuleCommand);

        /*
         * View all lessons of the module indexed with 1.
         */
        String listLessonCommand = ViewCommand.COMMAND_WORD + " 1";
        executeCommand(listLessonCommand);

        /* Case: lesson list, delete index within bounds of address book and lesson list -> deleted */
        Index index = INDEX_FIRST_LESSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredLessonList().size());
        assertDeleteLessonSuccess(index);

        /* Case: filtered lesson list, delete index within bounds of address book but out of bounds of lesson list
         * -> rejected
         */
        showLessonsWithName(CLASS_TYPE_LECTURE);
        int invalidIndex = getModel().getAddressBook().getLessonList().size();
        command = DeleteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_DISPLAYED_INDEX);

        /* ----------------- Performing delete operation while a location list is being shown -------------------- */

        /* list by location */
        String listLocationCommand = ListCommand.COMMAND_WORD + " location";
        executeCommand(listLocationCommand);

        /* Case: delete the first location in the list, command with leading spaces and trailing spaces -> deleted */
        expectedModel = getModel();

        command = "     " + DeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST_LESSON.getOneBased() + "       ";
        lessonList = removeLocation(expectedModel, new Location(KEYWORD_MATCHING_LT27));
        expectedResultMessage = String.format(MESSAGE_DELETE_LESSON_WITH_LOCATION_SUCCESS, KEYWORD_MATCHING_LT27);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);


        /* Case: delete the last module in the list -> deleted */
        modelBeforeDeletingLast = getModel();
        modelNotDeleteYet = getModel(); //noDelete is for Undo
        addressBook = getModel().getAddressBook();
        command = DeleteCommand.COMMAND_WORD + " " + getLastModuleIndex(modelBeforeDeletingLast).getOneBased();
        lessonList = removeLocation(modelBeforeDeletingLast, new Location("COM02-03"));
        expectedResultMessage = String.format(MESSAGE_DELETE_LESSON_WITH_LOCATION_SUCCESS, "COM02-03");
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);


        /* Case: undo deleting the last module in the list -> last lesson restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelNotDeleteYet, expectedResultMessage);


        /* --------------------- Performing delete operation while a lesson card is selected ------------------------ */

        /* Case: delete the selected module -> module list panel selects the lesson before the deleted lesson */
        executeCommand(listModuleCommand);
        expectedModel = getModel();
        Index selectedIndex = getLastModuleIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        command = DeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        lessonList = removeModule(expectedModel, CS2101);
        expectedResultMessage = String.format(MESSAGE_DELETE_LESSON_WITH_MODULE_SUCCESS, CS2101.fullCodeName);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);



        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getLessonList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }


    /**
     * Removes the {@code ReadOnlyLesson} at the specified {@code index} in {@code model}'s address book.
     * @return the removed lesson
     */
    private ArrayList<ReadOnlyLesson> removeModule(Model model, Code code) {
        ArrayList<ReadOnlyLesson> targetLessons = new ArrayList<ReadOnlyLesson>();
        ObservableList<ReadOnlyLesson> lessonList = model.getAddressBook().getLessonList();
        for (int i = 0; i < lessonList.size(); i++) {
            ReadOnlyLesson lesson = lessonList.get(i);
            if (lesson.getCode().equals(code)) {
                try {
                    targetLessons.add(lesson);
                    model.deleteLesson(lesson);
                } catch (LessonNotFoundException e) {
                    throw new AssertionError("targetModule is retrieved from model.");
                }
                i--;
            }
        }
        return targetLessons;
    }

    /**
     * Removes the {@code ReadOnlyLesson} at the specified {@code index} in {@code model}'s address book.
     * @return the removed lesson
     */
    private ArrayList<ReadOnlyLesson> removeLocation(Model model, Location location) {
        ArrayList<ReadOnlyLesson> targetLessons = new ArrayList<ReadOnlyLesson>();
        ObservableList<ReadOnlyLesson> lessonList = model.getAddressBook().getLessonList();
        for (int i = 0; i < lessonList.size(); i++) {
            ReadOnlyLesson lesson = lessonList.get(i);
            if (lesson.getLocation().equals(location)) {
                try {
                    targetLessons.add(lesson);
                    model.deleteLesson(lesson);
                } catch (LessonNotFoundException e) {
                    throw new AssertionError("targetModule is retrieved from model.");
                }
                i--;
            }
        }
        return targetLessons;
    }


    /**
     * Removes the {@code ReadOnlyLesson} at the specified {@code index} in {@code model}'s address book.
     * @return the removed lesson
     */
    private ReadOnlyLesson removeLesson(Model model, Index index) {
        ObservableList<ReadOnlyLesson> lessonList = model.getAddressBook().getLessonList();
        ReadOnlyLesson targetLesson = lessonList.get(index.getZeroBased());

        try {
            model.deleteLesson(targetLesson);
        } catch (LessonNotFoundException e) {
            e.printStackTrace();
            throw new AssertionError("targetLesson is retrieved from model.");
        }
        return targetLesson;
    }

    /**
     * Deletes the lesson at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertDeleteLessonSuccess(Index toDelete) {
        Model expectedModel = getModel();
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        ReadOnlyLesson deletedLesson = removeLesson(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_LESSON_SUCCESS, deletedLesson);

        assertCommandSuccess(
                DeleteCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
public class EditCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void editLesson() throws Exception {
        /* ----------------- Performing edit operation while an lesson list is being shown ---------------------- */

        /* list by module */
        String listModuleCommand = ListCommand.COMMAND_WORD + " module";
        executeCommand(listModuleCommand);

        /*
         * View all lessons of the module indexed with 1.
         */
        String listLessonCommand = ViewCommand.COMMAND_WORD + " 1";
        executeCommand(listLessonCommand);
        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_LESSON;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + CODE_DESC_MA1101R + "  "
                + CLASSTYPE_DESC_MA1101R + " " + VENUE_DESC_MA1101R + "  " + GROUP_DESC_MA1101R + " "
                + TIMESLOT_DESC_MA1101R + " " + LECTURER_DESC_MA1101R + " ";
        Lesson editedLesson = new LessonBuilder().withCode(VALID_CODE_MA1101R).withClassType(VALID_CLASSTYPE_MA1101R)
                .withLocation(VALID_VENUE_MA1101R).withGroup(VALID_GROUP_MA1101R).withTimeSlot(VALID_TIMESLOT_MA1101R)
                .withLecturers(VALID_LECTURER_MA1101R).build();
        assertCommandSuccess(command, index, editedLesson);

        /* Case: edit a lesson with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandSuccess(command, index, TYPICAL_MA1101R);

        /* Case: edit some fields -> edited */
        index = INDEX_FIRST_LESSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + LECTURER_DESC_MA1101R;
        ReadOnlyLesson lessonToEdit = getModel().getFilteredLessonList().get(index.getZeroBased());
        editedLesson = new LessonBuilder(lessonToEdit).withLecturers(VALID_LECTURER_MA1101R).build();
        assertCommandSuccess(command, index, editedLesson);

        /* --------------------- Performing edit operation while a lesson card is selected -------------------------- */

        /* Case: selects first card in the lesson list, edit a lesson -> edited, card selection remains unchanged but
         * browser url changes
         */
        index = INDEX_FIRST_LESSON;
        selectLesson(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + LECTURER_DESC_MA1101R;
        // this can be misleading: card selection actually remains unchanged.
        assertEditLessonSuccess(command, index, TYPICAL_MA1101R, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + CODE_DESC_MA1101R,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE_LESSON));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + CODE_DESC_MA1101R,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE_LESSON));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredLessonList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + CODE_DESC_MA1101R,
                Messages.MESSAGE_INVALID_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + CODE_DESC_MA1101R,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE_LESSON));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid module code -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + INVALID_CODE_DESC,
                Code.MESSAGE_CODE_CONSTRAINTS);

        /* Case: invalid class type -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + INVALID_CLASSTYPE_DESC,
                ClassType.MESSAGE_CLASSTYPE_CONSTRAINTS);

        /* Case: invalid location -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + INVALID_VENUE_DESC,
                Location.MESSAGE_LOCATION_CONSTRAINTS);

        /* Case: invalid group number -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + INVALID_GROUP_DESC,
                Group.MESSAGE_GROUP_CONSTRAINTS);

        /* Case: invalid time slot -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + INVALID_TIMESLOT_DESC,
                TimeSlot.MESSAGE_TIMESLOT_CONSTRAINTS);

        /* Case: invalid lecturer -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + INVALID_LECTURER_DESC,
                Lecturer.MESSAGE_LECTURER_CONSTRAINTS);

        /* Case: edit a lesson with new values same as another lesson's values -> rejected */
        executeCommand(LessonUtil.getAddCommand(TYPICAL_CS2101));
        assertTrue(getModel().getAddressBook().getLessonList().contains(TYPICAL_CS2101));
        index = INDEX_FIRST_LESSON;
        assertFalse(getModel().getFilteredLessonList().get(index.getZeroBased()).equals(TYPICAL_CS2101));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + CODE_DESC_CS2101 + CLASSTYPE_DESC_CS2101
                + VENUE_DESC_CS2101 +  TIMESLOT_DESC_CS2101 +  GROUP_DESC_CS2101 + LECTURER_DESC_CS2101;
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOKEDSLOT);

        /* Case: edit a lesson with new values same as another lesson's values but with different tags -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased()  + CODE_DESC_CS2101 + CLASSTYPE_DESC_CS2101
                + VENUE_DESC_CS2101 + TIMESLOT_DESC_CS2101 + GROUP_DESC_CS2101 + LECTURER_DESC_CS2101;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_BOOKEDSLOT);
    }

    /* ----------------- Performing edit operation while a module list is being shown ---------------------- */
    @Test
    public void editModule() throws Exception {
        /* ----------------- Performing edit operation while an module list is being shown ---------------------- */
        /* list by module */
        String listModuleCommand = ListCommand.COMMAND_WORD + " module";
        executeCommand(listModuleCommand);

        /* Case: edit module code, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_LESSON;
        String command = " " + EditCommand.COMMAND_WORD + " 1 " + VALID_CODE_MA1102R;
        assertCommandSuccess(command, index, VALID_CODE_MA1102R);

        command = " " + EditCommand.COMMAND_WORD + "    1     " + VALID_CODE_MA1101R;
        assertCommandSuccess(command, index, VALID_CODE_MA1101R);

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + VALID_CODE_MA1101R,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE_MODULE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1 " + VALID_CODE_MA1101R,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE_MODULE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredLessonList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + " " + VALID_CODE_MA1101R,
                Messages.MESSAGE_INVALID_DISPLAYED_INDEX);

        /* Case: invalid module code -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + " MA*",
                Code.MESSAGE_CODE_CONSTRAINTS);
    }

    /* ----------------- Performing edit operation while a module list is being shown ---------------------- */
    @Test
    public void editLocation() throws Exception {
        /* ----------------- Performing edit operation while an location list is being shown ---------------------- */
        /* list by location */
        String listLocationCommand = ListCommand.COMMAND_WORD + " location";
        executeCommand(listLocationCommand);

        /* Case: edit location, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_LESSON;
        String command = " " + EditCommand.COMMAND_WORD + " 1 " + VALID_VENUE_MA1102R;
        assertCommandSuccess(command, index, VALID_VENUE_MA1102R);

        command = " " + EditCommand.COMMAND_WORD + "    1     " + VALID_VENUE_MA1101R;
        assertCommandSuccess(command, index, VALID_VENUE_MA1101R);

        command = " " + EditCommand.COMMAND_WORD + "    1     " + VALID_VENUE_CS2101;
        assertCommandSuccess(command, index, VALID_VENUE_CS2101);

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + VALID_VENUE_MA1101R,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE_LOCATION));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1 " + VALID_VENUE_MA1101R,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE_LOCATION));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredLessonList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + " " + VALID_VENUE_MA1101R,
                Messages.MESSAGE_INVALID_DISPLAYED_INDEX);

        /* Case: invalid location. -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + " ",
                Location.MESSAGE_LOCATION_CONSTRAINTS);
    }


    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, ReadOnlyLesson, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertEditLessonSuccess(String, Index, ReadOnlyLesson, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ReadOnlyLesson editedLesson)
            throws DuplicateBookedSlotException {
        assertEditLessonSuccess(command, toEdit, editedLesson, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, String, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertEditModuleSuccess(String, Index, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, String attributeValue)
            throws DuplicateBookedSlotException {
        switch (ListingUnit.getCurrentListingUnit()) {
        case MODULE:
            assertEditModuleSuccess(command, toEdit, attributeValue, null);
            break;
        case LOCATION:
            assertEditLocationSuccess(command, toEdit, attributeValue, null);
            break;
        default:
            break;
        }
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();
        assertStatusBarUnchangedExceptSyncStatus();
    }


    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the lesson at index {@code toEdit} being
     * updated to values specified {@code editedLesson}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertEditModuleSuccess(String command, Index toEdit, String attributeValue,
                                      Index expectedSelectedCardIndex) throws DuplicateBookedSlotException {
        Model expectedModel = getModel();
        Code codeToEdit = expectedModel.getFilteredLessonList().get(toEdit.getZeroBased()).getCode();

        try {
            Code editedCode = new Code(attributeValue);
            expectedModel.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
            ObservableList<ReadOnlyLesson> lessonList = expectedModel.getFilteredLessonList();
            for (ReadOnlyLesson p : lessonList) {

                ReadOnlyLesson curEditedLesson;
                if (p.getCode().equals(codeToEdit)) {
                    curEditedLesson = new Lesson(p.getClassType(), p.getLocation(), p.getGroup(),
                            p.getTimeSlot(), editedCode, p.getLecturers());
                    expectedModel.updateLesson(p, curEditedLesson);
                }
            }
            expectedModel.updateFilteredLessonList(new UniqueModuleCodePredicate(expectedModel.getUniqueCodeSet()));
            assertCommandSuccess(command, expectedModel,
                    String.format(EditCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedCode), expectedSelectedCardIndex);
        } catch (DuplicateLessonException | LessonNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedLesson is a duplicate in expectedModel, or it isn't found in the model.");
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException(
                    "edited module code is not valid");
        }
    }


    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the lesson at index {@code toEdit} being
     * updated to values specified {@code editedLesson}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertEditLocationSuccess(String command, Index toEdit, String attributeValue,
                                         Index expectedSelectedCardIndex) throws DuplicateBookedSlotException {
        Model expectedModel = getModel();
        Location locationToEdit = expectedModel.getFilteredLessonList().get(toEdit.getZeroBased()).getLocation();

        try {
            Location editedLocation = new Location(attributeValue);
            expectedModel.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
            ObservableList<ReadOnlyLesson> lessonList = expectedModel.getFilteredLessonList();
            for (ReadOnlyLesson p : lessonList) {

                ReadOnlyLesson curEditedLesson;
                if (p.getLocation().equals(locationToEdit)) {
                    curEditedLesson = new Lesson(p.getClassType(), editedLocation, p.getGroup(),
                            p.getTimeSlot(), p.getCode(), p.getLecturers());
                    BookedSlot bookedSlotToEdit = new BookedSlot(p.getLocation(), p.getTimeSlot());
                    BookedSlot editedBookedSlot = new BookedSlot(editedLocation, p.getTimeSlot());
                    expectedModel.updateBookedSlot(bookedSlotToEdit, editedBookedSlot);
                    expectedModel.updateLesson(p, curEditedLesson);
                }
            }
            expectedModel.updateFilteredLessonList(new UniqueLocationPredicate(expectedModel.getUniqueLocationSet()));
            assertCommandSuccess(command, expectedModel,
                    String.format(EditCommand.MESSAGE_EDIT_LOCATION_SUCCESS, editedLocation),
                    expectedSelectedCardIndex);
        } catch (DuplicateLessonException | LessonNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedLesson is a duplicate in expectedModel, or it isn't found in the model.");
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException(
                    "edited module code is not valid");
        }
    }
```
###### \java\systemtests\MarkCommandSystemTest.java
``` java
public class MarkCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_DUPLICATE_LESSON_FAILURE = "Operation would result in duplicate lesson";
    private static final String MESSAGE_INVALID_MARK_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);

    @Test
    public void mark() throws Exception {

        /* list by module */
        String listModuleCommand = ListCommand.COMMAND_WORD + " module";
        executeCommand(listModuleCommand);

        /*
         * View all lessons of the module indexed with 1.
         */
        String listLessonCommand = ViewCommand.COMMAND_WORD + " 1";
        executeCommand(listLessonCommand);

        /* Case: Unmark the first lesson in the list, command with leading spaces and trailing spaces -> unmarked */
        Model expectedModel = getModel();
        String command = "     " + UnmarkCommand.COMMAND_WORD + "      " + INDEX_FIRST_LESSON.getOneBased() + "       ";
        ReadOnlyLesson lessonToUnmark = expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        expectedModel.unBookmarkLesson(lessonToUnmark);
        String expectedResultMessage = String.format(MESSAGE_UNBOOKMARK_LESSON_SUCCESS, lessonToUnmark);
        assertCommandExecuteSuccess(command, expectedModel, expectedResultMessage);

        /* Case: Mark the first lesson in the list, command with leading spaces and trailing spaces -> marked */
        expectedModel = getModel();
        command = "     " + MarkCommand.COMMAND_WORD + "      " + INDEX_FIRST_LESSON.getOneBased() + "       ";
        ReadOnlyLesson lessonToMark = expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        expectedModel.bookmarkLesson(lessonToMark);
        expectedResultMessage = String.format(MESSAGE_BOOKMARK_LESSON_SUCCESS, lessonToMark);
        assertCommandExecuteSuccess(command, expectedModel, expectedResultMessage);

        /* Case: Unmark the first lesson in the marked list,
         * command with leading spaces and trailing spaces -> unmarked
         */
        /* list marked lessons */
        String listMarkedCommand = ListCommand.COMMAND_WORD + " marked";
        executeCommand(listMarkedCommand);

        expectedModel = getModel();
        command = "     " + UnmarkCommand.COMMAND_WORD + "      " + INDEX_FIRST_LESSON.getOneBased() + "       ";
        lessonToUnmark = expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        expectedModel.unBookmarkLesson(lessonToUnmark);
        expectedResultMessage = String.format(MESSAGE_UNBOOKMARK_LESSON_SUCCESS, lessonToUnmark);
        assertCommandExecuteSuccess(command, expectedModel, expectedResultMessage);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */


        executeCommand(listModuleCommand);
        executeCommand(listLessonCommand);

        command = MarkCommand.COMMAND_WORD + " 1";
        executeCommand(command);

        /* Case: Mark the first lesson in the list again, duplicate mark -> rejected */
        command = "     " + MarkCommand.COMMAND_WORD + "      " + INDEX_FIRST_LESSON.getOneBased() + "       ";
        assertCommandExecuteFailure(command, MESSAGE_DUPLICATE_LESSON_FAILURE);

        /* Case: invalid index (0) -> rejected */
        command = MarkCommand.COMMAND_WORD + " 0";
        assertCommandExecuteFailure(command, MESSAGE_INVALID_MARK_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = MarkCommand.COMMAND_WORD + " -1";
        assertCommandExecuteFailure(command, MESSAGE_INVALID_MARK_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getLessonList().size() + 1);
        command = MarkCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandExecuteFailure(command, MESSAGE_INVALID_DISPLAYED_INDEX);

        command = UnmarkCommand.COMMAND_WORD + " 1";
        executeCommand(command);

        /* Case: wrong listing type, list by module -> rejected */
        executeCommand(listModuleCommand);
        command = MarkCommand.COMMAND_WORD + " 1";
        assertCommandExecuteFailure(command, MarkCommand.MESSAGE_WRONG_LISTING_UNIT_FAILURE);

        /* Case: wrong listing type, list by location -> rejected */
        String listLocationCommand = ListCommand.COMMAND_WORD + " location";
        executeCommand(listLocationCommand);
        command = MarkCommand.COMMAND_WORD + " 1";
        assertCommandExecuteFailure(command, MarkCommand.MESSAGE_WRONG_LISTING_UNIT_FAILURE);

    }
}
```
###### \java\systemtests\RemarkCommandSystemTest.java
``` java
public class RemarkCommandSystemTest extends AddressBookSystemTest {

    private static final String SAMPLE_REMARK = "This is a sample remark";
    private static final String MESSSAGE_DUPLICATE_REMARK = "Operation would result in duplicate remark";
    private static final String MESSAGE_INVALID_REMARK_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

    @Test
    public void remark() throws Exception {
        /* list by module */
        String listModuleCommand = ListCommand.COMMAND_WORD + " module";
        executeCommand(listModuleCommand);

        /* Case:remark the first module in the list, command with leading spaces and trailing spaces -> remarked*/
        Model expectedModel = getModel();
        String command = "     " + RemarkCommand.COMMAND_WORD + "      " + INDEX_FIRST_LESSON.getOneBased() + "       "
                + SAMPLE_REMARK;
        Code moduleToRemark = expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()).getCode();
        expectedModel.addRemark(new Remark(SAMPLE_REMARK, moduleToRemark));
        String expectedResultMessage = String.format(MESSAGE_REMARK_MODULE_SUCCESS, moduleToRemark);
        assertCommandExecuteSuccess(command, expectedModel, expectedResultMessage);

        /* Case:remark the second module in the list -> remarked*/
        expectedModel = getModel();
        command = RemarkCommand.COMMAND_WORD + " " + INDEX_SECOND_LESSON.getOneBased() + "  " + SAMPLE_REMARK;
        moduleToRemark = expectedModel.getFilteredLessonList().get(INDEX_SECOND_LESSON.getZeroBased()).getCode();
        expectedModel.addRemark(new Remark(SAMPLE_REMARK, moduleToRemark));
        expectedResultMessage = String.format(MESSAGE_REMARK_MODULE_SUCCESS, moduleToRemark);
        assertCommandExecuteSuccess(command, expectedModel, expectedResultMessage);

        /* Case:delete the second remark in the list -> deleted*/
        expectedModel = getModel();
        command = RemarkCommand.COMMAND_WORD + " -d " + INDEX_SECOND_LESSON.getOneBased();
        Remark remarkToDelete = expectedModel.getFilteredRemarkList().get(INDEX_SECOND_LESSON.getZeroBased());
        expectedModel.deleteRemark(remarkToDelete);
        expectedResultMessage = String.format(MESSAGE_DELETE_REMARK_SUCCESS, remarkToDelete);
        assertCommandExecuteSuccess(command, expectedModel, expectedResultMessage);

        /* Case: Undo the previous step -> Undoed */
        expectedModel = getModel();
        command = UndoCommand.COMMAND_WORD;
        expectedModel.addRemark(new Remark(SAMPLE_REMARK, moduleToRemark));
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandExecuteSuccess(command, expectedModel, expectedResultMessage);

        /* Case: redo the last command -> redoed */
        expectedModel = getModel();
        command = RedoCommand.COMMAND_WORD;
        expectedModel.deleteRemark(remarkToDelete);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandExecuteSuccess(command, expectedModel, expectedResultMessage);

        /* Case: add in the same remark again to the first module, duplicate remark -> rejected */
        command = RemarkCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + " " + SAMPLE_REMARK;
        assertCommandExecuteFailure(command, MESSSAGE_DUPLICATE_REMARK);

        /* Case: invalid module index (0) -> rejected */
        command = RemarkCommand.COMMAND_WORD + " 0 " + SAMPLE_REMARK;
        assertCommandExecuteFailure(command, MESSAGE_INVALID_REMARK_COMMAND_FORMAT);

        /* Case: invalid module index (-1) -> rejected */
        command = RemarkCommand.COMMAND_WORD + " -1 " + SAMPLE_REMARK;
        assertCommandExecuteFailure(command, MESSAGE_INVALID_REMARK_COMMAND_FORMAT);

        /* Case: invalid module index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getLessonList().size() + 1);
        command = RemarkCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased() + " " + SAMPLE_REMARK;
        assertCommandExecuteFailure(command, MESSAGE_INVALID_DISPLAYED_INDEX);

        /* Case: invalid remark index (0) -> rejected */
        command = RemarkCommand.COMMAND_WORD + " -d 0";
        assertCommandExecuteFailure(command, MESSAGE_INVALID_REMARK_COMMAND_FORMAT);

        /* Case: invalid remark index (-1) -> rejected */
        command = RemarkCommand.COMMAND_WORD + " -d -1 " + SAMPLE_REMARK;
        assertCommandExecuteFailure(command, MESSAGE_INVALID_REMARK_COMMAND_FORMAT);

        /* Case: invalid remark index (size + 1) -> rejected */
        outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getRemarkList().size() + 1);
        command = RemarkCommand.COMMAND_WORD + " -d " + outOfBoundsIndex.getOneBased();
        assertCommandExecuteFailure(command, MESSAGE_INVALID_DISPLAYED_INDEX);

        /* Case: add remark when list by lessons, wrong listing type -> rejected */
        command = ViewCommand.COMMAND_WORD + " 1";
        executeCommand(command);
        command = RemarkCommand.COMMAND_WORD + " 1  another remark";
        assertCommandExecuteFailure(command, MESSAGE_WRONG_LISTING_UNIT_FAILURE);

        /* Case: delete remark when list by lessons, wrong listing type -> rejected */
        command = RemarkCommand.COMMAND_WORD + " -d 1";
        assertCommandExecuteFailure(command, MESSAGE_WRONG_LISTING_UNIT_FAILURE);

        /* Case: add remark when list by location, wrong listing type -> rejected */
        command = ListCommand.COMMAND_WORD + " location";
        executeCommand(command);
        command = RemarkCommand.COMMAND_WORD + " 1  another remark";
        assertCommandExecuteFailure(command, MESSAGE_WRONG_LISTING_UNIT_FAILURE);

        /* Case: delete remark when list by location, wrong listing type -> rejected */
        command = RemarkCommand.COMMAND_WORD + " -d 1";
        assertCommandExecuteFailure(command, MESSAGE_WRONG_LISTING_UNIT_FAILURE);
    }

}
```
