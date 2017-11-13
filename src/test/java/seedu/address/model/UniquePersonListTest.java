package seedu.address.model;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
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

    //@@author charlesgoh
    @Test
    public void sortPerson_byName_bothOrders() {
        // Set up expected result
        List<ReadOnlyPerson> personList = TypicalPersons.getTypicalPersons();
        Comparator<ReadOnlyPerson> nameComparator = new Comparator<ReadOnlyPerson>() {
            @Override
            public int compare(ReadOnlyPerson o1, ReadOnlyPerson o2) {
                return o1.getName().value.compareTo(o2.getName().value);
            }
        };
        Collections.sort(personList, nameComparator);
        ObservableList<ReadOnlyPerson> expectedTaskList = FXCollections.observableList(personList);

        // Set up actual result
        AddressBook addressBook = TypicalPersons.getTypicalPersonsAddressBook();
        addressBook.sortPersonsBy("name", "asc");
        ObservableList<ReadOnlyPerson> actualTaskList = addressBook.getPersonList();

        // Check ascending order sort
        assertEquals(expectedTaskList, actualTaskList);

        // Set up descending order sort
        Collections.sort(personList, Collections.reverseOrder(nameComparator));
        expectedTaskList = FXCollections.observableList(personList);
        addressBook.sortPersonsBy("name", "desc");
        actualTaskList = addressBook.getPersonList();

        // Check descending order sort
        assertEquals(expectedTaskList, actualTaskList);
    }

    @Test
    public void sortPerson_byPhone_bothOrders() {
        // Set up expected result
        List<ReadOnlyPerson> personList = TypicalPersons.getTypicalPersons();
        Comparator<ReadOnlyPerson> phoneComparator = new Comparator<ReadOnlyPerson>() {
            @Override
            public int compare(ReadOnlyPerson o1, ReadOnlyPerson o2) {
                return o1.getPhone().value.compareTo(o2.getPhone().value);
            }
        };
        Collections.sort(personList, phoneComparator);
        ObservableList<ReadOnlyPerson> expectedTaskList = FXCollections.observableList(personList);

        // Set up actual result
        AddressBook addressBook = TypicalPersons.getTypicalPersonsAddressBook();
        addressBook.sortPersonsBy("phone", "asc");
        ObservableList<ReadOnlyPerson> actualTaskList = addressBook.getPersonList();

        // Check ascending order sort
        assertEquals(expectedTaskList, actualTaskList);

        // Set up descending order sort
        Collections.sort(personList, Collections.reverseOrder(phoneComparator));
        expectedTaskList = FXCollections.observableList(personList);
        addressBook.sortPersonsBy("phone", "desc");
        actualTaskList = addressBook.getPersonList();

        // Check descending order sort
        assertEquals(expectedTaskList, actualTaskList);
    }

    @Test
    public void sortPerson_byEmail_bothOrders() {
        // Set up expected result
        List<ReadOnlyPerson> personList = TypicalPersons.getTypicalPersons();
        Comparator<ReadOnlyPerson> emailComparator = new Comparator<ReadOnlyPerson>() {
            @Override
            public int compare(ReadOnlyPerson o1, ReadOnlyPerson o2) {
                return o1.getEmail().value.compareTo(o2.getEmail().value);
            }
        };
        Collections.sort(personList, emailComparator);
        ObservableList<ReadOnlyPerson> expectedTaskList = FXCollections.observableList(personList);

        // Set up actual result
        AddressBook addressBook = TypicalPersons.getTypicalPersonsAddressBook();
        addressBook.sortPersonsBy("email", "asc");
        ObservableList<ReadOnlyPerson> actualTaskList = addressBook.getPersonList();

        // Check ascending order sort
        assertEquals(expectedTaskList, actualTaskList);

        // Set up descending order sort
        Collections.sort(personList, Collections.reverseOrder(emailComparator));
        expectedTaskList = FXCollections.observableList(personList);
        addressBook.sortPersonsBy("email", "desc");
        actualTaskList = addressBook.getPersonList();

        // Check descending order sort
        assertEquals(expectedTaskList, actualTaskList);
    }

    @Test
    public void sortPerson_byAddress_bothOrders() {
        // Set up expected result
        List<ReadOnlyPerson> personList = TypicalPersons.getTypicalPersons();
        Comparator<ReadOnlyPerson> addressComparator = new Comparator<ReadOnlyPerson>() {
            @Override
            public int compare(ReadOnlyPerson o1, ReadOnlyPerson o2) {
                return o1.getAddress().value.compareTo(o2.getAddress().value);
            }
        };
        Collections.sort(personList, addressComparator);
        ObservableList<ReadOnlyPerson> expectedTaskList = FXCollections.observableList(personList);

        // Set up actual result
        AddressBook addressBook = TypicalPersons.getTypicalPersonsAddressBook();
        addressBook.sortPersonsBy("address", "asc");
        ObservableList<ReadOnlyPerson> actualTaskList = addressBook.getPersonList();

        // Check ascending order sort
        assertEquals(expectedTaskList, actualTaskList);

        // Set up descending order sort
        Collections.sort(personList, Collections.reverseOrder(addressComparator));
        expectedTaskList = FXCollections.observableList(personList);
        addressBook.sortPersonsBy("address", "desc");
        actualTaskList = addressBook.getPersonList();

        // Check descending order sort
        assertEquals(expectedTaskList, actualTaskList);
    }
    //@@author
}
