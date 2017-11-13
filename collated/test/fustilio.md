# fustilio
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
    /**
     * A Model stub that always accept the parcel being added.
     */
    private class ModelStubAcceptingParcelAdded extends ModelStub {
        final ArrayList<Parcel> parcelsAdded = new ArrayList<>();

        /*
        @Override
        public boolean hasSelected() {
            return false;
        }
        */
        @Override
        public void addParcelCommand(ReadOnlyParcel parcel) throws DuplicateParcelException {
            addParcel(parcel);
        }

        @Override
        public void maintainSorted() {
            Collections.sort(parcelsAdded);
        }

        @Override
        public void forceSelectParcel(ReadOnlyParcel parcel) {
            logger.info("Simulate force selection of parcel.");
        }

        @Override
        public void addParcel(ReadOnlyParcel parcel) throws DuplicateParcelException {
            parcelsAdded.add(new Parcel(parcel));
        }

        @Override
        public boolean getActiveIsAllBool() {
            return true;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
```
###### \java\seedu\address\logic\commands\DeleteTagCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FLAMMABLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRAGILE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalParcels.getTypicalAddressBook;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class DeleteTagCommandTest {

    private Model model;
    private Model expectedModel;
    private DeleteTagCommand deleteTagCommand;
    private Tag tagToDelete;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        ObservableList<ReadOnlyParcel> parcelsToManipulate = model.getFilteredParcelList();

        Iterator it = parcelsToManipulate.iterator();
        Boolean noCandidate = true;

        while (it.hasNext() && noCandidate) {
            ReadOnlyParcel parcelToManipulate = (ReadOnlyParcel) it.next();
            if (!parcelToManipulate.getTags().isEmpty()) {
                tagToDelete = (Tag) parcelToManipulate.getTags().toArray()[0];
                noCandidate = false;
            }
        }
    }

    @Test
    public void execute_deleteTag_success() throws Exception {

        deleteTagCommand = prepareCommand(tagToDelete);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagToDelete);

        try {
            expectedModel.deleteTag(tagToDelete);
        } catch (TagNotFoundException e) {
            e.printStackTrace();
        }

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteTag_tagNotFoundFailure() throws Exception {

        try {
            tagToDelete = Tag.getInstance(Tag.FRAGILE.toString());
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        deleteTagCommand = prepareCommand(tagToDelete);

        String exceptionMessage = String.format(DeleteTagCommand.MESSAGE_INVALID_DELETE_TAG_NOT_FOUND, tagToDelete);

        assertCommandFailure(deleteTagCommand, model, exceptionMessage);
    }

    @Test
    public void execute_deleteTag_tagNotValid() throws Exception {

        String exceptionMessage = "";

        try {
            tagToDelete = Tag.getInstance("!@#$%^&*()");
        } catch (IllegalValueException e) {
            exceptionMessage = e.getMessage();
        }

        String expectedMessage = Tag.MESSAGE_TAG_CONSTRAINTS;

        assertEquals(expectedMessage, exceptionMessage);
    }

    private DeleteTagCommand prepareCommand(Tag target) {
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(target);
        deleteTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTagCommand;
    }

    @Test
    public void equals() {
        Tag flammable = null;
        Tag fragile = null;
        try {
            flammable = Tag.getInstance(VALID_TAG_FLAMMABLE.toLowerCase());
            fragile = Tag.getInstance(VALID_TAG_FRAGILE);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        DeleteTagCommand deleteUrgentTagCommand = new DeleteTagCommand(flammable);
        DeleteTagCommand deleteFragileTagCommand = new DeleteTagCommand(fragile);

        // same object -> returns true
        assertTrue(deleteUrgentTagCommand.equals(deleteUrgentTagCommand));

        // same values -> returns true
        DeleteTagCommand deleteUrgentTagCommandCopy = new DeleteTagCommand(flammable);
        assertTrue(deleteUrgentTagCommand.equals(deleteUrgentTagCommandCopy));

        // different types -> returns false
        assertFalse(deleteUrgentTagCommand.equals(1));

        // null -> returns false
        assertFalse(deleteUrgentTagCommand.equals(null));

        // different parcel -> returns false
        assertFalse(deleteUrgentTagCommand.equals(deleteFragileTagCommand));
    }
}
```
###### \java\seedu\address\logic\commands\EditCommandTest.java
``` java
    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditCommand prepareCommand(Index index, EditCommand.EditParcelDescriptor descriptor) {
        EditCommand editCommand = new EditCommand(index, descriptor);
        editCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editCommand;
    }
```
###### \java\seedu\address\model\parcel\DeliveryDateTest.java
``` java
    @Test
    public void isValidDate() {
        // invalid dates
        assertFalse(DeliveryDate.isValidDate("")); // empty string
        assertFalse(DeliveryDate.isValidDate(" ")); // spaces only
        assertFalse(DeliveryDate.isValidDate("91")); // less than 3 numbers
        assertFalse(DeliveryDate.isValidDate("9321313213213123212131")); // only numbers, can't understand
        assertFalse(DeliveryDate.isValidDate("a")); // short string
        assertFalse(DeliveryDate.isValidDate("date")); // non-numeric
        assertFalse(DeliveryDate.isValidDate("#(_!@!@(")); // special charactors
        assertFalse(DeliveryDate.isValidDate("\u200E\uD83D\uDE03\uD83D\uDC81")); // emojis
        assertFalse(DeliveryDate.isValidDate("I love cs2103T")); // non date sentence
        assertFalse(DeliveryDate.isValidDate("32-05-1995")); // too many days in a month
        assertFalse(DeliveryDate.isValidDate("05-13-1995")); // too many months in a year
        assertFalse(DeliveryDate.isValidDate("32/05/1995")); // too many days in a month
        assertFalse(DeliveryDate.isValidDate("05/13/1995")); // too many months in a year
        assertFalse(DeliveryDate.isValidDate("32.05.1995")); // too many days in a month
        assertFalse(DeliveryDate.isValidDate("05.13.1995")); // too many months in a year
        assertFalse(DeliveryDate.isValidDate("29.02.2001")); // Not a leap year
        assertFalse(DeliveryDate.isValidDate("0.02.2001")); // single digit but wrong day
        assertFalse(DeliveryDate.isValidDate("29.0.2001")); // single digit but wrong month

        // valid dates
        assertTrue(DeliveryDate.isValidDate("20-12-1990")); // exactly 3 numbers
        assertTrue(DeliveryDate.isValidDate("01-01-2001"));
        assertTrue(DeliveryDate.isValidDate("01/01/2001"));
        assertTrue(DeliveryDate.isValidDate("01.01.2001"));
        assertTrue(DeliveryDate.isValidDate("31.1.2001")); // single digit month
        assertTrue(DeliveryDate.isValidDate("1.01.2001")); // single digit day
        assertTrue(DeliveryDate.isValidDate("1.1.2001")); // single digit day and month
        assertTrue(DeliveryDate.isValidDate("29.02.2004")); // is leap year

        // invalid dates but returns true because parser "understands" it
        assertTrue(DeliveryDate.isValidDate("9011p041")); // alphabets within digits
        assertTrue(DeliveryDate.isValidDate("9312 1534")); // spaces within digits
    }

    @Test
    public void isPrettyTimeAccurate() throws IllegalValueException {
        assertEquals(new DeliveryDate("01-01-2001"), new DeliveryDate("First day of 2001"));
        assertEquals(new DeliveryDate("02-08-2017"), new DeliveryDate("Second day of August 2017"));
        assertEquals(new DeliveryDate("4-7-2017"), new DeliveryDate("independence day 2017"));
        assertEquals(new DeliveryDate("14-2-2017"), new DeliveryDate("Valentines day 2017"));
        assertEquals(new DeliveryDate("24-12-2017"), new DeliveryDate("Christmas eve 2017"));
    }
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: add Hoon's parcel (completed) and Ida's parcel (pending) and check if tab is switched back and forth*/
        model = getModel();
        assertTrue(model.getTabIndex().equals(TAB_ALL_PARCELS));
        model.addParcelCommand(HOON);
        assertTrue(model.getTabIndex().equals(TAB_COMPLETED_PARCELS));
        model.addParcelCommand(IDA);
        assertTrue(model.getTabIndex().equals(TAB_ALL_PARCELS));
        model.deleteParcel(HOON);
        model.deleteParcel(IDA);
```
###### \java\systemtests\DeleteTagCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FLAMMABLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FROZEN;
import static seedu.address.logic.commands.DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS;
import static seedu.address.logic.commands.DeleteTagCommand.MESSAGE_INVALID_DELETE_TAG_NOT_FOUND;
import static seedu.address.testutil.TestUtil.getParcel;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PARCEL;

import java.util.Iterator;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagInternalErrorException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

public class DeleteTagCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void deleteTag() throws IllegalValueException {
        /* ---------------- Performing deleteTag operation while an unfiltered list is being shown ---------------- */

        /* Case: delete the first parcel in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();

        ReadOnlyParcel targetParcel = getParcel(expectedModel, INDEX_FIRST_PARCEL);

        Iterator<Tag> targetTags = targetParcel.getTags().iterator();
        Tag targetTag = null;

        if (targetTags.hasNext()) {
            targetTag = targetTags.next();
        }

        String command = "     " + DeleteTagCommand.COMMAND_WORD + "      " + targetTag.toString() + "       ";

        Tag deletedTag = removeTag(expectedModel, targetTag);
        String expectedResultMessage = String.format(MESSAGE_DELETE_TAG_SUCCESS, deletedTag);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        Model modelBeforeDeletingLast = getModel();
        targetTag = Tag.getInstance(VALID_TAG_FLAMMABLE);

        assertCommandSuccess(targetTag);

        /* Case: undo deleting the previous tag in the list -> deleted tag restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: redo deleting the last parcel in the list -> last tag deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeTag(modelBeforeDeletingLast, targetTag);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* ------------------------------- Performing invalid deleteTag operation ----------------------------------- */

        /* Case: invalid arguments (tag not founds) -> rejected */
        expectedResultMessage = String.format(MESSAGE_INVALID_DELETE_TAG_NOT_FOUND,
                Tag.getInstance(Tag.FROZEN.toString()));
        assertCommandFailure(DeleteTagCommand.COMMAND_WORD + " " + VALID_TAG_FROZEN.toString(),
                expectedResultMessage);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETEtAG friends", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code ReadOnlyParcel} at the specified {@code index} in {@code model}'s address book.
     * @return the removed parcel
     */
    private Tag removeTag(Model model, Tag targetTag) {
        try {
            model.deleteTag(targetTag);
        } catch (TagNotFoundException | TagInternalErrorException e) {
            throw new AssertionError("targetTag is retrieved from model.");
        }
        return targetTag;
    }

    /**
     * Deletes the tag at {@code toDelete} by creating a default {@code DeleteTagCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteTagCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Tag toDelete) {
        Model expectedModel = getModel();
        Tag deletedTag = removeTag(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_TAG_SUCCESS, deletedTag);

        assertCommandSuccess(
                DeleteTagCommand.COMMAND_WORD + " " + toDelete.toString(), expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteTagCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\DeleteTagCommandSystemTest.java
``` java

```
###### \java\systemtests\EditCommandSystemTest.java
``` java
        /* ----------------------------- Performing edit operation with tab switches -------------------------------- */

        /* Case: Edit status of first parcel to completed and check if tab is switched back and forth*/
        model = getModel();
        assertTrue(model.getTabIndex().equals(TAB_ALL_PARCELS));
        parcelToEdit = model.getActiveList().get(index.getZeroBased());
        editedParcel = new ParcelBuilder(parcelToEdit).withStatus(VALID_STATUS_COMPLETED).build();
        model.editParcelCommand(parcelToEdit, editedParcel);
        assertTrue(model.getTabIndex().equals(TAB_COMPLETED_PARCELS));
        parcelToEdit = editedParcel;
        editedParcel = new ParcelBuilder(parcelToEdit).withStatus(VALID_STATUS_OVERDUE).build();
        model.editParcelCommand(parcelToEdit, editedParcel);
        assertTrue(model.getTabIndex().equals(TAB_ALL_PARCELS));
```
###### \java\systemtests\MaintainSortedMechanismSystemTest.java
``` java
package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_DELIVERING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FLAMMABLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRACKING_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalParcels.getTypicalAddressBook;

import java.util.List;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditParcelDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.parcel.Parcel;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.testutil.EditParcelDescriptorBuilder;
import seedu.address.testutil.ParcelBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class MaintainSortedMechanismSystemTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_maintainSorted_success() throws Exception {
        Index indexLastParcel = Index.fromOneBased(model.getActiveList().size());
        ReadOnlyParcel lastParcel = model.getActiveList().get(indexLastParcel.getZeroBased());

        ParcelBuilder parcelInList = new ParcelBuilder(lastParcel);
        Parcel editedParcel = parcelInList.withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withTags(VALID_TAG_FLAMMABLE).withTrackingNumber(VALID_TRACKING_NUMBER_AMY)
                .withStatus(VALID_STATUS_DELIVERING).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).build();

        EditParcelDescriptor descriptor = new EditParcelDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withTags(VALID_TAG_FLAMMABLE).withTrackingNumber(VALID_TRACKING_NUMBER_AMY)
                .withStatus(VALID_STATUS_DELIVERING).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .build();
        EditCommand editCommand = prepareCommand(indexLastParcel, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PARCEL_SUCCESS, editedParcel);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateParcel(lastParcel, editedParcel);
        expectedModel.maintainSorted();
        expectedModel.forceSelectParcel(editedParcel);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        assertTrue(checkSortedLinear(model));
    }

    /**
     * Method to retrieve list of parcels from input model and checks if the list is in sorted order.
     */
    private boolean checkSortedLinear(Model inputModel) {
        ObservableList<ReadOnlyParcel> listToCheck = inputModel.getActiveList();
        return checkSorted(listToCheck);
    }

    /**
     * Iterates recursively through the input list to check whether each element is in sorted order.
     */
    private boolean checkSorted(List listToCheck) {
        if (listToCheck.size() == 0 || listToCheck.size() == 1) {
            return true;
        } else {
            return compareParcels((Parcel) listToCheck.get(0), (Parcel) listToCheck.get(1))
                    && checkSorted(listToCheck.subList(1, listToCheck.size() - 1));
        }
    }

    /**
     * Compares two parcels, returns true if first Parcel should come before second Parcel
     * @param parcelOne
     * @param parcelTwo
     * @return true when ParcelOne compared to ParcelTwo returns less than 0;
     */
    private boolean compareParcels(ReadOnlyParcel parcelOne, ReadOnlyParcel parcelTwo) {
        int result = parcelOne.compareTo(parcelTwo);
        return result <= 0;
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditCommand prepareCommand(Index index, EditParcelDescriptor descriptor) {
        EditCommand editCommand = new EditCommand(index, descriptor);
        editCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editCommand;
    }
}
```
