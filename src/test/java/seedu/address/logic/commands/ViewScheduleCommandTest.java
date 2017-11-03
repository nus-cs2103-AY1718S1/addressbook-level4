package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.schedule.Schedule;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author limcel
public class ViewScheduleCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void executeViewScheduleCommand_success() throws PersonNotFoundException {
        Model model = new ModelManager();
        Calendar date = Calendar.getInstance();
        Schedule newSchedule = new Schedule(getTypicalAddressBook().getPersonList().get(0).getName().toString(), date);
        ViewScheduleCommand newViewCommand = new ViewScheduleCommand();
        model.addSchedule(newSchedule);
        newViewCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        ObservableList<Schedule> newScheduleList = model.getAddressBook().getScheduleList();
        String expectedMessage = "Listed your schedule. \n" + newScheduleList.toString();

        CommandResult result = newViewCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);

    }
}
