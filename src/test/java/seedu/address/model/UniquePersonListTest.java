package seedu.address.model;

import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.AddAppointmentParser;
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
    //@@author
}
