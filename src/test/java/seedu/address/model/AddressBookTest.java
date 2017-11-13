package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookPersons;
import static seedu.address.testutil.TypicalTasks.EVENT;
import static seedu.address.testutil.TypicalTasks.MEETING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getModuleList());
        assertEquals(Collections.emptyList(), addressBook.getTaskList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBookPersons();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        // Repeat ALICE twice
        List<Person> newPersons = Arrays.asList(new Person(ALICE), new Person(ALICE));
        List<Module> newModules = new ArrayList<>(ALICE.getModules());
        List<Task> newTasks = Arrays.asList(new Task(MEETING), new Task(EVENT));
        AddressBookStub newData = new AddressBookStub(newPersons, newModules, newTasks);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
    }

    @Test
    public void getModuleList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getModuleList().remove(0);
    }

    // @@author ahmadalkaff
    @Test
    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTaskList().remove(0);
    }
    // @@author

    /**
     * A stub ReadOnlyAddressBook whose persons and mods lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<ReadOnlyPerson> persons = FXCollections.observableArrayList();
        private final ObservableList<Module> modules = FXCollections.observableArrayList();
        // @@author tanchc
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
        // @@author
        AddressBookStub(Collection<? extends ReadOnlyPerson> persons, Collection<? extends Module> modules,
                        Collection<? extends ReadOnlyTask> tasks) {
            this.persons.setAll(persons);
            this.modules.setAll(modules);
            this.tasks.setAll(tasks);
        }

        @Override
        public ObservableList<ReadOnlyPerson> getPersonList() {
            return persons;
        }
        // @@author tanchc
        @Override
        public ObservableList<ReadOnlyTask> getTaskList() {
            return tasks;
        }
        // @@author
        @Override
        public ObservableList<Module> getModuleList() {
            return modules;
        }
    }

}
