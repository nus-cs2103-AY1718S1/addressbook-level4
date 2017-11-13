# kengying
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public List<Tag> getTagList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public List<Group> getGroupList() {
            fail("This method should not be called.");
            return null;
        }
```
###### \java\seedu\address\logic\commands\ListCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_listAll_everPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        ListCommand command = prepareCommand("all");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    /**
     * Parses {@code userInput} into a {@code ListCommand}.
     */
    private ListCommand prepareCommand(String userInput) {
        ListCommand command = new ListCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(ListCommand command, String expectedMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

}
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // invalid field
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + GROUP_DESC_SAVING + INVALID_FIELD_DESC
                + VALID_TAG_FRIEND, CustomField.MESSAGE_FIELD_CONSTRAINTS);

        // invalid group
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_GROUP_DESC + VALID_TAG_FRIEND, Group.MESSAGE_GROUP_CONSTRAINTS);
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " all") instanceof ListCommand);
    }

    @Test
    public void parseCommandAlias_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " all") instanceof ListCommand);
    }
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        // add tags
        userInput = targetIndex.getOneBased() + GROUP_DESC_HEALTH;
        descriptor = new EditPersonDescriptorBuilder().withGroup(VALID_GROUP_HEALTH).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
```
###### \java\seedu\address\logic\parser\ListCommandParserTest.java
``` java
public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        ListCommand expectedListCommand = new ListCommand();

        assertParseSuccess(parser, "all", expectedListCommand);
        assertParseSuccess(parser, "tags", expectedListCommand);
        assertParseSuccess(parser, "groups", expectedListCommand);

    }

    @Test
    public void parse_invalidArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        ListCommand expectedListCommand = new ListCommand();

        assertParseFailure(parser, "random", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "all tags", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));

    }

}
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
    @Test
    public void getGroupList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getGroupList().remove(0);
    }
```
###### \java\seedu\address\model\UniqueGroupListTest.java
``` java
public class UniqueGroupListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueGroupList uniqueGroupList = new UniqueGroupList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueGroupList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Group} of the {@code EditPersonDescriptorBuilder} that we are building.
     */
    public EditPersonDescriptorBuilder withGroup(String group) {
        try {
            ParserUtil.parseGroup(Optional.of(group)).ifPresent(descriptor::setGroup);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("group is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Group} of the {@code Person} that we are building.
     */
    public PersonBuilder withGroup(String group) {
        try {
            this.person.setGroup(new Group(group));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("group is expected to be unique.");
        }
        return this;
    }
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
        /* Case: invalid group -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_GROUP_DESC,
                Group.MESSAGE_GROUP_CONSTRAINTS);
```
