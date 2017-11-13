package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

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
    public void removePersonFromList_invalidPerson_throwsPersonNotFoundException() throws PersonNotFoundException {
        UniquePersonList uniquePersonList = new UniquePersonList();
        thrown.expect(PersonNotFoundException.class);
        Person alice = new PersonBuilder().withName("Alice").build();
        uniquePersonList.remove(alice);
    }

    @Test
    public void setPerson_throwsPersonNotFoundException() throws PersonNotFoundException, DuplicatePersonException {
        UniquePersonList uniquePersonList = new UniquePersonList();
        thrown.expect(PersonNotFoundException.class);
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        uniquePersonList.setPerson(alice, bob);
    }
}
