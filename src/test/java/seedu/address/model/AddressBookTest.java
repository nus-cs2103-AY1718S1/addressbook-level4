package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.parser.CliSyntax.ARG_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.ARG_EMAIL;
import static seedu.address.logic.parser.CliSyntax.ARG_NAME;
import static seedu.address.logic.parser.CliSyntax.ARG_PHONE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getSortedTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getSortedTypicalPersons;
import static seedu.address.testutil.TypicalPersons.getUnsortedTypicalPersons;

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
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.weblink.WebLink;
import seedu.address.model.tag.Tag;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getTagList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getSortedTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        // Repeat ALICE twice
        List<Person> newPersons = Arrays.asList(new Person(ALICE), new Person(ALICE));
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        List<WebLink> newWebLinks = new ArrayList<>(ALICE.getWebLinks());
        AddressBookStub newData = new AddressBookStub(newPersons, newTags, newWebLinks);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void sortPersonListByName_modifyList_success() {
        try {
            AddressBook newData = new AddressBook();
            newData.setPersons(getSortedTypicalPersons());
            addressBook.setPersons(getUnsortedTypicalPersons());
            addressBook.sortPersons(ARG_NAME);
            assertEquals(addressBook, newData);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("person is expected to be unique.");
        }
    }

    @Test
    public void sortPersonListByPhone_modifyList_success() {
        try {
            List<ReadOnlyPerson> sortedPersons = Arrays.asList(new Person(ALICE),
                    new Person(DANIEL), new Person(ELLE), new Person(FIONA), new Person(GEORGE),
                    new Person(CARL), new Person(BENSON));
            AddressBook newData = new AddressBook();
            newData.setPersons(sortedPersons);
            addressBook.setPersons(getUnsortedTypicalPersons());
            addressBook.sortPersons(ARG_PHONE);
            assertEquals(addressBook, newData);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("person is expected to be unique.");
        }
    }

    @Test
    public void sortPersonListByEmail_modifyList_success() {
        try {
            List<ReadOnlyPerson> sortedPersons = Arrays.asList(new Person(ALICE),
                    new Person(GEORGE), new Person(DANIEL), new Person(CARL),
                    new Person(BENSON), new Person(FIONA), new Person(ELLE));
            AddressBook newData = new AddressBook();
            newData.setPersons(sortedPersons);
            addressBook.setPersons(getUnsortedTypicalPersons());
            addressBook.sortPersons(ARG_EMAIL);
            assertEquals(addressBook, newData);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("person is expected to be unique.");
        }
    }

    @Test
    public void sortPersonListByAddress_modifyList_success() {
        try {
            List<ReadOnlyPerson> sortedPersons = Arrays.asList(new Person(DANIEL),
                    new Person(ALICE), new Person(BENSON), new Person(GEORGE), new Person(FIONA),
                    new Person(ELLE), new Person(CARL));
            AddressBook newData = new AddressBook();
            newData.setPersons(sortedPersons);
            addressBook.setPersons(getUnsortedTypicalPersons());
            addressBook.sortPersons(ARG_ADDRESS);
            assertEquals(addressBook, newData);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("person is expected to be unique.");
        }
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

    /**
     * A stub ReadOnlyAddressBook whose persons and tags lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<ReadOnlyPerson> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        private final ObservableList<WebLink> webLinks = FXCollections.observableArrayList();

        AddressBookStub(Collection<? extends ReadOnlyPerson> persons, Collection<? extends Tag> tags,
                        Collection<? extends WebLink> webLinks) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
            this.webLinks.setAll(webLinks);
        }

        @Override
        public ObservableList<ReadOnlyPerson> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
