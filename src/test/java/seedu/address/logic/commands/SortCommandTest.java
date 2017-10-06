package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class SortCommandTest {

    private static final ReadOnlyPerson ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255")
            .withTags("friends").build();
    private static final ReadOnlyPerson BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    private static final ReadOnlyPerson CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    private static final ReadOnlyPerson DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").build();
    private static final ReadOnlyPerson ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    private static final ReadOnlyPerson FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    private static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    private static final String TO_SORT_NONE = "unsorted";
    private static final String TO_SORT_NAME = "name";
    private static final String TO_SORT_PHONE = "phone";
    private static final String TO_SORT_EMAIL = "email";
    private static final String TO_SORT_ADDRESS = "address";

    private static final AddressBook unsortedAddressBook = getAddressBook(TO_SORT_NONE);
    private static final AddressBook sortedAddressBookByName = getAddressBook(TO_SORT_NAME);
    private static final AddressBook sortedAddressBookByPhone = getAddressBook(TO_SORT_PHONE);
    private static final AddressBook sortedAddressBookByEmail = getAddressBook(TO_SORT_EMAIL);
    private static final AddressBook sortedAddressBookByAddress = getAddressBook(TO_SORT_ADDRESS);

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_sortByName_success() {
        SortCommand sortCommand = prepareCommand(TO_SORT_NAME);
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, TO_SORT_NAME);
        unsortedAddressBook.sortList(TO_SORT_NAME);
        assertEquals(sortedAddressBookByName, unsortedAddressBook);
        assertCommandSuccess(sortCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_sortByPhone_success() {
        SortCommand sortCommand = prepareCommand(TO_SORT_PHONE);
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, TO_SORT_PHONE);
        unsortedAddressBook.sortList(TO_SORT_PHONE);
        assertEquals(sortedAddressBookByPhone, unsortedAddressBook);
        assertCommandSuccess(sortCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_sortByEmail_success() {
        SortCommand sortCommand = prepareCommand(TO_SORT_EMAIL);
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, TO_SORT_EMAIL);
        unsortedAddressBook.sortList(TO_SORT_EMAIL);
        assertEquals(sortedAddressBookByEmail, unsortedAddressBook);
        assertCommandSuccess(sortCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_sortByAddress_success() {
        SortCommand sortCommand = prepareCommand(TO_SORT_ADDRESS);
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, TO_SORT_ADDRESS);
        unsortedAddressBook.sortList(TO_SORT_ADDRESS);
        assertEquals(sortedAddressBookByAddress, unsortedAddressBook);
        assertCommandSuccess(sortCommand, model, expectedMessage, model);
    }

    /**
     * Generates a new {@code SortCommand} which upon execution, sorts the AddressBook accordingly.
     */
    private SortCommand prepareCommand(String toSort) {
        SortCommand command = new SortCommand(toSort);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    private static AddressBook getAddressBook(String toSort) {
        AddressBook ab = new AddressBook();
        return getAddressBook(toSort, ab);
    }

    private static AddressBook getAddressBook(String toSort, AddressBook ab) {
        switch (toSort) {
        case TO_SORT_NONE:
            for (ReadOnlyPerson person : getTypicalPersonsUnsorted()) {
                try {
                    ab.addPerson(person);
                } catch (DuplicatePersonException e) {
                    assert false : "not possible";
                }
            }
            return ab;
        case TO_SORT_NAME:
            for (ReadOnlyPerson person : getTypicalPersonsSortByName()) {
                try {
                    ab.addPerson(person);
                } catch (DuplicatePersonException e) {
                    assert false : "not possible";
                }
            }
            return ab;
        case TO_SORT_PHONE:
            for (ReadOnlyPerson person : getTypicalPersonsSortByPhone()) {
                try {
                    ab.addPerson(person);
                } catch (DuplicatePersonException e) {
                    assert false : "not possible";
                }
            }
            return ab;
        case TO_SORT_EMAIL:
            for (ReadOnlyPerson person : getTypicalPersonsSortByEmail()) {
                try {
                    ab.addPerson(person);
                } catch (DuplicatePersonException e) {
                    assert false : "not possible";
                }
            }
            return ab;
        case TO_SORT_ADDRESS:
            for (ReadOnlyPerson person : getTypicalPersonsSortByAddress()) {
                try {
                    ab.addPerson(person);
                } catch (DuplicatePersonException e) {
                    assert false : "not possible";
                }
            }
            return ab;
        default:
            return ab;
        }
    }

    private static List<ReadOnlyPerson> getTypicalPersonsUnsorted() {
        return new ArrayList<>(Arrays.asList(ELLE, CARL, BENSON, ALICE, FIONA, GEORGE, DANIEL));
    }

    private static List<ReadOnlyPerson> getTypicalPersonsSortByName() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    private static List<ReadOnlyPerson> getTypicalPersonsSortByPhone() {
        return new ArrayList<>(Arrays.asList(ALICE, DANIEL, ELLE, FIONA, GEORGE, CARL, BENSON));
    }

    private static List<ReadOnlyPerson> getTypicalPersonsSortByEmail() {
        return new ArrayList<>(Arrays.asList(ALICE, GEORGE, DANIEL, CARL, BENSON, FIONA, ELLE));
    }

    private static List<ReadOnlyPerson> getTypicalPersonsSortByAddress() {
        return new ArrayList<>(Arrays.asList(DANIEL, ALICE, BENSON, GEORGE, FIONA, ELLE, CARL));
    }
}
