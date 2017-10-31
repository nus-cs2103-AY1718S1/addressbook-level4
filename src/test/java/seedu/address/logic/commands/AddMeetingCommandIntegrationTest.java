package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMeetings.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meeting.Meeting;
import seedu.address.testutil.MeetingBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddMeetingCommand}.
 */
public class AddMeetingCommandIntegrationTest {

    private Model model;
    private Index index;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newMeeting_success() throws Exception {
        Meeting validMeeting = new MeetingBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addMeeting(validMeeting);

        assertCommandSuccess(prepareCommand(validMeeting, model), model,
                String.format(AddMeetingCommand.MESSAGE_SUCCESS, validMeeting), expectedModel);
    }

    @Test
    public void execute_duplicateMeeting_throwsCommandException() {
        Meeting meetingInList = new Meeting(model.getAddressBook().getMeetingList().get(0));
        assertCommandFailure(prepareCommand(meetingInList, model), model, AddMeetingCommand.MESSAGE_DUPLICATE_MEETING);
    }

    /**
     * Generates a new {@code AddMeetingCommand} which upon execution, adds {@code meeting} into the {@code model}.
     */
    private AddMeetingCommand prepareCommand(Meeting meeting, Model model) {
        this.index = Index.fromZeroBased(1);
        AddMeetingCommand command =
                new AddMeetingCommand(meeting.getName(), meeting.getDate(), meeting.getPlace(), index);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
