# yangminxingnus
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void addRemarkPerson(ReadOnlyPerson person, String FilePath, Index targetIndex) {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\AddReminderTest.java
``` java
        @Override
        public void addRemarkPerson(ReadOnlyPerson person, String remark, Index targetIndex) {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\AddReminderTest.java
``` java

```
###### \java\seedu\address\logic\commands\RemarkCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;

/**
  * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_addRemark_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("Remark").build();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertEquals(editedPerson, expectedModel.getFilteredPersonList().get(0));
    }
    @Test
    public void execute_invalidIndexUnfilteredList_failure() throws IllegalValueException {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundsIndex, "CS2103T/SEC/1");
        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    @Test
    public void executeCommandSuccess() throws IllegalValueException {
        ReadOnlyPerson personToAdd = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        personToAdd.remarkProperty().set(new Remark("CS2103T/SEC/1"));
        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, personToAdd);
        RemarkCommand remarkCommand = prepareCommand(INDEX_SECOND_PERSON, "CS2103T/SEC/1");
        assertCommandSuccessRemark(remarkCommand, model, expectedMessage);
    }

    /**
     * Returns a {@code RemarkCommand} with the parameter {@code index}.
     */
    private RemarkCommand prepareCommand(Index index, String remarkSent) throws IllegalValueException {

        Integer newIndex = index.getOneBased();
        Remark remark = new Remark(remarkSent);
        RemarkCommand Command = new RemarkCommand(newIndex, remark);
        Command.setData(model, new CommandHistory(), new UndoRedoStack());
        return Command;
    }
    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccessRemark(Command command, Model actualModel, String expectedMessage) {
        try {

            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);;
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }


}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_remark() throws Exception {
        final Remark remark = new Remark("CS2101/SEC/1");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
            + 1 + " " + PREFIX_REMARK + " " + "CS2101/SEC/1");
        assertEquals(new RemarkCommand(1, remark), command);
    }
    @Test
    public void parseCommand_logout() throws Exception {
        final LogoutCommand command = new LogoutCommand();
        assertEquals(command, command);
    }
    @Test
    public void parseCommand_email() throws Exception {
        final EmailCommand command = new EmailCommand("friends", "party");
        assertFalse(new EmailCommand("colleagues", "birthday").equals(command));
    }
```
###### \java\seedu\address\logic\parser\RemarkCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;
import seedu.address.testutil.RemarkBuilder;

public class RemarkCommandParserTest {

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_validValue_success() throws ParseException, IllegalValueException {

        Remark expectedRemark = new RemarkBuilder().withDetails(VALID_REMARK_AMY);

        assertParseSuccess(parser, " 1 " + PREFIX_REMARK + VALID_REMARK_AMY,
                new RemarkCommand(1, expectedRemark));

    }

    @Test
    public void parse_voidValue_success() throws ParseException, IllegalValueException {

        Remark expectedRemark = new RemarkBuilder().withDetails("");

        assertParseSuccess(parser, " 1 " + PREFIX_REMARK + "",
                new RemarkCommand(1, expectedRemark));

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid remark
        assertParseFailure(parser, " 1 " + PREFIX_REMARK + INVALID_REMARK_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));

    }
}
```
###### \java\seedu\address\storage\AccountStorageTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.UserPrefs;

public class AccountStorageTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/AccountStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAccPrefs_nullFilePath_throwsNullPointerException() throws
            DataConversionException, IOException {
        thrown.expect(NullPointerException.class);
        readAccountsPrefs(null);
    }

    private Optional<AccountsStorage> readAccountsPrefs(String userPrefsFileInTestDataFolder) throws
            DataConversionException, IOException {
        String prefsFilePath = addToTestDataPathIfNotNull(userPrefsFileInTestDataFolder);
        return new AccountsStorage(prefsFilePath).readAccountsPrefs(prefsFilePath);
    }

    @Test
    public void readAccPrefs_missingFile_emptyResult() throws DataConversionException, IOException {
        assertFalse(readAccountsPrefs("NonExistentFile.json").isPresent());
    }

    private String addToTestDataPathIfNotNull(String userPrefsFileInTestDataFolder) {
        return userPrefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + userPrefsFileInTestDataFolder
                : null;
    }

    @Test
    public void savePrefs_nullPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveUserPrefs(null, "SomeFile.json");
    }

    @Test
    public void saveUserPrefs_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveUserPrefs(new UserPrefs(), null);
    }

    /**
     * Saves {@code userPrefs} at the specified {@code prefsFileInTestDataFolder} filepath.
     */
    private void saveUserPrefs(UserPrefs userPrefs, String prefsFileInTestDataFolder) {
        try {
            new JsonUserPrefsStorage(addToTestDataPathIfNotNull(prefsFileInTestDataFolder))
                    .saveUserPrefs(userPrefs);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file", ioe);
        }
    }

    @Test
    public void saveAccPrefs_allInOrder_success() throws DataConversionException, IOException {

        AccountsStorage original = new AccountsStorage();
        original.getHm().put("test", "test");

        String pefsFilePath = testFolder.getRoot() + File.separator + "TempPrefs.json";
        JsonAccountsStorage jsonAccountsPrefsStorage = new JsonAccountsStorage(pefsFilePath);

        //Try writing when the file doesn't exist
        jsonAccountsPrefsStorage.saveAccountsPrefs(original);
        AccountsStorage readBack = jsonAccountsPrefsStorage.readAccountsPrefs().get();
        assertEquals(original, readBack);
    }
}
```
###### \java\seedu\address\storage\JsonAccountsStorageTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.UserPrefs;

public class JsonAccountsStorageTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/JsonAccountsStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAccPrefs_nullFilePath_throwsNullPointerException()
            throws DataConversionException, IOException {
        thrown.expect(NullPointerException.class);
        readAccPrefs(null);
    }

    private Optional<AccountsStorage> readAccPrefs(String userPrefsFileInTestDataFolder)
            throws DataConversionException, IOException {
        String prefsFilePath = addToTestDataPathIfNotNull(userPrefsFileInTestDataFolder);
        return new JsonAccountsStorage(prefsFilePath).readAccountsPrefs(prefsFilePath);
    }

    @Test
    public void readAccPrefs_missingFile_emptyResult() throws DataConversionException, IOException {
        assertFalse(readAccPrefs("NonExistentFile.json").isPresent());
    }

    private String addToTestDataPathIfNotNull(String userPrefsFileInTestDataFolder) {
        return userPrefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + userPrefsFileInTestDataFolder
                : null;
    }

    @Test
    public void savePrefs_nullPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveUserPrefs(null, "SomeFile.json");
    }

    @Test
    public void saveUserPrefs_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveUserPrefs(new UserPrefs(), null);
    }

    /**
     * Saves {@code userPrefs} at the specified {@code prefsFileInTestDataFolder} filepath.
     */
    private void saveUserPrefs(UserPrefs userPrefs, String prefsFileInTestDataFolder) {
        try {
            new JsonUserPrefsStorage(addToTestDataPathIfNotNull(prefsFileInTestDataFolder))
                    .saveUserPrefs(userPrefs);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file", ioe);
        }
    }

    @Test
    public void saveUserPrefs_allInOrder_success() throws DataConversionException, IOException {

        UserPrefs original = new UserPrefs();
        original.setGuiSettings(1200, 200, 0, 2);

        String pefsFilePath = testFolder.getRoot() + File.separator + "TempPrefs.json";
        JsonUserPrefsStorage jsonUserPrefsStorage = new JsonUserPrefsStorage(pefsFilePath);

        //Try writing when the file doesn't exist
        jsonUserPrefsStorage.saveUserPrefs(original);
        UserPrefs readBack = jsonUserPrefsStorage.readUserPrefs().get();
        assertEquals(original, readBack);

        //Try saving when the file exists
        original.setGuiSettings(5, 5, 5, 5);
        jsonUserPrefsStorage.saveUserPrefs(original);
        readBack = jsonUserPrefsStorage.readUserPrefs().get();
        assertEquals(original, readBack);
    }

    @Test
    public void readUserPrefs_nullFilePath_throwsNullPointerException() throws DataConversionException {
        thrown.expect(NullPointerException.class);
        readUserPrefs(null);
    }

    private Optional<UserPrefs> readUserPrefs(String userPrefsFileInTestDataFolder) throws DataConversionException {
        String prefsFilePath = addToTestDataPathIfNotNull(userPrefsFileInTestDataFolder);
        return new JsonUserPrefsStorage(prefsFilePath).readUserPrefs(prefsFilePath);
    }

    @Test
    public void readUserPrefs_missingFile_emptyResult() throws DataConversionException {
        assertFalse(readUserPrefs("NonExistentFile.json").isPresent());
    }
}
```
###### \java\seedu\address\TestApp.java
``` java
    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage, 1);
    }
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   " + DOB_DESC_AMY + " " + REMARK_DESC_AMY + " "
                + USERNAME_AMY
                + TAG_DESC_FRIEND + " ";
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + REMARK_DESC_AMY
                + TAG_DESC_FRIEND;
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + DOB_DESC_AMY + REMARK_DESC_AMY
                + " " + PREFIX_TAG.getPrefix() + "friends";
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withDateOfBirth(VALID_DOB_AMY)
                .withUsername(VALID_USERNAME_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + DOB_DESC_AMY + USERNAME_AMY + REMARK_DESC_AMY
                + TAG_DESC_FRIEND;
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withDateOfBirth(VALID_DOB_AMY)
                .withUsername(VALID_USERNAME_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + USERNAME_AMY + REMARK_DESC_AMY
                + TAG_DESC_FRIEND;
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_AMY).withDateOfBirth(VALID_DOB_AMY)
                .withUsername(VALID_USERNAME_AMY)
                .withTags(VALID_TAG_FRIEND).build();

        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + USERNAME_AMY + REMARK_DESC_AMY
                + TAG_DESC_FRIEND;
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_BOB).withDateOfBirth(VALID_DOB_AMY).withUsername(VALID_USERNAME_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_BOB + DOB_DESC_AMY + USERNAME_AMY + REMARK_DESC_AMY
                + TAG_DESC_FRIEND;
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + NAME_DESC_BOB + DOB_DESC_BOB + REMARK_DESC_BOB + USERNAME_BOB
                + TAG_DESC_HUSBAND + EMAIL_DESC_BOB;
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + REMARK_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + REMARK_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_EMAIL_DESC
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + REMARK_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + REMARK_DESC_AMY
                + INVALID_TAG_DESC;
```
###### \java\systemtests\RemarkCommandSystemTest.java
``` java
import static seedu.address.logic.commands.RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.testutil.TestUtil.getPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;


public class RemarkCommandSystemTest extends AddressBookSystemTest {


    private static final String MESSAGE_INVALID_REMARK_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

    @Test
    public void addRemarkTests() {
        /* ----------------- Performing photo operation while an unfiltered list is being shown -------------------- */

        /* Case: Add photo to the first person in the list, command with leading spaces and
          trailing spaces -> deleted */

        String remark = "CS2101/SEC/1";

        Model expectedModel = getModel();
        String command = RemarkCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + " " + PREFIX_REMARK + remark;

        ReadOnlyPerson person = getTargetPerson(expectedModel, INDEX_FIRST_PERSON, remark);
        String expectedResultMessage = String.format(MESSAGE_ADD_REMARK_SUCCESS, person);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);


        /* --------------------------------- Performing invalid remark operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = RemarkCommand.COMMAND_WORD + " -1 " + PREFIX_REMARK + remark;
        assertCommandFailure(command, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: missing fileds -> rejected */
        command = RemarkCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + " " + PREFIX_REMARK + "//";
        assertCommandFailure(command, MESSAGE_INVALID_REMARK_COMMAND_FORMAT);
    }

    /**
     * Adds remark to the {@code ReadOnlyPerson} at the specified {@code index} in {@code model}'s address book.
     * @return the person with the remark added
     */
    private ReadOnlyPerson getTargetPerson(Model model, Index index, String remark) {
        ReadOnlyPerson targetPerson = getPerson(model, index);

        model.addRemarkPerson(targetPerson, remark, index);

        return targetPerson;
    }
    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
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

    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see PhotoCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }


        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }


}
```
