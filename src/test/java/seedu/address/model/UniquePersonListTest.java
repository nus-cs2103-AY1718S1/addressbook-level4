package seedu.address.model;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.address.logic.parser.AddAppointmentParser;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;
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
    public void addAppointmentThrowsNoPersonFoundException() throws PersonNotFoundException {
        UniquePersonList uniquePersonList = new UniquePersonList();
        thrown.expect(PersonNotFoundException.class);

        try {
            uniquePersonList.addAppointment(TypicalPersons.ALICE,
                    AddAppointmentParser.getAppointmentFromString("lunch, tomorrow 5pm"));
        } catch (seedu.address.logic.parser.exceptions.ParseException e) {
            fail();
        }
    }

    @Test
    public void testSortedAppointment() throws DuplicatePersonException {


        assertTrue(TypicalPersons.BENSON.getAppointments().get(0).getDate()
                .before(TypicalPersons.ALICE.getAppointments().get(0).getDate()));

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
