# joppeel
###### /java/seedu/address/logic/commands/LoadCommandTest.java
``` java
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
```
###### /java/seedu/address/logic/parser/LoadCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import seedu.address.logic.commands.LoadCommand;

public class LoadCommandParserTest {

    private static String fileName = "sampleData.xml";
    private static String testFileName = "sampleDataForTheTests.xml";
    private static Path source = Paths.get("src/test/data/sandbox/" + fileName);
    private static Path destination = Paths.get("data/" + testFileName);
    private LoadCommandParser parser = new LoadCommandParser();

    /**
     * Executed before testing, copies the sample data to the correct directory
     */
    @BeforeClass
    public static void copyTestData() throws Exception {
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Executed after testing, removes the sample data
     */
    @AfterClass
    public static void deleteTestData() throws Exception {
        Files.delete(destination);
    }


    @Test
    public void parse_failure() throws Exception {
        String expectedMessage = String.format(LoadCommand.MESSAGE_ERROR_LOADING_ADDRESSBOOK,
                LoadCommand.MESSAGE_USAGE);

        // test with incorrect file name
        assertParseFailure(parser, "notAFile123456.xml", expectedMessage);
    }

}
```
###### /java/seedu/address/model/person/BirthdayTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {

    @Test
    public void isValidEmail() {
        // blank email
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only

        // missing parts
        assertFalse(Birthday.isValidBirthday("11/11")); // missing local part
        assertFalse(Birthday.isValidBirthday("11")); // missing '@' symbol
        assertFalse(Birthday.isValidBirthday("19/10/")); // missing domain name

        // invalid parts
        assertFalse(Birthday.isValidBirthday("-11/23/2332")); // using illegal characters
        assertFalse(Birthday.isValidBirthday("22/12/12222")); // too many numbers in the year part
        assertFalse(Birthday.isValidBirthday("q/qw/qweq")); // using letters instead of numbers
        assertFalse(Birthday.isValidBirthday("000/99/2323")); // too many numbers for the day
        assertFalse(Birthday.isValidBirthday("23 /12/1122")); // space between

        // valid email
        assertTrue(Birthday.isValidBirthday("11/12/1099"));
        assertTrue(Birthday.isValidBirthday("09/03/2010"));
        assertTrue(Birthday.isValidBirthday("12/13/1009"));   // also accepts months which don't exists
        assertTrue(Birthday.isValidBirthday("00/00/2011"));
    }
}
```
###### /java/seedu/address/testutil/EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Birthday} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withBirthday(String birthday) {
        try {
            ParserUtil.parseBirthday(Optional.of(birthday)).ifPresent(descriptor::setBirthday);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("birthday is expected to be unique.");
        }
        return this;
    }

```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets the {@code Birthday} of the {@code Person} that we are building.
     */
    public PersonBuilder withBirthday(String birthday) {
        try {
            this.person.setBirthday(new Birthday(birthday));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("birthday is expected to be unique.");
        }
        return this;
    }

```
###### /java/systemtests/LoadCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import seedu.address.logic.commands.LoadCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.storage.XmlAddressBookStorage;

public class LoadCommandSystemTest extends AddressBookSystemTest {

    private static String testFileName = "sampleDataForTheTests.xml";
    private static Path destination = Paths.get("data/" + testFileName);

    @BeforeClass
    public static void createTestFile() throws Exception {
        new File(destination.toString());
    }

    @AfterClass
    public static void deleteTestFile() throws Exception {
        Files.delete(destination);
    }

    @Test
    public void load_success() throws Exception {

        ArrayList newPersons = new ArrayList<>(Arrays.asList(AMY, BOB));
        addInfoToTestFile(newPersons);
        String command = LoadCommand.COMMAND_WORD + " " + testFileName;

        // try to load new address book, should load new persons
        assertCommandSuccess(command, newPersons);

        // try to load the same address book again, shouldn't add new persons
        assertCommandSuccess(command, new ArrayList());

        deleteTestFile();
        createTestFile();

        ArrayList partiallyNewPersons = new ArrayList<>(Arrays.asList(AMY, ALICE));
        addInfoToTestFile(partiallyNewPersons);

        // try to load partially new persons, AMY is already in the address book
        assertCommandSuccess(command, new ArrayList(Arrays.asList(ALICE)));

    }

    @Test
    public void load_failure() throws Exception {

        // test with a non-existing file
        String command = LoadCommand.COMMAND_WORD + " notATestFile1234.xml";
        assertCommandFailure(command, LoadCommand.MESSAGE_ERROR_LOADING_ADDRESSBOOK);

        // test without giving any parameters
        command = LoadCommand.COMMAND_WORD;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
    }

    /**
     * Creates new xml file, adds contacts from {@code addressBook} to the test file
     */
    public void addTestDataToFile(ReadOnlyAddressBook addressBook) throws Exception {
        XmlAddressBookStorage storage = new XmlAddressBookStorage(destination.toString());
        storage.saveAddressBook(addressBook, destination.toString());
    }

    /**
     * Creates a new instance of address book and adds contacts from {@code personsToAdd}
     */
    public AddressBook createAddressBook(List<ReadOnlyPerson> personsToAdd) throws Exception {
        AddressBook addressBook = new AddressBook();

        for (ReadOnlyPerson person : personsToAdd) {
            addressBook.addPerson(person);
        }
        return addressBook;
    }

    /**
     * Adds {@code personToAdd} to an XML file
     */
    public void addInfoToTestFile(List<ReadOnlyPerson> personsToAdd) throws Exception {
        AddressBook addressBook = createAddressBook(personsToAdd);
        addTestDataToFile(addressBook);
    }

    /**
     * Executes the {@code LoadCommand} that adds {@code personsToAdd} to the model and verifies that the command box
     * displays an empty string, the result display box displays the success message of executing {@code LoadCommand},
     * and the model related components equal to the current model added with {@code personsToAdd}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes,
     * the browser url and selected card remains unchanged.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, List<ReadOnlyPerson> personsToAdd) {
        Model expectedModel = getModel();
        for (ReadOnlyPerson person : personsToAdd) {
            try {
                expectedModel.addPerson(person);
            } catch (DuplicatePersonException dpe) {
                // don't have to do anything
            }
        }
        String expectedResultMessage = String.format(LoadCommand.MESSAGE_LOAD_ADDRESSBOOK_SUCCESS);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, List)} except that the
     * result display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see LoadCommandSystemTest#assertCommandSuccess(String, List)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }


}
```
