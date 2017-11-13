# syy94
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // multiple fields - all accepted
        Person expectedPersonMultipleFields = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withGroup(VALID_GROUP_SAVING)
                .withTags(VALID_TAG_FRIEND).withFields(VALID_FIELD_SCHOOL, VALID_FIELD_COMPANY).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + GROUP_DESC_SAVING
                        + TAG_DESC_FRIEND + FIELD_DESC_SCHOOL + FIELD_DESC_COMPANY,
                new AddCommand(expectedPersonMultipleFields));
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        // add tags
        userInput = targetIndex.getOneBased() + TAG_ADD_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withToAddTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remove tags
        userInput = targetIndex.getOneBased() + TAG_REMOVE_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withToRemoveTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // clear
        userInput = targetIndex.getOneBased() + " " + PREFIX_CLEAR_TAG.getPrefix();
        descriptor = new EditPersonDescriptorBuilder().clearTags(true).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // custom field
        userInput = targetIndex.getOneBased() + FIELD_DESC_COMPANY;
        descriptor = new EditPersonDescriptorBuilder().withFields(VALID_FIELD_COMPANY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
```
###### \java\seedu\address\logic\parser\PasswordCommandParserTest.java
``` java
public class PasswordCommandParserTest {
    private PasswordCommandParser parser = new PasswordCommandParser();

    @Test
    public void parse_missingPrefix() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PasswordCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_addPassModeTest() {
        // valid values test
        PasswordCommand expectedCommand = new PasswordCommand(new PasswordCommand.SetPass(PASSWORD_SAMPLE));
        String userInput = " " + PREFIX_PASS + PASSWORD_SAMPLE;

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_changePassModeTest() {
        PasswordCommand expectedCommand = new PasswordCommand(new PasswordCommand.ChangePass(PASSWORD_SAMPLE,
                PASSWORD_NEW_SAMPLE));
        String userInput = " " + PREFIX_PASS + PASSWORD_SAMPLE + " " + PREFIX_NEW_PASS + PASSWORD_NEW_SAMPLE;

        assertParseSuccess(parser, userInput, expectedCommand);

        // missing PREFIX_PASS
        userInput = " " + PREFIX_NEW_PASS + PASSWORD_NEW_SAMPLE;
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PasswordCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_removePassModeTest() {
        PasswordCommand expectedCommand = new PasswordCommand(new PasswordCommand.ClearPass(PASSWORD_SAMPLE));
        String userInput = " " + PREFIX_PASS + PASSWORD_SAMPLE + " " + PREFIX_CLEAR_PASS;

        assertParseSuccess(parser, userInput, expectedCommand);

        // missing PREFIX_PASS
        userInput = " " + PREFIX_CLEAR_PASS;
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PasswordCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\UnlockApplicationTest.java
``` java
    private static final String testPass = "testPass";

    private Model model = new ModelManager();
    private Logic logic = new LogicManager(model);

    @BeforeClass
    public static void setup() throws IOException, NoSuchAlgorithmException {
        SecurityManager.savePass(testPass);
    }

    @Test
    public void applicationUnlockTest() {
        assertCommandException("test1", PasswordCommand.MESSAGE_WRONG_PASS);
        assertCommandSuccess(testPass, "Welcome", model);
    }

    @AfterClass
    public static void cleanUp() throws NoSuchAlgorithmException, IOException, WrongPasswordException {
        SecurityManager.removePass(testPass);
    }
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withFields(String... fields) {
        try {
            descriptor.setFieldsList(ParserUtil.parseCustomFields(Arrays.asList(fields)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("fields are expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} to be added into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withToAddTags(String... tags) {
        try {
            descriptor.setToAdd(ParserUtil.parseTags(Arrays.asList(tags)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} to be removed into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withToRemoveTags(String... tags) {
        try {
            descriptor.setToRemove(ParserUtil.parseTags(Arrays.asList(tags)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    /**
     * Set whether that the tags should be cleared first.
     */
    public EditPersonDescriptorBuilder clearTags(boolean shouldClear) {
        descriptor.setClearTags(shouldClear);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withFields(String ... fields) {
        try {
            this.person.setFields(SampleDataUtil.getFieldsSet(fields));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("field is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\testutil\PersonUtil.java
``` java
        person.getFields().stream().forEach(
            s -> sb.append(PREFIX_CUSTOM_FIELD + s.toString() + " ")
        );
```
###### \java\seedu\address\testutil\PersonUtil.java
``` java
    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetailsForEdit(ReadOnlyPerson person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_GROUP + person.getGroup().groupName + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_ADD_TAG + s.tagName + " ")
        );
        person.getFields().stream().forEach(
            s -> sb.append(PREFIX_CUSTOM_FIELD + s.toString() + " ")
        );
        return sb.toString();
    }
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_CLEAR_TAG.getPrefix();
        editedPerson = new PersonBuilder(personToEdit).withTags().build();
        assertCommandSuccess(command, index, editedPerson);

        /* Case: checks that edit tag prefix precedence is respected -> tags cleared */
        index = INDEX_FIRST_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TAG_REMOVE_DESC_FRIEND + TAG_ADD_DESC_FRIEND
                + " " + PREFIX_CLEAR_TAG.getPrefix();
        editedPerson = new PersonBuilder(personToEdit).withTags().build();
        assertCommandSuccess(command, index, editedPerson);
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /* Case: find field value of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_CUSTOM_FIELD + VALID_FIELD_FIND_DANIEL_VALUE;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find field key of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_CUSTOM_FIELD + VALID_FIELD_FIND_DANIEL_KEY;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find field key and value of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_CUSTOM_FIELD + VALID_FIELD_DANIEL;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
```
###### \java\systemtests\PasswordCommandSystemTest.java
``` java
public class PasswordCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void passwordTest() {
        //Clear Password without pwd/ prefix
        String command = PasswordCommand.COMMAND_WORD + " " + PREFIX_CLEAR_PASS + PASSWORD_SAMPLE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, PasswordCommand.MESSAGE_USAGE));

        //Edit Password without pwd/ prefix
        command = PasswordCommand.COMMAND_WORD + " " + PREFIX_NEW_PASS + PASSWORD_SAMPLE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, PasswordCommand.MESSAGE_USAGE));

        //Set Password Success
        command = PasswordCommand.COMMAND_WORD + " " + PREFIX_PASS + PASSWORD_SAMPLE;
        assertCommandSuccess(command, PasswordCommand.MESSAGE_SET_PASS);

        //Set Password again
        command = PasswordCommand.COMMAND_WORD + " " + PREFIX_PASS + PASSWORD_SAMPLE;
        assertCommandFailure(command, PasswordCommand.MESSAGE_PASS_EXISTS);

        //Edit Password
        command = PasswordCommand.COMMAND_WORD + " " + PREFIX_PASS + PASSWORD_SAMPLE + " " + PREFIX_NEW_PASS
                + PASSWORD_NEW_SAMPLE;
        assertCommandSuccess(command, PasswordCommand.MESSAGE_CHANGED_PASS);

        //Edit Password back to PASSWORD_SAMPLE for convenience.
        command = PasswordCommand.COMMAND_WORD + " " + PREFIX_PASS + PASSWORD_NEW_SAMPLE + " " + PREFIX_NEW_PASS
                + PASSWORD_SAMPLE;
        assertCommandSuccess(command, PasswordCommand.MESSAGE_CHANGED_PASS);

        //Edit Password with wrong existing password
        command = PasswordCommand.COMMAND_WORD + " " + PREFIX_PASS + PASSWORD_NEW_SAMPLE + " " + PREFIX_NEW_PASS
                + PASSWORD_SAMPLE;
        assertCommandFailure(command, PasswordCommand.MESSAGE_WRONG_PASS
                + "\n" + PasswordCommand.MESSAGE_PASS_NOT_CHANGED);

        //Remove Password with wrong existing password
        command = PasswordCommand.COMMAND_WORD + " " + PREFIX_PASS + PASSWORD_NEW_SAMPLE + " " + PREFIX_CLEAR_PASS;
        assertCommandFailure(command, PasswordCommand.MESSAGE_WRONG_PASS);

        //Remove Password Success
        command = PasswordCommand.COMMAND_WORD + " " + PREFIX_PASS + PASSWORD_SAMPLE + " " + PREFIX_CLEAR_PASS;
        assertCommandSuccess(command, PasswordCommand.MESSAGE_CLEARED_PASS);

        //No existing Password to remove
        command = PasswordCommand.COMMAND_WORD + " " + PREFIX_PASS + PASSWORD_SAMPLE + " " + PREFIX_CLEAR_PASS;
        assertCommandFailure(command, PasswordCommand.MESSAGE_NO_PASS_TO_CLEAR);

        //No existing Password to change
        command = PasswordCommand.COMMAND_WORD + " " + PREFIX_PASS + PASSWORD_SAMPLE + " " + PREFIX_NEW_PASS
                + PASSWORD_NEW_SAMPLE;
        assertCommandFailure(command, PasswordCommand.MESSAGE_NO_PASS_TO_CHANGE);
    }

    private void createPasswordForUnlockTest() {
        String command = PasswordCommand.COMMAND_WORD + " " + PREFIX_PASS + PASSWORD_SAMPLE;
        assertCommandSuccess(command, PasswordCommand.MESSAGE_SET_PASS);
    }

    private void assertCommandSuccess(String command, String expectedCommandMessage) {
        assertCommandSuccess(command, expectedCommandMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status remains unchanged.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, String expectedCommandMessage, Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedCommandMessage, getModel());
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchanged();
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
     *
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
