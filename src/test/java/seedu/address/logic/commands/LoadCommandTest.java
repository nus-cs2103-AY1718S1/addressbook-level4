package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;


public class LoadCommandTest {

    private ModelManager model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void execute_newAddressBookContainsNewPersons() throws Exception {
        AddressBook loadedAddressBook = createAddressBookWithNewPersons(getListOfNewPersons());
        LoadCommand loadNewCommand = prepareLoadCommand(loadedAddressBook);
        List<ReadOnlyPerson> expectedPersons = getListOfNewPersons();
        List<ReadOnlyPerson> oldPersons = model.getFilteredPersonList();

        for (ReadOnlyPerson person : oldPersons) {
            expectedPersons.add(person);
        }

        loadNewCommand.executeUndoableCommand();

        // test that the lists contain the same elements
        assertTrue(model.getFilteredPersonList().containsAll(expectedPersons));
        assertTrue(expectedPersons.containsAll(model.getFilteredPersonList()));
    }

    @Test
    public void execute_newAddressBookContainsPartiallyNewPersons() throws Exception {
        AddressBook loadedAddressBook = createAddressBookWithNewPersons(getListOfPartiallyNewPersons());
        LoadCommand loadNewCommand = prepareLoadCommand(loadedAddressBook);
        List<ReadOnlyPerson> expectedPersons = getListOfPartiallyNewPersons();
        List<ReadOnlyPerson> oldPersons = model.getFilteredPersonList();

        for (ReadOnlyPerson person : oldPersons) {
            if (!expectedPersons.contains(person)) {
                expectedPersons.add(person);
            }
        }

        loadNewCommand.executeUndoableCommand();

        // test that the lists contain the same elements
        assertTrue(model.getFilteredPersonList().containsAll(expectedPersons));
        assertTrue(expectedPersons.containsAll(model.getFilteredPersonList()));
    }

    @Test
    public void execute_loadTheExistingPersons() throws Exception {
        AddressBook loadedAddressBook = createAddressBookWithNewPersons(model.getFilteredPersonList());
        LoadCommand loadNewCommand = prepareLoadCommand(loadedAddressBook);
        List<ReadOnlyPerson> expectedPersons = model.getFilteredPersonList();

        loadNewCommand.executeUndoableCommand();

        // test that the lists contain the same elements
        assertTrue(model.getFilteredPersonList().containsAll(expectedPersons));
        assertTrue(expectedPersons.containsAll(model.getFilteredPersonList()));
    }


    @Test
    public void equals() {

        AddressBook typicalAddressBook = getTypicalAddressBook();
        AddressBook newAddressBook = createAddressBookWithNewPersons(getListOfNewPersons());
        LoadCommand loadTypicalCommand = prepareLoadCommand(typicalAddressBook);
        LoadCommand loadNewCommand = prepareLoadCommand(newAddressBook);

        // same object -> returns true
        assertTrue(loadTypicalCommand.equals(loadTypicalCommand));

        // same values -> returns true
        LoadCommand loadTypicalCommandCopy = prepareLoadCommand(typicalAddressBook);
        assertTrue(loadTypicalCommand.equals(loadTypicalCommandCopy));

        // different types -> returns false
        assertFalse(loadTypicalCommand.equals(1));

        // null -> returns false
        assertFalse(loadTypicalCommand.equals(null));

        // different object -> returns false
        assertFalse(loadTypicalCommand.equals(loadNewCommand));
    }


    /**
     * Creates new instance of LoadCommand, the parameter represents the address book
     * that will be loaded to the existing address book.
     */
    private LoadCommand prepareLoadCommand(AddressBook addressBook) {
        LoadCommand loadCommand = new LoadCommand(addressBook);
        loadCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return loadCommand;
    }

    /**
     * Creates new instance of AddressBook with persons that are not included
     * in the typical address book.
     */
    private AddressBook createAddressBookWithNewPersons(List<ReadOnlyPerson> persons) {
        AddressBook addressBook = new AddressBook();

        for (ReadOnlyPerson person : persons) {
            try {
                addressBook.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "Not possible";
            }
        }
        return addressBook;
    }

    /**
     * Returns list of completely new example persons
     */
    private List<ReadOnlyPerson> getListOfNewPersons() {

        ReadOnlyPerson mark = new PersonBuilder().withName("Mark Long")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("mark@example.com")
                .withPhone("85355255").withBirthday("13/12/2001").withTags("friends").build();
        ReadOnlyPerson julia = new PersonBuilder().withName("Julia Gordon")
                .withAddress("311, Clementi Ave 2, #02-25").withEmail("julia@example.com")
                .withPhone("98765432").withBirthday("03/02/2011").withTags("owesMoney", "friends").build();
        ReadOnlyPerson harvey = new PersonBuilder().withName("Harvey Ross").withPhone("95352563")
                .withEmail("harvey@example.com").withAddress("wall street").withBirthday("09/06/1993").build();
        ReadOnlyPerson sam = new PersonBuilder().withName("Sam West").withPhone("87652533")
                .withEmail("sam@example.com").withAddress("10th street").withBirthday("01/09/1992").build();

        return new ArrayList<>(Arrays.asList(mark, julia, harvey, sam));
    }

    /**
     * Returns list of completely new example persons
     */
    private List<ReadOnlyPerson> getListOfPartiallyNewPersons() {

        ReadOnlyPerson fiona = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
                .withEmail("lydia@example.com").withAddress("little tokyo").withBirthday("24/12/1994").build();
        ReadOnlyPerson george = new PersonBuilder().withName("George Best").withPhone("9482442")
                .withEmail("anna@example.com").withAddress("4th street").withBirthday("31/12/1999").build();
        ReadOnlyPerson harvey = new PersonBuilder().withName("Harvey Ross").withPhone("95352563")
                .withEmail("harvey@example.com").withAddress("wall street").withBirthday("09/06/1993").build();
        ReadOnlyPerson sam = new PersonBuilder().withName("Sam West").withPhone("87652533")
                .withEmail("sam@example.com").withAddress("10th street").withBirthday("01/09/1992").build();

        return new ArrayList<>(Arrays.asList(fiona, george, harvey, sam));
    }

}
