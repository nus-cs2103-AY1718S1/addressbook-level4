# nadhira15
###### \java\seedu\address\logic\commands\AddTagCommandTest.java
``` java
public class AddTagCommandTest {

    public static final String VALID_TAG = "neighbours";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unfilteredList_success() throws Exception {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        ReadOnlyPerson lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());
        Person editedPerson = new PersonBuilder(lastPerson).withTags("neighbours").build();
        Set<Tag> tag = ParserUtil.parseTags(Arrays.asList(VALID_TAG));
        AddTagCommand addTagCommand = prepareCommand(indexLastPerson, tag);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(lastPerson, editedPerson);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withTags(VALID_TAG).build();
        Set<Tag> tag = ParserUtil.parseTags(Arrays.asList(VALID_TAG));
        AddTagCommand addTagCommand = prepareCommand(INDEX_FIRST_PERSON, tag);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTagUnfilteredList_failure() {
        Person firstPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        Set<Tag> tag = firstPerson.getTags();

        AddTagCommand addTagCommand = prepareCommand(INDEX_FIRST_PERSON, tag);

        assertCommandFailure(addTagCommand, model, AddTagCommand.MESSAGE_DUPLICATE_TAG);
    }

    @Test
    public void execute_duplicateTagFilteredList_failure() {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Set<Tag> tag = personInList.getTags();
        
        AddTagCommand addTagCommand = prepareCommand(INDEX_FIRST_PERSON, tag);

        assertCommandFailure(addTagCommand, model, AddTagCommand.MESSAGE_DUPLICATE_TAG);
    }
    
    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Set<Tag> tag = ParserUtil.parseTags(Arrays.asList(VALID_TAG));

        AddTagCommand addTagCommand = prepareCommand(outOfBoundIndex, tag);

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        Set<Tag> tag = ParserUtil.parseTags(Arrays.asList(VALID_TAG));

        AddTagCommand addTagCommand = prepareCommand(outOfBoundIndex, tag);

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        Set<Tag> tag = ParserUtil.parseTags(Arrays.asList(VALID_TAG));
        Set<Tag> otherTag = ParserUtil.parseTags(Arrays.asList("husband"));
        final AddTagCommand standardCommand = new AddTagCommand(INDEX_FIRST_PERSON, tag);

        // same values -> returns true
        Set<Tag> copyTag = tag;
        AddTagCommand commandWithSameValues = new AddTagCommand(INDEX_FIRST_PERSON, copyTag);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(INDEX_SECOND_PERSON, tag)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(INDEX_FIRST_PERSON, otherTag)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private AddTagCommand prepareCommand(Index index, Set<Tag> tagToAdd) {
        AddTagCommand addTagCommand = new AddTagCommand(index, tagToAdd);
        addTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addTagCommand;
    }
}
```
###### \java\seedu\address\logic\commands\DeleteTagCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for DeleteTagCommand.
 */

public class DeleteTagCommandTest {

    public static final String VALID_TAG = "friends";
    public static final String VALID_TAG_2 = "neighbours";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unfilteredList_success() throws Exception {
        Index indexFirstPerson = INDEX_FIRST_PERSON;
        ReadOnlyPerson firstPerson = model.getFilteredPersonList().get(indexFirstPerson.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withTags("friends").build();
        Set<Tag> tag = ParserUtil.parseTags(Arrays.asList(VALID_TAG));
        DeleteTagCommand deleteTagCommand = prepareCommand(indexFirstPerson, tag);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withTags(VALID_TAG).build();
        Set<Tag> tag = ParserUtil.parseTags(Arrays.asList(VALID_TAG));
        DeleteTagCommand deleteTagCommand = prepareCommand(INDEX_FIRST_PERSON, tag);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTagUnfilteredList_failure() throws Exception {
        Index indexFirstPerson = INDEX_FIRST_PERSON;
        Set<Tag> tag = ParserUtil.parseTags(Arrays.asList(VALID_TAG_2));
        DeleteTagCommand deleteTagCommand = prepareCommand(indexFirstPerson, tag);

        assertCommandFailure(deleteTagCommand, model, DeleteTagCommand.MESSAGE_NOT_EXIST_TAG);
    }

    @Test
    public void execute_invalidTagFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Set<Tag> tag = ParserUtil.parseTags(Arrays.asList(VALID_TAG_2));
        DeleteTagCommand deleteTagCommand = prepareCommand(INDEX_FIRST_PERSON, tag);

        assertCommandFailure(deleteTagCommand, model, DeleteTagCommand.MESSAGE_NOT_EXIST_TAG);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Set<Tag> tag = ParserUtil.parseTags(Arrays.asList(VALID_TAG));

        DeleteTagCommand deleteTagCommand = prepareCommand(outOfBoundIndex, tag);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        Set<Tag> tag = ParserUtil.parseTags(Arrays.asList(VALID_TAG));

        DeleteTagCommand deleteTagCommand = prepareCommand(outOfBoundIndex, tag);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        Set<Tag> tag = ParserUtil.parseTags(Arrays.asList(VALID_TAG));
        Set<Tag> otherTag = ParserUtil.parseTags(Arrays.asList("husband"));
        final DeleteTagCommand standardCommand = new DeleteTagCommand(INDEX_FIRST_PERSON, tag);

        // same values -> returns true
        Set<Tag> copyTag = tag;
        DeleteTagCommand commandWithSameValues = new DeleteTagCommand(INDEX_FIRST_PERSON, copyTag);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(INDEX_SECOND_PERSON, tag)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(INDEX_FIRST_PERSON, otherTag)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private DeleteTagCommand prepareCommand(Index index, Set<Tag> tagToDelete) {
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(index, tagToDelete);
        deleteTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTagCommand;
    }
}
```
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
    @Test
    public void execute_findByPhone() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommandForPhone("85355255");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE));

        String secondExpectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand secondCommand = prepareCommandForPhone("11111111");
        assertCommandSuccess(secondCommand, secondExpectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_findByEmail() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommandForEmail("cornelia@example.com");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(DANIEL));

        String secondExpectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand secondCommand = prepareCommandForEmail("example@example.com");
        assertCommandSuccess(secondCommand, secondExpectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_findByAddress() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommandForAddress("street");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, DANIEL, GEORGE));

        String secondExpectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand secondCommand = prepareCommandForAddress("london");
        assertCommandSuccess(secondCommand, secondExpectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_findByBirthday() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        FindCommand command = prepareCommandForBirthday("01/01/1991");
        assertCommandSuccess(command, expectedMessage,
                Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));

        String secondExpectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand secondCommand = prepareCommandForBirthday("31/12/2017");
        assertCommandSuccess(secondCommand, secondExpectedMessage, Collections.emptyList());
    }
```
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
    /**
     * Parses {@code userInput} into a {@code FindCommand} for phone prefix.
     */
    private FindCommand prepareCommandForPhone(String userInput) {
        FindCommand command =
                new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand} for email prefix.
     */
    private FindCommand prepareCommandForEmail(String userInput) {
        FindCommand command =
                new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand} for address prefix.
     */
    private FindCommand prepareCommandForAddress(String userInput) {
        FindCommand command =
                new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand} for birthday prefix.
     */
    private FindCommand prepareCommandForBirthday(String userInput) {
        FindCommand command =
                new FindCommand(new BirthdayContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
    @Test
    public void parse_commandAlias() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withBirthday(VALID_BIRTHDAY_BOB)
                .withAvatar(VALID_AVATAR_BOB).withTags(VALID_TAG_FRIEND).build();

        // complete input, success
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB + AVATAR_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix, failure
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + VALID_NAME_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB + AVATAR_DESC_BOB, expectedMessage);

        // invalid name, failure
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB + AVATAR_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_NAME_CONSTRAINTS);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_addTag() throws Exception {
        List<String> tagList = Arrays.asList("neighbours");
        Set<Tag> tagToAdd = ParserUtil.parseTags(tagList);
        AddTagCommand command = (AddTagCommand) parser.parseCommand(
                AddTagCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + tagList.stream().collect(Collectors.joining(" ")));
        assertEquals(new AddTagCommand(INDEX_FIRST_PERSON, tagToAdd), command);
    }

    @Test
    public void parseCommand_deleteTag() throws Exception {
        List<String> tagList = Arrays.asList("neighbours");
        Set<Tag> tagToDelete = ParserUtil.parseTags(tagList);
        DeleteTagCommand command = (DeleteTagCommand) parser.parseCommand(
                DeleteTagCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                        + tagList.stream().collect(Collectors.joining(" ")));
        assertEquals(new DeleteTagCommand(INDEX_FIRST_PERSON, tagToDelete), command);
    }
```
###### \java\seedu\address\logic\parser\AddTagCommandParserTest.java
``` java
public class AddTagCommandParserTest {
    public static final String VALID_TAG = "neighbours";
    public static final String VALID_TAG_2 = "friends";
    public static final String INVALID_TAG = "hubby*";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE);

    private AddTagCommandParser parser = new AddTagCommandParser();

    @Test
    public void parse_missingFieldSpecified_failure() throws Exception {
        // no index and tag specified
        assertParseFailure(parser, null , MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, " ", MESSAGE_INVALID_FORMAT);

        // no index specified
        assertParseFailure(parser, VALID_TAG , MESSAGE_INVALID_FORMAT);

        // no tag specified
        assertParseFailure(parser, "1 " , MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_wrongFieldTypeSpecified_failure() throws Exception {
        // both tags
        assertParseFailure(parser, VALID_TAG + " " + VALID_TAG, MESSAGE_INVALID_FORMAT);

        // invalid index
        assertParseFailure(parser, "-3 " + VALID_TAG, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "0 " + VALID_TAG, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, VALID_TAG + " " + VALID_TAG, MESSAGE_INVALID_FORMAT);

        // invalid tag
        assertParseFailure(parser, "1 " + INVALID_TAG, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_wrongOrderSpecified_failure() throws Exception {
        // specifying tag before index
        assertParseFailure(parser, VALID_TAG + " 1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgument_success() throws Exception {
        Index index = INDEX_FIRST_PERSON;
        Set<Tag> tagToAdd = ParserUtil.parseTags(Arrays.asList(VALID_TAG));
        AddTagCommand expectedCommand = new AddTagCommand(index, tagToAdd);
        assertParseSuccess(parser, "1 " + VALID_TAG, expectedCommand);

        Set<Tag> twoTagsToAdd = ParserUtil.parseTags(Arrays.asList(VALID_TAG, VALID_TAG_2));
        AddTagCommand secondExpectedCommand = new AddTagCommand(index, twoTagsToAdd);
        assertParseSuccess(parser, "1 " + VALID_TAG + " " + VALID_TAG_2, secondExpectedCommand);
    }
}
```
###### \java\seedu\address\logic\parser\DeleteTagCommandParserTest.java
``` java
public class DeleteTagCommandParserTest {
    public static final String VALID_TAG = "neighbours";
    public static final String VALID_TAG_2 = "friends";
    public static final String INVALID_TAG = "hubby*";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE);

    private DeleteTagCommandParser parser = new DeleteTagCommandParser();

    @Test
    public void parse_missingFieldSpecified_failure() throws Exception {
        // no index and tag specified
        assertParseFailure(parser, null , MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, " ", MESSAGE_INVALID_FORMAT);

        // no index specified
        assertParseFailure(parser, VALID_TAG , MESSAGE_INVALID_FORMAT);

        // no tag specified
        assertParseFailure(parser, "1 " , MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_wrongFieldTypeSpecified_failure() throws Exception {
        // both tags
        assertParseFailure(parser, VALID_TAG + " " + VALID_TAG, MESSAGE_INVALID_FORMAT);

        // invalid index
        assertParseFailure(parser, "-3 " + VALID_TAG, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "0 " + VALID_TAG, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, VALID_TAG + " " + VALID_TAG, MESSAGE_INVALID_FORMAT);

        // invalid tag
        assertParseFailure(parser, "1 " + INVALID_TAG, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_wrongOrderSpecified_failure() throws Exception {
        // specifying tag before index
        assertParseFailure(parser, VALID_TAG + " 1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgument_success() throws Exception {
        Index index = INDEX_FIRST_PERSON;
        Set<Tag> tagToDelete = ParserUtil.parseTags(Arrays.asList(VALID_TAG));
        DeleteTagCommand expectedCommand = new DeleteTagCommand(index, tagToDelete);
        assertParseSuccess(parser, "1 " + VALID_TAG, expectedCommand);

        Set<Tag> tagsToDelete = ParserUtil.parseTags(Arrays.asList(VALID_TAG, VALID_TAG_2));
        DeleteTagCommand secondExpectedCommand = new DeleteTagCommand(index, tagsToDelete);
        assertParseSuccess(parser, "1 " + VALID_TAG + " " + VALID_TAG_2, secondExpectedCommand);
    }
}
```
###### \java\seedu\address\logic\parser\FindCommandParserTest.java
``` java
    @Test
    public void parse_validPhoneArgs_returnsFindCommand() {
        FindCommand expectedFindCommandForPhone =
                new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("85355251")));
        assertParseSuccess(parser, "p/ 85355251", expectedFindCommandForPhone);
    }

    @Test
    public void parse_validEmailArgs_returnsFindCommand() {
        FindCommand expectedFindCommandForEmail =
                new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList("asdf@example.com")));
        assertParseSuccess(parser, "e/ asdf@example.com", expectedFindCommandForEmail);
    }

    @Test
    public void parse_validAddressArgs_returnsFindCommand() {
        FindCommand expectedFindCommandForAddress =
                new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList("clementi")));
        assertParseSuccess(parser, "a/ clementi", expectedFindCommandForAddress);
    }

    @Test
    public void parse_validBirthdayArgs_returnsFindCommand() {
        FindCommand expectedFindCommandForBirthday =
                new FindCommand(new BirthdayContainsKeywordsPredicate(Arrays.asList("01/01/1991")));
        assertParseSuccess(parser, "b/ 01/01/1991", expectedFindCommandForBirthday);
    }
```
###### \java\seedu\address\model\person\BirthdayTest.java
``` java
public class BirthdayTest {

    @Test
    public void isValidBirthday() {
        // invalid birthday
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("912345")); // less than 8 numbers
        assertFalse(Birthday.isValidBirthday("phone")); // non-numeric
        assertFalse(Birthday.isValidBirthday("9011p041")); // alphabets within digits
        assertFalse(Birthday.isValidBirthday("9011@041")); // characters within digits
        assertFalse(Birthday.isValidBirthday("9312 1534")); // spaces within digits
        assertFalse(Birthday.isValidBirthday("123456789123456789")); // more than 8 numbers

        // valid birthday
        assertTrue(Birthday.isValidBirthday("01/01/1991")); // exactly 8 numbers valid birthday
        assertTrue(Birthday.isValidBirthday("14/06/1996")); // exactly 8 numbers valid birthday
        assertTrue(Birthday.isValidBirthday("31/12/2001")); // exactly 8 numbers valid birthday
    }
}
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
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
###### \java\seedu\address\testutil\PersonBuilder.java
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
###### \java\systemtests\FindCommandSystemTest.java
``` java

        /* Case: find person with phone query -> 1 person found */
        command = FindCommand.COMMAND_WORD + " p/ " + CARL.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person with partial phone query -> 1 person found */
        command = FindCommand.COMMAND_WORD + " p/ 3525";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person with partial address query -> 1 person found */
        command = FindCommand.COMMAND_WORD + " a/ wall";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person with email query -> 1 person found */
        command = FindCommand.COMMAND_WORD + " e/ " + CARL.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person with partial email query -> 1 person found */
        command = FindCommand.COMMAND_WORD + " e/ hein";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person with birthday query -> 1 person found */
        command = FindCommand.COMMAND_WORD + " b/ " + CARL.getBirthday().value;
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
        
        /* Case: find person with partial birthday query -> 1 person found */
        command = FindCommand.COMMAND_WORD + " b/ 01/01";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

```
