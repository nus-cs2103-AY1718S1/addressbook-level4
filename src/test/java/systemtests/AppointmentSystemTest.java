package systemtests;

import org.junit.Test;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.commands.CancelAppointmentCommand;
import seedu.address.logic.parser.AddAppointmentParser;
import seedu.address.model.Model;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author Eric
public class AppointmentSystemTest extends AddressBookSystemTest {

    @Test
    public void addAndRemoveAppointment() throws Exception {
        Model model = getModel();
        ReadOnlyPerson toAddAppointment = model.getAddressBook().getPersonList().get(0);
        String description = "dinner";
        String str = " 1 d/" + description + ", tonight 7pm to 10pm";
        String command = AddAppointmentCommand.COMMAND_WORD + str;
        assertCommandSuccess(command, toAddAppointment, AddAppointmentParser.getAppointmentFromString(str));

        command = CancelAppointmentCommand.COMMAND_WORD + " " + description + " with " + toAddAppointment.getName();
        assertCommandSuccess(command, toAddAppointment, AddAppointmentParser.getAppointmentFromString(str));
    }



    /**
     * Performs verification that the expected model is the same after command is executing
     */
    private void assertCommandSuccess(String command, ReadOnlyPerson toAdd, Appointment appointment) {
        Model expectedModel = getModel();
        String expectedResultMessage;

        try {
            if (!command.contains("cancel")) {
                expectedModel.addAppointment(toAdd, appointment);
                expectedResultMessage = AddAppointmentCommand.MESSAGE_SUCCESS;
            } else {
                expectedModel.removeAppointment(toAdd, appointment);
                expectedResultMessage = CancelAppointmentCommand.MESSAGE_SUCCESS;
            }
        } catch (PersonNotFoundException e) {
            throw new IllegalArgumentException("person not found in model.");
        }

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }
    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyPerson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     *
     * @see AppointmentSystemTest#assertCommandSuccess(String, ReadOnlyPerson, Appointment)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
    }

}
