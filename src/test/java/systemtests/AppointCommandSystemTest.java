//@@author namvd2709
package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AppointCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class AppointCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void appoint() throws Exception {
        Model model = getModel();

        /* Case: adds appointment to person, with random whitespace */
        Index index = INDEX_SECOND_PERSON;
        String command = " " + AppointCommand.COMMAND_WORD + "  " + index.getOneBased() + "   "
                + PREFIX_APPOINT + "  " + "27/09/2018    00:00  60    ";
        ReadOnlyPerson personToEdit = getModel().getFilteredPersonList().get(index.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withAppointment("27/09/2018 00:00 60").build();
        assertCommandSuccess(command, index, editedPerson, null);

        /* Case: undo adding appointment */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage, null);

        /* Case: redo adding appointment */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updatePerson(getModel().getFilteredPersonList().get(index.getZeroBased()), editedPerson);
        assertCommandSuccess(command, model, expectedResultMessage, null);

        /* Case: changing appointment for person already with appointment -> override */
        command = AppointCommand.COMMAND_WORD + " " + index.getOneBased() + APPOINTMENT_DESC;
        editedPerson = new PersonBuilder(personToEdit).withAppointment(VALID_APPOINTMENT).build();
        assertCommandSuccess(command, index, editedPerson, null);

        /* Case: changing appointment for person with appointment when the two clashes -> override */
        command = AppointCommand.COMMAND_WORD + " " + index.getOneBased() + " "
                + PREFIX_APPOINT + "01/01/2020 00:30 60";
        editedPerson = new PersonBuilder(personToEdit).withAppointment("01/01/2020 00:30 60").build();
        model.updatePerson(getModel().getFilteredPersonList().get(index.getZeroBased()), editedPerson);
        assertCommandSuccess(command, index, editedPerson, null);

        /* Case: adding another appointment to another person */
        Index otherIndex = INDEX_THIRD_PERSON;
        command = AppointCommand.COMMAND_WORD + " " + otherIndex.getOneBased() + " "
                + PREFIX_APPOINT + "01/01/2021 00:00 60";
        personToEdit = getModel().getFilteredPersonList().get(otherIndex.getZeroBased());
        editedPerson = new PersonBuilder(personToEdit).withAppointment("01/01/2021 00:00 60").build();
        assertCommandSuccess(command, otherIndex, editedPerson, null);

        /* Case: adding a clashing appointment to another person -> fails */
        command = AppointCommand.COMMAND_WORD + " " + otherIndex.getOneBased() + APPOINTMENT_DESC;
        editedPerson = new PersonBuilder(personToEdit).withAppointment(VALID_APPOINTMENT).build();
        model.updatePerson(getModel().getFilteredPersonList().get(otherIndex.getZeroBased()), editedPerson);
        assertCommandFailure(command, AppointCommand.MESSAGE_APPOINTMENT_CLASH);

        /* Case: removing an appointment */
        command = AppointCommand.COMMAND_WORD + " " + otherIndex.getOneBased() + " " + PREFIX_APPOINT;
        editedPerson = new PersonBuilder(personToEdit).withAppointment("").build();
        model.updatePerson(getModel().getFilteredPersonList().get(otherIndex.getZeroBased()), editedPerson);
        expectedResultMessage = String.format(AppointCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS, editedPerson);
        assertCommandSuccess(command, model, expectedResultMessage, null);

        /* Case: trying to re-add removed appointment to another contact -> success */
        otherIndex = INDEX_FIRST_PERSON;
        command = AppointCommand.COMMAND_WORD + " " + otherIndex.getOneBased() + " "
                + PREFIX_APPOINT + "01/01/2021 00:00 60";
        personToEdit = getModel().getFilteredPersonList().get(otherIndex.getZeroBased());
        editedPerson = new PersonBuilder(personToEdit).withAppointment("01/01/2021 00:00 60").build();
        assertCommandSuccess(command, otherIndex, editedPerson, null);

        /* Case: wrong format -> fails */
        command = AppointCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_APPOINT + "1/1/20 00:00 60";
        assertCommandFailure(command, Appointment.MESSAGE_INVALID_DATETIME);

        /* Case: missing duration -> fails */
        command = AppointCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_APPOINT + "15/12/2020 00:00";
        assertCommandFailure(command, Appointment.MESSAGE_APPOINTMENT_CONSTRAINTS);

        /* Case: missing time -> fails */
        command = AppointCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_APPOINT + "15/12/2020 60";
        assertCommandFailure(command, Appointment.MESSAGE_APPOINTMENT_CONSTRAINTS);

        /* Case: missing date -> fails */
        command = AppointCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_APPOINT + "12:00 60";
        assertCommandFailure(command, Appointment.MESSAGE_APPOINTMENT_CONSTRAINTS);

        /* Case: invalid day of month -> fails */
        command = AppointCommand.COMMAND_WORD + " " + index.getOneBased() + " "
                + PREFIX_APPOINT + "32/01/2020 00:00 60";
        assertCommandFailure(command, Appointment.MESSAGE_INVALID_DATETIME);

        /* Case: invalid month -> fails */
        command = AppointCommand.COMMAND_WORD + " " + index.getOneBased() + " "
                + PREFIX_APPOINT + "31/13/2020 00:00 60";
        assertCommandFailure(command, Appointment.MESSAGE_INVALID_DATETIME);

        /* Case: invalid duration -> fails */
        command = AppointCommand.COMMAND_WORD + " " + index.getOneBased() + " "
                + PREFIX_APPOINT + "31/13/2020 00:00 -12";
        assertCommandFailure(command, Appointment.MESSAGE_DURATION_CONSTRAINT);
        command = AppointCommand.COMMAND_WORD + " " + index.getOneBased() + " "
                + PREFIX_APPOINT + "31/13/2020 00:00 random";
        assertCommandFailure(command, Appointment.MESSAGE_DURATION_CONSTRAINT);

        /* Case: invalid date -> fails */
        command = AppointCommand.COMMAND_WORD + " " + index.getOneBased() + " "
                + PREFIX_APPOINT + "29/02/2019 00:00 60";
        assertCommandFailure(command, Appointment.MESSAGE_INVALID_DATETIME);
    }

    /**
     * Performs verification
     */
    private void assertCommandSuccess(String command, Index toEdit, ReadOnlyPerson editedPerson,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updatePerson(
                    expectedModel.getFilteredPersonList().get(toEdit.getZeroBased()), editedPerson);
            expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } catch (DuplicatePersonException | PersonNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedPerson is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(AppointCommand.MESSAGE_APPOINT_SUCCESS, editedPerson), expectedSelectedCardIndex);
    }
    /**
     * Executes command and assert success
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts failure
     * @see EditCommandSystemTest#assertCommandFailure(String, String)
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
