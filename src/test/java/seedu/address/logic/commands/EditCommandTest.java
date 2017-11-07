package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPlaceOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PLACE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PLACE;
import static seedu.address.testutil.TypicalPlaces.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditCommand.EditPlaceDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.place.Place;
import seedu.address.model.place.ReadOnlyPlace;
import seedu.address.testutil.EditPlaceDescriptorBuilder;
import seedu.address.testutil.PlaceBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Place editedPlace = new PlaceBuilder().build();
        EditCommand.EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder(editedPlace).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_PLACE, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PLACE_SUCCESS, editedPlace);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePlace(model.getFilteredPlaceList().get(0), editedPlace);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastPlace = Index.fromOneBased(model.getFilteredPlaceList().size());
        ReadOnlyPlace lastPlace = model.getFilteredPlaceList().get(indexLastPlace.getZeroBased());

        PlaceBuilder placeInList = new PlaceBuilder(lastPlace);
        Place editedPlace = placeInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = prepareCommand(indexLastPlace, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PLACE_SUCCESS, editedPlace);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePlace(lastPlace, editedPlace);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = prepareCommand(INDEX_FIRST_PLACE, new EditPlaceDescriptor());
        ReadOnlyPlace editedPlace = model.getFilteredPlaceList().get(INDEX_FIRST_PLACE.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PLACE_SUCCESS, editedPlace);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPlaceOnly(model);

        ReadOnlyPlace placeInFilteredList = model.getFilteredPlaceList().get(INDEX_FIRST_PLACE.getZeroBased());
        Place editedPlace = new PlaceBuilder(placeInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_PLACE,
                new EditPlaceDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PLACE_SUCCESS, editedPlace);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePlace(model.getFilteredPlaceList().get(0), editedPlace);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePlaceUnfilteredList_failure() {
        Place firstPlace = new Place(model.getFilteredPlaceList().get(INDEX_FIRST_PLACE.getZeroBased()));
        EditCommand.EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder(firstPlace).build();
        EditCommand editCommand = prepareCommand(INDEX_SECOND_PLACE, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PLACE);
    }

    @Test
    public void execute_duplicatePlaceFilteredList_failure() {
        showFirstPlaceOnly(model);

        // edit place in filtered list into a duplicate in address book
        ReadOnlyPlace placeInList = model.getAddressBook().getPlaceList().get(INDEX_SECOND_PLACE.getZeroBased());
        EditCommand editCommand = prepareCommand(INDEX_FIRST_PLACE,
                new EditPlaceDescriptorBuilder(placeInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PLACE);
    }

    @Test
    public void execute_invalidPlaceIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPlaceList().size() + 1);
        EditCommand.EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPlaceIndexFilteredList_failure() {
        showFirstPlaceOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PLACE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPlaceList().size());

        EditCommand editCommand = prepareCommand(outOfBoundIndex,
                new EditPlaceDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PLACE, DESC_AMY);

        // same values -> returns true
        EditCommand.EditPlaceDescriptor copyDescriptor = new EditCommand.EditPlaceDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PLACE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PLACE, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PLACE, DESC_BOB)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditCommand prepareCommand(Index index, EditCommand.EditPlaceDescriptor descriptor) {
        EditCommand editCommand = new EditCommand(index, descriptor);
        editCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editCommand;
    }
}
