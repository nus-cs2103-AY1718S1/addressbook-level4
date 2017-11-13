# PhuaJunJie
###### \java\seedu\address\commons\GoogleAuthenticatorTest.java
``` java
package seedu.address.commons;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.exceptions.GoogleAuthException;

public class GoogleAuthenticatorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private GoogleAuthenticator authenticator = new GoogleAuthenticator();

    /**
     * Checks if Login url generated is in the valid format
     */
    @Test
    public void execute_obtain_loginUrl() {
        assertTrue(authenticator.getAuthorizationUrl().contains(
                "https://accounts.google.com/o/oauth2/auth?client_id"));
    }

    /**
     * Checks if user is authenticated. In this case no token is generated and null exception is thrown.
     */
    @Test
    public void execute_get_invalidToken() throws Exception {
        thrown.expect(GoogleAuthException.class);
        authenticator.getToken();
    }

    /**
     * Checks if user is authenticated. In this case no google contacts list is generated and null exception is thrown.
     */
    @Test
    public void execute_get_invalidGooglePersonList() throws Exception {
        thrown.expect(NullPointerException.class);
        authenticator.getConnections(null);
    }
}
```
###### \java\seedu\address\logic\commands\ExportCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.BERNICE;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.api.services.people.v1.model.Person;

import seedu.address.commons.GoogleContactsBuilder;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.GoogleAuthException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalGoogleContactsList;

public class ExportCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private List<Person> googleList;

    @Before
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
        googleList = new ArrayList<>();
    }

    /**
     * Checks if Login is authenticated. In this case it is not and GoogleAuthException is thrown.
     */
    @Test
    public void execute_require_login() throws Exception {
        thrown.expect(GoogleAuthException.class);
        new ExportCommand();
    }

    /**
     * Test for exporting an empty addressbook. Throws Command exception for empty addressbook
     */
    @Test
    public void execute_commandFailure_noContacts() throws Exception {
        thrown.expect(CommandException.class);
        ExportCommand command = prepareCommand(this.model);
        command.execute();
    }

    /**
     * Test for exporting an empty addressbook when authentication is lost.
     */
    @Test
    public void execute_commandFailure_noAuthenticationToExport() throws Exception {
        thrown.expect(GoogleAuthException.class);
        model.addPerson(BERNICE);
        ExportCommand command = prepareCommand(this.model);
        command.execute();
    }


    /**
     * Test for no importing of contacts to addressbook as all contacts in addressbook are already in google contacts.
     */
    @Test
    public void execute_commandFailure_allGoogleContacts() throws Exception {
        model.addPerson(TypicalGoogleContactsList.FREEDYADDRESSBOOK);

        ExportCommand command = prepareCommand(this.model);
        String expectedMessage = "0 contact/s exported!     All contacts can be now found in google contact";
        assertCommandFailure(command, expectedMessage, model);
    }

    /**
     * Test for converting a addressbook contact into a google contact
     */
    @Test
    public void execute_updateAddressBookContactToGoogle() throws Exception {
        model.addPerson(BERNICE);

        ExportCommand command = prepareCommand(this.model);
        Person aliceGoogleContact = command.createGoogleContact(BERNICE);
        assertEquals(aliceGoogleContact, TypicalGoogleContactsList.BERNICE);
    }
    /**
     * Test for changes in contact after being exported to google
     */
    @Test
    public void execute_exportContactChanges() throws Exception {
        ExportCommand command = prepareCommand(this.model);
        ReadOnlyPerson editedAlice =
                command.getNewAddressBookContact(BERNICE, TypicalGoogleContactsList.BERNICEWITHGOOGLEID);
        assertEquals(editedAlice, TypicalGoogleContactsList.BERNICEADDRESSBOOK);
    }

    /**
     * Test detailed messages for export command.
     */
    @Test
    public void execute_exportDetailedMessages() throws Exception {
        ExportCommand command = prepareCommand(this.model);
        String messageSuccess = command.setCommandMessage(1, 0);
        String messageFailure = command.setCommandMessage(0, 1);
        assertEquals(messageSuccess, "1 contact/s exported!     "
                + "All contacts can be now found in google contact");
        assertEquals(messageFailure, "0 contact/s exported!     1 contact/s failed to export!");
    }

    /**
     * Preparing import command by using a test-only constructor (Using a stub google person list)
     */
    private ExportCommand prepareCommand(Model model) throws CommandException {
        GoogleContactsBuilder builder = null;
        ExportCommand command = new ExportCommand(builder);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts if a command unsuccessfully executed
     */
    private void assertCommandFailure(Command command, String expectedMessage, Model modelstub)
            throws CommandException, GoogleAuthException {
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(modelstub.getAddressBook(), model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\FindLettersCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_ALPHABET_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_NO_ALPHABET_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsAlphabetsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) for {@code FindLettersCommand}.
 */
public class FindLettersCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsAlphabetsPredicate firstPredicate =
                new NameContainsAlphabetsPredicate(Collections.singletonList("first"));
        NameContainsAlphabetsPredicate secondPredicate =
                new NameContainsAlphabetsPredicate(Collections.singletonList("second"));

        FindLettersCommand findFirstCommand = new FindLettersCommand(firstPredicate);
        FindLettersCommand findSecondCommand = new FindLettersCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindLettersCommand findFirstCommandCopy = new FindLettersCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }
    //Test when no input by user
    @Test
    public void executeZeroAlphabetsNoPersonFound() {
        String expectedMessage = String.format(MESSAGE_NO_ALPHABET_LISTED_OVERVIEW);
        FindLettersCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }
    //Test when user inputs partial names
    @Test
    public void executeMultipleAlphabetsMultiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_ALPHABET_LISTED_OVERVIEW, 2);
        FindLettersCommand command = prepareCommand("Ku");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, FIONA));
    }
    //Test when user inputs full names
    @Test
    public void executeMultipleKeywordsMultiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_ALPHABET_LISTED_OVERVIEW, 3);
        FindLettersCommand command = prepareCommand("Kurz Elle Kunz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindLettersCommand prepareCommand(String userInput) {
        FindLettersCommand command =
                new FindLettersCommand(new NameContainsAlphabetsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     */
    private void assertCommandSuccess(FindLettersCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());

    }
}
```
###### \java\seedu\address\logic\commands\ImportCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.api.services.people.v1.model.Person;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.GoogleAuthException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalGoogleContactsList;

public class ImportCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private List<Person> googleList;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        googleList = new ArrayList<>();
    }

    /**
     * Checks if Login is authenticated. In this case it is not as user is not authenticated
     * and GoogleAuthException is thrown.
     */
    @Test
    public void execute_require_login() throws Exception {
        thrown.expect(GoogleAuthException.class);
        new ImportCommand();
    }
    /**
     * Checks if Google contacts is empty. In this case it is empty and null exception is thrown.
     */
    @Test
    public void execute_empty_googleContacts() throws Exception {
        thrown.expect(NullPointerException.class);
        new ImportCommand(googleList).executeUndoableCommand();
    }

    /**
     * Test for normal importing of a google contact
     */
    @Test
    public void execute_commandSuccess_imported() throws Exception {
        googleList.add(TypicalGoogleContactsList.FREDDYGOOGLE);
        ImportCommand command = prepareCommand(googleList, this.model);

        Model modelstub = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelstub.addPerson(TypicalGoogleContactsList.FREEDYADDRESSBOOK);
        String expectedMessage = String.format(Messages.MESSAGE_IMPORT_CONTACT, 1, 0) + "\n";

        assertCommandSuccess(command, expectedMessage, modelstub);
    }

    /**
     * Test for invalid importing of a google contact due to invalid attributes
     */
    @Test
    public void execute_commandFailure_contactInvalidFormat() throws Exception {
        googleList.add(TypicalGoogleContactsList.MAYGOOGLE);
        ImportCommand command = prepareCommand(googleList, this.model);

        String expectedMessage = String.format(Messages.MESSAGE_IMPORT_CONTACT, 0, 1) + "\n"
                + String.format(Messages.MESSAGE_IMPORT_STATUS, 0, 1) + "\n"
                + "Please check the format of the following google contacts :  May";

        assertCommandFailure(command, expectedMessage, this.model);
    }

    /**
     * Test for importing a contact that already exists in the addressbook
     */
    @Test
    public void execute_commandFailure_contactExists() throws Exception {
        model.addPerson(TypicalGoogleContactsList.FREEDYADDRESSBOOK);
        googleList.add(TypicalGoogleContactsList.FREDDYGOOGLE);
        ImportCommand command = prepareCommand(googleList, model);

        String expectedMessage = String.format(Messages.MESSAGE_IMPORT_CONTACT, 0, 1) + "\n"
                + String.format(Messages.MESSAGE_IMPORT_STATUS, 1, 0) + "\n";

        assertCommandFailure(command, expectedMessage, model);
    }

    /**
     * Preparing import command by using a test-only constructor (Using a stub google person list)
     */
    private ImportCommand prepareCommand(List<Person> personList, Model model) throws CommandException {
        ImportCommand command = new ImportCommand(personList);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts if a command successfully executed
     */
    private void assertCommandSuccess(Command command, String expectedMessage, Model modelstub)
            throws CommandException, GoogleAuthException {
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(modelstub.getAddressBook(), model.getAddressBook());
    }

    /**
     * Asserts if a command unsuccessfully executed
     */
    private void assertCommandFailure(Command command, String expectedMessage, Model modelstub)
            throws CommandException, GoogleAuthException {
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(modelstub.getAddressBook(), model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\SyncCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.api.services.people.v1.model.Person;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.GoogleAuthException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalGoogleContactsList;

```
###### \java\seedu\address\logic\commands\SyncCommandTest.java
``` java
public class SyncCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private List<Person> googleList;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        googleList = new ArrayList<>();
    }

    /**
     * Checks if Login is authenticated. In this case it is not.
     */
    @Test
    public void execute_require_login() throws Exception {
        thrown.expect(GoogleAuthException.class);
        new SyncCommand();
    }

    /**
     * Test for normal Syncing of a single google contact
     */
    @Test
    public void execute_syncSuccess() throws Exception {
        model.addPerson(TypicalGoogleContactsList.FREEDYADDRESSBOOK);
        googleList.add(TypicalGoogleContactsList.FREDDYSYNGOOGLE);
        SyncCommand command = prepareCommand(googleList, model);

        Model modelStub = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelStub.addPerson(TypicalGoogleContactsList.FREDDYSYNADDRESSBOOK);
        String expectedMessage = String.format(Messages.MESSAGE_SYNC_CONTACT, 1, 0);

        assertCommandSuccess(command, expectedMessage, modelStub);
    }

    /**
     * Test for invalid Syncing of a google contact due to invalid attributes
     */
    @Test
    public void execute_syncFailure_contactInvalidFormat() throws Exception {

        model.addPerson(TypicalGoogleContactsList.MAYADDRESSBOOK);
        googleList.add(TypicalGoogleContactsList.MAYGOOGLE);
        SyncCommand command = prepareCommand(googleList, model);

        String expectedMessage = String.format(Messages.MESSAGE_SYNC_CONTACT, 0, 1) + "\n"
                + "Please check the format of the following google contacts : May";

        assertCommandFailure(command, expectedMessage, model);
    }

    /**
     * Test for syncing a contact that is of no difference than the one in the addressbook
     */
    @Test
    public void execute_commandFailure_contactSimilar() throws Exception {
        model.addPerson(TypicalGoogleContactsList.FREEDYADDRESSBOOK);
        googleList.add(TypicalGoogleContactsList.FREDDYGOOGLE);
        SyncCommand command = prepareCommand(googleList, model);

        String expectedMessage = String.format(Messages.MESSAGE_SYNC_CONTACT, 0, 0);
        assertCommandFailure(command, expectedMessage, model);
    }

    /**
     * Test for syncing a contact that is no longer in the addressbook. The contact will lose its google contacts
     * status
     */
    @Test
    public void execute_commandFailure_contactNoLongerExists() throws Exception {
        model.addPerson(TypicalGoogleContactsList.FREEDYADDRESSBOOK);
        SyncCommand command = prepareCommand(googleList, model);

        Model modelStub = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelStub.addPerson(TypicalGoogleContactsList.FREEDYNOTGOOGLEADDRESSBOOK);
        String expectedMessage = String.format(Messages.MESSAGE_SYNC_CONTACT, 1, 0);

        assertCommandFailure(command, expectedMessage, modelStub);
    }

    /**
     * Preparing import command by using a test-only constructor (Using a stub google person list)
     */
    private SyncCommand prepareCommand(List<Person> personList, Model model) throws CommandException {
        SyncCommand command = new SyncCommand(personList);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts if a command successfully executed
     */
    private void assertCommandSuccess(Command command, String expectedMessage, Model modelstub)
            throws CommandException, GoogleAuthException {
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(modelstub.getAddressBook(), model.getAddressBook());
    }

    /**
     * Asserts if a command unsuccessfully executed
     */
    private void assertCommandFailure(Command command, String expectedMessage, Model modelstub)
            throws CommandException, GoogleAuthException {
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(modelstub.getAddressBook(), model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\parser\FindLettersCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindLettersCommand;
import seedu.address.model.person.NameContainsAlphabetsPredicate;

public class FindLettersCommandParserTest {

    private FindLettersCommandParser parser = new FindLettersCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindLettersCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindLettersCommand expectedFindCommand = new FindLettersCommand(new
                NameContainsAlphabetsPredicate(Arrays.asList("Fiona", "Amelia")));
        assertParseSuccess(parser, "Fiona Amelia", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Fiona \n \t Amelia  \t", expectedFindCommand);
    }

}
```
###### \java\seedu\address\model\person\GoogleIdTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GoogleIdTest {

    @Test
    public void isValidId() {
        // invalid GoogleId
        assertFalse(GoogleId.isValidGoogleId("")); // empty string
        assertFalse(GoogleId.isValidGoogleId(" ")); // spaces only


        // valid GoogleId
        assertTrue(GoogleId.isValidGoogleId("1234567890")); // numbers
        assertTrue(GoogleId.isValidGoogleId("not GoogleContact")); // Letters
        assertTrue(GoogleId.isValidGoogleId("f2134324")); // alphanumeric

    }
}
```
###### \java\seedu\address\model\person\NameContainsAlphabetsPredicateTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsAlphabetsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateAlphabetList = Collections.singletonList("first");
        List<String> secondPredicateAlphabetList = Arrays.asList("first", "second");

        NameContainsAlphabetsPredicate firstPredicate = new NameContainsAlphabetsPredicate(firstPredicateAlphabetList);
        NameContainsAlphabetsPredicate secondPredicate =
                new NameContainsAlphabetsPredicate(secondPredicateAlphabetList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsAlphabetsPredicate firstPredicateCopy =
                new NameContainsAlphabetsPredicate(firstPredicateAlphabetList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testNameContainsAlphabetsReturnsTrue() {
        // One Alphabet
        NameContainsAlphabetsPredicate predicate = new NameContainsAlphabetsPredicate(Collections.singletonList("A"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Multiple Separated Alphabets
        predicate = new NameContainsAlphabetsPredicate(Arrays.asList("A", "B"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only One Matching Alphabet Sequence
        predicate = new NameContainsAlphabetsPredicate(Arrays.asList("Bob", "Car"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-Case Alphabets
        predicate = new NameContainsAlphabetsPredicate(Arrays.asList("bO", "aLIC"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Full Keywords
        predicate = new NameContainsAlphabetsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void testNameDoesNotContainAlphabetsReturnsFalse() {
        // Zero Keywords
        NameContainsAlphabetsPredicate predicate = new NameContainsAlphabetsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching Alphabets in Sequence
        predicate = new NameContainsAlphabetsPredicate(Arrays.asList("Dob"));
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsAlphabetsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
```
###### \java\seedu\address\testutil\GoogleContactBuilder.java
``` java
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.api.services.people.v1.model.Address;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.PhoneNumber;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.FacebookAddress;
import seedu.address.model.person.GoogleId;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Builds Stub Google contact and addressbook contacts
 */
public class GoogleContactBuilder {
    private String nameDefault;
    private String phoneDefault;
    private String emailDefault;
    private String addressDefault;
    private String googleIdDefault;

    /**
     * Constructor
     */
    public GoogleContactBuilder(String name, String phone, String email, String address, String googleId) {
        this.nameDefault = name;
        this.phoneDefault = phone;
        this.emailDefault = email;
        this.addressDefault = address;
        this.googleIdDefault = googleId;
    }

    /**
     * Builds Stub Google contact
     */
    public Person buildGooglePerson() {
        Person contactToCreate = new Person();

        List names = new ArrayList();
        List email = new ArrayList();
        List phone = new ArrayList();
        List address = new ArrayList();

        names.add(new Name().setGivenName(nameDefault));
        email.add(new EmailAddress().setValue(emailDefault));
        phone.add(new PhoneNumber().setValue(phoneDefault));
        address.add(new Address().setStreetAddress(addressDefault));

        return contactToCreate.setNames(names).setEmailAddresses(email).setPhoneNumbers(phone)
                .setAddresses(address).setResourceName(googleIdDefault);
    }
    /**
     * Builds Stub Addressbook contact
     */
    public seedu.address.model.person.Person buildAddressBookPerson() {
        seedu.address.model.person.Person person;
        try {
            seedu.address.model.person.Name name = new seedu.address.model.person.Name(nameDefault);
            Phone phone = new Phone(phoneDefault);
            Email email = new Email(emailDefault);
            seedu.address.model.person.Address address = new seedu.address.model.person.Address(addressDefault);
            GoogleId id;
            if (googleIdDefault.length() != 0) {
                id = new GoogleId(googleIdDefault.substring(8));
            } else {
                id = new GoogleId("not GoogleContact");
            }

            Tag tag = new Tag("GoogleContact");
            Set<Tag> tags = new HashSet<>();
            tags.add(tag);
            FacebookAddress facebookAddress = new FacebookAddress("");
            seedu.address.model.person.Birthday birthday = new seedu.address.model.person.Birthday("");
            person = new seedu.address.model.person.Person(name, phone, email, address, birthday, facebookAddress,
                    tags, id);
        } catch (IllegalValueException e) {
            person = null;
        }
        return person;
    }
}
```
###### \java\seedu\address\testutil\TypicalGoogleContactsList.java
``` java
package seedu.address.testutil;

import com.google.api.services.people.v1.model.Person;

/**
 * List of Stub Google contacts and its version of the addressbook contact
 */
public class TypicalGoogleContactsList {
    public static final Person FREDDYGOOGLE = new GoogleContactBuilder("Freddy", "91234567",
            "freddy@hotmail.com", "Simei Blk 1 avenue 2",
            "1234567891011121310").buildGooglePerson();
    public static final Person MAYGOOGLE = new GoogleContactBuilder("May", "91234567",
            "may", "Simei Blk 15 avenue 21",
            "1234567891011121311").buildGooglePerson();
    public static final Person FREDDYSYNGOOGLE = new GoogleContactBuilder("Freddy", "90000000",
            "freddy@hotmail.com", "Simei Blk 1 avenue 2",
            "1234567891011121310").buildGooglePerson();

    public static final Person BERNICE = new GoogleContactBuilder("Alice Pauline",
            "85355255", "alice@example.com",
            "123, Jurong West Ave 6, #08-111", null).buildGooglePerson();

    public static final Person BERNICEWITHGOOGLEID = new GoogleContactBuilder("Alice Pauline",
            "85355255", "alice@example.com",
            "123, Jurong West Ave 6, #08-111", "12345678910111213112").buildGooglePerson();

    public static final seedu.address.model.person.Person FREEDYADDRESSBOOK = new GoogleContactBuilder("Freddy",
            "91234567", "freddy@hotmail.com", "Simei Blk 1 avenue 2",
            "1234567891011121310").buildAddressBookPerson();
    public static final seedu.address.model.person.Person MAYADDRESSBOOK = new GoogleContactBuilder("May",
            "91234567",
            "may@hotmail.com", "Simei Blk 15 avenue 21",
            "1234567891011121311").buildAddressBookPerson();
    public static final seedu.address.model.person.Person FREDDYSYNADDRESSBOOK = new GoogleContactBuilder(
            "Freddy", "90000000", "freddy@hotmail.com", "Simei Blk 1 avenue 2",
            "1234567891011121310").buildAddressBookPerson();
    public static final seedu.address.model.person.Person FREEDYNOTGOOGLEADDRESSBOOK = new GoogleContactBuilder(
            "Freddy", "91234567", "freddy@hotmail.com", "Simei Blk 1 avenue 2",
            "").buildAddressBookPerson();
    public static final seedu.address.model.person.Person BERNICEADDRESSBOOK = new GoogleContactBuilder(
            "Alice Pauline", "85355255", "alice@example.com",
            "123, Jurong West Ave 6, #08-111", "12345678910111213112").buildAddressBookPerson();

}
```
