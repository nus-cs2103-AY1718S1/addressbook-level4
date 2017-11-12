//@@author namvd2709
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

public class AppointCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addAppointment_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withAppointment("15/12/2020 00:00 60").build();
        AppointCommand appointCommand = prepareCommand(INDEX_FIRST_PERSON,
                Appointment.getOriginalAppointment(editedPerson.getAppointment().value));
        String expectedMessage = String.format(AppointCommand.MESSAGE_APPOINT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(appointCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void  execute_removeAppointment_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withAppointment("").build();
        AppointCommand appointCommand = prepareCommand(INDEX_FIRST_PERSON, "");

        String expectedMessage = String.format(AppointCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(appointCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withAppointment(VALID_APPOINTMENT).build();
        AppointCommand appointCommand = prepareCommand(INDEX_FIRST_PERSON,
                Appointment.getOriginalAppointment(editedPerson.getAppointment().value));

        String expectedMessage = String.format(AppointCommand.MESSAGE_APPOINT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(appointCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AppointCommand appointCommand = prepareCommand(outOfBoundIndex, VALID_APPOINTMENT);

        assertCommandFailure(appointCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws IllegalValueException {
        final AppointCommand standardCommand = new AppointCommand(INDEX_FIRST_PERSON,
                                                                    new Appointment(VALID_APPOINTMENT));

        // same values -> returns true
        String copyDescriptor = new String(VALID_APPOINTMENT);
        AppointCommand commandWithSameValues = new AppointCommand(INDEX_FIRST_PERSON, new Appointment(copyDescriptor));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AppointCommand(INDEX_SECOND_PERSON,
                new Appointment(VALID_APPOINTMENT))));
    }

    private AppointCommand prepareCommand(Index index, String appointment) throws IllegalValueException {
        AppointCommand appointCommand = new AppointCommand(index, new Appointment(appointment));
        appointCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return appointCommand;
    }
}
