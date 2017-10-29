package seedu.address.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ACTIVITY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BIKING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BIKING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BIKING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PERSONTOMEET_BIKING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONENUM_BIKING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PLACE_BIKING;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstMeetingOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETING;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditMeetingCommand.EditMeetingDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.ReadOnlyMeeting;
import seedu.address.testutil.EditMeetingDescriptorBuilder;
import seedu.address.testutil.MeetingBuilder;


public class EditMeetingCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Meeting editedMeeting = new MeetingBuilder().build();
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(editedMeeting).build();
        EditMeetingCommand editMeetingCommand = prepareCommand(INDEX_FIRST_MEETING, descriptor);

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateMeeting(model.getFilteredMeetingList().get(0), editedMeeting);

        assertCommandSuccess(editMeetingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastMeeting = Index.fromOneBased(model.getFilteredMeetingList().size());
        ReadOnlyMeeting lastMeeting = model.getFilteredMeetingList().get(indexLastMeeting.getZeroBased());

        MeetingBuilder meetingInList = new MeetingBuilder(lastMeeting);
        Meeting editedMeeting = meetingInList.withNameMeeting(VALID_NAME_BIKING).withPhoneNum(VALID_PHONENUM_BIKING)
                .withDateTime(VALID_DATE_BIKING).withPersonToMeet(VALID_PERSONTOMEET_BIKING).withPlace(VALID_PLACE_BIKING)
                .build();

        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder().withMeetingName(VALID_NAME_BIKING)
                .withPhoneNum(VALID_PHONENUM_BIKING).withPlace(VALID_PLACE_BIKING).withDate(VALID_DATE_BIKING)
                .build();

        EditMeetingCommand editMeetingCommand = prepareCommand(indexLastMeeting, descriptor);

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateMeeting(lastMeeting, editedMeeting);

        assertCommandSuccess(editMeetingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditMeetingCommand editMeetingCommand = prepareCommand(INDEX_FIRST_MEETING, new EditMeetingDescriptor());
        ReadOnlyMeeting editedMeeting = model.getFilteredMeetingList().get(INDEX_FIRST_MEETING.getZeroBased());

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editMeetingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstMeetingOnly(model);

        ReadOnlyMeeting meetingInFilteredList = model.getFilteredMeetingList().get(INDEX_FIRST_MEETING.getZeroBased());
        Meeting editedMeeting = new MeetingBuilder(meetingInFilteredList).withNameMeeting(VALID_NAME_BIKING).build();
        EditMeetingCommand editMeetingCommand = prepareCommand(INDEX_FIRST_MEETING,
                new EditMeetingDescriptorBuilder().withMeetingName(VALID_NAME_BIKING).build());

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateMeeting(model.getFilteredMeetingList().get(0), editedMeeting);

        assertCommandSuccess(editMeetingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateMeetingUnfilteredList_failure() {
        Meeting firstMeeting = new Meeting(model.getFilteredMeetingList().get(INDEX_FIRST_MEETING.getZeroBased()));
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(firstMeeting).build();
        EditMeetingCommand editMeetingCommand = prepareCommand(INDEX_SECOND_MEETING, descriptor);

        assertCommandFailure(editMeetingCommand, model, EditMeetingCommand.MESSAGE_DUPLICATE_MEETING);
    }

    @Test
    public void execute_duplicateMeetingFilteredList_failure() {
        showFirstMeetingOnly(model);

        // edit meeting in filtered list into a duplicate in address book
        ReadOnlyMeeting meetingInList = model.getAddressBook().getMeetingList().get(INDEX_SECOND_MEETING.getZeroBased());
        EditMeetingCommand editMeetingCommand = prepareCommand(INDEX_FIRST_MEETING,
                new EditMeetingDescriptorBuilder(meetingInList).build());

        assertCommandFailure(editMeetingCommand, model, EditMeetingCommand.MESSAGE_DUPLICATE_MEETING);
    }

    @Test
    public void execute_invalidMeetingIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMeetingList().size() + 1);
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder().withMeetingName(VALID_NAME_BIKING).build();
        EditMeetingCommand editMeetingCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editMeetingCommand, model, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidMeetingIndexFilteredList_failure() {
        showFirstMeetingOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_MEETING;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getMeetingList().size());

        EditMeetingCommand editMeetingCommand = prepareCommand(outOfBoundIndex,
                new EditMeetingDescriptorBuilder().withMeetingName(VALID_NAME_BIKING).build());

        assertCommandFailure(editMeetingCommand, model, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditMeetingCommand standardCommand = new EditMeetingCommand(INDEX_FIRST_MEETING, DESC_ACTIVITY);

        // same values -> returns true
        EditMeetingDescriptor copyDescriptor = new EditMeetingDescriptor(DESC_ACTIVITY);
        EditMeetingCommand commandWithSameValues = new EditMeetingCommand(INDEX_FIRST_MEETING, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditMeetingCommand(INDEX_SECOND_MEETING, DESC_ACTIVITY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditMeetingCommand(INDEX_FIRST_MEETING, DESC_BIKING)));
    }

    /**
     * Returns an {@code EditMeetingCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditMeetingCommand prepareCommand(Index index, EditMeetingDescriptor descriptor) {
        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(index, descriptor);
        editMeetingCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editMeetingCommand;
    }
}
