package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ScheduleCommandParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.UniqueScheduleList;
import seedu.address.testutil.TypicalPersons;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author limcel
public class ScheduleCommandTest {
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
    public void executeScheduleCommand_success() throws CommandException, ParseException, DuplicatePersonException {
        Index firstPersonIndex = Index.fromOneBased(1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ScheduleCommandParser.DATE_FORMAT.parse("2018-12-25 10:00:00"));

        Schedule schedulePerson = new Schedule(model.getFilteredPersonList().get(0).getName().toString(), calendar);
        ScheduleCommand scheduleCommand = new ScheduleCommand(firstPersonIndex, calendar);
        Model model1 = createAndSetModel(scheduleCommand);
        model1.addPerson(TypicalPersons.ALICE);
        CommandResult result = scheduleCommand.execute();
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        //Add the schedule to the expected model to compare with the model created
        expectedModel.addSchedule(schedulePerson);

        String expectedMessage = "Added " + schedulePerson.getPersonName() + " to consultations schedule "
                + "on " + schedulePerson.getDate().toString() + ".\n"
                + "Use 'viewsch' or 'viewschedule' command to view all your schedules.";

        assertEquals(expectedMessage , result.feedbackToUser);

    }

    @Test
    public void executeScheduleCommand_targetIndexExceededListSize() throws CommandException, DuplicatePersonException {
        Index targetIndex = Index.fromOneBased(1000);
        ScheduleCommand scheduleCommand = createNewScheduleCommand(targetIndex);
        Model model = createAndSetModel(scheduleCommand);
        model.addPerson(TypicalPersons.ALICE);
        thrown.expect(CommandException.class);
        scheduleCommand.execute();
    }

    @Test
    public void executeScheduleCommand_toStringUnitTest() {
        Calendar date = Calendar.getInstance();
        Schedule newSchedule = new Schedule(TypicalPersons.ALICE.getName().toString(), date);
        UniqueScheduleList newList = new UniqueScheduleList();
        newList.add(newSchedule);

        String expectedPersonName = "Alice Pauline";
        String personName = newSchedule.getPersonName();
        assertEquals(expectedPersonName, personName);

        String expectedDate = date.getTime().toString();
        String dateInSchedule = newSchedule.getDate().toString();
        assertEquals(expectedDate, dateInSchedule);

    }

    //================================= HELPER METHODS =====================================

    /**
     * Returns a model that is set
     */
    private Model createAndSetModel(ScheduleCommand scheduleCommand) {
        Model model = new ModelManager();
        scheduleCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return model;
    }

    /**
     * Creates a new ScheduleCommand
     */
    private ScheduleCommand createNewScheduleCommand(Index index) {
        Calendar date = Calendar.getInstance();
        ScheduleCommand scheduleCommand = new ScheduleCommand(index, date);
        return scheduleCommand;
    }
}
