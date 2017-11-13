package seedu.address.logic.commands;
//@@author liuhang0213
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingList;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyMeeting;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.InternalId;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Contains integration tests (interaction with the Model) and unit tests for NextMeetingCommand.
 */
public class NextMeetingCommandTest {

    private Model model;
    private NextMeetingCommand nextMeetingCommand;
    private ReadOnlyMeeting meeting;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalMeetingList(), new UserPrefs());
        nextMeetingCommand = new NextMeetingCommand();
        nextMeetingCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        meeting = getTypicalMeetingList().getUpcomingMeeting();
    }

    @Test
    public void execute_nextMeeting_successfully() {
        StringBuilder expected = new StringBuilder();
        expected.append(NextMeetingCommand.MESSAGE_OUTPUT_PREFIX);
        try {
            for (InternalId id : meeting.getListOfPersonsId()) {
                expected.append(model.getAddressBook().getPersonByInternalIndex(id.getId()).getName().fullName);
                expected.append(", ");
            }
            expected.delete(expected.length() - 2, expected.length());
            expected.append('\n');
            expected.append(model.getMeetingList().getUpcomingMeeting().toString());
            assertCommandSuccess(nextMeetingCommand, model, expected.toString(), model);
        } catch (PersonNotFoundException e) {
            fail("All people should be in the address book.");
        }
    }
}
