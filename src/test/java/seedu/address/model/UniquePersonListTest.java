package seedu.address.model;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.address.model.person.Appointment;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.TypicalPersons;



public class UniquePersonListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePersonList uniquePersonList = new UniquePersonList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePersonList.asObservableList().remove(0);
    }

    @Test
    public void setPersonThrowsNoPersonFoundException() throws PersonNotFoundException, DuplicatePersonException {
        UniquePersonList uniquePersonList = new UniquePersonList();
        thrown.expect(PersonNotFoundException.class);
        uniquePersonList.setPerson(TypicalPersons.ALICE, TypicalPersons.ALICE);
    }

    @Test
    public void removePersonThrowsNoPersonFoundException() throws PersonNotFoundException, DuplicatePersonException {
        UniquePersonList uniquePersonList = new UniquePersonList();
        thrown.expect(PersonNotFoundException.class);
        uniquePersonList.remove(TypicalPersons.ALICE);
    }

    //@@author Eric
    @Test
    public void addAppointmentReturnsCorrectPerson() throws DuplicatePersonException, PersonNotFoundException {

        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(Appointment.DATE_FORMATTER.parse("2018/10/10 10:10"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Appointment appointment = new Appointment (TypicalPersons.ALICE.getName().toString(), calendar);

        UniquePersonList uniquePersonList = new UniquePersonList();
        uniquePersonList.add(TypicalPersons.ALICE);

        uniquePersonList.addAppointment(appointment);

        assertTrue(uniquePersonList.contains(TypicalPersons.ALICE));

        for (ReadOnlyPerson person : uniquePersonList.asObservableList()) {
            if (person.getName().toString().equals(TypicalPersons.ALICE.getName().fullName)) {
                assertTrue(person.getAppointment().equals(appointment));
            }
        }
    }

    @Test
    public void addAppointmentThrowsNoPersonFoundException() throws PersonNotFoundException {
        UniquePersonList uniquePersonList = new UniquePersonList();
        thrown.expect(PersonNotFoundException.class);
        uniquePersonList.addAppointment(new Appointment(TypicalPersons.ALICE.getName().toString()));
    }

    @Test
    public void testSortedAppointment() throws DuplicatePersonException {

        //ALICE appointment = 2018/01/02 00:00 BENSON appointment = 2018/01/01 00:00,
        //BENSON appointment should be before ALICE
        assertTrue(TypicalPersons.BENSON.getAppointment().getDate()
                .before(TypicalPersons.ALICE.getAppointment().getDate()));

        UniquePersonList list = new UniquePersonList();
        list.add(TypicalPersons.ALICE);
        list.add(TypicalPersons.BENSON);

        ObservableList<ReadOnlyPerson> sortedList = list.asObservableListSortedByAppointment();

        //Order should be BENSON then ALICE
        assertEquals(sortedList.get(0).getName(), TypicalPersons.BENSON.getName());
        assertEquals(sortedList.get(1).getName(), TypicalPersons.ALICE.getName());

    }
    //@@author
}
