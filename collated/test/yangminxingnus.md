# yangminxingnus
###### /java/seedu/address/logic/commands/RemarkCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
  * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_addRemark_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("Remark").build();

        RemarkCommand remarkCommand = new RemarkCommand(0, editedPerson.getRemark());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertEquals(editedPerson, expectedModel.getFilteredPersonList().get(0));
    }

}
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_remark() throws Exception {
        final Remark remark = new Remark("CS2101/SEC/1");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
            + 1 + " " + PREFIX_REMARK + " " + "CS2101/SEC/1");
        assertEquals(new RemarkCommand(1, remark), command);
    }

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }
```
###### /java/seedu/address/logic/parser/RemarkCommandParserTest.java
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
###### /java/seedu/address/storage/AccountStorageTest.java
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
###### /java/seedu/address/storage/JsonAccountsStorageTest.java
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
###### /java/systemtests/AddCommandSystemTest.java
``` java
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   " + DOB_DESC_AMY + " " + REMARK_DESC_AMY + " "
                + USERNAME_AMY
                + TAG_DESC_FRIEND + " ";
```
###### /java/systemtests/AddCommandSystemTest.java
``` java
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + REMARK_DESC_AMY
                + TAG_DESC_FRIEND;
```
###### /java/systemtests/AddCommandSystemTest.java
``` java
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + DOB_DESC_AMY + REMARK_DESC_AMY
                + " " + PREFIX_TAG.getPrefix() + "friends";
```
###### /java/systemtests/AddCommandSystemTest.java
``` java
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withDateOfBirth(VALID_DOB_AMY)
                .withUsername(VALID_USERNAME_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + DOB_DESC_AMY + USERNAME_AMY + REMARK_DESC_AMY
                + TAG_DESC_FRIEND;
```
###### /java/systemtests/AddCommandSystemTest.java
``` java
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withDateOfBirth(VALID_DOB_AMY)
                .withUsername(VALID_USERNAME_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + USERNAME_AMY + REMARK_DESC_AMY
                + TAG_DESC_FRIEND;
```
###### /java/systemtests/AddCommandSystemTest.java
``` java
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_AMY).withDateOfBirth(VALID_DOB_AMY)
                .withUsername(VALID_USERNAME_AMY)
                .withTags(VALID_TAG_FRIEND).build();

        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + USERNAME_AMY + REMARK_DESC_AMY
                + TAG_DESC_FRIEND;
```
###### /java/systemtests/AddCommandSystemTest.java
``` java
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_BOB).withDateOfBirth(VALID_DOB_AMY).withUsername(VALID_USERNAME_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_BOB + DOB_DESC_AMY + USERNAME_AMY + REMARK_DESC_AMY
                + TAG_DESC_FRIEND;
```
###### /java/systemtests/AddCommandSystemTest.java
``` java
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + NAME_DESC_BOB + DOB_DESC_BOB + REMARK_DESC_BOB + USERNAME_BOB
                + TAG_DESC_HUSBAND + EMAIL_DESC_BOB;
```
###### /java/systemtests/AddCommandSystemTest.java
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
