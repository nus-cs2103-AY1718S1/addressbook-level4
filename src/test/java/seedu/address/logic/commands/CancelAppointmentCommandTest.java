package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;

//@@author Eric
public class CancelAppointmentCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        String personName = "Alice";
        String appointmentDescription  = "Lunch, tonight 5pm";
        CancelAppointmentCommand command = new CancelAppointmentCommand(personName, appointmentDescription);
        CancelAppointmentCommand command1 = new CancelAppointmentCommand(personName, appointmentDescription);

        assertEquals(command, command1);

        String personName1 = "Bob";
        command1 = new CancelAppointmentCommand(personName1, appointmentDescription);

        //Different name
        assertNotEquals(command, command1);

        String appointmentDescription1 = "Dinner, tonight 5pm";
        command = new CancelAppointmentCommand(personName1, appointmentDescription1);

        //Different appointment
        assertNotEquals(command, command1);

    }

    @Test
    public void noSuchPersonTest() throws CommandException {
        CancelAppointmentCommand command = new CancelAppointmentCommand("noSuchPerson", "Dinner, 5pm");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        thrown.expect(CommandException.class);
        command.executeUndoableCommand();
    }

    @Test
    public void noSuchAppointmentTest() throws CommandException {
        CancelAppointmentCommand command = new CancelAppointmentCommand("Alice Pauline", "Study, 5pm");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        thrown.expect(CommandException.class);
        command.executeUndoableCommand();
    }

}
