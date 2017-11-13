# hengyu95
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java


        // missing birthdate - using command word
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmailAddress(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withPhoto(IMAGE_STORAGE_AMY)
                .withBirthdate(NOT_FILLED).withTags().build();

        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                 + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + PHOTO_DESC_AMY, new AddCommand(expectedPerson));


        // missing birthdate - using command alias
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + PHOTO_DESC_AMY, new AddCommand(expectedPerson));
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java

        // invalid birthdate - using command word
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + INVALID_BIRTHDATE_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Birthdate.MESSAGE_BIRTHDATE_CONSTRAINTS);

        // invalid birthdate - using command alias
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + INVALID_BIRTHDATE_DESC
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Birthdate.MESSAGE_BIRTHDATE_CONSTRAINTS);
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_insta() throws Exception {
        //Using command word
        InstaCommand command = (InstaCommand) parser.parseCommand(
                InstaCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new InstaCommand(INDEX_FIRST_PERSON), command);

        //Using command alias
        command = (InstaCommand) parser.parseCommand(
                InstaCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new InstaCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_backup() throws Exception {
        //Using command word
        assertTrue(parser.parseCommand(BackupCommand.COMMAND_WORD) instanceof BackupCommand);
        assertTrue(parser.parseCommand(BackupCommand.COMMAND_WORD + " 3") instanceof BackupCommand);

        //Using command alias
        assertTrue(parser.parseCommand(BackupCommand.COMMAND_ALIAS) instanceof BackupCommand);
        assertTrue(parser.parseCommand(BackupCommand.COMMAND_ALIAS + " 3") instanceof BackupCommand);

    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    public PersonBuilder withBirthdate(String birthdate) {
        try {
            this.person.setBirthdate(new Birthdate(birthdate));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("birthdate is expected.");
        }
        return this;
    }

    /**
     * Sets the {@code UserID} of the {@code Person} that we are building.
     */

    public PersonBuilder withUserId(String id) {
        try {
            this.person.setUserId(new UserId(id));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("id is expected.");
        }
        return this;
    }
```
###### \java\seedu\address\ui\LeftDisplayPanelTest.java
``` java
    @Test
    public void toggleTabs() throws Exception {
        //Start from Tab 1
        assertEquals(0, leftDisplayPanelHandle.getSelectedTabIndex());

        //Toggling once goes to Tab 2
        leftDisplayPanel.toggleTabs(-1);
        assertEquals(1, leftDisplayPanelHandle.getSelectedTabIndex());

        //Toggling once goes to Tab 3
        leftDisplayPanel.toggleTabs(-1);
        assertEquals(2, leftDisplayPanelHandle.getSelectedTabIndex());

    }
}
```
###### \java\systemtests\InstaCommandSystemTest.java
``` java

public class InstaCommandSystemTest extends AddressBookSystemTest {

    @BeforeClass
    public static void setup() throws Exception {
        ImageInit.checkDirectories();
        ImageInit.initPictures();
    }

    @AfterClass
    public static void recovery() throws Exception {
        ImageInit.deleteEditedFiles();
        ImageInit.deleteImagesFiles();
    }

    @Test
    public void insta() {
        /* Case: select the first card in the person list, command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + InstaCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + "   ";
        assertCommandSuccess(command, ALICE.getName().fullName, INDEX_FIRST_PERSON);

        /* Case: select the last card in the person list -> selected */
        Index personCount = Index.fromOneBased(getTypicalPersons().size());
        command = InstaCommand.COMMAND_WORD + " " + personCount.getOneBased();
        assertCommandSuccess(command, GEORGE.getName().fullName, personCount);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: select the middle card in the person list -> selected */
        Index middleIndex = Index.fromOneBased(personCount.getOneBased() / 2);
        command = InstaCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        assertCommandSuccess(command, CARL.getName().fullName, middleIndex);

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredPersonList().size() + 1;
        assertCommandFailure(InstaCommand.COMMAND_WORD + " " + invalidIndex,
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: select the current selected card -> selected */
        assertCommandSuccess(command, CARL.getName().fullName, middleIndex);

        /* Case: filtered person list, select index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        invalidIndex = getModel().getAddressBook().getPersonList().size();
        assertCommandFailure(InstaCommand.COMMAND_WORD + " " + invalidIndex,
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: filtered person list, select index within bounds of address book and person list -> selected */
        Index validIndex = Index.fromOneBased(1);
        assert validIndex.getZeroBased() < getModel().getFilteredPersonList().size();
        command = InstaCommand.COMMAND_WORD + " " + validIndex.getOneBased();
        assertCommandSuccess(command, BENSON.getName().fullName, validIndex);

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(InstaCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, InstaCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(InstaCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, InstaCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(InstaCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, InstaCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(InstaCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, InstaCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("inStA 1", MESSAGE_UNKNOWN_COMMAND);

        /* Case: select from empty address book -> rejected */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        assertCommandFailure(InstaCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays the success message of executing select command with the {@code expectedSelectedCardIndex}
     * of the selected person, and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar remain unchanged. The resulting
     * browser url and selected card will be verified if the current selected card and the card at
     * {@code expectedSelectedCardIndex} are different.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, String expectedPerson, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = (new StringBuilder()
                .append(String.format(MESSAGE_SELECT_PERSON_SUCCESS, expectedPerson,
                        expectedSelectedCardIndex.getOneBased()))
                .append(MESSAGE_SELECT_PERSON_SUCCESS3).toString());
        int preExecutionSelectedCardIndex = getPersonListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
            assertSelectedCardUnchanged();
        } else {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
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
