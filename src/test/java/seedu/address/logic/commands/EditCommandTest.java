package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSTYPE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstLessonOnly;
import static seedu.address.logic.commands.EditCommand.EditLessonDescriptor;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LESSON;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.ListingUnit;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.predicates.FixedCodePredicate;
import seedu.address.model.module.predicates.MarkedListPredicate;
import seedu.address.model.module.predicates.UniqueLocationPredicate;
import seedu.address.model.module.predicates.UniqueModuleCodePredicate;
import seedu.address.testutil.EditLessonDescriptorBuilder;
import seedu.address.testutil.LessonBuilder;

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
