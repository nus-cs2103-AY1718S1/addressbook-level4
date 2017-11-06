package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TodoItem;
import seedu.address.model.tag.Tag;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getTagList());
        //@@author qihao27
        assertEquals(Collections.emptyList(), addressBook.getTodoList());
        //@@author
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        // Repeat ALICE twice
        List<Person> newPersons = Arrays.asList(new Person(ALICE), new Person(ALICE));
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        //@@author qihao27
        List<TodoItem> newTodo = new ArrayList<>(ALICE.getTodoItems());
        AddressBookStub newData = new AddressBookStub(newPersons, newTags, newTodo);
        //@@author

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTagList().remove(0);
    }

    //@@author qihao27
    @Test
    public void getTodoList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTodoList().remove(0);
    }
    //@@author

    /**
     * A stub ReadOnlyAddressBook whose persons, tags lists and todolists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<ReadOnlyPerson> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        //@@author qihao27
        private final ObservableList<TodoItem> todo = FXCollections.observableArrayList();

        AddressBookStub(Collection<? extends ReadOnlyPerson> persons, Collection<? extends Tag> tags,
                        Collection<? extends TodoItem> todoItems) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
            this.todo.setAll(todoItems);
        }
        //@@author

        @Override
        public ObservableList<ReadOnlyPerson> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }

        //@@author qihao27
        @Override
        public ObservableList<TodoItem> getTodoList() {
            return todo;
        }
        //@@author
    }

}
