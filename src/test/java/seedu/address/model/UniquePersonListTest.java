package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

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
    public void sortPersons_correctlySortsInternalList() {
        UniquePersonList uniquePersonList1 = new UniquePersonList();
        UniquePersonList uniquePersonList2 = new UniquePersonList();
        List<ReadOnlyPerson> newPersons = Arrays.asList(new Person(DANIEL),
                new Person(CARL), new Person(BENSON), new Person(ALICE));
        List<ReadOnlyPerson> sortedList = Arrays.asList(new Person(ALICE),
                new Person(BENSON), new Person(CARL), new Person(DANIEL));
        try {
            uniquePersonList1.setPersons(newPersons);
            uniquePersonList1.sortPersons();
            uniquePersonList2.setPersons(sortedList);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("person is expected to be unique.");
        }
        assertEquals(uniquePersonList1, uniquePersonList2);
    }
}
