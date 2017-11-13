package seedu.address.model;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.ELLE;

import java.util.Iterator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

//@@author limcel
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
    public void test_sortedList() throws DuplicatePersonException {
        UniquePersonList uniquePersonList = new UniquePersonList();

        uniquePersonList.add(ALICE);
        uniquePersonList.add(ELLE);
        uniquePersonList.add(BENSON);

        assertFalse(isSorted(uniquePersonList));

        ObservableList<ReadOnlyPerson> sortedList = uniquePersonList.asObservableListSortedByName();
        assertTrue(isSorted(sortedList));
    }

    //======================================== HELPER METHODS ============================================

    /**
     * @param e
     * @return boolean of value true if the list is not sorted, false otherwise.
     */
    private boolean isSorted(UniquePersonList e) {
        Iterator<Person> iterator = e.iterator();
        while (iterator.hasNext()) {
            Person person1 = iterator.next();
            Person person2 = iterator.hasNext() ? iterator.next() : null;
            if (person2 != null) {
                if (person1.getName().toString().toLowerCase().compareTo(person2.getName().toString().toLowerCase())
                        < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param e
     * @return boolean of value true if the list is sorted, false otherwise.
     */
    private boolean isSorted(ObservableList<ReadOnlyPerson> e) {
        Iterator<ReadOnlyPerson> iterator = e.iterator();
        while (iterator.hasNext()) {
            ReadOnlyPerson person1 = iterator.next();
            ReadOnlyPerson person2 = iterator.hasNext() ? iterator.next() : null;
            if (person2 != null) {
                if (person1.getName().toString().toLowerCase().compareTo(person2.getName().toString().toLowerCase())
                        > 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
